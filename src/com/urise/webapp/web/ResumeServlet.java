package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getINSTANCE().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (uuid == null || uuid.length() == 0) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (value != null || value.trim().length() != 0 && values.length > 1) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = new ArrayList<>();
                        for (String str : value.split("\r\n")) {
                            if (!str.equals("")) {
                                list.add(str);
                            }
                        }
                        resume.addSection(type, new ListSection(list));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        String typeName = type.name();
                        String[] urls = request.getParameterValues(typeName + "url");
                        List<Organization> organizations = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (name != null && name.trim().length() != 0) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String[] startDates = request.getParameterValues(typeName + i + "startDate");
                                String[] endDates = request.getParameterValues(typeName + i + "endDate");
                                String[] titles = request.getParameterValues(typeName + i + "title");
                                String[] descriptions = request.getParameterValues(typeName + i + "description");
                                for (int j = 0; j < startDates.length; j++) {
                                    if (startDates[j] != null && startDates[j].trim().length() != 0) {
                                        positions.add(new Organization.Position(DateUtil.getLocalDate(startDates[j]),
                                                DateUtil.getLocalDate(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.addSection(type, new OrganizationSection(organizations));
                        break;
                }
            } else {
                resume.getSections().remove(type);
            }
        }
        if (uuid == null || uuid.length() == 0) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                resume.addSection(type, new TextSection(""));
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                resume.addSection(type, new ListSection(""));
                            }
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> organizations = new ArrayList<>();
                            organizations.add(new Organization("", "", new Organization.Position()));
                            if (organizationSection != null) {
                                for (Organization org : organizationSection.getListOrganization()) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    positions.add(new Organization.Position());
                                    positions.addAll(org.getPositions());
                                    organizations.add(new Organization(org.getHomePage(), positions));
                                }
                            }
                            resume.addSection(type, new OrganizationSection(organizations));
                    }
                }
                break;
            case "create":
                resume = new Resume();
                resume.addSection(SectionType.OBJECTIVE, new TextSection(""));
                resume.addSection(SectionType.PERSONAL, new TextSection(""));
                resume.addSection(SectionType.ACHIEVEMENT, new ListSection(""));
                resume.addSection(SectionType.QUALIFICATIONS, new ListSection(""));
                resume.addSection(SectionType.EDUCATION, new OrganizationSection(new Organization("", "", new Organization.Position())));
                resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization("", "", new Organization.Position())));
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}




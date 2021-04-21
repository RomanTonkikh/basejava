package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;


import static com.urise.webapp.model.SectionType.EXPERIENCE;


public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getINSTANCE().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String[] values = request.getParameterValues(type.name());
            if (values != null && values.length != 0) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new TextSection(values[0]));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(Arrays.asList(values)));
                        break;
                    default:
                        return;
                }
            } else {
                resume.getContacts().remove(type);
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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
            case "edit":
                resume = storage.get(uuid);
                break;
            case "create":
                resume = storage.get(UUID.randomUUID().toString());
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
        ).forward(request, response);


//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
//        Writer writer = response.getWriter();
//        writer.write(
//                "<html>\n" +
//                        "<head>\n" +
//                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
//                        "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
//                        "    <title>Список всех резюме</title>\n" +
//                        "</head>\n" +
//                        "<body>\n" +
//                        "<section>\n" +
//                        "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
//                        "    <tr>\n" +
//                        "        <th>Имя</th>\n" +
//                        "        <th>Email</th>\n" +
//                        "    </tr>\n");
//        for (Resume resume : storage.getAllSorted()) {
//            writer.write(
//                    "<tr>\n" +
//                            "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
//                            "     <td>" + resume.getContact(ContactType.EMAIL) + "</td>\n" +
//                            "</tr>\n");
//        }
//        writer.write("</table>\n" +
//                "</section>\n" +
//                "</body>\n" +
//                "</html>\n");
    }
}


//        String uuid = request.getParameter("uuid");
//        PrintWriter responseWriter = response.getWriter();
//        if (uuid == null) {
//            responseWriter.write("<html>" +
//                    "<head>" +
//                    "<table border=\"1\">" +
//                    "<tr>" +
//                    "<th>Uuid</th>" +
//                    "<th>Full name</th>" +
//                    "</tr>");
//
//            for (Resume resume : storage.getAllSorted()) {
//                responseWriter.write("<tr>" +
//                        "<td>" + resume.getUuid() +
//                        "<td>" + resume.getFullName() +
//                        "</td>" +
//                        "</tr>");
//            }
//            responseWriter.write("</table>" +
//                    "</head>" +
//                    "</html>");
//        } else {
//            Resume resume = storage.get(uuid);
//            responseWriter.write("<html>" + resume.toString() + "<br>");
//            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
//                responseWriter.write(entry.getKey().getTitle() + ": " + entry.getValue() + "<br>");
//            }
//            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
//                responseWriter.write(entry.getKey().getTitle() + ": " + entry.getValue() + "<br>");
//            }
//            responseWriter.write("/<html>");
//        }
//
//        request.setAttribute("resumes", storage.getAllSorted());
//        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
//    }




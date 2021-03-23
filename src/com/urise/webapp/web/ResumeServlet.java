package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


public class ResumeServlet extends HttpServlet {

    private final Storage storage = Config.getINSTANCE().getStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        PrintWriter responseWriter = response.getWriter();
        if (uuid == null) {
            responseWriter.write("<html>" +
                    "<head>" +
                    "<table border=\"1\">" +
                    "<tr>" +
                    "<th>Uuid</th>" +
                    "<th>Full name</th>" +
                    "</tr>");

            for (Resume resume : storage.getAllSorted()) {
                responseWriter.write("<tr>" +
                        "<td>" + resume.getUuid() +
                        "<td>" + resume.getFullName() +
                        "</td>" +
                        "</tr>");
            }
            responseWriter.write("</table>" +
                    "</head>" +
                    "</html>");
        } else {
            Resume resume = storage.get(uuid);
            responseWriter.write("<html>" + resume.toString() + "<br>");
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                responseWriter.write(entry.getKey().getTitle() + ": " + entry.getValue() + "<br>");
            }
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                responseWriter.write(entry.getKey().getTitle() + ": " + entry.getValue() + "<br>");
            }
            responseWriter.write("/<html>");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

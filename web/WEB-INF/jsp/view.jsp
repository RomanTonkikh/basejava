<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>

    <%--
    <%
    Resume r1 = (Resume) request.getAttribute("resume");
    request.setAttribute("resum",r1);
    %>
    --%>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<table id="tableView">
    <tr class="view">
        <td colspan="2"><h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">
            <img src="img/pencil.png" width="16" height="16" alt="Edit"></a></h2>
        </td>
    </tr>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <tr class="view">
            <td colspan="2">
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
            </td>
        </tr>
    </c:forEach>

    <tr class="view">
        <td colspan="2">
            <hr>
        </td>
    </tr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
    <jsp:useBean id="sectionEntry"
                 type="java.util.Map.Entry<com.urise.webapp.model.SectionType,
                         com.urise.webapp.model.AbstractSection>"/>
    <c:choose>
    <c:when test="${sectionEntry.key == SectionType.OBJECTIVE || sectionEntry.key == SectionType.PERSONAL}">
        <tr class="view">
            <td colspan="2"><h2>${sectionEntry.key.title}</h2></td>
        </tr>
        <tr class="view">
            <td colspan="2">
                ${sectionEntry.value}
            </td>
        </tr>

    </c:when>
    <c:when test="${sectionEntry.key == SectionType.ACHIEVEMENT || sectionEntry.key == SectionType.QUALIFICATIONS}">
        <tr class="view">
            <td colspan="2">
                <h2>${sectionEntry.key.title}</h2>
            </td>
           </tr>
        <c:set var="listSection" value="<%=((ListSection)sectionEntry.getValue()).getTextList()%>"/>
        <c:forEach var="text" items="${listSection}">
            <tr class="view">
                <td colspan="2">
                    <ul>
                        <li>${text}</li>
                    </ul>
                </td>
            </tr>
        </c:forEach>
    </c:when>
    <c:when test="${sectionEntry.key == SectionType.EXPERIENCE || sectionEntry.key == SectionType.EDUCATION}">
    <tr class="view">
        <td colspan="2"><h2>${sectionEntry.key.title}</h2></td>
    </tr>
    <c:set var="orgSection"
           value="<%=((OrganizationSection)sectionEntry.getValue()).getListOrganization()%>"/>
    <c:forEach var="organization" items="${orgSection}">
    <tr class="view">
        <td colspan="2"><h3>${organization.homePage.link}</h3></td>
    </tr>
    <c:forEach var="position" items="${organization.positions}">
    <tr class="view">
        <td id="date" rowspan="2">${position.date}</td>
        <td><b>${position.title}</b></td>
    </tr>
    <tr class="view">
        <td>${position.description}</td>
        </c:forEach>
        </c:forEach>
        </c:when>
        <c:otherwise>""</c:otherwise>
        </c:choose>
        </c:forEach>
</table>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

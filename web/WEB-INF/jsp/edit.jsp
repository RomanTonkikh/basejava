<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

    <form id="edit" method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>

        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}">
                </dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType,
                         com.urise.webapp.model.AbstractSection>"/>
            <c:choose>
                <c:when test="${sectionEntry.key == SectionType.OBJECTIVE || sectionEntry.key == SectionType.PERSONAL}">
                    <dl>
                        <dt>${sectionEntry.key.title}</dt>
                        <c:set var="textSection" value="<%=((TextSection) resume.getSection(sectionEntry.getKey()))%>"/>
                        <dd><input type="text" name="${sectionEntry.key}"
                                   size=80 value="${textSection.content}"></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionEntry.key == SectionType.ACHIEVEMENT ||
                 sectionEntry.key == SectionType.QUALIFICATIONS}">
                    <c:set var="listSection" value="<%=((ListSection) resume.getSection(sectionEntry.getKey()))%>"/>
                    <c:forEach var="text" items="${listSection.textList}">
                        <dl>
                            <dt>${sectionEntry.key.title}</dt>
                            <dd><input type="text" name="${sectionEntry.key}"
                                       size=80 value="${text}"></dd>
                        </dl>
                    </c:forEach>
                </c:when>
                <c:when test="${sectionEntry.key == SectionType.EXPERIENCE ||
                 sectionEntry.key == SectionType.EDUCATION}"/>

                <c:otherwise></c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
<section>
    <form id="edit" method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input required type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
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
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <c:choose>
                <c:when test="${sectionType.name() == SectionType.OBJECTIVE || sectionType.name() == SectionType.PERSONAL}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><input type="text" name="${sectionType.name()}"
                                   size=80
                                   value="<%=((TextSection) section).getContent()%>">
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType.name() == SectionType.ACHIEVEMENT ||
                sectionType.name() == SectionType.QUALIFICATIONS}">
                    <dl>
                        <dd>${sectionType.title}</dd>
                        <dt><textarea name="${sectionType.name()}" cols=50 rows="5"><%=String.
                                join("\n", ((ListSection) section).getTextList())%></textarea></dt>
                    </dl>
                </c:when>
                <c:when test="${sectionType.name() == SectionType.EDUCATION ||
                sectionType.name() == SectionType.EXPERIENCE}">

                    <dl>
                        <dt><b>${sectionType.title}</b></dt>
                        <hr>
                    </dl>
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getListOrganization()%>"
                               varStatus="orgCount">
                        <jsp:useBean id="organization" type="com.urise.webapp.model.Organization"/>
                        <dl>
                            <dt>Организация</dt>
                            <dd><input type="text" name="${sectionType.name()}"
                                       size=20
                                       value="<%=organization.getHomePage().getName()%>">
                            </dd>
                            <br/></dl>
                        <dl>
                            <dt>Сайт</dt>
                            <dd><input type="text" name="${sectionType.name()}url"
                                       size=20
                                       value="<%=(organization.getHomePage().getUrl() == null) ? "" :
                                       organization.getHomePage().getUrl()%>">
                            </dd>
                        </dl>
                        <br/>
                        <c:forEach var="position" items="<%=organization.getPositions()%>">
                            <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                            <dl>
                                <dt>Период (yyyy-mm-dd)</dt>
                                <dd><input type="text" name="${sectionType.name()}${orgCount.index}startDate"
                                           size=10
                                           value="<%=DateUtil.getStringDate(position.getStartDate())%>">
                                </dd>
                                <dt>-</dt>
                                <dd><input style="margin-left: -173px" type="text"
                                           name="${sectionType.name()}${orgCount.index}endDate" size=10
                                           value="<%=DateUtil.getStringDate(position.getEndDate())%>">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Должность</dt>
                                <dd><input type="text" name="${sectionType.name()}${orgCount.index}title"
                                           size=27
                                           value="<%=(position.getTitle() == null) ? "" : position.getTitle()%>">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Описание</dt>
                                <dd><input type="text" name="${sectionType.name()}${orgCount.index}description"
                                           size=80
                                           value="<%=(position.getDescription() == null) ? "" :
                                           position.getDescription()%>">
                                </dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                 </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

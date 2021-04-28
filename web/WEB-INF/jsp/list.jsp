<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<form id="create">
       <button><a href="resume?action=create">Создать новое резюме</a></button>
   </form>
<br/>
<table id="tableList">
    <tr class="list">
        <th>Имя</th>
        <th>Email</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="resume" items="${resumes}">
        <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
        <tr class="list">
            <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
            <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
            </td>
            <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" width="16" height="16"
                                                                        alt="Delete"></a></td>
            <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" width="16" height="16"
                                                                      alt="Edit"></a></td>
        </tr>
    </c:forEach>
</table>
<br/>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

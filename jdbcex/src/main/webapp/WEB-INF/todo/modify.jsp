<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Todo Read</title>
</head>
<body>
<form id="form1" action="/todo/modify" method="post">
    <div>
        <input type="text" name="tno" value="${dto.tno}" readonly/>
    </div>
    <div>
        <input type="text" name="title" value="${dto.title}"/>
    </div>
    <div>
        <input type="text" name="dueDate" value="${dto.dueDate}"/>
    </div>
    <div>
        <input type="text" name="finished" value="${dto.finished ? "checked" : ""}"/>
    </div>
    <div>
        <button type="submit">Modify</button>
    </div>
</form>

<form id="form2" action="/todo/remove" method="post">
    <input type="hidden" name="tno" value="${dto.tno}" readonly>
    <div>
        <button type="submit">Remove</button>
    </div>
</form>

</body>
</html>
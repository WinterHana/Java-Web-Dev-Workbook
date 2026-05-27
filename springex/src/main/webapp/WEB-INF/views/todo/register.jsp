<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/todo/register" method="POST">
      <div>
        Title : <input type="text" name="title"/>
      </div>
      <div>
        DueDate : <input type="date" name="dueDate" value="2022-12-25"/>
      </div>
      <div>
        Writer: <input type="text" name="writer"/>
      </div>
      <div>
        Finished : <input type="checkbox" name="finished"/>
      </div>
      <div>
        <button type="submit">Register</button>
      </div>
    </form>
</body>
</html>

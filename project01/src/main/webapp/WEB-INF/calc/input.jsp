<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!--
<form>
    <h1>Get</h1>
    <input type="number" name="num1"/>
    <input type="number" name="num2"/>
    <button type="submit">SEND</button>
</form>
-->
<form action="/calc/makeResult" method="post">
    <h1>Post</h1>
    <input type="number" name="num1">
    <input type="number" name="num2">
    <button type="submit">SEND</button>
</form>

</body>
</html>
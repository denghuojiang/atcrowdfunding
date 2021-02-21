<%--
  Created by IntelliJ IDEA.
  User: 86156
  Date: 2021/2/19
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
    <h1>出错了!!!</h1>
<%--请求雨中有异常属性信息--%>
${requestScope.exception.message}
</body>
</html>

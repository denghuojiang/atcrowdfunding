<%--
  Created by IntelliJ IDEA.
  User: 86156
  Date: 2021/2/19
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/admin_webui_war/">
    <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
<%--    静态资源过滤默认映射到js文件下--%>
    <script type="text/javascript">
        $(function () {
            $('#btnArr').click(function () {
                var array = [5, 8, 12];
                var requestBody = JSON.stringify(array);
                $.ajax({
                    "url": 'send/array',
                    "type": 'post',
                    "data": requestBody,
                    "contentType": 'application/json;charset=utf-8',
                    "dataType": 'json',
                    "success": function (data) {
                        alert(data)
                    },
                    "error": function (error) {
                        alert(error)
                    }
                });
            });
        })
    </script>
</head>
<body>
<%--    不呢以斜杠开头 否则会不依赖base标签--%>
<a href="test/ssm">你好test</a>
<button id="btnArr">send [5,8,12]</button>
<a href="admin/to/login/page">登录页面</a>
</body>
</html>

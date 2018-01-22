<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css" src="resource/css/myStyle.css"></style>
    <script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <!-- jqGrid -->
    <script src="//cdn.bootcss.com/jqgrid/4.6.0/js/jquery.jqGrid.min.js"></script>
    <script src="//cdn.bootcss.com/jqgrid/4.6.0/js/i18n/grid.locale-cn.js"></script>
    <script
            src="//cdn.bootcss.com/moment.js/2.10.6/moment-with-locales.min.js"></script>
    <script src="resource/js/thirdpart/jquery-ui.min.js"></script>
</head>
<body>
<div class="container-fluid">
<h1></h1>
    <div class="row">
        <div class="col-md-8">测试</div>
    </div>

</div>
</body>
</html>

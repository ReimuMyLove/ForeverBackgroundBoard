<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 筱邪丶
  Date: 2021/5/26
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/favicon.ico">
    <link rel="canonical" href="https://getbootstrap.com/docs/3.4/examples/dashboard/">

    <title>书单添加</title>
    <link href="../css/bookPic.css" rel="stylesheet">
    <style  rel="stylesheet">
        table.hoverTable {
            font-family: verdana,arial,sans-serif;
            font-size:20px;
            color:#333333;
            width: 600px;
            border-width: 1px;
            border-color: #ffffff;
            border-collapse: collapse;
        }
        table.hoverTable th {
            background-color: #f5f5f5;
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #ffffff;
        }
        table.hoverTable tr {
            background-color: #ffffff;
        }
        table.hoverTable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #ffffff;
        }
        a:link{
            text-underline: none;
            text-decoration: none;
            color: #3c3c3c;
        }
        a:visited{
            text-underline: none;
            text-decoration: none;
            color: #3c3c3c;
        }
        a:hover{
            text-underline: none;
            text-decoration: none;
            color: #3c3c3c;
        }
        a:active{
            text-underline: none;
            text-decoration: none;
            color: #3c3c3c;
        }
    </style>

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/examples/dashboard/dashboard.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="bookData?pageNumber=1">OuranBook-1.0</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">书籍</a></li>
                <li><a href="#">书单</a></li>
                <li><a href="#">评论</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search..." name="searchGoal">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <h2>书籍</h2>
            <ul class="nav nav-sidebar">
                <li><a href="bookData?pageNumber=1">书籍展示</a></li>
                <li><a href="bookAdd">上传信息</a></li>
                <li><a href="bookType">类型管理</a></li>
            </ul>
            <h2>书单</h2>
            <ul class="nav nav-sidebar">
                <li><a href="bookListData?pageNumber=1">书单展示</a></li>
                <li><a href="bookListAdd">上传书单</a></li>
            </ul>
            <h2>评论</h2>
            <ul class="nav nav-sidebar">
                <li class="active"><a href="bookCommentManage?pageNumber=1">评论管理</a><span class="sr-only">(current)</span></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">全部评论信息</h1>
            <div class="row placeholders">
                <!-- CSS goes in the document HEAD or added to your external stylesheet -->

                <!-- Table goes in the document BODY -->
                <div style="float: left;">
                    <table class="hoverTable">
                        <tr>
                            <th colspan="1" style="text-align: center">TypeId</th>
                            <th colspan="1" style="text-align: center">TypeName</th>
                            <th colspan="3" style="text-align: center">option</th>
                        </tr>
                        <c:forEach items="${bookTypesList}" var="bookType" begin="0" end="${number}">
                            <tr onmouseover="this.style.backgroundColor='rgba(222,222,222,0.87)';" onmouseout="this.style.backgroundColor='#ffffff';">
                                <td>${bookType.typeId}</td>
                                <td><label>
                                    <input type="text"
                                           name="typeName"
                                           value="${bookType.typeName}"
                                           style="width: 100px;background: #ffffff;border: none;outline:none;">
                                </label></td>
                                <td><a href="typeDelete?typeId=${bookType.typeId}">删除属性</a></td>
                                <td><a href="typeUpdate?typeId=${bookType.typeId}">修改属性</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div>
                    <table class="hoverTable">
                        <tr>
                            <th colspan="1" style="text-align: center">TypeId</th>
                            <th colspan="1" style="text-align: center">TypeName</th>
                            <th colspan="3" style="text-align: center">option</th>
                        </tr>
                        <c:forEach items="${bookTypesList}" var="bookType" begin="${number+1}">
                            <form action="typeUpdate">
                                <tr onmouseover="this.style.backgroundColor='rgba(222,222,222,0.87)';" onmouseout="this.style.backgroundColor='#ffffff';">
                                    <td>${bookType.typeId}<input type="hidden" name="typeId" value="${bookType.typeId}"></td>
                                    <td><label>
                                        <input type="text"
                                               name="typeName"
                                               value="${bookType.typeName}"
                                               style="width: 100px;background: #ffffff;border: none;outline:none;">
                                    </label></td>
                                    <td><a href="typeDelete?typeId=${bookType.typeId}">删除属性</a></td>
                                    <td colspan="1"><input type="submit"
                                                           value="修改属性"
                                                           id="update"
                                                           style="border: none;background: #00000000;outline:none;"></td>
                                </tr>
                            </form>

                        </c:forEach>
                        <tr >
                            <form method="post" action="typeAdd">
                                <td>${maxNumber+1}</td>
                                <td><label>
                                    <input type="text"
                                           name="typeName"
                                           placeholder="新类型..."
                                           style="width: 100px;background: #ffffff;border: none;outline:none;"
                                           onchange="addAdmit()">
                                </label></td>
                                <td colspan="2"><input type="submit"
                                                       disabled
                                                       value="添加"
                                                       id="add"
                                                       style="border: none;background: white;outline:none;"></td>
                            </form>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function addAdmit(qualifiedName, value){
        const add = document.getElementById("add");
        if (add.getAttribute("value") !== null || add.getAttribute("value") !== ""){
            add.removeAttribute("disabled");
        }else{
            add.setAttribute("disabled", "disabled");
        }
    }
</script>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/dist/js/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<script src="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/assets/js/vendor/holder.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="https://cdn.jsdelivr.net/npm/@bootcss/v3.bootcss.com@1.0.8/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ouranservice.entity.BookTypes" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: 筱邪丶
  Date: 2021/5/26
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
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

    <title>添加书籍</title>

    <style rel="stylesheet">
        .input{
            border  : 1px solid #3c3c3c;
            outline : none;
            cursor  : pointer;
            width   : 100px;
        }
        .time{
            border  : 1px solid #3c3c3c;
            outline : none;
            cursor  : pointer;
            width   : 80px;
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
    <%
        @SuppressWarnings("unchecked")
        List<BookTypes> bookTypesList = (ArrayList<BookTypes>)request.getAttribute("bookTypesList");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        pageContext.setAttribute("bookTypesList",bookTypesList);
        pageContext.setAttribute("year",year);
    %>

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
                <label>
                    <input type="text" class="form-control" placeholder="Search..." name="searchGoal">
                </label>
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
                <li class="active"><a href="bookAdd">上传信息</a><span class="sr-only">(current)</span></li>
                <li><a href="bookType">类型管理</a></li>
            </ul>
            <h2>书单</h2>
            <ul class="nav nav-sidebar">
                <li><a href="bookListData?pageNumber=1">书单展示</a></li>
                <li><a href="bookListAdd">上传书单</a></li>
            </ul>
            <h2>评论</h2>
            <ul class="nav nav-sidebar">
                <li><a href="bookCommentManage?pageNumber=1">评论管理</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">添加书籍</h1>
            <form action="bookAdd" method="post" enctype="multipart/form-data">
                <div style="height: 600px;width: 500px;padding-top:10px;float:left;">
                    <h3>添加封面</h3>
                    <div style="border: 1px solid black;width: 126px;height: 202px;">
                        <img src="../PNG/bookPic/BookPic.png"
                             alt="封面预览"
                             height="200px"
                             width="124px"
                             id="picDisplay">
                    </div>
                    <input type="file"
                           name="bookPic"
                           id="bookPic"
                           alt="上传书籍封面"
                           onchange="preview()"
                           style="border: 0;outline:none;cursor: pointer;width: 200px;margin-bottom: 20px;margin-top: 20px" />
                    <h3>书籍信息</h3>
                    <h4>书　　名： <label><input type="text" name="bookName" class="input"></label></h4>
                    <h4>作　　者： <label><input type="text" name="bookWriter" class="input"></label></h4>
                    <%-- 年月日下拉栏 --%>
                    <h4>出版日期：
                        <label>
                            <input type="number"
                                   max="${year}"
                                   min="1"
                                   step="1"
                                   name="year"
                                   class="time"/> 年
                        </label>
                        <label>
                            <input type="text"
                                   oninput = "value=value.replace(/[^\d]/g,'')"
                                   max="12"
                                   min="1"
                                   name="month"
                                   class="time"/> 月
                        </label>
                        <label>
                            <input type="text"
                                   oninput = "value=value.replace(/[^\d]/g,'')"
                                   max="31"
                                   min="1"
                                   name="day"
                                   class="time"/> 日
                        </label></h4>
                    <h4>
                        出版地区：
                        <label>
                            <select name="bookPublishArea" style="width: 110px;">
                                <option value="中国大陆">中国大陆</option>
                                <option value="中国香港">中国香港</option>
                                <option value="中国台湾">中国台湾</option>
                                <option value="中国海外">海外</option>
                            </select>
                        </label>
                    </h4>
                </div>

                <div style="margin-top: 50px;padding-top:10px;">
                    <h3>其他信息</h3>
                    <h4>星　　级： <label>
                        <input
                                type="number"
                                name="bookStar"
                                class="input"
                                step="0.01"
                                min="1"
                                max="5"/>
                    </label></h4>
                    <h4>下载地址： <label><input type="text" name="bookDownAddress" style="width: 300px"></label></h4>
                    <h4>
                        类　　型：
                        <label>
                            <select name="typeName1" style="width: 120px">
                                <option value="未选择">未选择</option>
                                <c:forEach items="${bookTypesList}" var="type">
                                    <option value="${type.typeName}">${type.typeName}</option>
                                </c:forEach>
                            </select>
                            <select name="typeName2" style="width: 120px">
                                <option value="未选择">未选择</option>
                                <c:forEach items="${bookTypesList}" var="type">
                                    <option value="${type.typeName}">${type.typeName}</option>
                                </c:forEach>
                            </select>
                            <select name="typeName3" style="width: 120px">
                                <option value="未选择">未选择</option>
                                <c:forEach items="${bookTypesList}" var="type">
                                    <option value="${type.typeName}">${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </h4>
                    <br><br>
                    <h4>简介：</h4>
                    <h4><label>
                        <textarea name="bookIntroduction" style="width: 400px;height: 200px"></textarea>
                    </label></h4>
                </div>
                <div><h4><input type="submit" value="确认添加"></h4></div>
            </form>
        </div>
    </div>
</div>

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
<script>
    const nowYear = new Date().getFullYear();                           //当前年份
    function preview() {
        // 获取图片选择框中图片的二进制信息
        const bookPic = document.getElementById("bookPic");
        const file = bookPic.files[0]; // 拿到数组中第一个图片文件

        // 读取文件内容
        const read = new FileReader();
        // 定义事件
        read.onload = function() {
            // 获取结果
            document.getElementById("picDisplay").src = this.result;
        };
        // 开始读取
        read.readAsDataURL(file);
    }
</script>
</html>

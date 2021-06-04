<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ouranservice.entity.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ouranservice.util.StaticData" %><%--
  Created by IntelliJ IDEA.
  User: 筱邪丶
  Date: 2021/5/26
  Time: 14:15
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

    <title>偶然书城后台管理系统</title>
    <link href="../css/bookPic.css" rel="stylesheet">


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

    <style type="text/css" rel="stylesheet">
        a:link{
            color: #2f2a2a;
        }
    </style>

</head>
<%
    @SuppressWarnings("unchecked")
    List<Book> books = (ArrayList<Book>)request.getAttribute("books");  //书籍信息
    int pageNumber = (int)request.getAttribute("pageNumber");           //当前页码
    int maxNumber = (int)request.getAttribute("maxNumber");             //最大页码
    int lastNumber = pageNumber-1;                                      //上一页
    int nextNumber = pageNumber+1;                                      //下一页
    if(pageNumber == 1){
        lastNumber = 1;
    }else if (pageNumber == maxNumber){
        nextNumber = pageNumber;
    }
    String picPath = StaticData.BOOK_PIC_PATH;
    pageContext.setAttribute("books",books);
    pageContext.setAttribute("pageNumber",pageNumber);
    pageContext.setAttribute("picPath",picPath);
    pageContext.setAttribute("maxNumber",maxNumber);
    pageContext.setAttribute("lastNumber",lastNumber);
    pageContext.setAttribute("nextNumber",nextNumber);
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
                <li class="active"><a href="bookData?pageNumber=1">书籍展示<span class="sr-only">(current)</span></a></li>
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
                <li><a href="bookCommentManage?pageNumber=1">评论管理</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">全部书籍信息</h1>
            <div class="row placeholders">
                <div class="row placeholders" style="height: 650px;">
                    <c:forEach items="${books}" var="book">
                        <div class="col-xs-6 " style="width: 20%;">
                            <div style="height: 200px;">
                                <a href="bookManage?bookId=${book.bookId}&pageNumber=1">
                                    <img src="${picPath}${book.bookPic}"
                                         width="124"
                                         height="200"
                                         alt="${book.bookName}">
                                </a>
                            </div>
                            <h5>No${book.bookId}</h5>
                            <a href="bookManage?bookId=${book.bookId}&pageNumber=1"><h4>${book.bookName}</h4></a>
                            <span class="text-muted">${book.bookPublishTime}</span>
                            <h5>评分：${book.bookStar}
                                <a href="bookRecycle?bookId=${book.bookId}">删除</a>
                            </h5>
                        </div>
                    </c:forEach>
                </div>
                <form action="bookData" method="GET">
                    <a href="bookData?pageNumber=${lastNumber}"><input type="button" name="lastPage" value="上一页"></a>
                    　${pageNumber}/${maxNumber}页　
                    <a href="bookData?pageNumber=${nextNumber}"><input type="button" name="nextPage" value="下一页"></a>　　
                    <label>
                        <input type="text"
                               name="pageNumber"
                               oninput = "value=value.replace(/[^\d]/g,'')"
                               class="form-control"
                               placeholder="跳转至..." style="width: 80px;height: 28px">
                    </label>　
                    <input type="submit" value="跳转">
                </form>
            </div>
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
</html>

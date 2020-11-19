<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="tuwen/add" method ="post">
		title：
		<input type="text" name="title"/>
		<br/>
		writer：
		<input type="text" name="writer"/>
		<br/>
		userid：
		<input type="text" name="userid"/>
		<br/>
		typeid：
		<input type="text" name="typeid"/>
		<br/>
		likes：
		<input type="text" name="likes"/>
		<br/>
		isoriginal：
		<input type="text" name="isoriginal"/>
		<br/>
		text：
		<input type="text" name="text"/>
		<br/>
		<input type="submit" value="提交">
	</form>
</body>
</html>
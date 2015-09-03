<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<style type="text/css">
			body {
				color: white;
			}
			table {
				width: 100%;
				border-collapse: collapse;
				font-size: small;
			}
			table, th, td {
				border: 1px solid white;
				text-align: center;
			}
			th {
				background-color: orange;
				color: black;
			}
			
			#buttonGroup {
				margin: 10px;
				text-align: center;
			}
			
			#buttonGroup a {
				display:inline-block;
				width: 70px;
				line-height: 30px;
				text-decoration: none;
				font-size: small;
				color: white;
				border: 1px solid darkgray;
				background-color: gray;
				font-weight: bold;
			}
			
			#buttonGroup a:hover {
				color: black;
				background-color: lightgray;
			}
			
			#pager a.pagaNo {
				margin-left: 5px;
				margin-right: 5px;
			}
			
			
		</style>
	</head>
	
	<body>
		<h4>게시물 목록</h4>
		
		<table>
			<tr>
				<th style="width:50px">번호</th>
				<th>이름</th>
				<th style="width:60px">가격</th>
				<th style="width:80px">재고</th>
			</tr>
			
			<c:forEach var="product" items="${list}">
				<tr>
					<td>${product.no}</td>
					<td>${product.name}</td>
					<td>${product.price}</td>
					<td>${product.stock}</td>
				</tr>
			</c:forEach>
			
		</table>
		
		<div id = "pager">
			<a href= "list.?pageNo=1">[처음]</a>
			
			<c:if test="${groupNo>1}">
			<a href= "list.?pageNo=">[이전]</a>
			</c:if>
			<a class = "pageNo" href="list?pageNo="></a>
			<a href="list.?pageNo=">[다음]</a>
			<a href="list.?pageNo=">[맨끝]</a>
		</div>
		
		<div id="buttonGroup">
			<a href="writeForm">상품등록</a>
		</div>
	</body>
</html>

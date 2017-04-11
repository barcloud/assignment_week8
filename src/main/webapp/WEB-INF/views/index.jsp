<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>首页</title>
</head>
<body>


<div class="jumbotron">
    <div class="container">
        <h1>凤雏商城为您服务</h1>
        <p>现在已有${applicationScope["counter"]}访问页面.</p>
        <p><a class="btn btn-primary btn-lg" href="/login" role="button">${sessionScope["status"]}</a></p>
    </div>
</div>
    <article class="container">
        <form action="/cart" method="post">
            <c:forEach items="${sessionScope.good}" var="good">
                 <div class="row">
                    <div class="form-group col-md-2 ">
                        <label for="good">${good.good_name}数量：</label>
                        <input placeholder="请输入购买数量" type="number" min="0" class="form-control " id="good" name="${good.suk}">
                    </div>
                 </div>
                <br />
            </c:forEach>
            <button type="submit" class="btn btn-default">立即购买</button>
        </form>
    </article>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>购物车</title>
</head>
<body>
    <div class="container">
        <table class="table table-striped">
            <tr>
                <th>商品名称</th>
                <th>数量</th>
            </tr>
            <c:forEach items="${sessionScope.cart}" var="good">
                <tr>
                    <td>${good.good_name}</td>
                    <td>${good.shopping_num}</td>
                    <td>
                        <form action="/del" method="post">
                            <input type="hidden" name="suk" value="${good.suk}">
                            <button type="submit" class="btn btn-primary">删除</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <a class="btn btn-primary" href="index.html" role="button">返回</a>
        <a class="btn btn-primary" href="#" role="button">提交订单</a>
    </div>

</body>
</html>

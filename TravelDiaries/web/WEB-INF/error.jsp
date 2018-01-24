<%-- 
    Document   : error
    Created on : 2017/12/07, 13:35:45
    Author     : yoshikawa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>エラー</title>
        <!-- Bootstrap core CSS -->
        <link href="./css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
         <link href="./css/sign.css" rel="stylesheet"> 
    </head>
    <body>
        <!-- Static navbar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">TravelDiaries</a>
        </nav>

        <div class="container">
            <h1>エラー</h1>
            <p><%= request.getAttribute("errorMessage")%></p>
            <h3 class="text-center">ログイン</h3>
            <form class="form-signin" action="login" method="POST">
                <div class="form-group">
                    <label for="inputId" class="control-label">ログインID</label>
                    <input type="text" class="form-control" id="inputId" name="id" placeholder="ログインID" required>
                </div>
                <div class="form-group">
                    <label for="inputPass" class="control-label">パスワード</label>
                    <input type="password" class="form-control" id="inputPass" name="pass" placeholder="パスワード" required>
                </div>
                <div class="form-group text-right">
                    <button type="submit" class="btn btn-lg btn-primary btn-block">ログイン</button>
                    <br>
                    <a href="signup.html">未登録ユーザーの方はこちら</a>
                </div>
            </form>

            <!-- Bootstrap core JavaScript
            ================================================== -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script> 
            <script src="./js/bootstrap.min.js"></script> 

            <!-- Custom JS -->
            <!-- <script src="./js/main.js"></script> -->
    </body>
</html>

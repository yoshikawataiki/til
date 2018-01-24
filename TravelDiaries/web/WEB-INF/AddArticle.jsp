<%-- 
    Document   : AddArticle
    Created on : 2018/01/19, 17:49:49
    Author     : yoshikawa
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="jp.ac.ait.oop2.k16123.web.database.Article"%>
<%@page import="jp.ac.ait.oop2.k16123.web.Auth"%>
<%@page import="jp.ac.ait.oop2.k16123.web.database.User"%>
<%
    // ログインユーザーの取得
    User loggedInUser = Auth.getLoggedInUserOrNull(request);

    // セッションから"edit"があるかどうかを調べる
    // もしあるなら、inputタグのvalue属性をArticleのインスタンスから設定する
    Article edit = (Article) session.getAttribute("edit");
    List<String> errors = null;
    if (edit != null) {
        // データは退避したので、不要となった"edit"キーをsessionから削除する
        session.removeAttribute("edit");
        // エラーデータをresponceスコープから取得
        if (request.getAttribute("error") instanceof List) {
            errors = (List<String>) request.getAttribute("error");
        }
    }

    // 日付データのフォーマット指定(入力項目用)
    DateTimeFormatter dfIn = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>TravelDiaries</title>
        <!-- Bootstrap core CSS -->
        <link href="./css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="./css/dashboard.css" rel="stylesheet">
    </head>
    <body>
        <!-- Static navbar -->
        <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
            <button class="navbar-toggler navbar-toggler-right hidden-lg-up" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand" href="dashoboard">Dashboard</a>

            <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="dashboard">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="profile">Profile</a>
                    </li>
                </ul>
            </div>
        </nav>


        <div class="container-fluid">
            <div class="row">
                <nav class="col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar">
                    <ul class="nav nav-pills flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="add">Add <span class="sr-only">(current)</span></a>
                        </li>
                        <li><a class="nav-link" href="profile"><%= loggedInUser.getName()%></a></li>
                    </ul>
                </nav>
                <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
                    <h1>新規投稿</h1>
                    <% if (errors != null) { %>
                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <h3 class="panel-title">入力エラー</h3>
                        </div>
                        <div class="panel-body">
                            <ul>
                                <% for (String message : errors) {%>
                                <li><%= message%></li>
                                    <% } %>
                            </ul>
                        </div>
                    </div>
                    <% }%>

                    <form class="form-horizontal" action="register" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="<%= (edit != null) ? edit.getId() : 0%>">

                        <div class="form-group form-group-lg">
                            <label for="inputTitle" class="control-label col-xs-2">タイトル</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="inputTitle" name="title" placeholder="タイトル" required value="<%= (edit != null) ? edit.getTitle() : ""%>">
                            </div>
                        </div>
                        <div class="form-group　form-group-lg">
                            <label for="inputDetail" class="control-label col-xs-2">本文</label>
                            <div class="col-xs-10">
                                <textarea class="form-control" id="inputDetail" name="detail" placeholder="本文"><%= (edit != null) ? edit.getDetail() : ""%></textarea>                        
                            </div>
                        </div>
                        <div class="form-group　form-group-lg">
                            <label for="inputImage" class="control-label col-xs-2">画像</label>
                            <div class="col-xs-10">
                                <input type="file" class="form-control" id="inputImage" name="image" placeholder="画像">                       
                            </div>
                        </div>
                        <div class="form-group form-group-lg">
                            <label for="inputCategory" class="control-label col-xs-2">カテゴリー</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="inputCategory" name="category" placeholder="カテゴリー" required value="<%= (edit != null) ? edit.getCategory() : ""%>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputCreated" class="control-label col-xs-2">日時</label>
                            <div class="col-xs-3">
                                <input type="date" class="form-control" id="inputCreated" name="created" placeholder="日付" required value="<%= (edit != null) ? edit.getCreated().format(dfIn) : ""%>">
                            </div>
                        </div>
                        <div class="form-group form-group-lg">
                            <div class="col-xs-2">
                            </div>
                            <div class="col-xs-3">
                                <button type="submit" class="btn btn-primary">登録</button>
                            </div>
                        </div>
                    </form>
                </main>
            </div>
        </div> <!-- /container -->

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="./js/bootstrap.min.js"></script>

        <!-- Custom JS -->
        <!-- <script src="./js/main.js"></script> -->
    </body>
</html>

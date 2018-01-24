<%-- 
    Document   : dashboard
    Created on : 2017/12/07, 13:58:53
    Author     : yoshikawa
--%>


<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="jp.ac.ait.oop2.k16123.web.database.Database"%>
<%@page import="jp.ac.ait.oop2.k16123.web.database.Article"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="jp.ac.ait.oop2.k16123.web.Auth"%>
<%@page import="jp.ac.ait.oop2.k16123.web.database.User"%>

<%

    // ログインユーザー情報の取得
    User loggedInUser = Auth.getLoggedInUserOrNull(request);
    
    List<Article> article = Database.selectArticleByUserId(loggedInUser.getId());

    // 日付項目のフォーマット用
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="utf-8">
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
            <a class="navbar-brand" href="dashboard">Dashboard</a>

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
                    <table class="table">
                        <tr>
                            <th>&nbsp;</th>
                            <th>画像</th>
                            <th>タイトル</th>
                            <th>本文</th>
                            <th>カテゴリー</th>
                            <th>日付</th>
                        </tr>
                        <% if (article != null && !article.isEmpty()) { %>
                        <%  for (Article c : article) {%>
                        <tr>
                            <td><a href="change?id=<%= c.getId()%>" role="button">編集</a> /
                                <a href="delete?id=<%= c.getId()%>" role="button">削除</a></td>
                            <td><img src=<%= "image/" + c.getImage()%> height="150" width="150"></td>
                            <td><%= c.getTitle()%></td>
                            <td><%= c.getDetail()%></td>
                            <td><%= c.getCategory()%></td>
                            <td><%= c.getCreated().format(df)%></td>
                        </tr>
                        <% }%>
                        <% } else { %>
                        <tr>
                            <td colspan="6" class="text-center">投稿記事はありません。</td>
                        </tr>
                        <% }%>
                    </table>
                </main>
            </div>
        </div>


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="./js/bootstrap.min.js"></script>
    </body>
</html>
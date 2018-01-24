<%-- 
    Document   : userlist
    Created on : 2017/11/30, 15:54:45
    Author     : yoshikawa
--%>

<%@page import="jp.ac.ait.oop2.k16123.web.database.User"%>
<%@page import="jp.ac.ait.oop2.k16123.web.database.Database"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<User> users = Database.selectAllUsers();
%>    


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <ul>
            <%for(User user:users){%>
                <li>
                <%= user.getId() %>
                <%= user.getName() %>
                </li>
            <%}%>
        </ul>
    </body>
</html>

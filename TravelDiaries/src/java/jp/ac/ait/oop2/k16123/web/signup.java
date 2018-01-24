/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k16123.web;

import jp.ac.ait.oop2.k16123.web.database.Encrypt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.ac.ait.oop2.k16123.web.database.User;

/**
 *
 * @author yoshikawa
 */
@WebServlet(name = "signup", urlPatterns = {"/signup"})

public class signup extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/TravelDiaries";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        
        //        暗号化
        String enc_pass = Encrypt.toHashValue("SHA-256", pass);

        User user = new User();
        user.setName(name);
        user.setPassword(enc_pass);
        if(insertUser(user)) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("index.html");
        }
    }
    
    public boolean insertUser(User user) {
    int result;
    
    //Step.1 JDBCドライバが参照できるかを確認
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      //JDBCドライバが読み込めなかったときの処理をここに書く
      //通常はデータベースの操作自体不能となるため、プログラムは続行不能となります。
      e.printStackTrace();
      return false;
    }

    //データベースへのコネクション
    //Step.2 データベースへ接続
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {

      //Step.3 SQLの発行
      /* ここで必要なデータの抽出や更新処理などをSQLで発行します */
      StringBuilder sql = new StringBuilder();
      sql.append("INSERT INTO ");
      sql.append("    User (");
      sql.append("    name, ");
      sql.append("    password ");
      sql.append(") VALUES ( ");
      sql.append("    ?, ");
      sql.append("    ? ");
      sql.append(") ");

      PreparedStatement stm = connection.prepareStatement(sql.toString());
      
      stm.setString(1, user.getName());
      stm.setString(2, user.getPassword());
      
      result = stm.executeUpdate();
    } catch (SQLException e) {
      //接続失敗時の処理を書きます
      e.printStackTrace();
      return false;
    }
    //connectionは自動的に切断され、破棄されます。
    return result >= 0; // 更新件数が1件以上かどうかで更新処理の成功判定を行う
  }
    
    

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

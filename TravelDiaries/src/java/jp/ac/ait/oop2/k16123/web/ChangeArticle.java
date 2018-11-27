/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k16123.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.ait.oop2.k16123.web.database.Article;
import jp.ac.ait.oop2.k16123.web.database.Database;
import jp.ac.ait.oop2.k16123.web.database.User;

/**
 *
 * @author yoshikawa
 */
@WebServlet(name = "ChangeArticle", urlPatterns = {"/change"})
public class ChangeArticle extends HttpServlet {

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

    User loggedInUser = Auth.getLoggedInUserOrNull(request);
    if (loggedInUser == null) {
      // ログイン済みでないので、ログインページに戻す 
      response.sendRedirect("index.html");
      return;
    }

    HttpSession session = request.getSession(false);

    String id = request.getParameter("id");

    try {
      int numId = Integer.parseInt(id);
    } catch (NumberFormatException e) {
      // ID指定がおかしいのでエラー 
      request.setAttribute("errorMessage", "IDは数値で入力してください。");
      ServletContext sc = getServletContext();
      sc.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
      return;
    }

    Article data = Database.selectArticleById(Integer.parseInt(id));

    if (data == null) {
      // データが見つからない場合
      response.sendRedirect("dashboard");
      return;
    }
    
    // sessionに編集データを設定する 
    session.setAttribute("edit", data);

    // 入力画面を編集で呼び出し
    ServletContext sc = getServletContext();
    sc.getRequestDispatcher("/WEB-INF/AddArticle.jsp").forward(request, response);

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

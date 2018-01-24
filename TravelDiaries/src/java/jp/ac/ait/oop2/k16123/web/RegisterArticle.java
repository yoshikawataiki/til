/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k16123.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import jp.ac.ait.oop2.k16123.web.database.Article;
import jp.ac.ait.oop2.k16123.web.database.Database;
import jp.ac.ait.oop2.k16123.web.database.User;

/**
 *
 * @author yoshikawa
 */
@WebServlet(name = "RegisterArticle", urlPatterns = {"/register"})
// 画像ファイルの保存場所　location
@MultipartConfig(location="/Users/yoshikawa/NetBeansProjects/TravelDiaries/web/image", maxFileSize=1048576)
public class RegisterArticle extends HttpServlet {

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

        User loggedInUser = Auth.getLoggedInUserOrNull(request);
        if (loggedInUser == null) {
            // ログイン済みでないので、ログインページに戻す 
            response.sendRedirect("index.html");
            return;
        }

        // Session取得 
        HttpSession session = request.getSession(false);

        // 登録用データの生成 
        Article data = new Article();

        // ユーザーIDの設定 
        data.setUser_id(loggedInUser.getId());

        // エラー格納用リストの生成 
        List<String> errors = new ArrayList<>();

        // 入力値チェック 
        if (!checkAndSetAllParameter(request, data, errors)) {
            // エラーあり 

            // sessionに編集データを設定する 
            session.setAttribute("edit", data);

            // リクエストスコープにエラーメッセージを渡す 
            request.setAttribute("error", errors);

            // 入力エラーのため入力ページへフォワードし直し 
            ServletContext sc = getServletContext();
            sc.getRequestDispatcher("/WEB-INF/AddArticle.jsp").forward(request, response);
            return;
        }

        // データベースに登録 
        boolean hasError = false;
        if (data.getId() == 0) {
            // IDが設定されていないのでINSERT 
            hasError = !Database.insertArticle(data);
        } else {
            // IDが指定されているのでUPDATE 
            hasError = !Database.UpdateArticle(data);
        }

        if (hasError) {
            session.setAttribute("edit", data);

            // リクエストスコープにエラーメッセージを渡す 
            errors.add("データの登録に失敗しました。");
            request.setAttribute("error", errors);

            // 入力エラーのため入力ページへフォワードし直し 
            ServletContext sc = getServletContext();
            sc.getRequestDispatcher("/WEB-INF/AddArticle.jsp").forward(request, response);
            return;
        }

        // ダッシュボードへリダイレクト 
        response.sendRedirect("dashboard");

    }
    
    /**
     * 入力値のチェックとCashBookデータの設定
     *
     * @param request
     * @param data
     * @param errors
     * @return
     */
    private boolean checkAndSetAllParameter(HttpServletRequest request, Article data, List<String> errors) throws IOException, ServletException {

        boolean isCheckComplete = true;

        // id 
        String id = request.getParameter("id");
        if (id == null) {
            data.setId(0);
        } else {
            try {
                data.setId(Integer.parseInt(id));
            } catch (Exception e) {
                errors.add("リクエストパラメータ変換エラー：IDにおいて不正な入力を検出しました。");
                isCheckComplete = false;
            }
        }

        // 摘要 
        String title = request.getParameter("title");
        if (title == null) {
            errors.add("摘要を入力してください。");
            isCheckComplete = false;
        } else {
            // 設定 ↓大事！！ 
            data.setTitle(title);
        }

        // 摘要詳細 
        String detail = request.getParameter("detail");
        if (detail == null) {
            errors.add("摘要を入力してください。");
            isCheckComplete = false;
        } else {
            // 設定 ↓大事！！ 
            data.setDetail(detail);
        }
        
        //　画像
        Part part = request.getPart("image");
        String image = this.getFileName(part);
        part.write(image);
        data.setImage(image);
        
        // カテゴリー
        String category = request.getParameter("category");
        if (detail == null) {
            errors.add("摘要を入力してください。");
            isCheckComplete = false;
        } else {
            // 設定 ↓大事！！ 
            data.setCategory(category);
        }

        // 発生日付 
        String inputCreated = request.getParameter("created");
        if (inputCreated == null) {
            errors.add("発生日付を入力してください。");
            isCheckComplete = false;
        } else {
            try {
                // 日付項目をLocalDateに変換 
                LocalDate d = LocalDate.parse(inputCreated);
                data.setCreated(d);
            } catch (Exception e) {
                errors.add("リクエストパラメータ変換エラー：発生日付において不正な入力を検出しました。");
                isCheckComplete = false;
            }
        }

        return isCheckComplete;
    }
    private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
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

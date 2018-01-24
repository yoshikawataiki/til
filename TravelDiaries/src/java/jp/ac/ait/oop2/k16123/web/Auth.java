/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k16123.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jp.ac.ait.oop2.k16123.web.database.User;

/**
 *
 * @author yoshikawa
 */
/**
 * Session認証
 */
public class Auth {
    // ログイン済みユーザーデータ保存キー 
    public static final String LOGGEDIN_USER_SESSION_KEY = "LoggedInUser";
    /**
     * ログイン済みユーザーの取得
     * @param request
     * @return
     */
    public static User getLoggedInUserOrNull(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        if (session.getAttribute(LOGGEDIN_USER_SESSION_KEY) == null ||
                !(session.getAttribute(LOGGEDIN_USER_SESSION_KEY) instanceof User)) {
            return null;
        }
        User loggedInUser = (User)session.getAttribute(LOGGEDIN_USER_SESSION_KEY);
        return loggedInUser;
    }
    /**
     * ログイン済みユーザーのセット
     * @param request
     * @param user
     */
    public static void setLoggedInUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(LOGGEDIN_USER_SESSION_KEY, user);
    }
}
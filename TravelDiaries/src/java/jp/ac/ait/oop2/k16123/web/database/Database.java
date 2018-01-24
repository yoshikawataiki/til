/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k16123.web.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * データベース管理クラス
 */
public class Database {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/TravelDiaries";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    //<editor-fold desc="ユーザー関連SQL">
    /**
     * Userテーブルから全件取得
     *
     * @return User List
     */
    public static List<User> selectAllUsers() {

        List<User> result = new ArrayList<>();

        //Step.1 JDBCドライバが参照できるかを確認
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //JDBCドライバが読み込めなかったときの処理をここに書く
            //通常はデータベースの操作自体不能となるため、プログラムは続行不能となります。
            e.printStackTrace();
            return null;
        }

        //データベースへのコネクション
        //Step.2 データベースへ接続
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {

            //Step.3 SQLの発行
            /* ここで必要なデータの抽出や更新処理などをSQLで発行します */
            //例えば、勇者の名前だけを列挙する
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    id, ");
            sql.append("    name, ");
            sql.append("    password ");
            sql.append("FROM ");
            sql.append("    User ");

            PreparedStatement stm = connection.prepareStatement(sql.toString());

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                result.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("password")
                        )
                );
            }

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return null;
        }
        //connectionは自動的に切断され、破棄されます。
        return result;
    }

    /**
     * IDを指定してUserテーブルから1件取得
     *
     * @param id
     * @return
     */
    public static User selectUserById(int id) {

        User result = null;

        //Step.1 JDBCドライバが参照できるかを確認
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //JDBCドライバが読み込めなかったときの処理をここに書く
            //通常はデータベースの操作自体不能となるため、プログラムは続行不能となります。
            e.printStackTrace();
            return null;
        }

        //データベースへのコネクション
        //Step.2 データベースへ接続
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {

            //Step.3 SQLの発行
            /* ここで必要なデータの抽出や更新処理などをSQLで発行します */
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    id, ");
            sql.append("    name, ");
            sql.append("    password ");
            sql.append("FROM ");
            sql.append("    User ");
            sql.append("WHERE ");
            sql.append("    id = ? ");

            PreparedStatement stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                result = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return null;
        }
        //connectionは自動的に切断され、破棄されます。
        return result;
    }

    /**
     * Userテーブルにデータ1件挿入
     *
     * @param u
     * @return
     */
    public static boolean insertUser(User u) {

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
            stm.setString(1, u.getName());
            stm.setString(2, u.getPassword());

            result = stm.executeUpdate();

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return false;
        }
        //connectionは自動的に切断され、破棄されます。

        return result >= 0; // 更新件数が1件以上かどうかで更新処理の成功判定を行う
    }
    //</editor-fold>

    /**
     * 現金出納帳のユーザーID指定での取得
     *
     * @return
     */
    public static List<Article> selectArticleByUserId(int user_id) {

        List<Article> result = new ArrayList<>();

        //Step.1 JDBCドライバが参照できるかを確認 
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //JDBCドライバが読み込めなかったときの処理をここに書く 
            //通常はデータベースの操作自体不能となるため、プログラムは続行不能となります。 
            e.printStackTrace();
            return null;
        }

        //データベースへのコネクション 
        //Step.2 データベースへ接続 
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {

            //Step.3 SQLの発行 
            /* ここで必要なデータの抽出や更新処理などをSQLで発行します */
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    * ");
            sql.append("FROM ");
            sql.append("    Articles ");
            sql.append("WHERE ");
            sql.append("    user_id = ? ");

            PreparedStatement stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, user_id);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                result.add(
                        new Article(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getString("title"),
                                rs.getString("detail"),
                                rs.getString("image"),
                                rs.getString("category"),
                                rs.getDate("created").toLocalDate()
                        )
                );
            }

        } catch (SQLException e) {
            //接続失敗時の処理を書きます 
            e.printStackTrace();
            return null;
        }
        //connectionは自動的に切断され、破棄されます。 
        return result;
    }

    /**
     * 現金出納帳の全件取得
     *
     * @return
     */
    public static List<Article> selectAllArticles() {

        List<Article> result = new ArrayList<>();

        //Step.1 JDBCドライバが参照できるかを確認
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //JDBCドライバが読み込めなかったときの処理をここに書く
            //通常はデータベースの操作自体不能となるため、プログラムは続行不能となります。
            e.printStackTrace();
            return null;
        }

        //データベースへのコネクション
        //Step.2 データベースへ接続
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {

            //Step.3 SQLの発行
            /* ここで必要なデータの抽出や更新処理などをSQLで発行します */
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    * ");
            sql.append("FROM ");
            sql.append("    Articles ");
            PreparedStatement stm = connection.prepareStatement(sql.toString());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                result.add(
                        new Article(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getString("title"),
                                rs.getString("detail"),
                                rs.getString("image"),
                                rs.getString("category"),
                                rs.getDate("created").toLocalDate()
                        )
                );
            }

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return null;
        }
        //connectionは自動的に切断され、破棄されます。
        return result;
    }

    /**
     * 現金出納帳の1件取得
     *
     * @return
     */
    public static Article selectArticleById(int id) {

        Article result = null;

        //Step.1 JDBCドライバが参照できるかを確認
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //JDBCドライバが読み込めなかったときの処理をここに書く
            //通常はデータベースの操作自体不能となるため、プログラムは続行不能となります。
            e.printStackTrace();
            return null;
        }

        //データベースへのコネクション
        //Step.2 データベースへ接続
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {

            //Step.3 SQLの発行
            /* ここで必要なデータの抽出や更新処理などをSQLで発行します */
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    * ");
            sql.append("FROM ");
            sql.append("    Articles ");
            sql.append("WHERE ");
            sql.append("    id = ? ");

            PreparedStatement stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                result = new Article(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("detail"),
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getDate("created").toLocalDate()
                );
            }

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return null;
        }
        //connectionは自動的に切断され、破棄されます。
        return result;
    }

    /**
     * 記事テーブルにデータ1件挿入
     *
     * @param c
     * @return
     */
    public static boolean insertArticle(Article c) {

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
            sql.append("    Articles ");
            sql.append("( ");
            sql.append("    user_id, ");
            sql.append("    title, ");
            sql.append("    detail, ");
            sql.append("    image, ");
            sql.append("    category, ");
            sql.append("    `created` ");
            sql.append(") VALUES ( ");
            sql.append("    ?, ");
            sql.append("    ?, ");
            sql.append("    ?, ");
            sql.append("    ?, ");
            sql.append("    ?, ");
            sql.append("    ? ");
            sql.append(") ");

            PreparedStatement stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, c.getUser_id());
            stm.setString(2, c.getTitle());
            stm.setString(3, c.getDetail());
            stm.setString(4, c.getImage());
            stm.setString(5, c.getCategory());
            stm.setDate(6, java.sql.Date.valueOf(c.getCreated()));

            result = stm.executeUpdate();

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return false;
        }
        //connectionは自動的に切断され、破棄されます。

        return result >= 0; // 更新件数が1件以上かどうかで更新処理の成功判定を行う
    }

    /**
     * 現金出納帳テーブルデータを1件更新
     *
     * @param c
     * @return
     */
    public static boolean UpdateArticle(Article c) {

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
            sql.append("UPDATE ");
            sql.append("    Articles ");
            sql.append("SET ");
            sql.append("    user_id = ?, ");
            sql.append("    title = ?, ");
            sql.append("    detail = ?, ");
            sql.append("    image = ?, ");
            sql.append("    category = ?, ");
            sql.append("    `created` = ? ");
            sql.append("WHERE ");
            sql.append("    id = ? ");

            PreparedStatement stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, c.getUser_id());
            stm.setString(2, c.getTitle());
            stm.setString(3, c.getDetail());
            stm.setString(4, c.getTitle());
            stm.setString(5, c.getImage());
            stm.setDate(6, java.sql.Date.valueOf(c.getCreated()));
            // WHERE の条件指定
            stm.setInt(7, c.getId());

            result = stm.executeUpdate();

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return false;
        }
        //connectionは自動的に切断され、破棄されます。

        return result >= 0; // 更新件数が1件以上かどうかで更新処理の成功判定を行う
    }

    /**
     * 現金出納帳テーブルデータを1件削除
     *
     * @param c
     * @return
     */
    public static boolean deleteArticle(Article c) {

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
            sql.append("DELETE FROM ");
            sql.append("    Articles ");
            sql.append("WHERE ");
            sql.append("    id = ? ");

            PreparedStatement stm = connection.prepareStatement(sql.toString());
            // WHERE の条件指定
            stm.setInt(1, c.getId());

            result = stm.executeUpdate();

        } catch (SQLException e) {
            //接続失敗時の処理を書きます
            e.printStackTrace();
            return false;
        }
        //connectionは自動的に切断され、破棄されます。

        return result >= 0; // 更新件数が1件以上かどうかで更新処理の成功判定を行う
    }
}

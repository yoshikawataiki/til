/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k16123.web.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author yoshikawa
 */
public class Encrypt {
   /**
     * アルゴリズムを指定してハッシュ関数を実行
     * MD2,MD5,SHA-1,SHA-256,SHA-384,SHA-512 に対応
     *
     * 暗号化文 = Encrypt.toHashValue("SHA-256", 暗号化したい文字列);
     * のように使用します。
     * @param algorithmName
     * @param value
     * @return
     */
    public static String toHashValue(String algorithmName, String value) {
        MessageDigest md = null;
        StringBuilder sb = null;
        try {
            md = MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
 
        md.update(value.getBytes());
 
        sb = new StringBuilder();
        for (byte b : md.digest()) {
            String hex = String.format("%02x", b);
            sb.append(hex);
        }
        return sb.toString();
    }
}

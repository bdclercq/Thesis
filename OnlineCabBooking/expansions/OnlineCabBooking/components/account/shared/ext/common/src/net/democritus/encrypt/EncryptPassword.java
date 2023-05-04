package net.democritus.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// @feature:password-encryption
public class EncryptPassword {

  public static String encrypt(String userName, String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(userName.getBytes());
      md.update(password.getBytes());

      byte[] digest = md.digest();

      return Base64.encode(digest);
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }

}

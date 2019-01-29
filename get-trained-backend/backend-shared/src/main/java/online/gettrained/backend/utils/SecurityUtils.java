package online.gettrained.backend.utils;

import online.gettrained.backend.domain.user.CurrentUser;
import online.gettrained.backend.domain.user.User;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.IntStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private static final List<Integer> VALID_PWD_CHARS = new ArrayList<>();

  public static User getCurrentUserOrException() {
    User user = getCurrentUser();
    if (user == null) {
      throw new IllegalStateException("Access forbidden");
    }

    return user;
  }

  public static User getCurrentUser() {
    if (SecurityContextHolder.getContext() != null
        && SecurityContextHolder.getContext().getAuthentication() != null
        && SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal() instanceof CurrentUser) {
      return ((CurrentUser) SecurityContextHolder.getContext()
          .getAuthentication()
          .getPrincipal()).getUser();
    }
    return null;
  }

  public static void setCurrentUser(User user) {
    ((CurrentUser) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal())
        .setUser(user);
  }

  static {
    IntStream.rangeClosed('0', '9').forEach(VALID_PWD_CHARS::add); // 0-9
    IntStream.rangeClosed('A', 'Z').forEach(VALID_PWD_CHARS::add); // A-Z
    IntStream.rangeClosed('a', 'z').forEach(VALID_PWD_CHARS::add); // a-z
    IntStream.rangeClosed('!', '*').forEach(VALID_PWD_CHARS::add); // !-*
  }

  public static String genPassword(int passwordLength) {
    StringBuilder psw = new StringBuilder();

    new SecureRandom().ints(passwordLength, 0, VALID_PWD_CHARS.size()).map(VALID_PWD_CHARS::get)
        .forEach(s -> psw.append((char) s));

    return psw.toString();
  }

  public static String[] rsaEncrypt(String text) throws Exception {
    String[] result = new String[2];
    // prepare key
    KeyGenerator keygen = KeyGenerator.getInstance("AES");
    SecretKey aesKey = keygen.generateKey();
    result[0] = Base64.getEncoder().encodeToString(aesKey.getEncoded());

    // cipher engine
    Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

    // cipher input
    aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
    byte[] clearTextBuff = text.getBytes();
    byte[] cipherTextBuff = aesCipher.doFinal(clearTextBuff);

    result[1] = Base64.getEncoder().encodeToString(cipherTextBuff);
    return result;
  }

  public static String rsaDecrypt(String key, String text) throws Exception {
    byte[] aesKeyBuff = Base64.getDecoder().decode(key);
    SecretKey aesDecryptKey = new SecretKeySpec(aesKeyBuff, "AES");
    // cipher engine
    Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    // decipher input
    aesCipher.init(Cipher.DECRYPT_MODE, aesDecryptKey);
    byte[] decipheredBuff = aesCipher.doFinal(Base64.getDecoder().decode(text));
    return new String(decipheredBuff);
  }

  public static String getIpOfRemoteHost(HttpServletRequest request) {
    String remoteAddress = request.getHeader("X-FORWARDED-FOR");
    if (remoteAddress == null || "".equals(remoteAddress)) {
      remoteAddress = request.getRemoteAddr();
    }
    return remoteAddress;
  }

  public static void main(String[] args) {
    System.out.println("---Generated Password---");
    for (int i = 0; i < 5; i++) {
      System.out.println(genPassword(8));
    }
  }
}

package cloud.weather.server.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaCrypt {

    public static String hash(String password) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(password.getBytes());
            byte byteData[] = sha256.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

}

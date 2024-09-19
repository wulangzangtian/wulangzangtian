package wu.lang.wedding.utils;

import java.security.MessageDigest;


public class Md5Util {

    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes("UTF-8"));
            StringBuilder ret = new StringBuilder(bytes.length << 1);
            for (byte b : bytes) {
                ret.append(Character.forDigit((b >> 4) & 0xf, 16));
                ret.append(Character.forDigit(b & 0xf, 16));
            }
            return ret.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



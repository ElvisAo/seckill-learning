package everett.ao.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    // 第一次md5，必须写死，用于加密后的数据传输
    private static final String salt = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    // 第一次加密：将用户输入的密码转换为提交的密码。如果前面不加引号，会导致在java中char+char->int
    public static String inputPass2submitPass(String inputPass) {
        String s = "" + salt.charAt(0) + salt.charAt(2) + salt.charAt(7) + inputPass + salt.charAt(6) + salt.charAt(5) + salt.charAt(1);
        System.out.println(s);
        return md5(s);
    }

    // 第二次加密：将用户提交的数据再次加密存库
    public static String submitPass2dbPass(String submitPass, String saltInDB) {
        String s = "" + saltInDB.charAt(0) + saltInDB.charAt(2) + saltInDB.charAt(7) + submitPass + saltInDB.charAt(6) + saltInDB.charAt(5) + saltInDB.charAt(1);
        return md5(s);
    }


    public static void main(String[] args) {
        String password = "12345678";
        String salt = "1a2b3c4d";
        String submitP = inputPass2submitPass(password);
        System.out.println(submitP);
        System.out.println(submitPass2dbPass(submitP, salt));
    }
}

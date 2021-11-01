package everett.ao.seckill.utils;

import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final Pattern pattern = Pattern.compile("1\\d{10}");

    public static Boolean mobileValidate(String mobile) {
        return pattern.matcher(mobile).matches();
    }

    public static void main(String[] args) {
        String s = "28323954185";
        System.out.println(mobileValidate(s));
    }
}

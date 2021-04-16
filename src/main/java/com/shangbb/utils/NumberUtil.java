package com.shangbb.utils;

/**
 * @Author benben.shang
 * @Date 2021/4/9 14:46
 */
public class NumberUtil {
    /**
     * 二进制转十进制
     *
     * @param number
     * @return
     */
    public static long binary2Decimal(String number) {
        return scale2Decimal(number, 2);
    }

    /**
     * 其他进制转十进制
     *
     * @param number
     * @return
     */
    public static long scale2Decimal(String number, int scale) {
        checkNumber(number);
        if (2 > scale || scale > 32) {
            throw new IllegalArgumentException("scale is not in range");
        }
        // 不同其他进制转十进制,修改这里即可
        long total = 0;
        String[] ch = number.split("");
        int chLength = ch.length;
        for (int i = 0; i < chLength; i++) {
            total += Integer.valueOf(ch[i]) * Math.pow(scale, chLength - 1 - i);
        }
        return total;

    }

    /**
     * 二进制转十进制
     *
     * @param number
     * @return
     */
    public static String decimal2Binary(long number) {
        return decimal2Scale(number, 2);
    }

    /**
     * 十进制转其他进制
     *
     * @param number
     * @param scale
     * @return
     */
    public static String decimal2Scale(long number, int scale) {
        if (2 > scale || scale > 32) {
            throw new IllegalArgumentException("scale is not in range");
        }
        String result = "";
        while (0 != number) {
            result = number % scale + result;
            number = number / scale;
        }

        return result;
    }

    public static void checkNumber(String number) {
        String regexp = "^\\d+$";
        if (null == number || !number.matches(regexp)) {
            throw new IllegalArgumentException("input is not a number");
        }
    }
}

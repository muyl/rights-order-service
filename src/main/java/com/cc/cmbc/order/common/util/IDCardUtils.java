package com.cc.cmbc.order.common.util;

import java.util.Calendar;

/**
 * @author 拓仲 on 2020/3/28
 */
public class IDCardUtils {

    /**
     * 15位的身份证号码转换成18位
     *
     * @param idCardNo 待转换的 15 位身份证号码
     * @return
     */
    public static String from15to18(String idCardNo) {
        String finalID = null;
        if (!isIdCardNo(idCardNo)) throw new IllegalArgumentException("您输入的身份证号码不正确:" + idCardNo);
        String century = "19";
        if (idCardNo.length() == 18) {
            finalID = idCardNo;
        } else {
            // 对应的17位的各个系数
            int[] weight = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

            // 通过加入世纪码, 变成 17 位的新号码本体.
            String tempNo = idCardNo.substring(0, 6) + century + idCardNo.substring(6);

            // 下面算最后一位校验码
            int checkSum = 0;
            for (int i = 0; i < weight.length; i++) {
                int ai = Integer.parseInt("" + tempNo.charAt(i)); // 位于 i 位置的数值
                checkSum = checkSum + ai * weight[i];
            }
            // 求余数
            int checkNum = checkSum % 11;
            String lastNumber = null;
            switch (checkNum) {
                case 0:
                    lastNumber = "1";
                    break;
                case 1:
                    lastNumber = "0";
                    break;
                case 2:
                    lastNumber = "X";
                    break;
                case 3:
                    lastNumber = "9";
                    break;
                case 4:
                    lastNumber = "8";
                    break;
                case 5:
                    lastNumber = "7";
                    break;
                case 6:
                    lastNumber = "6";
                    break;
                case 7:
                    lastNumber = "5";
                    break;
                case 8:
                    lastNumber = "4";
                    break;
                case 9:
                    lastNumber = "3";
                    break;
                case 10:
                    lastNumber = "2";
                    break;
            }
            finalID = tempNo + lastNumber;
        }

        return finalID;

    }

    /**
     * 18位的身份证号码转换成15位
     *
     * @param idCardNo18
     * @return
     */
    public static String from18to15(String idCardNo18) {
        if (!(isIdCardNo(idCardNo18) && idCardNo18.length() == 18)) {
            throw new IllegalArgumentException("身份证号参数格式不正确！");
        }
        return idCardNo18.substring(0, 6) + idCardNo18.substring(8, 17);
    }

    /**
     * 传入18位的身份证号,得到生日,格式:19880101
     *
     * @param idCardNo18
     * @return
     */
    public static String getBirthday(String idCardNo18) {
        return idCardNo18.substring(6, 14);
    }

    /**
     * 根据身份证号码获取年龄
     *
     * @param idCardNo18
     * @return
     */
    public static int getAge(String idCardNo18) {
        idCardNo18 = from15to18(idCardNo18);
        // 当前年份
        Calendar instance = Calendar.getInstance();
        int currentYear = instance.get(Calendar.YEAR);
        int age = currentYear - Integer.parseInt(getBirthYear(idCardNo18));
        return age;
    }

    /**
     * 传入身份证号,得到性别
     *
     * @param idCardNo18
     * @return
     */
    public static String getSex(String idCardNo18) {
        idCardNo18 = from15to18(idCardNo18);
        // 定位到倒数第二位
        char c = idCardNo18.charAt(16);
        String sex = "";
        if (c % 2 != 0) {
            sex = "男";
        } else {
            sex = "女";
        }
        return sex;
    }

    /**
     * 传入身份证号,获取出生年份
     *
     * @param idCardNo18
     * @return
     */
    private static String getBirthYear(String idCardNo18) {
        return idCardNo18.substring(6, 10);
    }

    /**
     * 判断输入的身份证号码是否符合身份证号的要求
     *
     * @param idCardNO
     * @return
     */
    public static boolean isIdCardNo(String idCardNO) {
        boolean isID = false;
        if (idCardNO == null) {
            return false;
        }
        int len = idCardNO.length();
        if (len != 15 && len != 18) {
            isID = false;
        } else {
            // 排除最后一位是:X的情况
            for (int i = 0; i < len; i++) {
                try {
                    if (i < len - 1) {
                        Integer.parseInt("" + idCardNO.charAt(i));
                    } else if ((idCardNO.charAt(i) + "").equalsIgnoreCase("X") || Integer.parseInt("" + idCardNO.charAt(i)) > -1) {
                        isID = true;
                    } else {
                        isID = false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return isID;
    }

    public static void main(String[] args) {
        System.out.println(getSex(from15to18("110104830512301")));
    }
}

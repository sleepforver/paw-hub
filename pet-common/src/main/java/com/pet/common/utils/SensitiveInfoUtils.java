package com.pet.common.utils;

import org.apache.commons.lang3.StringUtils;
import java.util.regex.Pattern;

/**
 * 敏感信息脱敏工具类
 * 支持手机号、身份证、银行卡、邮箱、地址等脱敏
 */
public class SensitiveInfoUtils {

    /**
     * 通用脱敏方法
     * @param value 原始值
     * @param start 开始保留的字符数
     * @param end 结束保留的字符数
     * @param maskChar 脱敏字符
     * @return 脱敏后的字符串
     */
    public static String mask(String value, int start, int end, char maskChar) {
        if (StringUtils.isBlank(value)) {
            return value;
        }

        int length = value.length();
        if (length <= start + end) {
            // 如果字符串太短，只保留第一个字符
            return maskCharString(1, length - 1, maskChar, value);
        }

        String startStr = value.substring(0, start);
        String endStr = value.substring(length - end);
        String middleMask = String.valueOf(maskChar).repeat(length - start - end);

        return startStr + middleMask + endStr;
    }

    /**
     * 手机号脱敏
     * 格式：186****1234
     */
    public static String maskMobile(String mobile) {
        if (StringUtils.isBlank(mobile) || mobile.length() < 7) {
            return mobile;
        }
        return mask(mobile, 3, 4, '*');
    }

    /**
     * 身份证号脱敏
     * 格式：370101********1234
     */
    public static String maskIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return idCard;
        }

        int length = idCard.length();
        if (length == 15) {
            return mask(idCard, 6, 3, '*');
        } else if (length == 18) {
            return mask(idCard, 6, 4, '*');
        }
        return mask(idCard, 6, 4, '*');
    }

    /**
     * 银行卡号脱敏
     * 格式：622848********1234
     */
    public static String maskBankCard(String bankCard) {
        if (StringUtils.isBlank(bankCard) || bankCard.length() < 8) {
            return bankCard;
        }
        return mask(bankCard, 6, 4, '*');
    }

    /**
     * 姓名脱敏
     * 格式：张*、欧阳**
     */
    public static String maskName(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        }

        int length = name.length();
        if (length == 1) {
            return name;
        } else if (length == 2) {
            return name.charAt(0) + "*";
        } else {
            StringBuilder masked = new StringBuilder();
            masked.append(name.charAt(0));
            for (int i = 1; i < length - 1; i++) {
                masked.append("*");
            }
            masked.append(name.charAt(length - 1));
            return masked.toString();
        }
    }

    /**
     * 邮箱脱敏
     * 格式：te***@example.com
     */
    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email) || !email.contains("@")) {
            return email;
        }

        int atIndex = email.indexOf("@");
        String prefix = email.substring(0, atIndex);
        String suffix = email.substring(atIndex);

        if (prefix.length() <= 1) {
            return "*" + suffix;
        }

        String maskedPrefix = prefix.charAt(0) +
                "*".repeat(Math.max(0, prefix.length() - 2)) +
                (prefix.length() > 1 ? prefix.charAt(prefix.length() - 1) : "");
        return maskedPrefix + suffix;
    }

    /**
     * 地址脱敏
     * 格式：北京市海淀区****
     */
    public static String maskAddress(String address) {
        if (StringUtils.isBlank(address)) {
            return address;
        }

        int length = address.length();
        if (length <= 8) {
            return address.charAt(0) + "*".repeat(Math.max(0, length - 1));
        }

        // 保留前4位
        String start = address.substring(0, 4);
        String mask = "*".repeat(Math.min(8, length - 4));

        // 如果地址较长，保留最后几位
        if (length > 12) {
            String end = address.substring(length - 4);
            return start + mask + end;
        }

        return start + mask;
    }

    /**
     * 密码脱敏
     * 格式：********
     */
    public static String maskPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        return "********";
    }

    /**
     * 固定电话脱敏
     * 格式：010-****1234
     */
    public static String maskPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return phone;
        }

        if (phone.contains("-")) {
            String[] parts = phone.split("-");
            if (parts.length == 2) {
                return parts[0] + "-****" +
                        (parts[1].length() > 4 ? parts[1].substring(parts[1].length() - 4) : parts[1]);
            }
        }

        return mask(phone, 4, 4, '*');
    }

    /**
     * 根据类型自动脱敏
     */
    public static String autoMask(String type, String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }

        switch (type.toLowerCase()) {
            case "mobile":
            case "phone":
            case "tel":
                return maskMobile(value);
            case "idcard":
            case "id":
            case "identity":
                return maskIdCard(value);
            case "bankcard":
            case "card":
            case "account":
                return maskBankCard(value);
            case "name":
            case "realname":
                return maskName(value);
            case "email":
            case "mail":
                return maskEmail(value);
            case "address":
            case "addr":
                return maskAddress(value);
            case "password":
            case "pwd":
                return maskPassword(value);
            default:
                return value;
        }
    }

    /**
     * 使用正则表达式匹配并脱敏
     * @param text 包含敏感信息的文本
     * @return 脱敏后的文本
     */
    public static String maskByPattern(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }

        // 手机号正则
        String mobilePattern = "(?<!\\d)(1[3-9]\\d{9})(?!\\d)";
        text = Pattern.compile(mobilePattern)
                .matcher(text)
                .replaceAll(match -> maskMobile(match.group()));

        // 身份证正则（简化版）
        String idCardPattern = "([1-9]\\d{5})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9Xx])";
        text = Pattern.compile(idCardPattern)
                .matcher(text)
                .replaceAll("$1********$5$6");

        // 邮箱正则
        String emailPattern = "([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})";
        text = Pattern.compile(emailPattern)
                .matcher(text)
                .replaceAll(match -> maskEmail(match.group()));

        return text;
    }

    /**
     * 脱敏JSON字符串中的敏感信息
     */
    public static String maskJson(String json) {
        if (StringUtils.isBlank(json)) {
            return json;
        }

        // 简单实现，实际项目中可以使用JSON解析库
        String[] sensitiveFields = {"mobile", "phone", "idCard", "identity", "bankCard",
                "cardNo", "account", "name", "realName", "email",
                "address", "password", "pwd"};

        String result = json;
        for (String field : sensitiveFields) {
            // 匹配JSON字段并脱敏
            String pattern = "\"" + field + "\"\\s*:\\s*\"([^\"]+)\"";
            result = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
                    .matcher(result)
                    .replaceAll(match -> {
                        String value = match.group(1);
                        return "\"" + field + "\":\"" + autoMask(field, value) + "\"";
                    });
        }

        return result;
    }

    /**
     * 检查是否为敏感字段
     */
    public static boolean isSensitiveField(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return false;
        }

        String lowerField = fieldName.toLowerCase();
        return lowerField.contains("mobile") || lowerField.contains("phone") ||
                lowerField.contains("id") || lowerField.contains("card") ||
                lowerField.contains("name") || lowerField.contains("email") ||
                lowerField.contains("mail") || lowerField.contains("address") ||
                lowerField.contains("addr") || lowerField.contains("password") ||
                lowerField.contains("pwd");
    }

    private static String maskCharString(int start, int end, char maskChar, String value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            if (i < start || i >= value.length() - end) {
                result.append(value.charAt(i));
            } else {
                result.append(maskChar);
            }
        }
        return result.toString();
    }
}

package com.jy.im.common.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private static final String MD5 = "MD5";

    public static String encryptPassword(String source) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(MD5);
        messageDigest.update(source.getBytes());
        return DatatypeConverter.printHexBinary(messageDigest.digest()).toLowerCase();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(encryptPassword("123456"));
    }
}

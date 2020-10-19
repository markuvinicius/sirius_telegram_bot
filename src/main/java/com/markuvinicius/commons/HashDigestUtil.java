package com.markuvinicius.commons;

import org.springframework.util.DigestUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashDigestUtil {
    public static final String md5Digest(String from) {
        return DigestUtils.md5DigestAsHex(from.getBytes()).toUpperCase();
    }

}

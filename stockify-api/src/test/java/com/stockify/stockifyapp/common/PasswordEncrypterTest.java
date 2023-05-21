package com.stockify.stockifyapp.common;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncrypterTest{
    private String pwd;
    private PasswordEncrypter pe;
    private String enPwd;


    @Test
    public void testEncrypt() throws Exception {
        pwd = "password";
        pe = new PasswordEncrypter();
        enPwd = pe.encrypt(pwd);
        assertNotEquals(pwd,enPwd);
    }
    @Test
    public void testDecrypt() throws Exception {
        pwd = "TestString";
        pe = new PasswordEncrypter();
        assertEquals(pwd,pe.decrypt(pe.encrypt(pwd)));
    }
}
package tech.aiflowy.codegen;

import tech.aiflowy.common.util.HashUtil;

public class PasswordGen {

    public static void main(String[] args) {
        String password = "123456";
        String salt = HashUtil.generateSalt(24);
        String hashPassword = HashUtil.sha256(salt + password);
        System.out.println("salt: " + salt);
        System.out.println("password: " + password);
        System.out.println("hashPassword: " + hashPassword);
    }


}

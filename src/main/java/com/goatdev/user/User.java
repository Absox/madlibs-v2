package com.goatdev.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.buf.HexUtils;

import javax.persistence.*;
import java.security.SecureRandom;

/**
 * User class.
 * Created by ran on 4/19/16.
 */
@Entity
@Table(name = "users")
public class User {

    // Number of bytes for salt.
    private static final int SALT_LENGTH = 16;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password_digest")
    private String passwordDigest;

    @Column(name = "salt")
    private String salt;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.passwordDigest = saltAndHash(password, generateSalt());
    }

    private byte[] generateSalt() {
        SecureRandom rng = new SecureRandom();
        byte[] saltBytes = new byte[SALT_LENGTH];
        rng.nextBytes(saltBytes);

        this.salt = HexUtils.toHexString(saltBytes);
        return saltBytes;
    }

    public void changePassword(String password) {
        this.passwordDigest = saltAndHash(password, generateSalt());
    }

    public boolean authenticate(String password) {
        byte[] saltBytes = HexUtils.fromHexString(salt);
        return (saltAndHash(password, saltBytes).equals(passwordDigest));
    }

    private static String saltAndHash(String password, byte[] saltBytes) {
        byte[] passwordRawBytes = password.getBytes();
        byte[] saltedPasswordBytes = concatenate(passwordRawBytes, saltBytes);
        return DigestUtils.sha512Hex(saltedPasswordBytes);
    }

    private static byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        for (int c = 0; c < a.length + b.length; c++) result[c] = c < a.length ? a[c] : b[c - a.length];
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}

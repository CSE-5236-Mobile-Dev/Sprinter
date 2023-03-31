package com.pearlsea.sprinter.db.User;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import android.util.Base64;
import android.util.Log;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String uid;

    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    @ColumnInfo(name = "email")
    @NonNull
    public String email;

    @ColumnInfo(name = "password")
    @NonNull
    public String password;

    @ColumnInfo(name = "gender")
    @Nullable
    public Character gender;

    @ColumnInfo(name = "age")
    @Nullable
    public Integer age;

    @ColumnInfo(name = "weight")
    @Nullable
    public Integer weight;

    @ColumnInfo(name = "height")
    @Nullable
    public Integer height;

    @Ignore
    public User(String name, String email, String password)
    {
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        try {
            this.password = encrypt(password);
        } catch (Exception e)
        {
            Log.e("MainActivity", "Error in Encrypting the Password for User");
        }
    }

    public User(@NonNull String uid, @NonNull String name, @NonNull String email, @NonNull String password, Character gender, Integer age, Integer weight, Integer height) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        try {
            this.password = encrypt(password);
        } catch (Exception e)
        {
            Log.e("MainActivity", "Error in Encrypting the Password for User");
        }
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public void updateUser(Character gender, Integer age, Integer weight, Integer height) {
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String SECRET_KEY = "mysecretkey12345";

    public User() {

    }

    public static String encrypt(String input) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ENCRYPTION_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    @Override
    public String toString() {
        return String.format("UID: %s, Name: %s, E-Mail %s, Password Hash: %s", this.uid, this.name, this.email, this.password);
    }

    public void setEmail(String email) {
    }

    public void setPassword(String pass) {
    }
}

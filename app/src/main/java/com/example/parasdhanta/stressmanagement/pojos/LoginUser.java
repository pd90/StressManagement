package com.example.parasdhanta.stressmanagement.pojos;

import java.io.Serializable;

/**
 * Created by Paras Dhanta on 10/3/2016.
 */
public class LoginUser implements Serializable {
    private static String emailId;
    private static String userName;
    private static String userId;
    private static String imageUrl;

    public static String getEmailId() {
        return emailId;
    }

    public static void setEmailId(String emailId) {
        LoginUser.emailId = emailId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        LoginUser.userName = userName;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        LoginUser.userId = userId;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        LoginUser.imageUrl = imageUrl;
    }
}

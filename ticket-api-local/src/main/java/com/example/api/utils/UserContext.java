package com.example.api.utils;


public class UserContext {
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void setUserInfo(String userid) {
        userContext.set(userid);
    }

    public static String getUserInfo() {
        return    userContext.get() == null? "2222" : userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}

package com.sasadev.todo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthUtils {

    public static boolean isUserSignedIn(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);
        return username!=null;

    }

    public static String getUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferences.getString("username",null);
    }

    public static void signOut(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}

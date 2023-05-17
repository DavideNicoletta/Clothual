package com.example.clothual.Util;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceReadWrite {

    private final Application application;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public SharePreferenceReadWrite(Application application) {
        this.application = application;
        this.sharedPref = application.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
    }

    public void writeString(String id, String string){
        editor = sharedPref.edit();
        editor.putString(id, string);
        editor.apply();
    }

    public void writeInt(String id, int data){
        editor = sharedPref.edit();
        editor.putInt(id, data);
        editor.apply();
    }

    public void writeBoolean(String id, boolean bool){
        editor = sharedPref.edit();
        editor.putBoolean(id, bool);
        editor.apply();
    }

    public String readString(String id){
        return sharedPref.getString(id, "");
    }

    public int readInt(String id){
        return sharedPref.getInt(id, 0);
    }

    public boolean readBoolean(String id){
        return sharedPref.getBoolean(id, false);
    }

}

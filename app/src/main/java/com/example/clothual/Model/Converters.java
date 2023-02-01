package com.example.clothual.Model;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    public static List<String> fromString(String string){

        if(string.equals("")){
            return null;
        }
        char [] charSequence = string.toCharArray();
        List<String> getList = new ArrayList<>();

        String support = "";
        if(charSequence.length == 1){
            getList.add(String.valueOf(charSequence[0]));
            return getList;
        }
        for(int i = 0; i < charSequence.length; i++){
            if(charSequence[i] == '-'){
                getList.add(support);
                support = "";
            }else{
                support += charSequence[i];
            }
        }
        getList.add(support);
        return getList;

    }

}

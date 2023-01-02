package com.example.clothual.UI.core.Personal.History;

import android.app.Application;

import com.example.clothual.Database.ClothualDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Model.Clothual;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryModel {

    public Application application;
    public RoomDatabase database;
    private ClothualDao clothualDao;

    public HistoryModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        clothualDao = database.clothualDao();
    }

    public float[] getRateTypeClothual(){
        List<Clothual> clothualList = clothualDao.getAllClothual();
        float [] rate = {0, 0, 0, 0, 0};
        for(int i = 0; i < clothualList.size(); i++){
            switch (clothualList.get(i).getType()){
                case 1:
                    rate[0] = rate[0]+1;
                    break;
                case 2:
                    rate[1] = rate[1]+1;
                    break;
                case 3:
                    rate[2] = rate[2]+1;
                    break;
                case 4:
                    rate[3] = rate[3]+1;
                    break;
                case 5:
                    rate[4] = rate[4]+1;
            }

        }
        System.out.println("rate1: " + rate[0] + ", Rate2: " + rate[1]
                + ", Rate3: " + rate[2] + ", Rate4: " + rate[3] + ", Rate5: " + rate[4]);
        for(int i = 0; i < rate.length; i++){
            rate[i] = (rate[i]/clothualList.size()) * 100;
        }
        return rate;
    }



    public String[] getRateColor(){
        List<Clothual> clothualList = clothualDao.getAllClothual();
        String result[] = new String[2];
        HashMap<String, Integer> rate = new HashMap<String, Integer>();
        for(int i = 0; i < clothualList.size(); i++){
            if(rate.containsKey(clothualList.get(i).getColor())){
                Integer interim = rate.get(clothualList.get(i).getColor()) +1 ;
                rate.put(clothualList.get(i).getColor(), interim);
            }else{
                rate.put(clothualList.get(i).getColor(), 1);
            }
        }
        float max = (Collections.max(rate.values()));
        System.out.println(max);

        for(Map.Entry<String, Integer> entry : rate.entrySet()){
            if(entry.getValue() == max){
                result[0] = entry.getKey();
                result[1] = "" + (int)((max/clothualList.size())*100);
            }
        }

        return  result;
    }

}

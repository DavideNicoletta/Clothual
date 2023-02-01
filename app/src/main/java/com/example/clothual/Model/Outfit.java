package com.example.clothual.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Outfit {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Ignore
    private List<Clothual> clothualList;

    private String clothualString;
    private String date;


    public Outfit(String date) {
        this.date = date;
        this.clothualString = "";
        this.clothualList = new ArrayList<>();
    }

    public List<Clothual> getClothualList() {
        return clothualList;
    }

    public void setClothualList(Clothual clothual) {
        this.clothualList.add(clothual);
    }

    public String getClothualString() {
        return clothualString;
    }

    public void setClothualString(String clothualString) {
        this.clothualString = clothualString;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void converter(){

        if(clothualList.size() != 0) {
            String db = "" + clothualList.get(0).getId();
            for (int i = 1; i < clothualList.size(); i++) {


                db = db + "-" + clothualList.get(i).getId();

            }
            setClothualString(db);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void removeClothualList(){
        for(int i = 0; i < clothualList.size(); i++){
            clothualList.remove(clothualList.get(i));
        }
    }

    public void setClothualListByList(List<Clothual> clothualList){
        this.clothualList = clothualList;
    }

}

package com.example.clothual.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {

    private MutableLiveData<String> stringDate;

    public LiveData<String> getStringDatet() {
        if (stringDate == null) {
            stringDate = new MutableLiveData<String>();
            // aggiornamento dell'oggetto myObject
            updateMyObject();
        }
        return stringDate;
    }

    private void updateMyObject() {
// aggiorna in modalità asincrona myObject
    }

}

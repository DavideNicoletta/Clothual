package com.example.clothual.UI.core.Map;

import android.app.Application;
import android.content.Context;

import com.example.clothual.Data.Repository.Core.CoreRepository;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapModel {

    CoreRepository coreRepository;

    public MapModel(Application application){
        this.coreRepository = new CoreRepository(application);
    }

    public MyLocationNewOverlay getLocationOverlay(Context ctx, MapView mapView){
        return coreRepository.getLocationOverlay(ctx, mapView);
    }

}

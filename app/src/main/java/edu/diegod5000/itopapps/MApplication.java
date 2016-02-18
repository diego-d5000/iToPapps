package edu.diegod5000.itopapps;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by diego-d on 16/02/16.
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}

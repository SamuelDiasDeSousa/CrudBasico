package com.br.samuel.crudbasico;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by SAMUEL on 15/11/2017.
 */

public class CrudBasicoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(3000);
    }
}

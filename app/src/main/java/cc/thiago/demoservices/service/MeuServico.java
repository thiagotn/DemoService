package cc.thiago.demoservices.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by rm49824 on 08/12/2015.
 */
public class MeuServico extends Service {

    private static final String TAG = "DEMOSERVICE";

    private boolean isRunning = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Logica do servico
                for (int i = 0 ; i < 100; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    if (isRunning) {
                        Log.i(TAG, "Servico rodando " + i);
                    }
                }
                stopSelf();
            }
        }).start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.i(TAG, "OnDestroy");
    }
}

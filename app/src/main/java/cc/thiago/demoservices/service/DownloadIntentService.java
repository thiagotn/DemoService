package cc.thiago.demoservices.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by rm49824 on 08/12/2015.
 */
public class DownloadIntentService extends IntentService {

    public static final int DOWNLOAD_SUCESSO = 1;
    public static final int DOWNLOAD_ERRO = 0;

    public static final String FILE_PATH = "filePath";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadIntentService(String name) {
        super(DownloadIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        File file = new File(
                Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "imagemservice.png");

        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
            URL downloadUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) downloadUrl.openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Nao foi possivel baixar a imagem");
            }

            InputStream is = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int byteCount;

            while ((byteCount = is.read(buffer)) != -1) {
                out.write(buffer, 0, byteCount);
            }

            out.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

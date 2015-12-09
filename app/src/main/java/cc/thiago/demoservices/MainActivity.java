package cc.thiago.demoservices;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cc.thiago.demoservices.service.DownloadIntentService;
import cc.thiago.demoservices.service.MeuServico;

public class MainActivity extends AppCompatActivity {

    private EditText etUrlImagem;
    private ImageView ivImagem;
    private DownloadResultReceiver downloadResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUrlImagem = (EditText) findViewById(R.id.etUrlImagem);
        ivImagem = (ImageView) findViewById(R.id.ivImagem);
        downloadResultReceiver = new DownloadResultReceiver(new Handler());
    }

    public void iniciarServico(View view) {
        Intent service = new Intent(this, MeuServico.class);
        startService(service);
    }

    public void pararServico(View view) {
        Intent service = new Intent(this, MeuServico.class);
        stopService(service);
    }

    public void baixarImagem(View view) {
        Intent i = new Intent(MainActivity.this, DownloadResultReceiver.class);
        i.putExtra(DownloadIntentService.URL_DOWNLOAD,
                etUrlImagem.getText().toString());

        i.putExtra(DownloadIntentService.RESULT_RECEIVER,
                downloadResultReceiver);

        startService(i);
    }

    private class DownloadResultReceiver extends ResultReceiver {

        public DownloadResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            switch (resultCode) {
                case DownloadIntentService.DOWNLOAD_ERRO:
                    Toast.makeText(MainActivity.this,
                            "Nao foi possivel baixar a imagem",
                            Toast.LENGTH_SHORT).show();
                    break;
                case DownloadIntentService.DOWNLOAD_SUCESSO:
                    String path = resultData.getString(DownloadIntentService.FILE_PATH);

                    Bitmap foto = BitmapFactory.decodeFile(path);
                    if (foto == null) {
                        Toast.makeText(MainActivity.this,
                                "Nao foi possivel carregar a imagem",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ivImagem.setImageBitmap(foto);
                    }
                    break;
            }
        }
    }
}

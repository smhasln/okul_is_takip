package com.cinarli.cinarlionlineariza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class ArizaEkle extends AppCompatActivity {

    Integer kontrol;

    EditText txt_baslik;
    EditText txt_aciklama;
    ImageButton btn_foto;

    Integer birim_kontrol;

    String secilen;
    String kullanici_id;
    String resim_kodu;
    TextView txt_durum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariza_ekle);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        kullanici_id = preferences.getString("kullanici_id", "N/A");

        final Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(ArizaEkle.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.birimler));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myadapter);

        resim_kodu = "0";
        birim_kontrol = 0;
        txt_durum = findViewById(R.id.TXT_DRM);

        txt_aciklama = findViewById(R.id.txt_ariza_aciklama);
        txt_baslik = findViewById(R.id.txt_ariza_baslik);
        btn_foto = findViewById(R.id.img_btn_resim);

        Button gonder = findViewById(R.id.btn_gonder);

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (txt_baslik.getText().length() == 0 || birim_kontrol == 0)
                {

                    Alerter.create(ArizaEkle.this)
                            .setTitle("İşlem Başarısız")
                            .setText("Başlık veya birim boş geçilemez!")
                            .setIcon(R.drawable.ic_cancel_black_24dp)
                            .setIconColorFilter(0)
                            .setBackgroundColorRes(android.R.color.holo_red_light)
                            .show();
                }
                else
                {
                    bitir();
                }
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilen = spinner.getItemAtPosition(i).toString();
                birim_kontrol = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                birim_kontrol = 0;
            }
        });

        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxImagePicker.with(ArizaEkle.this).requestImage(Sources.CAMERA).subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(@NonNull Uri uri) throws Exception {

                        final InputStream imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        resim_kodu = encodeImage(selectedImage);

                        txt_durum.setText("1 FOTOĞRAF EKLENDİ");

                    }
                });


            }

        });

    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,10,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    void bitir()
    {

        final ProgressDialog progress = ProgressDialog.show(ArizaEkle.this, "Ekleme işlemi", "Yükleniyor...", true);

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonresponse = new JSONObject(response);
                    Integer cek_hata = jsonresponse.getInt("hata");


                    Log.i("yaz",response);
                    if(cek_hata == 0)
                    {
                        progress.dismiss();
                        Intent git = new Intent(ArizaEkle.this, AnaMenu.class);
                        startActivity(git);
                    }
                    else
                    {
                        progress.dismiss();
                        Alerter.create(ArizaEkle.this)
                                .setTitle("İşlem Başarısız")
                                .setText("Daha sonra tekrar deneyin.")
                                .setIcon(R.drawable.ic_cancel_black_24dp)
                                .setIconColorFilter(0)
                                .setBackgroundColorRes(android.R.color.holo_red_light)
                                .show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        };

        Log.i("yaz",resim_kodu);
        ArizaEkleJSON loginrequest = new ArizaEkleJSON(kullanici_id,txt_baslik.getText().toString(),txt_aciklama.getText().toString(),secilen,resim_kodu,responselistener);
        RequestQueue queue = Volley.newRequestQueue(ArizaEkle.this);
        queue.add(loginrequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent git = new Intent(ArizaEkle.this,AnaMenu.class);
        startActivity(git);
    }
}

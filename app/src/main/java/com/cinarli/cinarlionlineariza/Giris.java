package com.cinarli.cinarlionlineariza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

public class Giris extends AppCompatActivity {

    EditText txt_kadi;
    EditText txt_pw;
    Button giris;

    String kadi;
    String sifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        txt_kadi = findViewById(R.id.txt_kullanici_adi);
        txt_pw = findViewById(R.id.txt_sifre);
        giris = findViewById(R.id.btn_oturum_ac);

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            kadi = txt_kadi.getText().toString();
            sifre = txt_pw.getText().toString();

            giris_yap();

            }
        });

    }

    void giris_yap()
    {

        final ProgressDialog progress = ProgressDialog.show(Giris.this, "Oturum Açma", "Giriş Yapılıyor...", true);

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    Integer hata = jsonresponse.getInt("hata");

                    if (hata == 0)
                    {

                        progress.dismiss();

                        String cek_id = jsonresponse.getString("id");
                        String cek_yetki = jsonresponse.getString("yetki");

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("kullanici_id", cek_id);
                        editor.putString("yetki", cek_yetki);

                        editor.commit();

                        Intent git = new Intent(Giris.this,AnaMenu.class);
                        startActivity(git);

                    }
                    else
                    {
                        progress.dismiss();

                        Alerter.create(Giris.this)
                                .setTitle("Giriş Başarısız")
                                .setText("Kullanıcı adı veya şifre hatalı!")
                                .setIcon(R.drawable.ic_cancel_black_24dp)
                                .setIconColorFilter(0)
                                .setBackgroundColorRes(android.R.color.holo_red_light)
                                .show();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        GirisJSON loginrequest = new GirisJSON(kadi,sifre,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Giris.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}

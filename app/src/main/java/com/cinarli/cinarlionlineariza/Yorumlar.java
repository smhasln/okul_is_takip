package com.cinarli.cinarlionlineariza;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Yorumlar extends AppCompatActivity {

    String[] dizi_yorum_yapan;
    String[] dizi_yorum_icerik;
    String[] dizi_tarih;

    ListView listView;

    String secilen_id;

    String yorum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorumlar);

        FloatingActionButton ekle = findViewById(R.id.fab_ekle);
        listView = findViewById(R.id.yorum_liste);

        Intent git = getIntent();
        secilen_id = git.getStringExtra("secilen_id");

        doldur();

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Yorumlar.this);
                final EditText txt_yorum = new EditText(Yorumlar.this);
                alert.setMessage("Bir yorum ekleyin");
                alert.setTitle("Yorum metni");

                alert.setView(txt_yorum);

                alert.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        yorum = txt_yorum.getText().toString();

                        yeni_ekle();
                    }
                });

                alert.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });

    }

    void yeni_ekle()
    {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String kullanici_id = preferences.getString("kullanici_id", "N/A");

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonresponse = new JSONObject(response);

                    Integer hata = jsonresponse.getInt("hata");

                    if (hata == 0)
                    {

                        doldur();


                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        YorumEkleJSON loginrequest = new YorumEkleJSON(secilen_id,kullanici_id,yorum,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Yorumlar.this);
        queue.add(loginrequest);
    }

    void doldur()
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    Integer hata = jsonresponse.getInt("hata");

                    Log.i("yaz",response);
                    if (hata == 0)
                    {

                        JSONArray cek_yorum_yapan = jsonresponse.getJSONArray("baslik");
                        JSONArray cek_icerik = jsonresponse.getJSONArray("icerik");
                        JSONArray cek_tarih = jsonresponse.getJSONArray("tarih");


                        dizi_yorum_yapan = new String[cek_yorum_yapan.length()];
                        dizi_yorum_icerik = new String[cek_icerik.length()];
                        dizi_tarih = new String[cek_tarih.length()];

                        for (int i = 0; i < cek_yorum_yapan.length(); i++)
                        {
                            dizi_yorum_yapan[i] = cek_yorum_yapan.get(i).toString();
                            dizi_yorum_icerik[i] = cek_icerik.get(i).toString();
                            dizi_tarih[i] = cek_tarih.get(i).toString();
                        }



                        listView.setAdapter(new CustomYorumAdapter(Yorumlar.this,dizi_yorum_yapan,dizi_yorum_icerik,dizi_tarih));
                    }
                    else
                    {

                        Alerter.create(Yorumlar.this)
                                .setTitle("Yorumlar")
                                .setText("Henüz yorum eklenmemiş.")
                                .setIcon(R.drawable.ic_comment_white_24dp)
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

        YorumCekJSON loginrequest = new YorumCekJSON(secilen_id,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Yorumlar.this);
        queue.add(loginrequest);
    }
}

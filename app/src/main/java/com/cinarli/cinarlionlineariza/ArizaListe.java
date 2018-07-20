package com.cinarli.cinarlionlineariza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArizaListe extends AppCompatActivity {

    String[] resimler;
    String[] basliklar;
    String[] birimler;
    String[] durumlar;
    String[] idler;

    String secilen_ariza_id;

    ListView liste;

    String resim_url = "http://www.matbapp.com/onlineariza/resimler/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariza_liste);

        liste = findViewById(R.id.ariza_liste);

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int secim, long l) {
                secilen_ariza_id = idler[secim];

                Intent git = new Intent(ArizaListe.this,ArizaDetay.class);
                git.putExtra("secilen_id",secilen_ariza_id);
                startActivity(git);
            }
        });

        doldur();
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

                    if (hata == 0)
                    {

                        JSONArray cek_resim = jsonresponse.getJSONArray("resimler");
                        JSONArray cek_baslik = jsonresponse.getJSONArray("basliklar");
                        JSONArray cek_birim = jsonresponse.getJSONArray("birimler");
                        JSONArray cek_durum = jsonresponse.getJSONArray("durumlar");
                        JSONArray cek_id = jsonresponse.getJSONArray("idler");

                        resimler = new String[cek_resim.length()];
                        basliklar = new String[cek_baslik.length()];
                        birimler = new String[cek_birim.length()];
                        durumlar = new String[cek_durum.length()];
                        idler = new String[cek_id.length()];

                        for (int i = 0; i < cek_baslik.length(); i++)
                        {
                            resimler[i] = resim_url + cek_resim.get(i).toString() + ".jpg";
                            basliklar[i] = cek_baslik.get(i).toString();
                            birimler[i] = cek_birim.get(i).toString();
                            durumlar[i] = cek_durum.get(i).toString();
                            idler[i] = cek_id.get(i).toString();
                        }

                        liste.setAdapter(new CustomArizaListeAdapter(ArizaListe.this,resimler,basliklar,birimler,durumlar));
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        ArizaCekJSON loginrequest = new ArizaCekJSON(responselistener);
        RequestQueue queue = Volley.newRequestQueue(ArizaListe.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent git = new Intent(ArizaListe.this,AnaMenu.class);
        startActivity(git);
    }
}

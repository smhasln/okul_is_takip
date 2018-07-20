package com.cinarli.cinarlionlineariza;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArizaDetay extends AppCompatActivity {

    String[] durumlar = {"YAPILMADI", "BEKLEMEDE", "TAMAMLANDI"};
    String[] durumlar_id = {"0","1","2"};

    TextView txt_aciklama;
    ImageView img;

    String secilen_durum_id;
    String secilen_id;

    String resim_url = "http://www.matbapp.com/onlineariza/resimler/";

    String aciklama;
    String son_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariza_detay);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String yetki = preferences.getString("yetki", "N/A");

        Intent git = getIntent();
        secilen_id = git.getStringExtra("secilen_id");

        txt_aciklama = findViewById(R.id.detay_aciklama);
        img = findViewById(R.id.detay_img);

        final ImageButton cop = findViewById(R.id.cop);

        if(yetki.equals("1"))
        {
            cop.setVisibility(View.VISIBLE);
        }

        cop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ArizaDetay.this);
                builder.setMessage("Silmek istediğinize emin misiniz?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Evet",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                admin_sil();
                            }
                        });

                builder.setNegativeButton(
                        "Hayır",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });

        Button islem = findViewById(R.id.btn_tamamla);
        if(yetki.equals("1"))
        {
            islem.setText("DURUMU DEĞİŞTİR");
        }
        else
        {
            islem.setText("YÖNETİCİ ONAYINA GÖNDER");
        }

        doldur();

        ImageView yorumlar = findViewById(R.id.img_btn_yorum);
        yorumlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent git = new Intent(ArizaDetay.this,Yorumlar.class);
                git.putExtra("secilen_id",secilen_id);
                startActivity(git);
            }
        });

        islem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (yetki.equals("1"))
                {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(ArizaDetay.this);
                    mBuilder.setTitle("Arıza Durumu Değiştirin");
                    mBuilder.setSingleChoiceItems(durumlar, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            secilen_durum_id = durumlar_id[i];
                            dialogInterface.dismiss();

                            admin_islem();
                        }
                    });

                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();
                }
                else
                {
                    onaya_gonder();
                }
            }
        });

    }

    void admin_sil()
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
                        Intent git = new Intent(ArizaDetay.this,ArizaListe.class);
                        startActivity(git);
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        AdminSilJSON loginrequest = new AdminSilJSON(secilen_id,responselistener);
        RequestQueue queue = Volley.newRequestQueue(ArizaDetay.this);
        queue.add(loginrequest);
    }

    void admin_islem()
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
                        Intent git = new Intent(ArizaDetay.this,ArizaListe.class);
                        startActivity(git);
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        OnayaGonderJSON loginrequest = new OnayaGonderJSON(secilen_id,secilen_durum_id,responselistener);
        RequestQueue queue = Volley.newRequestQueue(ArizaDetay.this);
        queue.add(loginrequest);
    }

    void onaya_gonder()
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
                        Intent git = new Intent(ArizaDetay.this,ArizaListe.class);
                        startActivity(git);
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        OnayaGonderJSON loginrequest = new OnayaGonderJSON(secilen_id,"1",responselistener);
        RequestQueue queue = Volley.newRequestQueue(ArizaDetay.this);
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

                    if (hata == 0)
                    {
                        son_url = resim_url + jsonresponse.getString("resim") + ".jpg";
                        aciklama = jsonresponse.getString("aciklama");
                    }

                    txt_aciklama.setText(aciklama);

                    if (son_url.equals("http://www.matbapp.com/onlineariza/resimler/yok.jpg"))
                    {
                        img.setImageResource(R.drawable.logo);
                    }
                    else
                    {
                        Picasso.get()
                                .load(son_url)
                                .placeholder(R.drawable.alerter_ic_face)
                                .error(R.drawable.ic_cancel_black_24dp)
                                .into(img);
                    }


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        ArizaDetayJSON loginrequest = new ArizaDetayJSON(secilen_id,responselistener);
        RequestQueue queue = Volley.newRequestQueue(ArizaDetay.this);
        queue.add(loginrequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent git = new Intent(ArizaDetay.this,ArizaListe.class);
        startActivity(git);
    }
}

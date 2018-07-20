package com.cinarli.cinarlionlineariza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class AnaMenu extends AppCompatActivity {

    TextView tamamlanan;
    TextView beklemede;
    TextView tumu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_menu);

        Button ariza_ekle = findViewById(R.id.btn_arizaekle);
        Button arizalar = findViewById(R.id.btn_arizalar);

        tamamlanan = findViewById(R.id.txt_tamam);
        beklemede = findViewById(R.id.txt_bekleme);
        tumu = findViewById(R.id.txt_tumu);

        doldur();


        ariza_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent git = new Intent(AnaMenu.this,ArizaEkle.class);
                startActivity(git);
            }
        });

        arizalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent git = new Intent(AnaMenu.this,ArizaListe.class);
                startActivity(git);
            }
        });

    }

    void doldur()
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    String cek_tamamlaman = jsonresponse.getString("tamamlanan");
                    String cek_beklemede = jsonresponse.getString("bekleme");
                    String cek_tumu = jsonresponse.getString("tumu");

                    tumu.setText(cek_tumu);
                    tamamlanan.setText(cek_tamamlaman);
                    beklemede.setText(cek_beklemede);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        AnaMenuCekJSON loginrequest = new AnaMenuCekJSON(responselistener);
        RequestQueue queue = Volley.newRequestQueue(AnaMenu.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent git = new Intent(AnaMenu.this,Giris.class);
        startActivity(git);
    }
}

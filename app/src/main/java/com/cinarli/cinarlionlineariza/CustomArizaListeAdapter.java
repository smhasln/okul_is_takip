package com.cinarli.cinarlionlineariza;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomArizaListeAdapter extends BaseAdapter {

    String resimler[];
    String basliklar[];
    String birimler[];
    String durumlar[];


    LayoutInflater layoutInflater = null;


    public CustomArizaListeAdapter(ArizaListe arizaListe, String resimler[], String basliklar[], String birimler[], String durumlar[])
    {
        this.resimler = resimler;
        this.basliklar = basliklar;
        this.birimler = birimler;
        this.durumlar = durumlar;

        layoutInflater = (LayoutInflater)arizaListe.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resimler.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View RowView = layoutInflater.inflate(R.layout.custom_ariza_liste,null);

        ImageView resim = RowView.findViewById(R.id.custom_img);
        TextView txt_baslik = RowView.findViewById(R.id.custom_baslik);
        TextView txt_birim = RowView.findViewById(R.id.custom_aciklama);
        TextView txt_durum = RowView.findViewById(R.id.custom_durum);


        if(resimler[position].equals("http://www.matbapp.com/onlineariza/resimler/yok.jpg"))
        {
            resim.setImageResource(R.drawable.logo);
        }
        else
        {
            Picasso.get()
                    .load(resimler[position])
                    .placeholder(R.drawable.alerter_ic_face)
                    .error(R.drawable.ic_cancel_black_24dp)
                    .into(resim);
        }



        txt_baslik.setText(basliklar[position]);
        txt_birim.setText(birimler[position]);

        if(durumlar[position].equals("0"))
        {
            txt_durum.setText("YAPILMADI");
            int tint = Color.parseColor("#474747");
            txt_durum.getBackground().setColorFilter(tint, PorterDuff.Mode.MULTIPLY);
        }
        if(durumlar[position].equals("1"))
        {
            txt_durum.setText("BEKLEMEDE");
            int tint = Color.parseColor("#ff5d00");
            txt_durum.getBackground().setColorFilter(tint, PorterDuff.Mode.MULTIPLY);
        }
        if(durumlar[position].equals("2"))
        {
            txt_durum.setText("TAMAMLANDI");
            int tint = Color.parseColor("#00ff37");
            txt_durum.getBackground().setColorFilter(tint, PorterDuff.Mode.MULTIPLY);
        }

        return RowView;
    }
}

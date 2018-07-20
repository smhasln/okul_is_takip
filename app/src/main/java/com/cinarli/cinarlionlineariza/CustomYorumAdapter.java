package com.cinarli.cinarlionlineariza;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomYorumAdapter extends BaseAdapter {

    String dizi_yorum_yapan[];
    String dizi_yorum_icerik[];
    String dizi_tarih[];


    LayoutInflater layoutInflater = null;


    public CustomYorumAdapter(Yorumlar yorumlar, String dizi_yorum_yapan[], String dizi_yorum_icerik[], String dizi_tarih[])
    {
        this.dizi_yorum_yapan = dizi_yorum_yapan;
        this.dizi_yorum_icerik = dizi_yorum_icerik;
        this.dizi_tarih = dizi_tarih;

        layoutInflater = (LayoutInflater)yorumlar.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dizi_yorum_yapan.length;
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
        View RowView = layoutInflater.inflate(R.layout.custom_yorum_liste,null);

        TextView txt_baslik = RowView.findViewById(R.id.txt_yorum_yapan);
        TextView txt_icerik = RowView.findViewById(R.id.txt_yorum_icerigi);
        TextView txt_tarih = RowView.findViewById(R.id.txt_tarih);

        txt_baslik.setText(dizi_yorum_yapan[position]);
        txt_icerik.setText(dizi_yorum_icerik[position]);
        txt_tarih.setText(dizi_tarih[position]);


        return RowView;
    }
}

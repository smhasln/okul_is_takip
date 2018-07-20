package com.cinarli.cinarlionlineariza;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ArizaEkleJSON extends StringRequest {
    private static final String GIRIS_URL = "http://www.matbapp.com/onlineariza/ariza_ekle.php";

    private Map<String, String> params;

    public ArizaEkleJSON(String kullanici_id, String baslik, String aciklama,String birim,String resim_kodu, Response.Listener<String> listener) {
        super(Method.POST, GIRIS_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("id", kullanici_id);
        params.put("baslik", baslik);
        params.put("aciklama", aciklama);
        params.put("birim", birim);
        params.put("resim_kodu", resim_kodu);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

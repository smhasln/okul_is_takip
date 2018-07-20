package com.cinarli.cinarlionlineariza;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class YorumEkleJSON extends StringRequest {
    private static final String GIRIS_URL = "http://www.matbapp.com/onlineariza/yorum_ekle.php";

    private Map<String, String> params;

    public YorumEkleJSON(String ariza_id, String kullanici_id, String yorum, Response.Listener<String> listener) {
        super(Method.POST, GIRIS_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("ariza_id", ariza_id);
        params.put("ekleyen_id", kullanici_id);
        params.put("yorum", yorum);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

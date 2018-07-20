package com.cinarli.cinarlionlineariza;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class GirisJSON extends StringRequest {
    private static final String GIRIS_URL = "http://www.matbapp.com/onlineariza/giris.php";

    private Map<String, String> params;

    public GirisJSON(String kullanici_adi, String sifre, Response.Listener<String> listener) {
        super(Request.Method.POST, GIRIS_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("kadi", kullanici_adi);
        params.put("sifre", sifre);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

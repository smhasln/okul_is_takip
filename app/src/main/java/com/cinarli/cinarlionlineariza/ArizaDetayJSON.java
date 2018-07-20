package com.cinarli.cinarlionlineariza;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ArizaDetayJSON extends StringRequest {
    private static final String GIRIS_URL = "http://www.matbapp.com/onlineariza/ariza_detay.php";

    private Map<String, String> params;

    public ArizaDetayJSON(String ariza_id, Response.Listener<String> listener) {
        super(Method.POST, GIRIS_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("ariza_id", ariza_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

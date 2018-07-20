package com.cinarli.cinarlionlineariza;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ArizaCekJSON extends StringRequest
{
    private static final String GIRIS_URL = "http://www.matbapp.com/onlineariza/ariza_cek.php";

    public ArizaCekJSON(Response.Listener<String> listener) {
        super(Method.GET, GIRIS_URL, listener, null);
    }

}

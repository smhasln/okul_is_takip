package com.cinarli.cinarlionlineariza;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


public class AnaMenuCekJSON extends StringRequest
{
    private static final String GIRIS_URL = "http://www.matbapp.com/onlineariza/sayilar.php";

    public AnaMenuCekJSON(Response.Listener<String> listener) {
        super(Method.GET, GIRIS_URL, listener, null);
    }

}

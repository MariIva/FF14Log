package ru.arizara.ff14log.ui.log.rest;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.collection.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileOutputStream;
import java.util.List;

public class ImageAPIVolley implements ImageAPI{

    private final Context context;
    public static final String BASE_URL = "http://192.168.0.105:8081/image";


    public ImageAPIVolley(Context context) {
        this.context = context;
    }

    @Override
    public void getImageOrchestrion(String str) {
        String url = BASE_URL + "/"+str; //"/orchestrion"

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                       //mImageView.setImageBitmap(bitmap);
                        Log.e("getImageOrchestrion", bitmap.getByteCount()+"");
                        try {
                            FileOutputStream fileOutputStream = context.openFileOutput(str+".png", Context.MODE_PRIVATE);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            fileOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse",error.toString());
                    }
                });
        requestQueue.add(request);;
    }
}

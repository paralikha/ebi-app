package com.ssagroup.ebi_app.services;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by User on 8/9/16.
 */
public class DownloadImageService extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Bitmap bitmap;
    Activity activity;

    public DownloadImageService() {

    }

    public DownloadImageService(Activity activity) {
        this.activity = activity;
    }

    public DownloadImageService(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public DownloadImageService(Bitmap bitmap, ImageView bmImage) {
        this.bitmap = bitmap;
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap b = null;


        try {
            URL url = new URL(urlDisplay);
            URLConnection conn = url.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            httpURLConnection.setAllowUserInteraction(true);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream in = null;
            int response = httpURLConnection.getResponseCode();
            in = httpURLConnection.getInputStream();
            switch (response) {
                case HttpURLConnection.HTTP_OK: // 200
                    in = httpURLConnection.getInputStream();
                    break;

                case HttpURLConnection.HTTP_MOVED_PERM: // 301
                    String location = httpURLConnection.getHeaderField("Location");
                    URL urlNew = new URL(location);
                    URLConnection connNew = urlNew.openConnection();
                    HttpURLConnection httpURLConnectionNew = (HttpURLConnection) connNew;
                    in = httpURLConnectionNew.getInputStream();
                    break;

                default:
                    break;
            }

            b = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        bitmap = result;
    }
}

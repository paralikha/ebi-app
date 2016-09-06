package com.ssagroup.ebi_app.services;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.ssagroup.ebi_app.handlers.HTTPDataHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 8/8/16.
 */
public class JSONServiceHandler extends AsyncTask<String, Void, String> {

    private View rootView;
    private Context context;
    private CardView mCardView;
    private TextView title;
    private Activity activity;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public JSONServiceHandler(Activity activity) {
        this.activity = activity;
    }

    public JSONServiceHandler(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    public JSONServiceHandler(Context context, View rootView, AsyncResponse delegate) {
        this.context = context;
        this.rootView = rootView;
        this.delegate = delegate;
    }

    protected String doInBackground(String... strings) {
        String stream = null;
        String urlString = strings[0];
        HTTPDataHandler dataHandler = new HTTPDataHandler();
        stream = dataHandler.GetHTTPData(urlString);

        return stream;
    }

    @Override
    protected void onPostExecute(String stream) {
        super.onPostExecute(stream);
        if (null != stream) {
            try {
                JSONObject reader = new JSONObject(stream);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.ssagroup.ebi_app;

/**
 * Created by User on 8/3/2016.
 */

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssagroup.ebi_app.models.Course;
import com.ssagroup.ebi_app.services.DownloadImageService;
import com.ssagroup.ebi_app.services.JSONServiceHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class DetailActivity extends AppCompatActivity {
    private List<Course> course = new ArrayList<>();
    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        String id = getIntent().getStringExtra("hidden_title");
        Resources resources = getResources();

        course = fillWithCourseData(id);
        JSONArray schedules = course.get(0).schedule;
        StringBuilder sb = new StringBuilder();
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < schedules.length(); i++) {
            try {
                JSONObject schedule = schedules.getJSONObject(i);

                sb.append(schedule.getString("calendar_text") + System.getProperty("line.separator"));
//                a.append(schedule.getString("time") + System.getProperty("line.separator"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make (v, "Share button intent",
//                        Snackbar.LENGTH_LONG).show();
//            }
//        });

        String[] places = resources.getStringArray(R.array.course_title);
        collapsingToolbar.setTitle(course.get(0).title.toString());

        String[] aboutDetails = resources.getStringArray(R.array.course_about);
        TextView aboutDetail = (TextView) findViewById(R.id.about_detail);
        aboutDetail.setText(course.get(0).description.toString());

        String[] outlineDetails = resources.getStringArray(R.array.course_outline);
        TextView outlineDetail =  (TextView) findViewById(R.id.outline_detail);
        outlineDetail.setText(course.get(0).summary.toString());

        String[] scheduleDetails = resources.getStringArray(R.array.course_sched);
        TextView scheduleDetail =  (TextView) findViewById(R.id.schedule_detail);
        scheduleDetail.setText(sb);
//        scheduleDetail.setText(a);

        String[] feeDetails = resources.getStringArray(R.array.course_fee);
        TextView feeDetail =  (TextView) findViewById(R.id.fee_detail);
        feeDetail.setText(course.get(0).fee.toString());

        TypedArray placePictures = resources.obtainTypedArray(R.array.places_picture);
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageBitmap(course.get(0).thumbnail);

        placePictures.recycle();
    }

    public List<Course> fillWithCourseData(String id) {
        List<Course> myCourse = new ArrayList<>();
        try {
            String results = new JSONServiceHandler(this).execute(
                    "http://training.ssagroup.com/_test/Yggdrasil/public/api/courses/"+id+".json").get();

            if (null != results) {
                JSONArray reader = new JSONArray(results);
                for (int i = 0; i < reader.length(); i++) {
                    JSONObject jObject = reader.getJSONObject(i);
                    Bitmap bitmap = new DownloadImageService().execute(jObject.getString("thumbnail")).get();
                    Course c = new Course(
                            jObject.getString("id"),
                            jObject.getString("name"),
                            jObject.getString("description"),
                            jObject.getString("summary"),
                            jObject.getJSONArray("schedules"),
                            jObject.getString("fee"),
                            bitmap);
                    myCourse.add(c);
                }
            } else {
                Toast.makeText(DetailActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myCourse;
    }
}

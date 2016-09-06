package com.ssagroup.ebi_app;

/**
 * Created by User on 8/3/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssagroup.ebi_app.adapters.RecyclerViewAdapter;
import com.ssagroup.ebi_app.models.Schedules;
import com.ssagroup.ebi_app.services.DownloadImageService;
import com.ssagroup.ebi_app.services.JSONServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScheduleFragment extends Fragment {
    private List<Schedules> schedules;
    private Integer limit = 8;
    private Integer offset = 0;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final int[] pastVisibleItems = new int[1];
    private final int[] visibleItemCount = new int[1];
    private final int[] totalItemCount = new int[1];
    private boolean loading = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        schedules = new ArrayList<>();
        schedules = fillWithSchedulesData(offset);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(schedules, recyclerView.getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //public interface LoadMoreItems { void LoadItems(); }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!loading && !recyclerView.canScrollVertically(1)) {
                    loading = true;
                    // Scrolled to bottom
                    offset += limit;
                    List<Schedules> moreSchedules = fillWithSchedulesData(offset);
                    if (null != moreSchedules) {
                        schedules.addAll(moreSchedules);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
//                    recyclerView.setAdapter(new RecyclerViewAdapter(course, recyclerView.getContext()));

                } else {
                    loading = false;
                }
//                else if (dy > 0) {
//                    // Scrolled down
//                    visibleItemCount[0] = mLayoutManager.getChildCount();
//                    totalItemCount[0] = recyclerView.getAdapter().getItemCount();
//                    pastVisibleItems[0] = mLayoutManager.findFirstVisibleItemPosition();
//                    //Log.v("StringTOTAL", String.valueOf(pastVisibleItems[0]));
//                    if (loading) {
//                        if ((visibleItemCount[0] + pastVisibleItems[0]) >= totalItemCount[0]) {
//                            loading = false;
//                            Log.v("String", "last VIew");
//                        }
//                    }
//                }
            }
        });
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //      public ImageView avator;
        public TextView name;
        public TextView details;
        public TextView date;
        public TextView desc;
        public TextView time;
        public ImageView picture;
        private CardView cv;
        TextView id;
        TextView title;
        ImageView thumbnail;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.schedule, parent, false));
//            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
//            description = (TextView) itemView.findViewById(R.id.list_details);
//            details = (TextView) itemView.findViewById(R.id.list_details);

            desc = (TextView) itemView.findViewById(R.id.list_desc);
            date = (TextView) itemView.findViewById(R.id.list_date);
            id = (TextView) itemView.findViewById(R.id.hidden_title);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)  itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.list_title);
//          description = (TextView) itemView.findViewById(R.id.list_details);
            desc = (TextView) itemView.findViewById(R.id.list_desc);
            date = (TextView) itemView.findViewById(R.id.list_date);
            id = (TextView) itemView.findViewById(R.id.hidden_title);
            time = (TextView) itemView.findViewById(R.id.list_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    intent.putExtra("hidden_title", id.getText());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 18;
        private final String[] mPlaces;
        private final String[] mPlaceDesc;
        private final String[] mPlaceDetails;
        private final String[] mPlaceDate;
//        private final Drawable[] mPlaceAvators;

        private List<Schedules> schedules = Collections.emptyList();
        Context context;

        public ContentAdapter(List<Schedules> schedules, Context context) {
            this.schedules = schedules;
            this.context = context;

            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.course_title);
            mPlaceDesc = resources.getStringArray(R.array.course_cat);
            mPlaceDetails = resources.getStringArray(R.array.course_about);
            mPlaceDate = resources.getStringArray(R.array.course_date);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.id.setText(schedules.get(position).id);
            holder.title.setText(schedules.get(position).title);
//            holder.description.setText(schedules.get(position).description);
            holder.desc.setText(schedules.get(position).desc);
            holder.date.setText(schedules.get(position).date);
            holder.time.setText(schedules.get(position).time);
        }

        @Override
        public int getItemCount() {
            return schedules.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        public void insert(int position, Schedules schedules) {
            this.schedules.add(position, schedules);
            notifyItemInserted(position);
        }
    }

    public List<Schedules> fillWithSchedulesData(Integer offset) {
        if (null == offset) offset = this.offset;
        List<Schedules> mySchedules = new ArrayList<>();
        try {
            String results = new JSONServiceHandler(getActivity()).execute(
                    "http://training.ssagroup.com/_test/Yggdrasil/public/api/schedules/all.json?limit="+limit+"&skip="+offset).get();

            if (null != results) {
                JSONArray reader = new JSONArray(results);
                for (int i = 0; i < reader.length(); i++) {
                    JSONObject jObject = reader.getJSONObject(i);

                    Bitmap bitmap = new DownloadImageService().execute(jObject.getString("thumbnail")).get();
                    Schedules s = new Schedules(
                            jObject.getString("course_id"),
                            jObject.getString("course_id"),
                            jObject.getString("name"),
                            jObject.getString("description"),
                            jObject.getString("category_name"),
                            jObject.getString("calendar_text"),
                            jObject.getString("time"));
                    mySchedules.add(s);
                }
            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mySchedules;
    }
}

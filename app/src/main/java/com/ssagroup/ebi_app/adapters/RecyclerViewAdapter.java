package com.ssagroup.ebi_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssagroup.ebi_app.R;
import com.ssagroup.ebi_app.models.Course;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 8/8/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Course> course = Collections.emptyList();
    Context context;

    public RecyclerViewAdapter(List<Course> list, Context context) {
        this.course = list;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.id.setText(course.get(position).id);
        holder.title.setText(course.get(position).title);
        holder.description.setText(course.get(position).description);
        holder.thumbnail.setImageBitmap(course.get(position).thumbnail);
    }

    @Override
    public int getItemCount() {
        return course.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Course course) {
        this.course.add(position, course);
        notifyItemInserted(position);
    }

}

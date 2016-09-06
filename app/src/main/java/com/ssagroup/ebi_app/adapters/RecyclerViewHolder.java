package com.ssagroup.ebi_app.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssagroup.ebi_app.R;

/**
 * Created by User on 8/8/16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private CardView cv;
    TextView id;
    TextView title;
    TextView description;
    ImageView thumbnail;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        id = (TextView) itemView.findViewById(R.id.hidden_title);
        cv = (CardView)  itemView.findViewById(R.id.card_view);
        title = (TextView) itemView.findViewById(R.id.tile_title);
        description = (TextView) itemView.findViewById(R.id.tile_desc);
        thumbnail = (ImageView) itemView.findViewById(R.id.tile_picture);
    }


}

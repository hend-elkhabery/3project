package com.example.hend.popmovies1.UI.Adapters;

import android.content.Context;
 import android.support.v7.widget.RecyclerView;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.hend.popmovies1.API.ReviewResponse;
import com.example.hend.popmovies1.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<ReviewResponse> reviewResults;

    public ReviewAdapter(Context mContext, List<ReviewResponse> reviewResults) {
        this.mContext = mContext;
        this.reviewResults = reviewResults;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder (final MyViewHolder viewHolder, int i){
        viewHolder.witer.setText(reviewResults.get(i).getAuthor());
        viewHolder.review.setText(reviewResults.get(i).getContent());


    }

    @Override
    public int getItemCount(){
        return reviewResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView witer, review;

        public MyViewHolder(View view){
            super(view);
            witer = (TextView) view.findViewById(R.id.tvwriter);
            review = (TextView) view.findViewById(R.id.tvreview);


        }
    }

}

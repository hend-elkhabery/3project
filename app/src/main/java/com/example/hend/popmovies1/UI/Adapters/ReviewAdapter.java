package com.example.hend.popmovies1.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


 import com.example.hend.popmovies1.R;
import com.example.hend.popmovies1.POJO.Review;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>{

    private Context mContext;
    private List<Review.Results> reviews;

    public ReviewAdapter(Context mContext, List<Review.Results> reviewResults) {
        this.mContext = mContext;
        this.reviews = reviewResults;
    }

    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, viewGroup, false);
        return new ReviewAdapter.MyViewHolder(view , mContext , reviews);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder myViewHolder, int i) {

        myViewHolder.writer.setText(reviews.get(i).getAuthor());
        myViewHolder.review.setText(reviews.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView writer , review;
        private Context context;
        private List<Review.Results> datList;
        public MyViewHolder(@NonNull View itemView , Context context , List<Review.Results> datList) {
            super(itemView);
            this.context = context;
            this.datList = datList;
            this.review = (TextView) itemView.findViewById(R.id.tvreview);
            this.writer = (TextView) itemView.findViewById(R.id.tvwriter);
        }
    }
}

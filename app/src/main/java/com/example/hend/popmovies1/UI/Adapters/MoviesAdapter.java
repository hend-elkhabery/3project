package com.example.hend.popmovies1.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hend.popmovies1.POJO.MovieModel;
import com.example.hend.popmovies1.R;
import com.example.hend.popmovies1.UI.act.Details_activity;
import com.squareup.picasso.Picasso;


import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<MovieModel> dataList;
    Context context;

    public MoviesAdapter(List<MovieModel> dataList , Context context) {

      this.dataList = dataList;
      this.context = context;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout, viewGroup, false);
        return new MovieViewHolder(view , context , dataList);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int i) {
         Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + dataList.get(i).getPoster_path())
                .placeholder(R.color.colorPrimaryDark)
                 .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details_activity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("poster_path", dataList.get(i).getPoster_path());
                intent.putExtra("title", dataList.get(i).getTitle());
                intent.putExtra("overview", dataList.get(i).getOverview());
                intent.putExtra("release_date", dataList.get(i).getRelease_date());
                intent.putExtra("vote_average", dataList.get(i).getVote_average());
                intent.putExtra("movie_id", dataList.get(i).getId());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView ;
        private Context context;
        private List<MovieModel> datList;

        public MovieViewHolder(View itemView, Context context, List<MovieModel> dataList) {
            super(itemView);
            this.context = context;
            this.datList = dataList;
            this.imageView = itemView.findViewById(R.id.ivpostermain);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }

}
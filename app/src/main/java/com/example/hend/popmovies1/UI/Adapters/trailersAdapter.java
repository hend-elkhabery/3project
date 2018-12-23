package com.example.hend.popmovies1.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hend.popmovies1.POJO.MovieModel;
import com.example.hend.popmovies1.POJO.Trailers;
import com.example.hend.popmovies1.R;

import java.util.List;

public class trailersAdapter  extends RecyclerView.Adapter<trailersAdapter.ViewHolder>{

      List<Trailers> mVideoResult;
      Context mContext;

    public trailersAdapter(List<Trailers> mVideoResult, Context mContext) {
        this.mVideoResult = mVideoResult;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailers_layout, null, false);

        return new ViewHolder(view , mContext ,mVideoResult);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(mVideoResult.get(i).getName());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Trailers clickedDataItem = mVideoResult.get(i);
                String videoId = mVideoResult.get(i).getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoId));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("VIDEO_ID", videoId);
                mContext.startActivity(intent);

                Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView textView ;
        private Context context;
        private List<Trailers> mVideoResult;
        public ViewHolder(@NonNull View itemView , Context context, List<Trailers> mVideoResult) {
            super(itemView);
            this.context = context;
            this.mVideoResult = mVideoResult;
            this.textView= (TextView) itemView.findViewById(R.id.tvtrilername);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

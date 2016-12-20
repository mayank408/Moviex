package com.example.mayanktripathi.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mayanktripathi.popularmovies.MainActivity;
import com.example.mayanktripathi.popularmovies.R;
import com.example.mayanktripathi.popularmovies.model.MovieDes;
import com.example.mayanktripathi.popularmovies.model.movies;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mayanktripathi on 17/12/16.
 */

public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.CardViewHolder> {

    private Context context;


    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Context context;
        CardView cardView;


        @BindView(R.id.movies_img)
        public ImageView imgMovie;

        @BindView(R.id.movies_title)
        public TextView titleMovie;

        @BindView(R.id.movies_genre)
        public TextView genreMovie;

        @BindView(R.id.mainHolder)
        public LinearLayout mainholder;

        public CardViewHolder(View itemView , Context context) {

            super(itemView);

            this.context = context;

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_card, parent, false);
        CardViewHolder cdh = new CardViewHolder(v , context);
        return cdh;

    }


    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        holder.titleMovie.setText(moviesList.get(position).getTitle());
        holder.genreMovie.setText(moviesList.get(position).getGenre());
        Glide.with(context)
                .load(moviesList.get(position).getImgUrl())
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .listener(GlidePalette.with(moviesList.get(position).getImgUrl())
                        .intoCallBack(new BitmapPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                //  RecycleviewAdapter.this.applyColors(palette.getVibrantSwatch());
                            }
                        }))
                .into(holder.imgMovie);


        holder.imgMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context ,MovieDes.class).putExtra("position" , position);
                context.startActivity(i);

            }
        });

        holder.mainholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context ,MovieDes.class).putExtra("position" , position);
                context.startActivity(i);

            }
        });



    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private List<movies> moviesList;

    public RecycleviewAdapter(Context context, List<movies> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }
}
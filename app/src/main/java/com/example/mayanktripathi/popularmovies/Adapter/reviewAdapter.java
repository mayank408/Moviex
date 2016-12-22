package com.example.mayanktripathi.popularmovies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayanktripathi.popularmovies.R;

import java.util.List;

/**
 * Created by mayanktripathi on 21/12/16.
 */


    public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.ViewHolder>{

        private List<String> SubjectValues;
        private Context context;
        private View view1;
        private ViewHolder viewHolder1;
        private TextView textView;
        private Boolean maxlines = false;

        public reviewAdapter(Context context1,List<String> SubjectValues1){

            SubjectValues = SubjectValues1;
            context = context1;
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

            TextView textView;

            ViewHolder(View v){

                super(v);

                textView = (TextView)v.findViewById(R.id.review_single_item);
            }
        }

        @Override
        public reviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            view1 = LayoutInflater.from(context).inflate(R.layout.movie_review,parent,false);

            viewHolder1 = new ViewHolder(view1);

            return viewHolder1;
        }

    @Override
    public void onBindViewHolder(final reviewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(SubjectValues.get(position));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!maxlines) {
                        holder.textView.setMaxLines(100);
                        maxlines = true;
                    } else {
                        holder.textView.setMaxLines(4);
                        maxlines = false;
                    }
                }

        });
    }


        @Override
        public int getItemCount(){

            return SubjectValues.size();
        }
    }


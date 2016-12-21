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

        List<String> SubjectValues;
        Context context;
        View view1;
        ViewHolder viewHolder1;
        TextView textView;

        public reviewAdapter(Context context1,List<String> SubjectValues1){

            SubjectValues = SubjectValues1;
            context = context1;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            public TextView textView;

            public ViewHolder(View v){

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
    public void onBindViewHolder(reviewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(SubjectValues.get(position));
    }


        @Override
        public int getItemCount(){

            return SubjectValues.size();
        }
    }


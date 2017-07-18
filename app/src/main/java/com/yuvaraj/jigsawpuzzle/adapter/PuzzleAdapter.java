package com.yuvaraj.jigsawpuzzle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.yuvaraj.jigsawpuzzle.R;
import com.yuvaraj.jigsawpuzzle.models.Pieces;
import com.yuvaraj.jigsawpuzzle.puzzle.PuzzleActivity;


public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.DateViewHolder> {

    int mPreviousPosition=0;
    Context context;
    List<Pieces> piecesModelList = new ArrayList<Pieces>();

    public PuzzleAdapter(Context mContext, List<Pieces> piecesModelList) {
        this.context = mContext;
        this.piecesModelList = piecesModelList;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.puzzle_item,
                parent, false);
        return new DateViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder( DateViewHolder holder, int position) {
        holder.imageView.setImageBitmap(piecesModelList.get(position).getOriginalResource());
        holder.imageView.setTag("" + piecesModelList.get(position).getpX() + "," + piecesModelList.get(position).getpY());
        holder.imageView.setOnLongClickListener(new PuzzleActivity.MyClickListener());
        animationmethod(holder,position);
    }

    @Override
    public int getItemCount() {
        return piecesModelList.size();
    }


    public class DateViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        public DateViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
    void animationmethod(DateViewHolder holder, int position)
    {
        if (position > mPreviousPosition) {


            AnimationUtils.animate(holder,true);
        } else {

            AnimationUtils.animate(holder, false);
        }
        mPreviousPosition = position;
    }
}

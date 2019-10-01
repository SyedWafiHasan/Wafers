package com.wafihasan.wafers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    public Context context;
    public ArrayList<Items> arrayList;
    public OnItemClickListener onItemClickListener;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        onItemClickListener = listener;
    }

    public Adapter(Context context, ArrayList<Items> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Items currentItem = arrayList.get(position);
        String imageUrl = currentItem.getImageUrl();

        Picasso.get().load(imageUrl).into(holder.image);

    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (onItemClickListener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

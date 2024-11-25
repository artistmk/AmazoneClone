package com.example.myapplication.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.interfaces.ItemClickListener;

public class ViewProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView addProductName, addProductPrice;
    private ItemClickListener itemClickListener;
    public ImageView addProductImg;

    public ViewProductsHolder(@NonNull View itemView) {
        super(itemView);

        addProductName = itemView.findViewById(R.id.prod_name);
        addProductPrice = itemView.findViewById(R.id.prod_price); // @23 nov 2024 you bind wrong id here
        addProductImg=itemView.findViewById(R.id.prod_image);
    }

    @Override
    public void onClick(View v) {
   itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

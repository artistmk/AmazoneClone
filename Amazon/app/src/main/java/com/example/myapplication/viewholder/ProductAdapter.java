package com.example.myapplication.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.constant.Constant;
import com.example.myapplication.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private List<Product> products = new ArrayList<Product>();

    private final Context context;

    public ProductAdapter(Context context) {
        this.context=context;
    }

    public void updateProducts(List<Product> products) {
        this.products.addAll(products);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView productName, productPrice;
        ImageView productImage;

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_product_adapter, parent, false);
            productName = view.findViewById(R.id.productName);
            productPrice = view.findViewById(R.id.productPrice);
            productImage = view.findViewById(R.id.productImage);
            view.setTag(new ViewHolder(productName, productPrice, productImage));
        } else {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            productName = viewHolder.productName;
            productPrice =  viewHolder.productPrice;
            productImage = viewHolder.productImage;
        }
        final Product product = (Product) getItem(position);
        productName.setText(product.getpName());
        productPrice.setText(Constant.CURRENCY + String.valueOf(product.getpPrice().setScale(0, BigDecimal.ROUND_HALF_UP)));
        productImage.setImageResource(context.getResources().getIdentifier(product.getpImageName(),
                "drawable", context.getPackageName()));
        return view;
    }
    private static class ViewHolder {
        public final TextView productName;
        public final TextView productPrice;
        public final ImageView productImage;

        public ViewHolder(TextView productName, TextView productPrice, ImageView productImage) {
            this.productName = productName;
            this.productPrice = productPrice;
            this.productImage = productImage;
        }
    }
}

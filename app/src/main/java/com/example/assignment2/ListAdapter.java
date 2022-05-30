package com.example.assignment2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<productDataModel> products;
    private String productType;
    LayoutInflater inflater;

    ListAdapter(Context context, ArrayList<productDataModel> userArrayList,String t){
        this.products=userArrayList;
        this.productType=t;
        inflater=LayoutInflater.from(context);
    }

    public ListAdapter() {

    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =inflater.inflate(R.layout.product_list_item,null);

        productDataModel product = products.get(position);
        //defining the components:-
        TextView type=convertView.findViewById(R.id.productType);
        ImageView imageView = convertView.findViewById(R.id.productpic);//picture
        TextView title = convertView.findViewById(R.id.productTitle);//title
        TextView sub = convertView.findViewById(R.id.productPrice);//price
        RatingBar rate= convertView.findViewById(R.id.rattingBar);//ratting


        //setting the values:-
        imageView.setImageBitmap(product.getImg());
        title.setText(product.getTitle());
        sub.setText("Price:   "+product.getPrice()+"$");
        rate.setRating(product.getRate());
        type.setText(productType);

        LayerDrawable stars = (LayerDrawable) rate.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(convertView.getContext(), R.color.starColor), PorterDuff.Mode.SRC_ATOP);

        return convertView;
    }

}


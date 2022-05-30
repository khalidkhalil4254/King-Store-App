package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class cartViewAdapter extends ListAdapter {

    ArrayList<productDataModel> products;
    LayoutInflater inflater;

    cartViewAdapter(Context c,ArrayList<productDataModel> arr){
        super();
        this.products=arr;
        inflater=LayoutInflater.from(c);
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
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.cart_list_item,null);
        productDataModel product=products.get(i);

        //referencing the components to xml within java:-
        TextView title=view.findViewById(R.id.productTitle_cartPage);
        TextView price=view.findViewById(R.id.productPrice_cartPage);
        TextView cate=view.findViewById(R.id.productType_cartPage);
        ImageButton add=view.findViewById(R.id.add_cartListView_btn);
        ImageButton remove=view.findViewById(R.id.remove_cartListView_btn);
        TextView quantity=view.findViewById(R.id.quantity_cartListItem_txt);
        ImageView img=view.findViewById(R.id.productpic_cartPage);
        ImageView delete=view.findViewById(R.id.delete_cartPage_btn);
        ListView listView_cart_list=viewGroup.findViewById(R.id.listView_cartPage_list);
        TextView totalPrice=view.findViewById(R.id.totalPrice_cartPage_txt);


        controller control=new controller(view.getContext());
        //setting data for each product in the view:-
        title.setText(product.getTitle());
        price.setText(product.getPrice());
        cate.setText(product.getCate());
        delete.setImageResource(R.drawable.delete_icon);
        img.setImageBitmap(product.getImg());
        String user=control.getActiveUser();
        String title_text=title.getText().toString();
        String cate_txt=cate.getText().toString();
        String key=user+title_text+cate_txt;
        quantity.setText(String.valueOf(control.getQuantity(key)));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String user=control.getActiveUser();
                    String title_text=title.getText().toString();
                    String cate_txt=cate.getText().toString();
                    String key=user+title_text+cate_txt;
                    int qua=control.getQuantity(key);

                    if(qua<=2){
                        control.setQuantity(control.getQuantity(key)+1,key);
                        quantity.setText(String.valueOf(control.getQuantity(key)));
                    }

                }catch (Exception er){
                    Toast.makeText(add.getContext(), er.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String user=control.getActiveUser();
                    String title_text=title.getText().toString();
                    String cate_txt=cate.getText().toString();
                    String key=user+title_text+cate_txt;

                    int qua=control.getQuantity(key);

                    if(qua>1){
                        control.setQuantity(control.getQuantity(key)-1,key);
                        quantity.setText(String.valueOf(control.getQuantity(key)));
                    }
                }catch (Exception er){
                    Toast.makeText(add.getContext(), er.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    controller control=new controller(view.getContext());
                    String user=control.getActiveUser();
                    String title_text=title.getText().toString();
                    String cate_txt=cate.getText().toString();
                    String key=user+title_text+cate_txt;
                    control.deleteProduct(key,"cart");
                    ListAdapter adapter=new cartViewAdapter(view.getContext(), control.getAllInCartProducts());
                    listView_cart_list.setAdapter(adapter);
                }catch (Exception er){
                    Toast.makeText(view.getContext(),er.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });


        return view;
    }
}

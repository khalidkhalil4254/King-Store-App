package com.example.assignment2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import java.util.List;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.startActivity;

public class menuAdapter extends RecyclerView.Adapter<menuAdapter.ViewHolder> {

    private List<menuItemModel> itemsList;
    controller control;

    menuAdapter(List<menuItemModel> list){
        this.itemsList=list;
    }

    @NonNull
    @Override
    public menuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menulistitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(menuAdapter.ViewHolder holder, int position) {
        int res=itemsList.get(position).getItemIcon();
        String setting=itemsList.get(position).getItemText();
        holder.setData(res,setting);
    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private ImageView img;
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.menuItemImage);
            text=itemView.findViewById(R.id.menuItemText);
            itemView.setOnClickListener(this);
        }


        public void setData(int res, String setting) {
            img.setImageResource(res);
            text.setText(setting);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),"Name"+itemsList.get(getAdapterPosition()).getItemText(),Toast.LENGTH_SHORT).show();
            if(getAdapterPosition()==0){}
            if(getAdapterPosition()==1){}
            if(getAdapterPosition()==2){
                control=new controller(view.getContext());
                control.releaseActive();
                view.getContext().startActivity(new Intent(view.getContext(),MainActivity.class));
            }
            if(getAdapterPosition()==3){}
        }
    }


}

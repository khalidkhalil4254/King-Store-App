package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class menu extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<menuItemModel> listAdapter;
    RecyclerView.Adapter adapter;
    ImageView homePage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        homePage=findViewById(R.id.homePage);

        initData();
        InitRecyclerView();
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        homePage.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(),storeHome.class));
        });


    }

    private void initData() {
        listAdapter=new ArrayList<>();
        listAdapter.add(new menuItemModel(R.drawable.ic_language_fill0_wght400_grad0_opsz48,"Country"));
        listAdapter.add(new menuItemModel(R.drawable.ic_policy_fill0_wght400_grad0_opsz48,"Legal & About"));
        listAdapter.add(new menuItemModel(R.drawable.ic_logout_fill0_wght400_grad0_opsz48__2_,"Sign-out"));
        listAdapter.add(new menuItemModel(R.drawable.ic_design_services_fill0_wght400_grad0_opsz48,"Customer-Services"));
    }

    private void InitRecyclerView() {
        recyclerView=findViewById(R.id.menuPageRecyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new menuAdapter(listAdapter);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

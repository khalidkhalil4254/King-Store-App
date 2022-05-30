package com.example.assignment2;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class storeHome extends AppCompatActivity {

    EditText search_action_txt;
    ImageView search_action_btn,menu,home,fav,cart,ps5_btn,ps4_btn,xbox_btn,nintendo_btn,nav;
    protected  static  final  int RESULT_SPEECH=1;
    ImageView mic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storehome);
        nav=findViewById(R.id.navBar);
        home=findViewById(R.id.homePage);
        fav=findViewById(R.id.favPage);
        cart=findViewById(R.id.cartPage);
        menu=findViewById(R.id.menuPage);
        search_action_txt=findViewById(R.id.search_actionbar_txt);
        search_action_btn=findViewById(R.id.search_action_btn);
        //referencing the categories in xml:-
        ps5_btn=findViewById(R.id.ps5Games_cate_btn);
        ps4_btn=findViewById(R.id.ps4Games_cate_btn);
        xbox_btn=findViewById(R.id.xboxGame_cate_btn);
        nintendo_btn=findViewById(R.id.nintendoGames_cate_btn);





        //creating search methods and so on:-
        mic=findViewById(R.id.micSearch_actionBar_btn);


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
                try {
                    startActivityForResult(intent,RESULT_SPEECH);
                    search_action_txt.setText("");
                }catch (Exception er){
                    Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });



        ps5_btn.setOnClickListener((e)->{
            Toast.makeText(getApplicationContext(),"ps5",Toast.LENGTH_SHORT).show();
            // creating a intent
            Intent intent = new Intent(getApplicationContext(), products.class);
            Bundle bundle = new Bundle();
            bundle.putString("productType", "ps5-game");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        ps4_btn.setOnClickListener((e)->{
            Toast.makeText(getApplicationContext(),"ps4",Toast.LENGTH_SHORT).show();
            // creating a intent
            Intent intent = new Intent(getApplicationContext(), products.class);
            Bundle bundle = new Bundle();
            bundle.putString("productType", "ps4-game");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        xbox_btn.setOnClickListener((e)->{
            Toast.makeText(getApplicationContext(),"xbox",Toast.LENGTH_SHORT).show();
            // creating a intent
            Intent intent = new Intent(getApplicationContext(), products.class);
            Bundle bundle = new Bundle();
            bundle.putString("productType", "xbox-gam");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        nintendo_btn.setOnClickListener((e)->{
            Toast.makeText(getApplicationContext(),"nintendo",Toast.LENGTH_SHORT).show();
            // creating a intent
            Intent intent = new Intent(getApplicationContext(), products.class);
            Bundle bundle = new Bundle();
            bundle.putString("productType", "nintendo");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        search_action_btn.setOnClickListener((e)->{
            String search_txt=search_action_txt.getText().toString();
            // creating a intent
            Intent intent = new Intent(getApplicationContext(), products.class);
            Bundle bundle = new Bundle();
            bundle.putString("search", search_txt);
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Searching:"+search_txt,Toast.LENGTH_SHORT).show();
        });

        search_action_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    String search_txt=search_action_txt.getText().toString();
                    // creating a intent
                    Intent intent = new Intent(getApplicationContext(), products.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("search", search_txt);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Searching:"+search_txt,Toast.LENGTH_SHORT).show();
                    handled = true;
                }
                return handled;
            }
        });

        nav.setOnClickListener((e)->{
        });
        //creating Events for navBar:-
        home.setOnClickListener((e)->{
        });
        fav.setOnClickListener((e)->{
        });
        cart.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(),cartPage.class));
        });
        menu.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(), com.example.assignment2.menu.class));
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode==RESULT_OK && data!=null) {
                    ArrayList<String> text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    search_action_txt.setText(text.get(0));
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}

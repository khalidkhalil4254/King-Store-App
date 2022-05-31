package com.example.assignment2;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class products extends AppCompatActivity{

    EditText search_action_txt;
    ImageView search_action_btn,menu,home,fav,cart,nav;
    ListView productsListView;
    ListAdapter adapter;
    ArrayList<productDataModel> productsList;
    protected  static  final  int RESULT_SPEECH=1;
    ImageView mic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);


        nav=findViewById(R.id.navBar);
        home=findViewById(R.id.homePage);
        fav=findViewById(R.id.favPage);
        cart=findViewById(R.id.cartPage);
        menu=findViewById(R.id.menuPage);
        search_action_txt=findViewById(R.id.search_actionbar_txt);
        search_action_btn=findViewById(R.id.search_action_btn);
        productsListView=findViewById(R.id.productsList);



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





        try {
            // getting the bundle back from the android
            Bundle bundle = getIntent().getExtras();
            String productType = bundle.getString("productType");
            String search=bundle.getString("search","");
            controller control=new controller(getApplicationContext());

            //searching logic if search text exists:-
            if(!search.equals("") && !search.equals(null)){
                productsList=control.searchAllProducts(search);
                adapter=new ListAdapter(getApplicationContext(),productsList,search);
                productsListView.setAdapter(adapter);
                productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {

                            //Convert to byte array
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            productsList.get(i).getImg().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            // creating a intent
                            Intent intent = new Intent(getApplicationContext(), productPage.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title",productsList.get(i).getTitle());
                            bundle.putString("price",productsList.get(i).getPrice());
                            bundle.putString("description",productsList.get(i).getDescription());
                            bundle.putInt("rate",productsList.get(i).getRate());
                            bundle.putString("cate",productsList.get(i).getCate());
                            bundle.putByteArray("img",byteArray);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }catch (Exception er){
                            Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                search="";
                //if search text is not exists:-
            }else {
                productsList = control.getAllProducts(productType);
                adapter=new ListAdapter(getApplicationContext(),productsList,productType);
                productsListView.setAdapter(adapter);
                productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {

                            //Convert to byte array
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            productsList.get(i).getImg().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            // creating a intent
                            Intent intent = new Intent(getApplicationContext(), productPage.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title",productsList.get(i).getTitle());
                            bundle.putString("price","Price : "+productsList.get(i).getPrice()+" EGP");
                            bundle.putString("description",productsList.get(i).getDescription());
                            bundle.putInt("rate",productsList.get(i).getRate());
                            bundle.putString("cate",productsList.get(i).getCate());
                            bundle.putByteArray("img",byteArray);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }catch (Exception er){
                            Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }catch (Exception er){
            Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
        }


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


        nav.setOnClickListener((e)->{});
        //creating Events for navBar:-
        home.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(),storeHome.class));
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


}

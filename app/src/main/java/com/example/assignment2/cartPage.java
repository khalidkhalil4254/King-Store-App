package com.example.assignment2;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class cartPage extends AppCompatActivity {


    Button checkout;
    ListView listView_cart_list;
    ListAdapter adapter;
    ArrayList<productDataModel> productsList;
    ImageView search_action_btn,menu,home,fav,cart,nav;
    EditText search_action_txt;
    protected  static  final  int RESULT_SPEECH=1;
    ImageView mic;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        //referencing the components of xml within the java:-
        checkout= (Button) findViewById(R.id.checkout);
        listView_cart_list=findViewById(R.id.listView_cartPage_list);
        nav=findViewById(R.id.navBar);
        home=findViewById(R.id.homePage);
        fav=findViewById(R.id.favPage);
        cart=findViewById(R.id.cartPage);
        menu=findViewById(R.id.menuPage);
        search_action_txt=findViewById(R.id.search_actionbar_txt);
        search_action_btn=findViewById(R.id.search_action_btn);
        total=findViewById(R.id.totalPrice_cartPage_txt);


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



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),checkoutPage.class));
                Toast.makeText(getApplicationContext(),"checkout",Toast.LENGTH_SHORT).show();
                try {
                    controller con=new controller(getApplicationContext());
                    String user=con.getActiveUser();
                    Toast.makeText(getApplicationContext(),"this is : "+String.valueOf(con.getFinalTotal(user)),Toast.LENGTH_LONG).show();
                }catch (Exception er){
                    Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });



        //creating the logic of the all application:-
        try {
            //creating the components of list within:-
            controller control=new controller(getApplicationContext());
            productsList=control.getAllInCartProducts();
            adapter= new cartViewAdapter(getApplicationContext(),productsList);
            listView_cart_list.setAdapter(adapter);


        }catch (Exception er){
            Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
        }



        //creating event handlers for all list items:-









        //creating event handlers for bottom navbar:-
        nav.setOnClickListener((e)->{
        });
        //creating Events for navBar:-
        home.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(),storeHome.class));
        });
        fav.setOnClickListener((e)->{
        });
        cart.setOnClickListener((e)->{
        });
        menu.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(), com.example.assignment2.menu.class));
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

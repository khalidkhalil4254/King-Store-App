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
    ImageView menu,home,fav,cart,nav;
    protected  static  final  int RESULT_SPEECH=1;
    ImageView mic;
    TextView total;
    ImageButton back_btn;

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
        total=findViewById(R.id.totalPrice_cartPage_txt);
        back_btn=findViewById(R.id.back_cart_actionbar);





        back_btn.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(),storeHome.class));
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



    }



}

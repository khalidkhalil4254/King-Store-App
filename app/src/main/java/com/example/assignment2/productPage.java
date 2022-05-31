package com.example.assignment2;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class productPage extends AppCompatActivity {

    EditText search_action_txt;
    ImageView search_action_btn,menu,home,fav,cart,nav;
    ImageView productImg;
    TextView productTitle,productPrice,productCate,productDescription;
    RatingBar productRate;
    Button addToCart,addToFav;
    protected  static  final  int RESULT_SPEECH=1;
    ImageView mic;
    String price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);


        nav=findViewById(R.id.navBar);
        home=findViewById(R.id.homePage);
        fav=findViewById(R.id.favPage);
        cart=findViewById(R.id.cartPage);
        menu=findViewById(R.id.menuPage);
        search_action_txt=findViewById(R.id.search_actionbar_txt);
        search_action_btn=findViewById(R.id.search_action_btn);
        productDescription=findViewById(R.id.description_productPage_txt);
        addToCart=findViewById(R.id.addToCart_productPage_btn);
        addToFav=findViewById(R.id.addToFavorite_productPage_btn);


        productImg=findViewById(R.id.product_productPage_img);
        productTitle=findViewById(R.id.productTitle_productPage_txt);
        productPrice=findViewById(R.id.productPrice_productPage_txt);
        productRate=findViewById(R.id.productRate_productPage_rate);
        productCate=findViewById(R.id.productCate_productPage_txt);




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



        // getting the bundle back from the android /////////data////////////
        Bundle bundle = getIntent().getExtras();
        byte[] imgByteArr= bundle.getByteArray("img");
        String title= bundle.getString("title");
        price= bundle.getString("price");
        Toast.makeText(getApplicationContext(),price,Toast.LENGTH_LONG).show();
        String description=bundle.getString("description");
        int rate= bundle.getInt("rate");
        String cate= bundle.getString("cate");
        Bitmap imgBitmap=getImage(imgByteArr);


        //setting and initializing the values:-
        productImg.setImageBitmap(imgBitmap);
        productTitle.setText(title);
        productPrice.setText(price);
        productRate.setRating(rate);
        productCate.setText(cate);
        productDescription.setText(description);
        LayerDrawable stars = (LayerDrawable) productRate.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.duskYellow), PorterDuff.Mode.SRC_ATOP);



        //adding event handlers for the add to cart and add to favorites:-
        addToCart.setOnClickListener((e)->{
            try {
                controller control=new controller(getApplicationContext());
                productDataModel product=new productDataModel(productCate.getText().toString(),productTitle.getText().toString(),String.valueOf(control.getProductPrice(title,cate)),"","",imgBitmap,-1);
                if(!control.isContain(product,"cart")){
                    control.addProductToCart(product);
                    Toast.makeText(getApplicationContext(),"Product added for user:"+control.getActiveUser(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Product already in cart!",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception er){
                Toast.makeText(getApplicationContext(),"Product already in cart!",Toast.LENGTH_SHORT).show();
            }
        });

        addToFav.setOnClickListener((e)->{
            try {
                controller control=new controller(getApplicationContext());
                productDataModel product=new productDataModel(cate,title,price,"","",imgBitmap,-1);

            }catch (Exception er){
                Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
            }
        });


        //adding event handlers to the action bar and the bottom navbar:-
        search_action_btn.setOnClickListener((e)->{
            String search_txt=search_action_txt.getText().toString();
            // creating a intent
            Intent intent = new Intent(getApplicationContext(), products.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("search", search_txt);
            intent.putExtras(bundle1);
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

    public Bitmap getImage(byte[] imag){
        Bitmap bt=null;
        bt= BitmapFactory.decodeByteArray(imag,0, imag.length);
        return bt;
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

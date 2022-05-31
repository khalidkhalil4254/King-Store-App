package com.example.assignment2;

import android.content.*;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class checkoutPage extends AppCompatActivity {


    ImageView search_action_btn,menu,home,fav,cart,nav;
    EditText search_action_txt;
    protected  static  final  int RESULT_SPEECH=1;
    ImageView mic;
    TextView total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        total=findViewById(R.id.totalPrice_cartPage_txt);
        nav=findViewById(R.id.navBar);
        home=findViewById(R.id.homePage);
        fav=findViewById(R.id.favPage);
        cart=findViewById(R.id.cartPage);
        menu=findViewById(R.id.menuPage);
        search_action_txt=findViewById(R.id.search_actionbar_txt);
        search_action_btn=findViewById(R.id.search_action_btn);





        controller con=new controller(getApplicationContext());
        total.setText("Your Total order Price : "+String.valueOf(con.getFinalTotal(con.getActiveUser()))+" EGP");



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
            startActivity(new Intent(getApplicationContext(),cartPage.class));
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





    public void buttonSendEmail(View view){

        try {
            controller con=new controller(getApplicationContext());
            String receiver=con.getEmail();
            Toast.makeText(getApplicationContext(),receiver,Toast.LENGTH_LONG).show();
            String stringSenderEmail = "kalidkalil4254@gmail.com";
            String stringReceiverEmail = receiver;
            String stringPasswordSenderEmail = "Kk01158194254@";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Order Confirmed Successfully!");
            String msg=con.getOrder();
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            mimeMessage.setText(msg);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
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

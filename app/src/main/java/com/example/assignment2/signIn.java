package com.example.assignment2;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class signIn extends AppCompatActivity {

    EditText email_txt,password_txt;
    Button signIn_btn,signUp_signIn_btn;
    CheckBox rememberMe_btn;
    TextView forgotpassword_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        //referencing xml components in java:-
        email_txt=findViewById(R.id.username_signIn_txt);
        password_txt=findViewById(R.id.password_signIn_txt);
        signIn_btn=findViewById(R.id.signIn_signIn_btn);
        signUp_signIn_btn=findViewById(R.id.signUp_signUp_btn);
        rememberMe_btn=findViewById(R.id.rememberMe_signIn_btn);
        forgotpassword_btn=findViewById(R.id.forgotPassword);

        //creating event handlers:-
        signIn_btn.setOnClickListener((e)->{
            if(!email_txt.getText().toString().equals("") && !password_txt.getText().toString().equals("")){
                try {
                    authDataModel auth=new authDataModel(email_txt.getText().toString(),password_txt.getText().toString(),rememberMe_btn.isChecked());
                    controller control=new controller(getApplicationContext());
                    boolean b=control.signIn(auth,rememberMe_btn.isChecked());
                    if(b){
                        startActivity(new Intent(getApplicationContext(),storeHome.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"Invalid Account:"+b,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception er){
                    Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
                }
                password_txt.setText("");
            }
        });


        signUp_signIn_btn.setOnClickListener((e)->{
            startActivity(new Intent(getApplicationContext(),signUp.class));
            finish();
        });



        forgotpassword_btn.setOnClickListener((e)->{
            try {
                String user=email_txt.getText().toString();
                controller con=new controller(getApplicationContext());
                Toast.makeText(getApplicationContext(),con.getPassword(user),Toast.LENGTH_LONG).show();
            }catch (Exception er){
                Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}

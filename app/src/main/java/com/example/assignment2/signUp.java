package com.example.assignment2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class signUp extends AppCompatActivity {

    EditText name_txt,phone_txt,email_txt,password_txt;
    Button signUp_btn;


    DatePicker datePicker;
    Calendar calendar;
    TextView dateView;
    int year, month, day;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);



        dateView = (TextView) findViewById(R.id.birthDay_signUp_txt);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);



        //referencing xml components in java:-
        name_txt=findViewById(R.id.username_signUp_txt);
        phone_txt=findViewById(R.id.phone_signUp_txt);
        email_txt=findViewById(R.id.email_signUp_txt);
        password_txt=findViewById(R.id.password_signUp_txt);
        signUp_btn=findViewById(R.id.signUp_signUp_btn);

        //creating event handlers:-
        signUp_btn.setOnClickListener((e)->{
            if(!name_txt.getText().toString().equals("") && !email_txt.getText().toString().equals("") && !password_txt.getText().toString().equals("") && !phone_txt.getText().toString().equals("")){
                try{
                    String username=name_txt.getText().toString();
                    String phone=phone_txt.getText().toString();
                    String email=email_txt.getText().toString();
                    String pass=password_txt.getText().toString();
                    customerDataModel c= new customerDataModel(username,phone,email,pass,false);
                    controller control=new controller(getApplicationContext());
                    boolean b = control.signUp(c);
                    List<customerDataModel> all=control.getEveryOne();
                    startActivity(new Intent(getApplicationContext(),signIn.class));
                    name_txt.setText("");
                    phone_txt.setText("");
                    email_txt.setText("");
                    password_txt.setText("");
                    finish();
                }catch (Exception er){
                    Toast.makeText(getApplicationContext(),"Username is already existed!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


}

package com.example.assignment2;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class controller extends SQLiteOpenHelper {

    public controller(@Nullable Context context) {
        super(context, "data_db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="CREATE TABLE \"customer\" (\n" +
                "\t\"username\"\tTEXT,\n" +
                "\t\"phone\"\tTEXT,\n" +
                "\t\"email\"\tTEXT,\n" +
                "\t\"password\"\tTEXT,\n" +
                "\t\"isActive\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"username\")\n" +
                ");";

        String sql1="CREATE TABLE \"Proudect\" (\n" +
                "\t\"index\"\tINTEGER,\n" +
                "\t\"image_id\"\tTEXT,\n" +
                "\t\"cat\"\tTEXT,\n" +
                "\t\"title\"\tNUMERIC,\n" +
                "\t\"price\"\tTEXT,\n" +
                "\t\"Game_Information\"\tTEXT,\n" +
                "\t\"Description\"\tTEXT,\n" +
                "\t\"img\"\tBLOB,\n" +
                "\t\"rate\"\tINTEGER\n" +
                ");";
        String sql2="CREATE TABLE cart(user TEXT,title TEXT,cate TEXT,price TEXT);";

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    boolean signUp(customerDataModel customerDataModel){
        boolean res=false;
        int isActive=customerDataModel.isActive()==true?1:0;
        String sql="INSERT INTO customer (username,phone,email,password,isActive)  VALUES ('"+customerDataModel.getName()+"','"+customerDataModel.getPhone()+"','"+customerDataModel.getEmail()+"','"+customerDataModel.getPassword()+"',"+isActive+");";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(sql);
        res=true;
        db.close();
        return res;
    }

    boolean signIn(authDataModel authDataModel,boolean isCheck){
        boolean res=false;
        String sql="SELECT * from customer;";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                String username=cursor.getString(0);
                String password=cursor.getString(3);
                if(username.equals(authDataModel.getEmail()) && password.equals(authDataModel.getPassword())){
                    if(isCheck){
                        String sql1="UPDATE customer SET isActive =1 WHERE username ='"+username+"';";
                        SQLiteDatabase db1=this.getWritableDatabase();
                        db1.execSQL(sql1);
                        db1.close();
                    }
                    res=true;
                    break;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return res;
    }

    public List<customerDataModel> getEveryOne(){
        ArrayList<customerDataModel> everyOne=new ArrayList<>();
        String sql="select * from customer;";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                String username=cursor.getString(0);
                String phone=cursor.getString(1);
                String email=cursor.getString(2);
                String password=cursor.getString(3);
                boolean isActive=cursor.getInt(4)==1?true:false;
                customerDataModel customerDataModel=new customerDataModel(username,phone,email,password,isActive);
                everyOne.add(customerDataModel);
            }while(cursor.moveToNext());
        }else{}
        cursor.close();
        db.close();
        return everyOne;
    }

    boolean isAnyOneActive(){
        boolean res=false;
        String sql="select * from customer;";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int isActive=cursor.getInt(4);
                if(isActive==1){
                    res=true;
                    break;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return res;
    }

    customerDataModel OneActive(){
        customerDataModel customer=null;
        String sql="select * from customer;";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String username=cursor.getString(0);
                String phone=cursor.getString(1);
                String email=cursor.getString(2);
                String password=cursor.getString(3);
                boolean isActive= cursor.getInt(4) ==1?true:false;
                if(isActive){
                    customer = new customerDataModel(username, phone, email, password, isActive);
                    break;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return customer;
    }

    void releaseActive(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql1="UPDATE customer SET isActive=0 WHERE isActive=1;";
        db.execSQL(sql1);
        db.close();
    }

    public Bitmap getImage(byte[] imag){
        Bitmap bt=null;
            bt= BitmapFactory.decodeByteArray(imag,0, imag.length);
        return bt;
    }

    public ArrayList<productDataModel> getAllProducts(String type){
        ArrayList<productDataModel> everyOne=new ArrayList<>();
        String sql="select * from Proudect where cat='"+type+"';";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null){
        if(cursor.moveToFirst()){
            do{
                //columns of database:-
                String cate=cursor.getString(2);
                String title=cursor.getString(3);
                String price=cursor.getString(4);
                String game_info=cursor.getString(5);
                String decription=cursor.getString(6);
                byte[] img=cursor.getBlob(7);
                int rate=cursor.getInt(8);
                productDataModel productData=new productDataModel(cate,title,price,game_info,decription,getImage(img),rate);
                everyOne.add(productData);
            }while(cursor.moveToNext());
        }else{}
        }
        cursor.close();
        db.close();
        return everyOne;
    }

    public ArrayList<productDataModel> searchAllProducts(String search){
        ArrayList<productDataModel> everyOne=new ArrayList<>();
        String sql="SELECT * FROM Proudect WHERE title LIKE '"+search+"%' ;";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    //columns of database:-
                    String cate=cursor.getString(2);
                    String title=cursor.getString(3);
                    String price=cursor.getString(4);
                    String game_info=cursor.getString(5);
                    String decription=cursor.getString(6);
                    byte[] img=cursor.getBlob(7);
                    int rate=cursor.getInt(8);
                    productDataModel productData=new productDataModel(cate,title,price,game_info,decription,getImage(img),rate);
                    everyOne.add(productData);
                }while(cursor.moveToNext());
            }else{}
        }
        cursor.close();
        db.close();
        return everyOne;
    }

    String getActiveUser(){
        String res="null";
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="select * from customer where isActive=1;";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String user=cursor.getString(0);
                res=user;
                break;
            }while(cursor.moveToNext());
        }
        cursor.close();
        return res;
    }

    void addProductToCart(productDataModel p){
        SQLiteDatabase db1=this.getWritableDatabase();
        String user=getActiveUser();
        String title=p.getTitle();
        String cate=p.getCate();
        String price=p.getPrice();
        String usertitlecate=user+title+cate;
        String sql="insert into cart (user ,title ,cate ,price ,usertitlecate,quantity) values ('"+user+"' ,'"+title+"' ,'"+cate+"' ,'"+price+"' ,'"+usertitlecate+"' ,1);";
        db1.execSQL(sql);

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int getQuantity(String usertitlecate){
        int res=0;
            String sql="select * from cart where usertitlecate='"+usertitlecate+"';";
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor=db.rawQuery(sql,null);
            if(cursor.moveToFirst()){
                do{
                    int quantity=cursor.getInt(5);
                    res=quantity;
                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
        return res;
    }

    void setQuantity(int q,String usertitlecate){
        String sql="UPDATE cart SET quantity="+q+" WHERE usertitlecate='"+usertitlecate+"';";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    Bitmap getImageFromDB(String title,String cate){
        Bitmap img=null;
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT * FROM Proudect WHERE title='"+title+"' and cat='"+cate+"';";
        Cursor cursor=db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
           do{
               byte[] byteImg=cursor.getBlob(7);
               img=getImage(byteImg);
               break;
           }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return img;
    }

    boolean isContain(productDataModel product,String table){
        boolean res=false;
        SQLiteDatabase db=this.getReadableDatabase();
        String username=getActiveUser();
        String sql="select * from cart;";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String user=cursor.getString(0);
                String cate=cursor.getString(1);
                String title=cursor.getString(2);
                String price=cursor.getString(3);
                Log.i("data:",title);
                if(user.equals(username) && title.equals(product.getTitle()) && cate.equals(product.getCate()) && price.equals(product.getPrice())){
                    res=true;
                    break;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return res;
    }

    public ArrayList<productDataModel> getAllInCartProducts(){
        ArrayList<productDataModel> arr=new ArrayList<>();
        String user=getActiveUser();
        SQLiteDatabase db=this.getWritableDatabase();
        String sql1="select * from cart where user='"+user+"';";
        Cursor cursor=db.rawQuery(sql1,null);
        if(cursor.moveToFirst()){
           do{
               String title=cursor.getString(1);
               String cate=cursor.getString(2);
               String price=cursor.getString(3);
               Bitmap img=getImageFromDB(title,cate);
               //creating an object with the information retrieved from database and so on:-
               productDataModel product=new productDataModel(cate,title,price,"","",img,-1);
               arr.add(product);
           }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arr;
    }

    void deleteProduct(String usertitlecate,String table){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="DELETE FROM "+table+" WHERE usertitlecate='"+usertitlecate+"';";
        db.execSQL(sql);
        db.close();
    }

    int getProductPrice(String title,String cate){
        int res=-1;
        String sql="select * from Proudect where title='"+title+"' and cat='"+cate+"';";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int price=cursor.getInt(4);
                res=price;
                break;
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return res;
    }

    void setTotal(int total,String usertitlecate){
        String sql="UPDATE cart SET total="+total+" WHERE usertitlecate='"+usertitlecate+"';";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    int getTotalPrice(String usertitlecate){
        int total=0;
        String sql="select * from cart where usertitlecate='"+usertitlecate+"';";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int res=cursor.getInt(6);
                total=res;
                break;
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    int getFinalTotal(String user){
        int res=-1;
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT sum(price*quantity) from cart where user='"+user+"';";

        Cursor cursor=db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                int total=cursor.getInt(0);
                res=total;
                break;
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return res;
    }

    String getPassword(String username){
        String res="Invalid Username!";
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT * from customer where username='"+username+"';";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String pass=cursor.getString(3);
                res=pass;
                break;
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return res;
    }

}

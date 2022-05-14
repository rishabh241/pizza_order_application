package com.example.pizzaorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pizzaorderapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView recyclerView;
    ActivityMainBinding binding;
//    ArrayList<list_item> pizzaList = new ArrayList<>();
    ArrayList<list_item2> pizzaList2 = new ArrayList<>();
    SQLiteDatabase database;
//    list_adapter adapter;
    list_adapter2 adapter2;
     public void updataList(){
         Cursor c= database.rawQuery("SELECT * FROM classification",null);

       int  nameIndedx =c.getColumnIndex("name");
       int descIndex =c.getColumnIndex("descr");
//       int sizeIndex = c.getColumnIndex("size");
//         Log.i("name",c.getString(nameIndedx));
         if(c.moveToFirst()){
            pizzaList2.clear();
            do {
                list_item2 list_item2 = new list_item2(c.getString(nameIndedx),c.getString(descIndex));
                pizzaList2.add(list_item2);
            }while(c.moveToNext());
            adapter2.notifyDataSetChanged();
         }

     }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result ="";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data= reader.read();
                while (data!=-1){
                    char current = (char) data;
                    result+=current;
                    data = reader.read();
                }
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
//            Log.i("result", result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            database.execSQL("DELETE FROM classification");
            try {
                JSONObject jsonObject = new JSONObject(s);
//                String name1 = jsonObject.getString("name");
                String desc = jsonObject.getString("description");
//                Log.i("name",desc);
                String type = jsonObject.getString("crusts");
                JSONArray jsonArray = new JSONArray(type);
//                String name = " ";
                String sizeName = "";
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String pizzaSize = jsonObject1.getString("name");
//                    JSONArray jsonArray1 = new JSONArray(pizzaSize);
//                    for (int j=0;j<jsonArray1.length();j++){
//                        JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
//                        sizeName = jsonObject2.getString("name");
////                        String Price = jsonObject2.getString("price");
////                        Log.i("price",Price);
////                        Log.i("name",sizeName);
//                    }
                    Log.i("name",pizzaSize);
                    String sql = "INSERT INTO classifiction(name,descr) VALUES(?,?)";
                    SQLiteStatement statement = database.compileStatement(sql);
                    statement.bindString(1,pizzaSize);
                    statement.bindString(2,desc);
//                    statement.bindString(3,sizeName);
                    statement.execute();




                }

                updataList();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = findViewById(R.id.listtt);
        database =this.openOrCreateDatabase("Classification",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS classification(id INTEGER PRIMARY KEY,name VARCHAR,descr VARCHAR)");
        DownloadTask task =new DownloadTask();
        task.execute("https://625bbd9d50128c570206e502.mockapi.io/api/v1/pizza/1");
//        String[] names = {"Hand-Tossed","Cheese Burst"};
//        String names = "Hand-Tossed";
//        String[] desc = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."};
//        String desc ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

//     pizzaList.add(lists);
//     Log.i("type",type);
//    for(int i=0;i<names.length;i++){
//        list_item lists = new list_item(names[i],desc[i]);
//        pizzaList.add(lists);
//    }
     adapter2 =new list_adapter2(MainActivity.this,pizzaList2);
    recyclerView.setAdapter(adapter2);
        updataList();
//    recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////            Intent intent =new Intent(getApplicationContext(),);
//        }
//    });


    }
}
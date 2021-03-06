package a07580542.emergencyphonenumber;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import a07580542.emergencyphonenumber.adapter.PhoneListAdapter;
import a07580542.emergencyphonenumber.db.PhonedbHelper;
import a07580542.emergencyphonenumber.model.PhoneItem;

public class MainActivity extends AppCompatActivity {

    private PhonedbHelper pdbh;
    private SQLiteDatabase sqldb;
    private ArrayList<PhoneItem> phoneitemslist = new ArrayList<>();
    private PhoneListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdbh = new PhonedbHelper(this);
        sqldb = pdbh.getReadableDatabase();

        loadDatafromDB();
        adapter = new PhoneListAdapter(this,R.layout.item,phoneitemslist);
        ListView lv = findViewById(R.id.listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PhoneItem item = phoneitemslist.get(position);
                String phone_number = item.number;

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel : "+phone_number));
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                String[] item = new String[]{"แก้ไขข้อมูล","ลบข้อมูล"};
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            PhoneItem item = phoneitemslist.get(position);
                            int phoneid = item.id;

                            ContentValues cv = new ContentValues();
                            cv.put(PhonedbHelper.getCol_number(),"12345");

                            sqldb.update(PhonedbHelper.getTb_name(),cv,PhonedbHelper.getCol_id()+"=?",new String[]{String.valueOf(phoneid)});
                            loadDatafromDB();
                            adapter.notifyDataSetChanged();
                        }else if(i==1){
                            PhoneItem item = phoneitemslist.get(position);
                            int phoneid = item.id;
                            String phoneimg = item.picture;
                            sqldb.delete(PhonedbHelper.getTb_name(),PhonedbHelper.getCol_id()+"=? AND "+PhonedbHelper.getCol_picture()+"=?",new String[]{String.valueOf(phoneid),phoneimg});
                            loadDatafromDB();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
//        Button insert_b = findViewById(R.id.insert_button);
//        insert_b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText phone_title = findViewById(R.id.phone_title_edittext);
//                EditText phone_number = findViewById(R.id.phone_number_edittext);
//                //เพิ่มตรวจสอบ input
//                String phonetitle = phone_title.getText().toString();
//                String phonenumber = phone_number.getText().toString();
//
//                ContentValues cv = new ContentValues();
//                cv.put(PhonedbHelper.getCol_title(),phonetitle);
//                cv.put(PhonedbHelper.getCol_number(),phonenumber);
//                cv.put(PhonedbHelper.getCol_picture(),"ic_launcher.png");
//                sqldb.insert(PhonedbHelper.getTb_name(),null,cv);
//                loadDatafromDB();
//                adapter.notifyDataSetChanged();
//            }
//        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddPhoneActivity.class);
                startActivityForResult(intent,123);
            }
        });

    }//End OnCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123){
            if(resultCode==RESULT_OK){
                loadDatafromDB();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void loadDatafromDB() {
        Cursor cursor = sqldb.query(PhonedbHelper.getTb_name(),null,null,null,null,null,null);
        phoneitemslist.clear();
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(PhonedbHelper.getCol_title()));
            String number = cursor.getString(cursor.getColumnIndex(PhonedbHelper.getCol_number()));
            String picture = cursor.getString(cursor.getColumnIndex(PhonedbHelper.getCol_picture()));
            int id = cursor.getInt(cursor.getColumnIndex(PhonedbHelper.getCol_id()));
            PhoneItem item = new PhoneItem(id,title,number,picture);
            phoneitemslist.add(item);
        }
    }
}//End Main

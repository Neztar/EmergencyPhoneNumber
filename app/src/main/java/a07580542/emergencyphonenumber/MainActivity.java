package a07580542.emergencyphonenumber;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
        Button insert_b = findViewById(R.id.insert_button);
        insert_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phone_title = findViewById(R.id.phone_title_edittext);
                EditText phone_number = findViewById(R.id.phone_number_edittext);
                //เพิ่มตรวจสอบ input
                String phonetitle = phone_title.getText().toString();
                String phonenumber = phone_number.getText().toString();

                ContentValues cv = new ContentValues();
                cv.put(PhonedbHelper.getCol_title(),phonetitle);
                cv.put(PhonedbHelper.getCol_number(),phonenumber);
                cv.put(PhonedbHelper.getCol_picture(),"ic_launcher.png");
                sqldb.insert(PhonedbHelper.getTb_name(),null,cv);
                loadDatafromDB();
                adapter.notifyDataSetChanged();
            }
        });

    }//End OnCreate

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

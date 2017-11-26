package a07580542.emergencyphonenumber;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import a07580542.emergencyphonenumber.db.PhonedbHelper;
import a07580542.emergencyphonenumber.model.PhoneItem;

public class MainActivity extends AppCompatActivity {

    private PhonedbHelper pdbh;
    private SQLiteDatabase sqldb;
    private ArrayList<PhoneItem> phoneitemslist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdbh = new PhonedbHelper(this);
        sqldb = pdbh.getReadableDatabase();

        Cursor cursor = sqldb.query(PhonedbHelper.getTb_name(),null,null,null,null,null,null);

        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(PhonedbHelper.getCol_title()));
            String number = cursor.getString(cursor.getColumnIndex(PhonedbHelper.getCol_number()));
            String picture = cursor.getString(cursor.getColumnIndex(PhonedbHelper.getCol_picture()));
            int id = cursor.getInt(cursor.getColumnIndex(PhonedbHelper.getCol_id()));
            PhoneItem item = new PhoneItem(id,title,number,picture);
            phoneitemslist.add(item);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,phoneitemslist);
        ListView lv = findViewById(R.id.listview);
        lv.setAdapter(adapter);
    }
}

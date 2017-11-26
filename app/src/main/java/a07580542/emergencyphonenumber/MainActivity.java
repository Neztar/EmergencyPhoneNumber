package a07580542.emergencyphonenumber;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import a07580542.emergencyphonenumber.db.PhonedbHelper;

public class MainActivity extends AppCompatActivity {

    private PhonedbHelper pdbh;
    private SQLiteDatabase sqldb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdbh = new PhonedbHelper(this);
        sqldb = pdbh.getReadableDatabase();

        Cursor cursor = sqldb.query(pdbh.getTb_name(),null,null,null,null,null,null);

        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
        }
    }
}

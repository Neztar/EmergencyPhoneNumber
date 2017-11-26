package a07580542.emergencyphonenumber.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dream on 26/11/2560.
 */

public class PhonedbHelper extends SQLiteOpenHelper{
    private static final String db_name="phone.db";
    private static final int db_ver=1;

    public static String getTb_name() {
        return tb_name;
    }

    public static String getCol_id() {
        return col_id;
    }

    public static String getCol_title() {
        return col_title;
    }

    public static String getCol_number() {
        return col_number;
    }

    public static String getCol_picture() {
        return col_picture;
    }

    private static final String tb_name="phone_number";
    private static final String col_id="_id";
    private static final String col_title="title";
    private static final String col_number="number";
    private static final String col_picture="picture";

    private static final String create_table="create table "+tb_name+"("
            +col_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +col_title+" TEXT,"
            +col_number+" TEXT,"
            +col_picture+" TEXT);";

    public PhonedbHelper(Context context) {
        super(context, db_name, null, db_ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
        insertdata(db);
    }

    private void insertdata(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(col_title,"แจ้งเหตุด่วน-เหตุร้าย");
        cv.put(col_number,"191");
        cv.put(col_picture,"phone191.png");
        db.insert(tb_name,null,cv);

        cv = new ContentValues();
        cv.put(col_title,"แจ้งเหตุเพลิงไหม้");
        cv.put(col_number,"199");
        cv.put(col_picture,"phone199.png");
        db.insert(tb_name,null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {

    }
}

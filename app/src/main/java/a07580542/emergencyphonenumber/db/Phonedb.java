package a07580542.emergencyphonenumber.db;

/**
 * Created by Dream on 26/11/2560.
 */

public class Phonedb {
    private static final String db_name="phone.db";
    private static final int db_ver=1;

    private static final String tb_name="phone_number";
    private static final String col_id="_id";
    private static final String col_title="title";
    private static final String col_number="number";
    private static final String col_picture="picture";

    private static final String create_table="create table "+tb_name+"("
            +col_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +col_title+" TEXT,"
            +col_number+" TEXT,"
            +col_picture+" TEXT)";
}

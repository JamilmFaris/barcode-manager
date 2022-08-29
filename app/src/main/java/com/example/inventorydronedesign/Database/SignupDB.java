package com.example.inventorydronedesign.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SignupDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "signup";

    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "accounts";

    private static final String ID_COL = "employeeid";

    private static final String NAME_COL = "name";

    private static final String EMAIL_COL = "email";

    private static final String PASSWORD = "password";

    public SignupDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + PASSWORD + " TEXT)";

        db.execSQL(query);
    }

    public void addNewAccount(int employeeID, String name, String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_COL, employeeID);
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(PASSWORD, password);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean profileExist(int employeeID, String email ){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + ID_COL + ", " + EMAIL_COL + " FROM " + TABLE_NAME
                , null);

        boolean existed = false;

        if (cursor.moveToFirst()) {
            do {
                if(employeeID == cursor.getInt(0) && email.equals(cursor.getString(1))){
                    existed = true;
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return existed;
    }
}

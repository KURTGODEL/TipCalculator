package com.example.tipcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase _db;

    public DBHelper(@Nullable Context context){
        super(context, "test.db", null, 1);
        this._db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_users =
                "CREATE TABLE IF NOT EXISTS users ( " +
                " ID integer PRIMARY KEY AUTOINCREMENT," +
                " name text " +
                ")";

        db.execSQL(create_table_users);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");

        onCreate(db);
    }

    public boolean insertUsers(){
        ContentValues values = new ContentValues();
        values.put("ID", 1);
        values.put("name", "Erick");

        this._db.insert("users", null, values);

        return true;
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(this._db, "users");
    }

    public String getUser(int id){

        Cursor cursor =
                this._db.query(
                    "users",
                    new String [] {"name"},
                    "ID = ?",
                    new String [] { String.valueOf(id) },
                    null,
                    null,
                    null);

        String name = "";

        while(cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        }

        return name;
    }
}

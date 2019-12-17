
package com.example.medi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DbOpenHelper{

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }

    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(boolean upgrade){

        if (upgrade = true){mDBHelper.onUpgrade(mDB, 1,1);}
        else {mDBHelper.onCreate(mDB);}
    }

    public void close(){
        mDB.close();
    }

    public long insertColumn(String codeNum, String name, String func , String use, String caution){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.codeNum, codeNum);
        values.put(DataBases.CreateDB.name, name);
        values.put(DataBases.CreateDB.func, func);
        values.put(DataBases.CreateDB.use, use);
        values.put(DataBases.CreateDB.cau, caution);

        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    public Cursor selectColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM usertable ORDER BY " + sort + ";", null);
        return c;
    }

    public boolean updateColumn(String codeNum, String name, String func , String use, String caution){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.codeNum, codeNum);
        values.put(DataBases.CreateDB.name, name);
        values.put(DataBases.CreateDB.func, func);
        values.put(DataBases.CreateDB.use, use);
        values.put(DataBases.CreateDB.cau, caution);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "codeNum=" + codeNum, null) > 0;
    }

    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    // Delete Column
    public boolean deleteColumn(long id){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
    }
}

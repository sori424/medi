package com.example.medi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String TABLE_NAME = "MEDICINE";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(TAG, "DataBaseHelper 생성자 호출");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Table Created");
        String createQuery = "CREATE TABLE " + TABLE_NAME +
                "( MEDID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MEDNAME TEXT NOT NULL, " +
                "MEDFUNC TEXT NOT NULL, " +
                "MEDPROB TEXT );";
        sqLiteDatabase.execSQL(createQuery);
    }   // 보험코드, 의약품명, 기능, 주의사항으로 구성된 DB 생성 (History용)

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "Table onUpgrade");
        // 테이블 재정의하기 위해 현재의 테이블 삭제
        String createQuery = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(createQuery);
    }
}

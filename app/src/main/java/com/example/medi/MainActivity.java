package com.example.medi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this, "HistoryDB", null, 1);
        // 쓰기 가능한 SQLiteDatabase 인스턴스 구함
        db = databaseHelper.getWritableDatabase();

        db.close();
        databaseHelper.close();
    }

    void DBInsert(String tableName, String medName, Integer medID, String medFunc, String medHow, String medCau) {
        Log.d(TAG, "Insert Data " + medName);

        ContentValues contentValues = new ContentValues();
        contentValues.put("보험코드", medID);
        contentValues.put("의약품명", medName);
        contentValues.put("효과", medFunc);
        contentValues.put("사용법", medHow);
        contentValues.put("주의사항", medCau);

        // 리턴값: 생성된 데이터의 id
        long id = db.insert(tableName, null, contentValues);

        Log.d(TAG, "id: " + id);
    }

    void DBDelete(String tableName, String medName) {
        Log.d(TAG, "Delete Data " + medName);

        String nameArr[] = {medName};

        // 리턴값: 삭제한 수
        int n = db.delete(tableName, "의약품명 = ?", nameArr);

        Log.d(TAG, "n: " + n);
    }

    // "SELECT * FROM MEDICINE"
    void DBSearch(String tableName) {
        Cursor cursor = null;

        try {
            cursor = db.query(tableName, null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("보험코드"));
                    String name = cursor.getString(cursor.getColumnIndex("의약품명"));
                    String func = cursor.getString(cursor.getColumnIndex("효과"));
                    String how = cursor.getString(cursor.getColumnIndex("사용법"));
                    String cau = cursor.getString(cursor.getColumnIndex("주의사항"));

                    Log.d(TAG, "id: " + id + ", name: " + name + ", function: " + func + ", method: " + how + ", caution: " + cau);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    void DBSearch(String tableName, Integer MEDID) {        // Search by scanner
        Cursor cursor = null;

        try {
            cursor = db.query(tableName, null, "MEDID" + " = ?", new String[]{MEDID.toString()}, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("보험코드"));
                    String name = cursor.getString(cursor.getColumnIndex("의약품명"));
                    String func = cursor.getString(cursor.getColumnIndex("효과"));
                    String how = cursor.getString(cursor.getColumnIndex("사용법"));
                    String cau = cursor.getString(cursor.getColumnIndex("주의사항"));

                    Log.d(TAG, "id: " + id + ", name: " + name + ", function: " + func + ", method: " + how + ", caution: " + cau);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    void DBSearch(String tableName, String MEDNAME) {       // Search by toolbar
        Cursor cursor = null;

        try {
            cursor = db.query(tableName, null, "MEDNAME" + " LIKE ?", new String[]{"%" + MEDNAME + "%"}, null, null, "MEDNAME");

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("보험코드"));
                    String name = cursor.getString(cursor.getColumnIndex("의약품명"));
                    String func = cursor.getString(cursor.getColumnIndex("효과"));
                    String how = cursor.getString(cursor.getColumnIndex("사용법"));
                    String cau = cursor.getString(cursor.getColumnIndex("주의사항"));

                    Log.d(TAG, "id: " + id + ", name: " + name + ", function: " + func + ", method: " + how + ", caution: " + cau);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
package com.example.medi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "history_db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Medicine.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Medicine.TABLE_NAME);
        onCreate(db);
    }

    public List<Medicine> getAllMed(){
        List<Medicine> medicines = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Medicine.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Medicine medicine = new Medicine();
                medicine.setId(cursor.getInt(cursor.getColumnIndex(Medicine.COLUMN_ID)));
                medicine.setDivNm(cursor.getString(cursor.getColumnIndex(Medicine.DIV_NM)));
                medicine.setFomnTpNm(cursor.getString(cursor.getColumnIndex(Medicine.FOMNTP_NM)));
                medicine.setGnlNm(cursor.getString(cursor.getColumnIndex(Medicine.GNL_NM)));
                medicine.setGnlNmCd(cursor.getString(cursor.getColumnIndex(Medicine.GNL_NM_CD)));
                medicine.setInjcPthNm(cursor.getString(cursor.getColumnIndex(Medicine.INJCPTH_NM)));
                medicine.setIqtyTxt(cursor.getString(cursor.getColumnIndex(Medicine.IQTY_TXT)));
                medicine.setMeftDivNo(cursor.getString(cursor.getColumnIndex(Medicine.MEFT_DIV_NO)));
                medicine.setUnit(cursor.getString(cursor.getColumnIndex(Medicine.UNIT)));

                medicines.add(medicine);
            } while(cursor.moveToNext());
        }
        db.close();
        return medicines;
    }

    public long insertMed(String div_nm, String fomntp_nm, String gnl_nm, String gnl_nm_cd, String injcpth_nm, String iqty_txt, String meft_div_no, String unit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Medicine.DIV_NM, div_nm);
        values.put(Medicine.FOMNTP_NM, fomntp_nm);
        values.put(Medicine.GNL_NM, gnl_nm);
        values.put(Medicine.GNL_NM_CD, gnl_nm_cd);
        values.put(Medicine.INJCPTH_NM, injcpth_nm);
        values.put(Medicine.IQTY_TXT, iqty_txt);
        values.put(Medicine.MEFT_DIV_NO, meft_div_no);
        values.put(Medicine.UNIT, unit);

        long id = db.insert(Medicine.TABLE_NAME, null, values);
        db.close();

        return id;
    }


}

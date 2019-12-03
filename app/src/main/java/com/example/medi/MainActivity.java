package com.example.medi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements PhotoFragment.OnFragmentInteractionListener {



    SQLiteDatabase db;
    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    @BindView(R.id.mainContainer)
    RelativeLayout mainLayout;

    @BindView(R.id.layout_frag)
    FrameLayout fragLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this, "HistoryDB", null, 1);
        // 쓰기 가능한 SQLiteDatabase 인스턴스 구함
        db = databaseHelper.getWritableDatabase();

        db.close();
        databaseHelper.close();

        ButterKnife.bind(this);
        checkPermissions();


        // MainActivity에서 AlarmActivity로 화면 넘어가기
        Button btn1 = (Button)findViewById(R.id.alarmButton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.findButton)
    void onClickScanButton(){
        if (!flagPermissions){
            checkPermissions();
            return;
        }
        mainLayout.setVisibility(View.GONE);
        fragLayout.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frag, new PhotoFragment()).addToBackStack(null).commit();
        //        getSupportFragmentManager().beginTransaction().replace(R.id.res_photo, new PhotoFragment()).addToBackStack(null).commit();
    }

    @SuppressLint("NewApi")
    void checkPermissions(){
        if (!hasPermissions(this, PERMISSIONS)){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
            flagPermissions = false;
        }
        flagPermissions = true;
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if (context != null && permissions != null){
            for(String permission : permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Bitmap bitmap) {
        if(bitmap != null){
            ImageFragment imageFragment = new ImageFragment();
            imageFragment.imageSetupFragment(bitmap);

            //            mainLayout.setVisibility(View.GONE);
            //            fragLayout.setVisibility(View.VISIBLE);
            //            getSupportFragmentManager().beginTransaction().replace(R.id.layout_frag, imageFragment).addToBackStack(null).commit();


        }
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
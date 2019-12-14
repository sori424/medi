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
import android.media.MediaExtractor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements PhotoFragment.OnFragmentInteractionListener {

    SQLiteDatabase db;

    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    private MainBackPressCloseHandler mainBackPressCloseHandler ;

    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public List<Medicine> mediList;
    private RecyclerView recyclerView;
    private EditText editSearch;
    private SimpleTextAdapter adapter;
    private List<Medicine> medicine;
    private List<Medicine> historyList;

    public void setMediList(List<Medicine> mediList) {
        Medicine med1 = new Medicine();
        Medicine med2 = new Medicine();
        Medicine med3 = new Medicine();
        med1.setCodeNum(646202070);
        med1.setName("마법의 약");
        med1.setFunc("과제를 끝내줌");
        med1.setUse("하루에 1번씩 복용");
        med1.setCau("힘들다");
        med2.setCodeNum(123456789);
        med2.setName("마술의 약");
        med2.setFunc("에러를 없애줌");
        med2.setUse("먹고 코드를 짠다");
        med2.setCau("과부하가 걸릴 수 있다");
        med3.setCodeNum(234567890);
        med3.setName("마법사의 약");
        med3.setFunc("행동이 빨라진다");
        med3.setUse("필요할 때 1방울");
        med3.setCau("시간도 빨라진다");
        mediList.add(med1);
        mediList.add(med2);
        mediList.add(med3);
    }


    private void initLoadDB(){

        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // db에 있는 값들을 model을 적용해서 넣는다.
        mediList = mDbHelper.getTableData();

        // db 닫기
        mDbHelper.close();
    }

    @BindView(R.id.mainContainer)
    RelativeLayout mainLayout;

    @BindView(R.id.layout_frag)
    FrameLayout fragLayout;

    @BindView(R.id.layout_image_frag)
    FrameLayout imageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setMediList(mediList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBackPressCloseHandler = new MainBackPressCloseHandler(this);
        //initLoadDB();

        editSearch = findViewById(R.id.editSearch);
        recyclerView = findViewById(R.id.recycler2);

        historyList = new ArrayList<Medicine>();
        mediList = new ArrayList<Medicine>();
        setMediList(mediList);

        medicine = new ArrayList<Medicine>();
        medicine.addAll(mediList);

        adapter = new SimpleTextAdapter(mediList);

        recyclerView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });

    DbOpenHelper dbhelper = new DbOpenHelper(this);
        dbhelper.open();
        dbhelper.create();

        ButterKnife.bind(this);
        checkPermissions();
    }

    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        historyList.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() != 0) {

            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < medicine.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (medicine.get(i).getName().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    historyList.add(medicine.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        //adapter.notifyDataSetChanged();
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

            fragLayout.setVisibility(View.GONE);
            imageLayout.setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.layout_image_frag, imageFragment).addToBackStack(null).commit();
            //            mainLayout.setVisibility(View.GONE);
            //            fragLayout.setVisibility(View.VISIBLE);
            //            getSupportFragmentManager().beginTransaction().replace(R.id.layout_frag, imageFragment).addToBackStack(null).commit();


        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_image_frag, fragment).commit();
    }


    /*void DBInsert(String tableName, String medName, Integer medID, String medFunc, String medHow, String medCau) {
        Log.d(TAG, "Insert Data " + medName);

        ContentValues contentValues = new ContentValues();
        contentValues.put("codeNum", medID);
        contentValues.put("name", medName);
        contentValues.put("func", medFunc);
        contentValues.put("use", medHow);
        contentValues.put("cau", medCau);

        // 리턴값: 생성된 데이터의 id
        long id = .insert(tableName, null, contentValues);

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
                    getResult(cursor);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }*/

    void DBSearch_scanner(int MEDID) {        // Search by scanner

        boolean found = false;
        int i;

        for (i = 0; i < mediList.size(); i++){
            if (MEDID == mediList.get(i).codeNum){
                found = true;
                break;
            }
        }

        if (found) {
            mediList.get(i);
        } else {

        }
        /*Cursor cursor = null;

        try {
            cursor = db.query("DBMed2", null, "MEDID" + " = ?", new String[]{String.valueOf(MEDID)}, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("보험코드"));
                    String name = cursor.getString(cursor.getColumnIndex("의약품명"));
                    String func = cursor.getString(cursor.getColumnIndex("효과"));
                    String how = cursor.getString(cursor.getColumnIndex("사용법"));
                    String cau = cursor.getString(cursor.getColumnIndex("주의사항"));

                    Log.d(TAG, "id: " + id + ", name: " + name + ", function: " + func + ", method: " + how + ", caution: " + cau);
                    getResult(cursor);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }*/
    }

    void DBSearch_tool(String tableName, String MEDNAME) {       // Search by toolbar

        boolean found = false;
        int i;

        for (i = 0; i < mediList.size(); i++){
            if (MEDNAME == mediList.get(i).name){
                found = true;
                break;
            }
        }

        if (found) {
            mediList.get(i);
        } else {

        }

        //Cursor cursor = null;

        /*try {
            cursor = db.query(tableName, null, "MEDNAME" + " LIKE ?", new String[]{"%" + MEDNAME + "%"}, null, null, "MEDNAME");

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("보험코드"));
                    String name = cursor.getString(cursor.getColumnIndex("의약품명"));
                    String func = cursor.getString(cursor.getColumnIndex("효과"));
                    String how = cursor.getString(cursor.getColumnIndex("사용법"));
                    String cau = cursor.getString(cursor.getColumnIndex("주의사항"));

                    Log.d(TAG, "id: " + id + ", name: " + name + ", function: " + func + ", method: " + how + ", caution: " + cau);
                    getResult(cursor);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }*/
    }

    public String getResult(Cursor cursor){
        String result="";
        while (cursor.moveToNext()){
            result+=cursor.getString(0)+":"
                    +cursor.getString(1)+"|"
                    +cursor.getString(2)+"|"
                    +cursor.getString(3)+"|"
                    +cursor.getString(4)+
                    "\n";
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    }
}

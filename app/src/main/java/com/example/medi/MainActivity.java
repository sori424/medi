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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
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
        Medicine med4 = new Medicine();
        Medicine med5 = new Medicine();
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
        med4.setCodeNum(646201050);
        med4.setName("종강의 약");
        med4.setFunc("종강시킨다");
        med4.setUse("원샷");
        med4.setCau("학점은 랜덤");
        med5.setCodeNum(646200690);
        med5.setName("종설프의 약");
        med5.setFunc("교수님을 호출한다");
        med5.setUse("먹고 약통을 문지른다");
        med5.setCau("혼날 수 있다");
        mediList.add(med1);
        mediList.add(med2);
        mediList.add(med3);
        mediList.add(med4);
        mediList.add(med5);
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

        historyList = new ArrayList<Medicine>();
        mediList = new ArrayList<Medicine>();
        setMediList(mediList);

        AutoCompleteTextView edit = findViewById(R.id.autoSearchView);

        String[] items= new String[5];
        if (mediList.size() != 0) {
            for (int i = 0; i < mediList.size(); i++) {
                items[i] = mediList.get(i).getName();
            }
        } else {
            List<Medicine> list1 = new ArrayList<>();
            Medicine med1 = new Medicine();
            Medicine med2 = new Medicine();
            Medicine med3 = new Medicine();
            Medicine med4 = new Medicine();
            Medicine med5 = new Medicine();
            med1.setCodeNum(646202070);
            med1.setName("마법의 약");
            med1.setFunc("과제를 끝내줌");
            med1.setUse("하루에 1번씩 복용");
            med1.setCau("힘들다");
            med2.setCodeNum(692000120);
            med2.setName("마술의 약");
            med2.setFunc("에러를 없애줌");
            med2.setUse("먹고 코드를 짠다");
            med2.setCau("과부하가 걸릴 수 있다");
            med3.setCodeNum(643901070);
            med3.setName("마법사의 약");
            med3.setFunc("행동이 빨라진다");
            med3.setUse("필요할 때 1방울");
            med3.setCau("시간도 빨라진다");
            med4.setCodeNum(646201050);
            med4.setName("종강의 약");
            med4.setFunc("종강시킨다");
            med4.setUse("원샷");
            med4.setCau("학점은 랜덤");
            med5.setCodeNum(646200690);
            med5.setName("종설프의 약");
            med5.setFunc("교수님을 호출한다");
            med5.setUse("먹고 약통을 문지른다");
            med5.setCau("혼날 수 있다");
            list1.add(med1);
            list1.add(med2);
            list1.add(med3);
            list1.add(med4);
            list1.add(med5);
            mediList=list1;
            for (int i = 0; i < mediList.size(); i++) {
                items[i] = mediList.get(i).getName();
            }
        }

        edit.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, items));


        ButterKnife.bind(this);
        checkPermissions();
        // MainActivity에서 AlarmActivity로 화면 넘어가기
        ImageButton btn1 = (ImageButton)findViewById(R.id.alarmButton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.historyButton)
    void onClickHistoryButton(){
        this.replaceFragment(new HistoryFragment(this.historyList));
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

    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    }
}

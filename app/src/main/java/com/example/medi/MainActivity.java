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
import androidx.recyclerview.widget.LinearLayoutManager;
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


    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    private HistoryAdapter historyAdapter;
    private List<Medicine> medList = new ArrayList<>();

    private DatabaseHelper db;

   // final static String dbName = "history.db";
   // final static int dbVersion = 2;



    private MainBackPressCloseHandler mainBackPressCloseHandler ;

    String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


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

        AutoCompleteTextView edit = findViewById(R.id.autoSearchView);


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

        ImageButton btn2 = (ImageButton)findViewById(R.id.historyButton);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }

        });

    }

    // TODO : xml parsing 결과 집어넣기

//    @OnClick(R.id.search_button)
//    void onClickSearchButton(){
//        boolean found=false;
//        AutoCompleteTextView edit = findViewById(R.id.autoSearchView);
//        String name = edit.getText().toString();
//        for (int i=0;i<mediList.size();i++){
//            String item = mediList.get(i).getName();
//            if (name.equals(item)){
//                found=true;
//                mainLayout.setVisibility(View.GONE);
//                fragLayout.setVisibility(View.VISIBLE);
//                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frag, new SearchFragment(item)).addToBackStack(null).commit();
//                break;
//            }
//        }
//        if (!found){
//            Toast.makeText(this, "검색결과가 없습니다.\n 다시 입력해 주세요.", Toast.LENGTH_LONG).show();
//        }
//    }

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

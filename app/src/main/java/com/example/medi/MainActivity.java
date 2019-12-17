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

    private MainBackPressCloseHandler mainBackPressCloseHandler ;

    String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public List<Medicine> mediList;
    public List<Medicine> historyList = new ArrayList<Medicine>();
    public static DbOpenHelper mDbOpenHelper;

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

        mDbOpenHelper = new DbOpenHelper(this);

        mDbOpenHelper.open();
        mDbOpenHelper.create(false); // true로 바꾸고 돌려 초기화, false로 재구동

        mediList = new ArrayList<Medicine>();
        /*setMediList(mediList);*/

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
            med1.setCodeNum("692000120");
            med1.setName("프레드포르테점안액");
            med1.setFunc("안검염, 결막염, 각막염, 공막염, 포도막염, 수술 후 염증");
            med1.setUse("1회 1-2방울 1일 2-4회 점안한다. 처음 1-2일간은 필요시 매시간 사용할 수 있다.");
            med1.setCau("1) 녹내장 환자\n2) 2세 미안의 영아\n3) 아황산수소나트륨이 함유되어 있으므로 아황산 아나필락시와 같은 알레르기를 일으킬 수 있으며, 일부 감수성 환자에서는 생명을 위협할 정도 또는 이보다 약한 천식 발작을 일으킬 수 있다. 일반 사람에서의 아황산감수성에 대한 총괄적인 빈도는 알려지지 않았으나 낮은 것으로 보이며 아황산감수성은 비천식 환자보다 천식 환자에서 빈번한 것으로 나타났다.");
            med2.setCodeNum("646202070");
            med2.setName("파모시드정20mg");
            med2.setFunc("위.십이지장궤양, 문합부궤양, 상부소화관출혈(소화성궤양, 급성스트레스궤양, 출혈성궤양에 의한), 역류성 식도염, 졸링거-엘리슨증후군, 다음 질환의 위점막 병변(미란, 출혈, 발적, 부종)의 개선: 급성위염, 만성위염의 급성악화기");
            med2.setUse("1) 성인 파모티딘으로서 1회 20mg 1일 2회(아침식사후, 저녁식사후 또는 취침시) 경구투여하거나 또는 1회 40mg 1일 1회(취침시) 경구투여한다. 상부소화관 출혈의 경우에는 보통 주사제로 치료를 개시하고, 경구투여가 가능하게 된 후에는 경구투여로 바꾼다.\n2) 주사투여 전까지와 동일하며, 연령 증상에 따라 적절히 증감한다.");
            med2.setCau("1) 이 약의 성분에 과민증의 병력이 있는 환자\n 2) 유당 함유 제품인 경우");
            med3.setCodeNum("643901070");
            med3.setName("솔로젠정");
            med3.setFunc("내분비 장애, 류마티스성 장애, 교원성 질환, 피부 질환, 알레르기성 질환, 안과 질환, 위장관계 질환, 호흡기계 질환, 혈액 질환, 악성 종양성 질환, 부종성 질환, 신경계 질환, 기타");
            med3.setUse("필요할 때 1방울");
            med3.setCau("시간도 빨라진다");
            med4.setCodeNum("646201050");
            med4.setName("종강의 약");
            med4.setFunc("종강시킨다");
            med4.setUse("원샷");
            med4.setCau("학점은 랜덤");
            med5.setCodeNum("646200690");
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

    List<Medicine> changeHistory(DbOpenHelper mDbOpenHelper){

        historyList.clear();
        Cursor cursor = null;
        int i =0;

        try {
            cursor = mDbOpenHelper.selectColumns();
            for (int j = 0; j<cursor.getCount();j++){
                Medicine medi = new Medicine();
                historyList.add(medi);
            }

            for (cursor.moveToFirst(), i= 0; !cursor.isAfterLast() && i<cursor.getCount(); cursor.moveToNext(),i++){
                historyList.get(i).setCodeNum(cursor.getString(0));
                historyList.get(i).setName(cursor.getString(1));
                historyList.get(i).setFunc(cursor.getString(2));
                historyList.get(i).setUse(cursor.getString(3));
                historyList.get(i).setCau(cursor.getString(4));
            }
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return historyList;
    }

    @OnClick(R.id.historyButton)
    void onClickHistoryButton(){

        changeHistory(mDbOpenHelper);

        if (historyList.size() != 0) {

            mainLayout.setVisibility(View.GONE);
            fragLayout.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_frag, new HistoryFragment(historyList)).addToBackStack(null).commit();
        } else {
            Toast.makeText(this, "히스토리가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.search_button)
    void onClickSearchButton(){
        boolean found=false;
        AutoCompleteTextView edit = findViewById(R.id.autoSearchView);
        String name = edit.getText().toString();
        for (int i=0;i<mediList.size();i++){
            String item = mediList.get(i).getName();
            if (name.equals(item)){
                found=true;
                mainLayout.setVisibility(View.GONE);
                fragLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frag, new SearchFragment(item)).addToBackStack(null).commit();
                break;
            }
        }
        if (!found){
            Toast.makeText(this, "검색결과가 없습니다.\n 다시 입력해 주세요.", Toast.LENGTH_LONG).show();
        }
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

    Medicine DBSearch(String tableName, String MEDID) {        // Search by scanner
        boolean found = false;
        int i;

        for (i = 0; i < mediList.size(); i++) {
            if (MEDID == mediList.get(i).codeNum) {
                found = true;
                break;
            }
        }

        if (found) {
            return mediList.get(i);
        } else {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    }
}

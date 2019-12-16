package com.example.medi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class SearchFragment extends Fragment {

    int i=0;
    MainActivity mainActivity = (MainActivity)getActivity();
    String item;
    List<Medicine> medi;

    public SearchFragment(String item) {
        this.item = item;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_search, container, false);
//        TextView resulttitle = v.findViewById(R.id.searchtitle);
//        TextView resultcontent = v.findViewById(R.id.searchresult);
//        boolean found =false;
//        if (medi!= null) {
//            for (i = 0; i < 5; i++) {
//                if (item == medi.get(i).getName()) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                for (i = 0; i < 5; i++) {
//                    if (item == medi.get(i).getCodeNum()) {
//                        break;
//                    }
//                }
//            }
//        } else {
//            List<Medicine> list1 = new ArrayList<>();
//            Medicine med1 = new Medicine();
//            Medicine med2 = new Medicine();
//            Medicine med3 = new Medicine();
//            Medicine med4 = new Medicine();
//            Medicine med5 = new Medicine();
//            med1.setCodeNum("646202070");
//            med1.setName("마법의 약");
//            med1.setFunc("과제를 끝내줌");
//            med1.setUse("하루에 1번씩 복용");
//            med1.setCau("힘들다");
//            med2.setCodeNum("692000120");
//            med2.setName("마술의 약");
//            med2.setFunc("에러를 없애줌");
//            med2.setUse("먹고 코드를 짠다");
//            med2.setCau("과부하가 걸릴 수 있다");
//            med3.setCodeNum("643901070");
//            med3.setName("마법사의 약");
//            med3.setFunc("행동이 빨라진다");
//            med3.setUse("필요할 때 1방울");
//            med3.setCau("시간도 빨라진다");
//            med4.setCodeNum("646201050");
//            med4.setName("종강의 약");
//            med4.setFunc("종강시킨다");
//            med4.setUse("원샷");
//            med4.setCau("학점은 랜덤");
//            med5.setCodeNum("646200690");
//            med5.setName("종설프의 약");
//            med5.setFunc("교수님을 호출한다");
//            med5.setUse("먹고 약통을 문지른다");
//            med5.setCau("혼날 수 있다");
//            list1.add(med1);
//            list1.add(med2);
//            list1.add(med3);
//            list1.add(med4);
//            list1.add(med5);
//            medi=list1;
//
//            for (i = 0; i < 4; i++) {
//                if (medi.get(i).getName().equals(item)) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                for (i = 0; i < 4; i++) {
//                    if (medi.get(i).getCodeNum().equals(item)) {
//                        found=true;
//                        break;
//                    }
//                }
//            }
//        }
//        if (found) {
//            resulttitle.setText("<<" + medi.get(i).getName() + ">>");
//            resultcontent.setText("보험코드: " + medi.get(i).getCodeNum() + "\n\n효능: " + medi.get(i).getFunc() + "\n사용법: " + medi.get(i).getUse() + "\n주의사항: " + medi.get(i).getCau() + "\n");
//        } else {
//            resulttitle.setText("검색 결과가 없습니다.");
//            resultcontent.setText("뒤로 가기 버튼을 두 번 눌러\n앱을 종료하세요.");
//        }
//        return v;
//    }
//
//    @OnClick(R.id.addButton)
//    void onClickaddButton(){
//        Toast.makeText(getActivity(),"History에 저장되었습니다.",Toast.LENGTH_LONG).show();
//
//    }

}

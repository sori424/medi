package com.example.medi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    List<Medicine> mediList;

    public HistoryFragment(List<Medicine> mediList) {
        this.mediList = mediList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<Medicine> list1 = new ArrayList<>();
        Medicine med1 = new Medicine();
        Medicine med2 = new Medicine();
        Medicine med3 = new Medicine();
        Medicine med4 = new Medicine();
        Medicine med5 = new Medicine();
        med1.setCodeNum("646202070");
        med1.setName("마법의 약");
        med1.setFunc("과제를 끝내줌");
        med1.setUse("하루에 1번씩 복용");
        med1.setCau("힘들다");
        med2.setCodeNum("692000120");
        med2.setName("마술의 약");
        med2.setFunc("에러를 없애줌");
        med2.setUse("먹고 코드를 짠다");
        med2.setCau("과부하가 걸릴 수 있다");
        med3.setCodeNum("643901070");
        med3.setName("마법사의 약");
        med3.setFunc("행동이 빨라진다");
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
        //list1.add(med1);
        //list1.add(med2);
        //list1.add(med3);
        list1.add(med4);
        //list1.add(med5);
        mediList=list1;
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleTextAdapter adapter = new SimpleTextAdapter(mediList);
        recyclerView.setAdapter(adapter);
        return v;
    }
}

package com.example.medi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

import static com.example.medi.MainActivity.mDbOpenHelper;

public class SearchFragment extends Fragment {

    int i=0;
    MainActivity mainActivity = (MainActivity)getActivity();
    String item;
    List<Medicine> medi;
    Context context;

    public SearchFragment(String item) {
        this.item = item;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        TextView resulttitle = v.findViewById(R.id.searchtitle);
        TextView resultcontent = v.findViewById(R.id.searchresult);

        context = getContext();
        Button button2 = (Button)v.findViewById(R.id.back_button);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        boolean found =false;
        if (medi!= null) {
            for (i = 0; i < 5; i++) {
                if (item == medi.get(i).getName()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (i = 0; i < 5; i++) {
                    if (item == medi.get(i).getCodeNum()) {
                        break;
                    }
                }
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
            med3.setCodeNum("646201050");
            med3.setName("솔로젠정");
            med3.setFunc("내분비 장애, 류마티스성 장애, 교원성 질환, 피부 질환, 알레르기성 질환, 안과 질환, 위장관계 질환, 호흡기계 질환, 혈액 질환, 악성 종양성 질환, 부종성 질환, 신경계 질환, 기타");
            med3.setUse("1일 4mg에서 48mg까지 다양할 수 있다. 일반적으로 심하지 않은 상태에서는 저용량으로 충분하나, 특별한 환자에서는 초기 고용량이 요구될 수도 있다.");
            med3.setCau("녹내장 환자, 결핵성 환자, 단순 포진성 각막염 환자(면역 억제작용에 의해 증상을 악화시킬 수 있다.");
            med4.setCodeNum("643901070");
            med4.setName("오큐시클로점안액");
            med4.setFunc("눈동자를 키워 안과질환의 진단 및 치료에 사용");
            med4.setUse("1일 1회 1적, 5분 후 2회째 점적");
            med4.setCau("1) 소아(전신 부작용이 일어나기 쉽다.)\n2) 전립선 비대 환자\n3) 심기능부전 환자\n4) 운동실조 환자\n5. 벨라돈나 알칼로이드에 감수성 있는 환자\n6) 다운증후군 환자");
            med5.setCodeNum("646200690");
            med5.setName("록소펜정");
            med5.setFunc("만성 류마티스 관절염, 골관절염(퇴행관절염), 요통, 견관절주위염, 경견완증후군 질환 및 증상의 소염.진통, 수술후, 외상후 및 발치후의 소염.진통");
            med5.setUse("성인: 록소프로펜나트륨(무수물)으로서 1회 60mg을 1일 3회 경구투여한다. 1회 요법시에는 1회 60~120mg을 경구투여한다.");
            med5.setCau("1) 소화성 궤양 환자\n2) 중증 혈액이상 환자\n3) 중증 간장애 환자\n4) 중증 신장애 환자");
            list1.add(med1);
            list1.add(med2);
            list1.add(med3);
            list1.add(med4);
            list1.add(med5);
            medi=list1;

            for (i = 0; i < 5; i++) {
                if (medi.get(i).getName().equals(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (i = 0; i < 5; i++) {
                    if (medi.get(i).getCodeNum().equals(item)) {
                        found=true;
                        break;
                    }
                }
            }
        }
        if (found) {
            resulttitle.setText("<<" + medi.get(i).getName() + ">>");
            resultcontent.setText("보험코드: " + medi.get(i).getCodeNum() + "\n\n효능: " + medi.get(i).getFunc() + "\n사용법: " + medi.get(i).getUse() + "\n주의사항: " + medi.get(i).getCau() + "\n");

            mDbOpenHelper.insertColumn(medi.get(i).getCodeNum(),medi.get(i).getName(),medi.get(i).getFunc(),medi.get(i).getUse(),medi.get(i).getCau());
            Toast.makeText(getActivity(),"History에 저장되었습니다. 저장된 값: "+mDbOpenHelper.selectColumns().getCount(),Toast.LENGTH_LONG).show();

        } else {
            resulttitle.setText("검색 결과가 없습니다.");
            resultcontent.setText("뒤로 가기 버튼을 두 번 눌러\n앱을 종료하세요.");
        }
        return v;
    }
}

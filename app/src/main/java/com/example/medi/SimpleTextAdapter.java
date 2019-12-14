package com.example.medi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    private List<Medicine> mData;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textname;
        TextView textnum;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textname = itemView.findViewById(R.id.text1);
            textnum = itemView.findViewById(R.id.text1_1);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SimpleTextAdapter(List<Medicine> list) {

        if (list!=null){mData = list;}
        else {
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
            mData=list1;
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
        boolean found = false;
        Medicine text = mData.get(position);
        for (int i =0;i<getItemCount();i++){
            if (true){
                holder.textname.setText("<<"+text.getName()+">>"+"\n보험코드: "+text.getCodeNum()+"\n\n효능: "+text.getFunc()+"\n사용법: "+text.getUse()+"\n주의사항: "+text.getCau()+"\n");
                found = true;
            }
            continue;
        }
        if (!found){
            holder.textname.setText("");
        }

        holder.textnum.setText(Integer.toString(text.getCodeNum()));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}

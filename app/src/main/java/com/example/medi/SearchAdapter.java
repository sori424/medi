package com.example.medi;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Medicine> mediList;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    public SearchAdapter(List<Medicine> list, Context context){
        this.mediList = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mediList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.recyclerview_item,null);

            viewHolder = new ViewHolder();
            viewHolder.label = convertView.findViewById(R.id.text1);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        Medicine text = mediList.get(position);
        viewHolder.label.setText(text.getName()+"\n\n보험코드: "+text.getCodeNum()+"\n효능: "+text.getFunc()+"\n사용법: "+text.getUse()+"\n주의사항: "+text.getCau()+"\n");

        return convertView;
    }

    class ViewHolder{
        public TextView label;
    }


}

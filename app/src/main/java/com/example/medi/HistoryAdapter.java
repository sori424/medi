package com.example.medi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter {

    private Context context;
    private List<Medicine> medList;

    public HistoryAdapter(@NonNull Context context, ArrayList medicines) {
        super(context, 0, medicines);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
        }

        // Get the data item for this position
        Medicine medicine = medList.get(position);

        // Lookup view for data population
        TextView divNm = (TextView) convertView.findViewById(R.id.divNm);
        TextView gnlNm = (TextView) convertView.findViewById(R.id.gnlNm);
        TextView fomnTpNm = (TextView) convertView.findViewById(R.id.fomnTpNm);
        TextView injcPthNm = (TextView) convertView.findViewById(R.id.injcPthNm);
        TextView iqtyTxt = (TextView) convertView.findViewById(R.id.iqtyTxt);
        TextView meftDivNo = (TextView) convertView.findViewById(R.id.meftDivNo);
        TextView unit = (TextView) convertView.findViewById(R.id.unit);
        // Populate the data into the template view using the data object
        divNm.setText(medicine.getDivNm());
        gnlNm.setText(medicine.getGnlNm());
        fomnTpNm.setText(medicine.getFomnTpNm());
        injcPthNm.setText(medicine.getInjcPthNm());
        iqtyTxt.setText(medicine.getIqtyTxt());
        meftDivNo.setText(medicine.getMeftDivNo());
        unit.setText(medicine.getUnit());
        // Return the completed view to render on screen
        return convertView;
    }
}

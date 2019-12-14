package com.example.medi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SearchFragment extends Fragment {

    List<Medicine> mediList;

    public SearchFragment(List<Medicine> mediList) {
        this.mediList = mediList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleTextAdapter adapter = new SimpleTextAdapter(mediList);
        recyclerView.setAdapter(adapter);
        return v;
    }

}

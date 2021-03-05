package com.example.basictimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ListScreenFragment extends Fragment {

    TextView times;

    public ListScreenFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);

        times = view.findViewById(R.id.lap);

        return view;
    }

    public void setText(String text) {


        times.setText(text);
    }

}
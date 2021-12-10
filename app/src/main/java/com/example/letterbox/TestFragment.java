package com.example.letterbox;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {
    String name;
    public TestFragment() {
//        this.name = name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        TextView text = view.findViewById(R.id.fragment_text);
//        text.setText(name);
        return view;
    }
}
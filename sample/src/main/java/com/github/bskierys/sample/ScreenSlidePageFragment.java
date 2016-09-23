/*
* author: Bartlomiej Kierys
* date: 2016-09-15
* email: bskierys@gmail.com
*/
package com.github.bskierys.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.bskierys.lockableviewpager.sample.R;

import butterknife.ButterKnife;

import butterknife.BindView;

public class ScreenSlidePageFragment extends Fragment {

    private static final String ARG_NAME = "fragment_name";
    @BindView(R.id.fragment_name) TextView nameView;
    private String name;
    private int color;

    public static ScreenSlidePageFragment newInstance(String fragmentName) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, fragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            color = Color.parseColor(generateColor(name));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        ButterKnife.bind(this, rootView);

        String hashTagName = "#" + name.toUpperCase();
        nameView.setText(hashTagName);
        rootView.setBackgroundColor(color);

        return rootView;
    }

    private String generateColor(String key) {
        int maxNumber = 16777215;
        int hash = Math.abs(key.hashCode() % maxNumber);
        StringBuilder color = new StringBuilder(Integer.toHexString(hash));
        while (color.length() < 6) {
            color.append("0");
        }

        return color.append("#").reverse().toString();
    }
}

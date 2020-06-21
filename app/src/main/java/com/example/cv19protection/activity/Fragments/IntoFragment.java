package com.example.cv19protection.activity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cv19protection.R;

import java.util.ArrayList;

public class IntoFragment extends Fragment {

    private ViewPager viewPager;
    private ArrayList<String> message_list;
    private ArrayList<Integer> image_list;

    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_introduction,container,false);

        fill_details();
        init(view);

        viewPager.setAdapter(new ViewPagerAdapater(getContext()));

        viewPager.setPageMargin(50);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void fill_details() {

        message_list=new ArrayList<String>();
        message_list.add("Wash Your Hand Frequently");
        message_list.add("Wear Mask Daily");
        message_list.add("Maintain Social Distance");

        image_list=new ArrayList<Integer>();
        image_list.add(R.drawable.hand_sanitazation);
        image_list.add(R.drawable.face_mask);
        image_list.add(R.drawable.distance);

    }

    private void init(View view) {

        viewPager=view.findViewById(R.id.into_viewpager);
        tabLayout=view.findViewById(R.id.tab_layout);

    }

    public class ViewPagerAdapater extends PagerAdapter{

        Context context;

        public ViewPagerAdapater(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return image_list.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view=LayoutInflater.from(context).inflate(R.layout.intoduction_imageview,container,false);

            ImageView p_img=view.findViewById(R.id.precaution_images);
            TextView p_pretext=view.findViewById(R.id.precation_text);

            p_img.setImageDrawable(getResources().getDrawable(image_list.get(position)));
            p_pretext.setText(message_list.get(position));

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

    }

}

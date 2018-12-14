package com.example.a01.house;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a01.house.adapter.MyTabFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragmentList_One extends Fragment {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private List<Fragment> fragments;
    private ViewPager mViewPager;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private TabLayout mTabLayout;
    private MyTabFragmentPagerAdapter mMyTabFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlist_one, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_main);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_pager);
        initdata();
        return view;
    }

    //初始化数据
    private void initdata() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new TabFragment1());
        fragments.add(new TabFragment2());
        fragments.add(new TabFragment3());
        //fragments.add(new TabFragment4());
        mMyTabFragmentPagerAdapter = new MyTabFragmentPagerAdapter(getActivity().getSupportFragmentManager()
                , fragments);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mMyTabFragmentPagerAdapter);
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        //four = mTabLayout.getTabAt(3);
    }
}



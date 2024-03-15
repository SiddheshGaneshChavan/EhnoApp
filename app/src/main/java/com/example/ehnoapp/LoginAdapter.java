package com.example.ehnoapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    public Context context;
    int totalTabs;
    public LoginAdapter(FragmentManager fm,Context context,int totalTabs){
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragment loginTabfragment=new LoginTabFragment();
                return loginTabfragment;
            case 1:
                SignUpTabFragment signUpTabFragment=new SignUpTabFragment();
                return signUpTabFragment;
            default:
                return null;
        }
    }

}

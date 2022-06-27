package com.example.instgran.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Locale;


public class FragmentAdapter extends FragmentPagerAdapter {
    private final LoginFragment loginFragment = new LoginFragment();
    private final SignUpFragment signUpFragment = new SignUpFragment();

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return loginFragment;
        }

        return signUpFragment;


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {

            if (Locale.getDefault().getLanguage() == "en") {
                return "Log in";
            } else {
                return "تسجيل دخول";
            }


        }
        if (Locale.getDefault().getLanguage() == "en") {
            return "Sign up";
        } else {
            return "تسجيل";
        }


    }
}

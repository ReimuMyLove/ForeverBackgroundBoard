package com.example.shoujiedemo.util;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements HandleBackInterface {
    @Override
    public boolean onBackPressed() {
        return HandleBackUtil.handleBackPress(this);
    }
}

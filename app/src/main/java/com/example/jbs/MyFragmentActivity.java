package com.example.jbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MyFragmentActivity extends AppCompatActivity {
    public MyFragmentActivity() {
        super();
    }
    public void replaceFragment(Fragment frag, int containerId, String tag) {
        Fragment currFrag = this.getSupportFragmentManager().findFragmentById(containerId);
        if(currFrag != null) {
            if (currFrag.getClass() == frag.getClass()) {
                return;
            }
            if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
                getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(containerId, frag, tag)
                .commit();
    }
}

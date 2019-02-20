package com.example.jbs.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jbs.CommonService;
import com.example.jbs.R;
import com.lamudi.phonefield.PhoneInputLayout;

public class PhoneRegisterActivity extends AppCompatActivity implements
        ConfirmPhoneFragment.ConfirmPhoneNumberCallback,
        VerifyOTPFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        if(null == savedInstanceState) {
            init();
        }
    }

    private void init( ) {
        ConfirmPhoneFragment confirmPhoneFragment = ConfirmPhoneFragment.newInstance("","");
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("confirmPhoneFragment")
                .replace(R.id.ctnFragment, confirmPhoneFragment)
                .commit();
    }

    public void replaceFragment(Fragment frag, String tag) {
        Fragment currFrag = getSupportFragmentManager().findFragmentById(R.id.ctnFragment);
        if(currFrag.getClass() == frag.getClass()) {
            return;
        }
        if(getSupportFragmentManager().findFragmentByTag(tag) != null){
            getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.ctnFragment, frag, tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int fragmentsInStack = getSupportFragmentManager().getBackStackEntryCount();
        if(fragmentsInStack > 1) {
            getSupportFragmentManager().popBackStack();
        } else if (fragmentsInStack == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPhoneNumberConfirmed(String phoneNumber) {
        VerifyOTPFragment verifyOTPFragment = VerifyOTPFragment.newInstance(phoneNumber);
        replaceFragment(verifyOTPFragment, "verifyOTPFragment");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

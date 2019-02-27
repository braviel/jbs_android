package com.example.jbs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.jbs.MyFragmentActivity;
import com.example.jbs.R;
import com.example.jbs.fragment.ConfirmPhoneFragment;
import com.example.jbs.fragment.VerifyOTPFragment;

public class PhoneRegisterActivity extends MyFragmentActivity implements
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
        Log.i("TAG", "PhoneNumber passed: " + phoneNumber);
        VerifyOTPFragment verifyOTPFragment = VerifyOTPFragment.newInstance(phoneNumber);
        replaceFragment(verifyOTPFragment, R.id.ctnFragment, "verifyOTPFragment");
    }

    @Override
    public void onFragmentInteraction() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}

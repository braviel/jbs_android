package com.example.jbs.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.example.jbs.MyFragmentActivity;
import com.example.jbs.R;
import com.example.jbs.fragment.ConfirmPhoneFragment;
import com.example.jbs.fragment.VerifyOTPFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lamudi.phonefield.PhoneInputLayout;

import androidx.annotation.Nullable;

public class PhoneRegisterActivity extends MyFragmentActivity implements
        ConfirmPhoneFragment.ConfirmPhoneNumberCallback,
        VerifyOTPFragment.OnFragmentInteractionListener
{
    public final static String TAG = PhoneRegisterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        if(null == savedInstanceState) {
            init();
        }
    }

    private void init( ) {
        requestHint();
    }
    private void beginVerifyPhoneNo(String phoneNo) {
        ConfirmPhoneFragment confirmPhoneFragment = ConfirmPhoneFragment.newInstance(phoneNo,"");
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(ConfirmPhoneFragment.TAG)
                .replace(R.id.ctnFragment, confirmPhoneFragment)
                .commit();
    }
    @Override
    public void onBackPressed() {
        backOnFragment();
//        int fragmentsInStack = getSupportFragmentManager().getBackStackEntryCount();
//        if(fragmentsInStack > 1) {
//            getSupportFragmentManager().popBackStack();
//        } else if (fragmentsInStack == 1) {
//            finish();
//        } else {
//            super.onBackPressed();
//        }
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
    private static final int RESOLVE_HINT = 1;
    private void requestHint() {
        GoogleApiClient apiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        HintRequest hintRequest = new HintRequest
                .Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(apiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch(IntentSender.SendIntentException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESOLVE_HINT) {
            if(resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                String unformatedPhone = credential.getId();
                beginVerifyPhoneNo(unformatedPhone);
            } else {
                beginVerifyPhoneNo("");
            }
        }
    }
}

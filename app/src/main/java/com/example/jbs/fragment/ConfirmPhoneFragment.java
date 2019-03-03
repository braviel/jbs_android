package com.example.jbs.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jbs.CommonService;
import com.example.jbs.R;
import com.lamudi.phonefield.PhoneInputLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmPhoneFragment} interface
 * to handle interaction events.
 * Use the {@link ConfirmPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ConfirmPhoneFragment extends BaseFragment implements CommonService.OnRequestPermissionListener{
    public interface ConfirmPhoneNumberCallback {
        void onPhoneNumberConfirmed(String phoneNumber);
    }
    public final static String TAG = ConfirmPhoneFragment.class.getSimpleName();
    private static final String ARG_PHONE_NO = "PhoneNo";
    final int REQUEST_READ_PHONE_STATE = 1;

//    private OnFragmentInteractionListener mListener;
    String mPhoneNo;
    @BindView(R.id.tvPhoneNumber)
    PhoneInputLayout tvPhoneNumber;
    @BindView(R.id.btnNext)
    Button btnNext;
    private ConfirmPhoneNumberCallback confirmPhoneNumberCallback;
    public ConfirmPhoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmPhoneFragment.
     */
    public static ConfirmPhoneFragment newInstance(String param1, String param2) {
        ConfirmPhoneFragment fragment = new ConfirmPhoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PHONE_NO, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoneNo = getArguments().getString(ARG_PHONE_NO);
//            Toast.makeText(getActivity(), "PhoneNo: " + mPhoneNo, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_confirm_phone, container, false);
        ButterKnife.bind(this, rootView);
        init(rootView);
        return rootView;
    }
    private void init(View rootView) {
        tvPhoneNumber.setHint(R.string.phone_number);
        tvPhoneNumber.setDefaultCountry("SG");
        tvPhoneNumber.setPhoneNumber(mPhoneNo);
//        String[] permissions = {
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.READ_CONTACTS
//        };
//        if(!CommonService.hasPermissions(getActivity(), permissions)) {
//            CommonService.requestPermission(getActivity(),
//                    permissions,
//                    REQUEST_READ_PHONE_STATE, this);
//        } else {
////            getPhoneNo();
//        }

        btnNext.setOnClickListener(v -> {
            boolean valid = true;
            if(tvPhoneNumber.isValid()) {
                tvPhoneNumber.setError(null);
            } else {
                tvPhoneNumber.setError("Invalid phone number");
                valid = false;
            }
            if(valid) {
                if(confirmPhoneNumberCallback != null) {
                    confirmPhoneNumberCallback.onPhoneNumberConfirmed(tvPhoneNumber.getPhoneNumber());
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ConfirmPhoneNumberCallback) {
            confirmPhoneNumberCallback = (ConfirmPhoneNumberCallback)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ConfirmPhoneNumberCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        confirmPhoneNumberCallback = null;
    }

    private void getPhoneNo() {
        Log.i(TAG, "Attempt to get phone no");
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            mPhoneNo = tMgr.getLine1Number();
            if (!mPhoneNo.equals("")) {
                tvPhoneNumber.setPhoneNumber(mPhoneNo);
            } else {
//                mPhoneNo = tMgr.getSubscriberId();
                String main_data[] = {"data1", "is_primary", "data3", "data2", "data1", "is_primary", "photo_uri", "mimetype"};
                Object object = getActivity().getContentResolver().query(Uri.withAppendedPath(android.provider.ContactsContract.Profile.CONTENT_URI, "data"),
                        main_data, "mimetype=?",
                        new String[]{"vnd.android.cursor.item/phone_v2"},
                        "is_primary DESC");
                if (object != null) {
                    do {
                        if (!((Cursor) (object)).moveToNext())
                            break;
                        // This is the phoneNumber
                        mPhoneNo = ((Cursor) (object)).getString(4);
                    } while (true);
                    ((Cursor) (object)).close();
                }
            }
            Toast.makeText(getActivity(), "Phone No: " + mPhoneNo, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getActivity(), "No Permission", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    getPhoneNo();
                } else {
                    //DENIED
                }
            }
        }
    }

    @Override
    public void onPermissionRequested(int REQ_CODE) {

    }
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }

}

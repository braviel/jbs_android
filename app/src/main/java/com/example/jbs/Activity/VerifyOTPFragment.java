package com.example.jbs.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbs.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerifyOTPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerifyOTPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerifyOTPFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PHONE_No = "PhoneNumber";

    // TODO: Rename and change types of parameters
    private String mPhoneNoParam;
    private TextInputEditText teOTPCode;
    private OnFragmentInteractionListener mListener;

    public VerifyOTPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param phoneNoParam Phone number to send OTP.*
     * @return A new instance of fragment VerifyOTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerifyOTPFragment newInstance(String phoneNoParam) {
        VerifyOTPFragment fragment = new VerifyOTPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PHONE_No, phoneNoParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoneNoParam = getArguments().getString(ARG_PHONE_No);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_verify_otp, container, false);
        final TextView fakeOTP = rootView.findViewById(R.id.tvFakeOTP);
        Random r = new Random();
        int randomInt = r.nextInt(999999);
        String s = String.format("%06d", randomInt);
        fakeOTP.setText(s);

        teOTPCode = rootView.findViewById(R.id.teOTPCode);

        Button btnValidate = rootView.findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = teOTPCode.getText().toString();
                if(s.equals(fakeOTP.getText().toString())) {
                    Toast.makeText(getActivity(), "Equal", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "NOT Equal", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

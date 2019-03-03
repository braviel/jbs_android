package com.example.jbs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.jbs.CommonService;
import com.example.jbs.MyFragmentActivity;
import com.example.jbs.R;
import com.example.jbs.activity.MainActivity;
import com.example.jbs.activity.SettingsActivity;
import com.example.jbs.activity.SplashActivity;
import com.example.jbs.controller.ProfileController;
import com.example.jbs.room.Profile;
import com.example.jbs.repo.DataRepo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileMenuFragment extends BaseFragment implements
        View.OnClickListener
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = ProfileMenuFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileMenuFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.lbPhoneNumber)
    TextView lbPhoneNumber;
    @BindView(R.id.lbCommonName)
    TextView lbCommonName;
    @BindView(R.id.pgLoading)
    ProgressBar pgLoading;
    @BindView(R.id.btnManageGroup)
    Button btnManageGroup;
    @BindView(R.id.btnSetting)
    Button btnSetting;
    private String mProfileUID;
    public static ProfileMenuFragment newInstance(String param1, String param2) {
        ProfileMenuFragment fragment = new ProfileMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, TAG + " CreateView");
        View rootView = inflater.inflate(R.layout.fragment_profile_menu, container, false);
        ButterKnife.bind(this, rootView);
        useToolBar(toolbar, true);
        Profile p = DataRepo.getInstance().getProfile();
        if(p == null) {
            new PrepareTask().execute();
        } else {
            UpdateUI(p);
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            ((MainActivity)getActivity()).onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnManageGroup)
    public void onBtnManageGroupClick(View v) {
        Log.i(TAG, "on Manage Group");
        ListGroupFragment listGroupFragment = ListGroupFragment.newInstance(1);
        ((MyFragmentActivity)getActivity()).replaceFragment(listGroupFragment,
                R.id.ctnFragment,
                ListGroupFragment.TAG
        );
    }

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
    public void onEditProfileClick(View v) {
        ViewProfileFragment viewProfileFragment = ViewProfileFragment.newInstance("");
        ((MyFragmentActivity)getActivity()).replaceFragment(viewProfileFragment,
                R.id.ctnFragment,
                ViewProfileFragment.TAG
                );
    }
    @Override
    @OnClick({R.id.imgAvatar, R.id.lbPhoneNumber, R.id.lbCommonName, R.id.btnSetting, R.id.btnUnbind})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imgAvatar:
            case R.id.lbCommonName:
            case R.id.lbPhoneNumber:
                onEditProfileClick(v);
                break;
            case R.id.btnSetting:

                break;
            case R.id.btnUnbind:
                try {
                    SharedPreferences sPref = getActivity().getSharedPreferences(getString(R.string.kJbsPref), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.remove(getString(R.string.kJbsProfileUID));
                    editor.apply();
                    Intent iSplashScreen = new Intent(getActivity(), SplashActivity.class);
                    startActivity(iSplashScreen);
                }catch (NullPointerException ex) {
                    Log.e(TAG, ex.getMessage());
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void UpdateUI(Profile profile) {
        lbCommonName.setText(profile.getCommonName());
        lbPhoneNumber.setText(profile.getProfilePhone());
        try {
            final String encodedURL = CommonService.SERVICE_BASE_URL + "profile/" +
                    URLEncoder.encode(profile.getProfileUID(), "UTF-8") + "/avatar";
            Log.i(TAG, "Load Image from: " + encodedURL);
            Glide.with(getActivity())
                    .load(encodedURL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvatar);
        } catch (UnsupportedEncodingException exp) {
            Log.e(TAG, exp.getMessage());
        }
    }
    private class PrepareTask extends AsyncTask<Void, Integer, Profile> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Profile doInBackground(Void... voids) {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.kJbsPref), Context.MODE_PRIVATE);
            mProfileUID = sharedPref.getString(getString(R.string.kJbsProfileUID), "");
            Profile p = ProfileController.getInstance().getProfile(mProfileUID);
            return p;
        }

        @Override
        protected void onPostExecute(Profile profile) {
            super.onPostExecute(profile);
            if(profile != null) {
                DataRepo.getInstance().setProfile(profile);
                UpdateUI(profile);
            }
            pgLoading.setVisibility(View.INVISIBLE);
        }
    }
}


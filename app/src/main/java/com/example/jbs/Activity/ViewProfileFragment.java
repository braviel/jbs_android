package com.example.jbs.activity;

import android.content.Context;
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
import android.widget.ProgressBar;

import com.example.jbs.CommonService;
import com.example.jbs.R;
import com.example.jbs.room.Profile;
import com.example.jbs.service.ProfileWebService;
import com.example.jbs.viewmodel.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfileFragment extends Fragment implements
        View.OnClickListener
{
    public static final String TAG = ViewProfileFragment.class.getSimpleName();
    public static final String UID_KEY = "uid";
//    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProfileViewModel viewModel;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFILEUID = "ProfileUID";
    private static final String ARG_PARAM2 = "param2";

    private String mParam2;
    @BindView(R.id.pgLoading)
    ProgressBar pgLoading;
    @BindView(R.id.root_view)
    CoordinatorLayout rootView;
    @BindView(R.id.tvPhoneNumber)
    TextInputEditText tvPhoneNumber;
    @BindView(R.id.tvCommonName)
    TextInputEditText tvCommonName;
    @BindView(R.id.tvFirstName)
    TextInputEditText tvFirstName;
    @BindView(R.id.tvLastName)
    TextInputEditText tvLastName;
    @BindView(R.id.tvEmail)
    TextInputEditText tvEmail;
    @BindView(R.id.tvUEN)
    TextInputEditText tvUEN;
    @BindView(R.id.tvDOB)
    TextInputEditText tvDOB;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindViews({R.id.tvCommonName, R.id.tvFirstName, R.id.tvLastName, R.id.tvEmail, R.id.tvUEN,
            R.id.tvAddress1, R.id.tvAddress2, R.id.tvDOB, R.id.tvBuildingName, R.id.tvPostalCode})
    List<TextInputEditText> mTextFields;
    //
    // STATE
    //
    private boolean mIsProfileExisted = false;
    private String mProfileUID;
    Profile mProfile;
    ProfileWebService mProfileService;
    private OnFragmentInteractionListener mListener;
    //
    private boolean mIsEditMode = false;
    public ViewProfileFragment() {
        // Required empty public constructor
    }
    private void getCurProfile() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mProfileUID = sharedPref.getString(getString(R.string.KeyProfileUID), "");
        Log.i(TAG, "Get Profile No: " + mProfileUID);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param profileUID Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProfileFragment.
     */
    public static ViewProfileFragment newInstance(String profileUID, String param2) {
        ViewProfileFragment fragment = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROFILEUID, profileUID);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mProfile = new Profile();
        CommonService.getInstance().initWebservice();
        mProfileService = CommonService.getInstance().provideApiWebservice();
        if (getArguments() != null) {
            mProfileUID = getArguments().getString(ARG_PROFILEUID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(mProfileUID.equals("")) {
            getCurProfile();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_profile, container, false);
        ButterKnife.bind(this, rootView);
        tvCommonName.setText("Nghia Hoang");
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolBar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Log.i(TAG, "Curr Phone No: " + mProfileUID);
        mProfile.setProfilePhone(mProfileUID);
        btnUpdate.setOnClickListener(this);
        pgLoading.setVisibility(View.VISIBLE);
        new GetProfileTask().execute(mProfileUID);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        this.configureDagger();
//        this.configureViewModel();
    }
    // -----------------
    // CONFIGURATION
    // -----------------

//    private void configureDagger(){
//        AndroidSupportInjection.inject(this);
//    }
//
//    private void configureViewModel(){
//        String profileUID = getArguments().getString(ARG_PROFILEUID);
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
//        viewModel.init(profileUID);
//        viewModel.getProfile().observe(this, mProfile -> updateUI(mProfile));
//    }
    // -----------------
    // UPDATE UI
    // -----------------

    private void updateUI(){
        if (mProfile != null){
            this.tvPhoneNumber.setText(mProfile.getProfilePhone());
            this.tvCommonName.setText(mProfile.getCommonName());
            this.tvFirstName.setText(mProfile.getFirstName());
            this.tvLastName.setText(mProfile.getLastName());
            this.tvDOB.setText(prettyDate(mProfile.getDoB()));
            this.tvUEN.setText(mProfile.getProfileUEN());
            this.tvEmail.setText(mProfile.getProfileEmail());
        }
    }
    private void updateModel(){
        {
//            Glide.with(this).load(user.getAvatar_url()).apply(RequestOptions.circleCropTransform()).into(imageView);
            mProfile.setProfilePhone(this.tvPhoneNumber.getText().toString());
            mProfile.setCommonName(this.tvCommonName.getText().toString());
            mProfile.setFirstName(this.tvFirstName.getText().toString());
            mProfile.setLastName(this.tvLastName.getText().toString());
            mProfile.setDoB(this.tvDOB.getText().toString());
            mProfile.setProfileUEN(this.tvUEN.getText().toString());
            mProfile.setProfileEmail(this.tvEmail.getText().toString());
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.i(TAG, "Prepare Menu" );
        menu.findItem(R.id.menu_profile_edit).setVisible(!mIsEditMode);
        menu.findItem(R.id.menu_profile_save).setVisible(mIsEditMode);

        btnUpdate.setVisibility(mIsEditMode ? View.VISIBLE : View.INVISIBLE);

        toggleTextFields();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.menu_profile_edit:
                Log.i(TAG, "Menu Item Edit Clicked" );
                mIsEditMode = true;
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.menu_profile_save:
                Log.i(TAG, "Menu Item Save Clicked" );
                mIsEditMode = false;
                getActivity().invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void toggleTextFields() {
        Log.i(TAG, "List TF: " + mTextFields.size());
        for (TextInputEditText tv:mTextFields) {
            tv.setEnabled(mIsEditMode);
        }


    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.btnUpdate:
                updateModel();
                if(mIsProfileExisted) {
                    updateProfile();
                } else {
                    createProfile();
                }
                break;
        }

    }
    private void updateProfile() {
        Log.i(TAG, "Updating Profile ");
        Call<Profile> profileUpdate = mProfileService.updateProfile(mProfileUID, mProfile);
        pgLoading.setVisibility(View.VISIBLE);
        profileUpdate.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int code = response.code();
                if(code == 200) {
                    mIsEditMode = false;
                    mIsProfileExisted = true;
                    getActivity().invalidateOptionsMenu();
                    Snackbar.make(getActivity().findViewById(R.id.ctnFragment), "Profile Updated", Snackbar.LENGTH_LONG).show();
                }
                pgLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, "FAILED: REQ -- " + t.getMessage());
                pgLoading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void createProfile() {
        Log.i(TAG, "Updating Profile ");
        mProfile.setProfileUID(mProfile.getProfilePhone());
        mProfile.setGender("M");
        Call<Profile> profileUpdate = mProfileService.createProfile(mProfile);
        pgLoading.setVisibility(View.VISIBLE);
        profileUpdate.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int code = response.code();
                if(code == 200) {
                    mIsEditMode = false;
                    mIsProfileExisted = true;
                    getActivity().invalidateOptionsMenu();
                    Snackbar.make(getActivity().findViewById(R.id.ctnFragment), "Profile created", Snackbar.LENGTH_LONG).show();
                }
                pgLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, "FAILED: REQ -- " + t.getMessage());
                pgLoading.setVisibility(View.INVISIBLE);
            }
        });
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

    private class GetProfileTask extends AsyncTask<String, Integer, Profile> {
        @Override
        protected void onPostExecute(Profile profile) {
            super.onPostExecute(profile);
            pgLoading.setVisibility(View.INVISIBLE);
            if(profile != null) {
                mProfile = profile;
                mIsProfileExisted = true;
            } else {
                Profile p = new Profile();
                p.setProfilePhone(mProfileUID);
                mProfile = p;
                Log.i(TAG, "First time login, please update profile ");
                Snackbar.make(getActivity().findViewById(R.id.ctnFragment),
                        "First time login, please update profile ",
                        Snackbar.LENGTH_LONG).show();
                mIsEditMode = true;
                mIsProfileExisted = false;
                getActivity().invalidateOptionsMenu();
            }
            updateUI();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Profile doInBackground(String... strings) {
            Call<Profile> profileGet = mProfileService.getProfile(strings[0]);
            Log.i(TAG, "Begin request");
            Response<Profile> response;
            try {
                response = profileGet.execute();
                if(response.code() == 200) {
                    return response.body();
                } else {
                    Log.e(TAG, response.message());
                    return null;
                }
            } catch (IOException ioExp){
                Log.e(TAG, ioExp.getMessage());
            }
            return null;
        }
    }
    private String prettyDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        if(strDate == null) {
            return "";
        }
        String outDateStr = strDate;
        try {
            Date date = format.parse(strDate);
            SimpleDateFormat outFormat = new SimpleDateFormat("dd-MM-yyyy");
            outDateStr = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDateStr;
    }
}

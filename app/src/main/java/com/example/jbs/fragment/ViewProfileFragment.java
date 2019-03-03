package com.example.jbs.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.jbs.CommonService;
import com.example.jbs.R;
import com.example.jbs.activity.MainActivity;
import com.example.jbs.controller.ProfileController;
import com.example.jbs.repo.DataRepo;
import com.example.jbs.room.InterestedSkill;
import com.example.jbs.room.Profile;
import com.example.jbs.service.ProfileWebService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
//import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfileFragment extends BaseFragment implements
        View.OnClickListener,
        CommonService.OnRequestPermissionListener
{
    public static final String TAG = ViewProfileFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFILEUID = "ProfileUID";
    private static final int REQ_CAMERA = 0;
    private static final int REQ_GALLERY = 1;

    private String mParam2;
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
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
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.btnChangeAvatar)
    Button btnChangeAvatar;
    @BindViews({R.id.tvCommonName, R.id.tvFirstName, R.id.tvLastName, R.id.tvEmail, R.id.tvUEN,
            R.id.tvAddress1, R.id.tvAddress2, R.id.tvDOB, R.id.tvBuildingName, R.id.tvPostalCode})
    List<TextInputEditText> mTextFields;
    //
    // STATE
    //
    private boolean mIsProfileExisted = false;
    private String mProfileUID;
    ProfileWebService mProfileService;
    private OnFragmentInteractionListener mListener;
    //
    private boolean mIsEditMode = false;
    public ViewProfileFragment() {
        // Required empty public constructor
    }
    private void getCurProfile() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.kJbsPref), Context.MODE_PRIVATE);
        mProfileUID = sharedPref.getString(getString(R.string.kJbsProfileUID), "");
        Log.i(TAG, "Get Profile No: " + mProfileUID);
    }
    public static ViewProfileFragment newInstance(String profileUID) {
        ViewProfileFragment fragment = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROFILEUID, profileUID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mProfile = new Profile();
        if (getArguments() != null) {
            mProfileUID = getArguments().getString(ARG_PROFILEUID);
        }
        if(mProfileUID.equals("")) {
            getCurProfile();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, TAG + "CreateView");
        View rootView = inflater.inflate(R.layout.fragment_view_profile, container, false);
        ButterKnife.bind(this, rootView);
        btnUpdate.setOnClickListener(this);
        btnChangeAvatar.setOnClickListener(this);
        // Toolbar
        useToolBar(toolBar, true);

        Log.i(TAG, "Curr Phone No: " + mProfileUID);
        CommonService.getInstance().initWebservice();
        mProfileService = ProfileController.getInstance().getProfileService();
        Profile p = DataRepo.getInstance().getProfile();
        if(p == null) {
            Log.i(TAG, "Fetching profile info : " + mProfileUID);
            new GetProfileTask().execute(mProfileUID);
        } else {
            updateUI(p);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);
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
                Log.i(TAG, "Back");
                ((MainActivity)getActivity()).onBackPressed();
                return true;
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
                    createProfile(DataRepo.getInstance().getProfile());
                }
                break;
            case R.id.btnChangeAvatar:
                CommonService.requestPermission(getActivity(), new String[] {Manifest.permission.CAMERA}
                        ,REQ_CAMERA,this);
                break;
        }

    }
    void takePhotoFromCamera() {
        if(!CommonService.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            CommonService.requestPermission(getActivity(), new String[] {Manifest.permission.CAMERA}
            ,REQ_CAMERA,this);
        } else {
            callCameraIntent();
        }
    }
    private void callCameraIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
    }
    void choseGaleryImage() {
        if(!CommonService.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            CommonService.requestPermission(getActivity(), new String[] {Manifest.permission.CAMERA}
                    ,REQ_CAMERA,this);
        } else {
            callGalleryIntent();
        }
    }
    private void callGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "Activity request returned: " + requestCode);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK) {

                }
                break;
            case 1:
                if(resultCode == RESULT_OK) {

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateProfile() {
        Log.i(TAG, "Updating Profile ");
        Profile profile = DataRepo.getInstance().getProfile();
        Call<Profile> profileUpdate = mProfileService.updateProfile(mProfileUID, profile);
        pgLoading.setVisibility(View.VISIBLE);
        profileUpdate.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int code = response.code();
                if (code == 200) {
                    mIsEditMode = false;
                    mIsProfileExisted = true;
                    getActivity().invalidateOptionsMenu();
                    Snackbar.make(appBarLayout, "Profile Updated", Snackbar.LENGTH_LONG).show();
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
    private void createProfile(Profile profile) {
        Log.i(TAG, "Updating Profile ");
        profile.setProfileUID(profile.getProfilePhone());
        profile.setGender("M");
        Call<Profile> profileUpdate = mProfileService.createProfile(profile);
        pgLoading.setVisibility(View.VISIBLE);
        profileUpdate.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int code = response.code();
                if(code == 200) {
                    mIsEditMode = false;
                    mIsProfileExisted = true;
                    getActivity().invalidateOptionsMenu();
                    Snackbar.make(appBarLayout, "Profile created", Snackbar.LENGTH_LONG).show();
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

    private void updateUI(Profile profile){
        if (profile != null){
            try {
                final String encodedURL = CommonService.SERVICE_BASE_URL + "profile/" +
                        URLEncoder.encode(profile.getProfileUID(), "UTF-8") + "/avatar";
                Glide.with(getActivity())
                        .load(encodedURL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.imgAvatar);
            } catch(UnsupportedEncodingException exp) {
                Log.e(TAG, exp.getMessage());
            }
            this.tvPhoneNumber.setText(profile.getProfilePhone());
            this.tvCommonName.setText(profile.getCommonName());
            this.tvFirstName.setText(profile.getFirstName());
            this.tvLastName.setText(profile.getLastName());
            this.tvDOB.setText(prettyDate(profile.getDoB()));
            this.tvUEN.setText(profile.getProfileUEN());
            this.tvEmail.setText(profile.getProfileEmail());
        }
    }
    private void updateModel(){
        Profile profile = DataRepo.getInstance().getProfile();
        profile.setProfilePhone(this.tvPhoneNumber.getText().toString());
        profile.setCommonName(this.tvCommonName.getText().toString());
        profile.setFirstName(this.tvFirstName.getText().toString());
        profile.setLastName(this.tvLastName.getText().toString());
        profile.setDoB(this.tvDOB.getText().toString());
        profile.setProfileUEN(this.tvUEN.getText().toString());
        profile.setProfileEmail(this.tvEmail.getText().toString());
        DataRepo.getInstance().setProfile(profile);
    }

    @Override
    public void onPermissionRequested(int REQ_CODE) {
        switch (REQ_CODE) {
            case REQ_CAMERA:
                callCameraIntent();
                break;
            case REQ_GALLERY:
                callGalleryIntent();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and namesave
        void onFragmentInteraction(Uri uri);
    }

    private class GetProfileTask extends AsyncTask<String, Integer, Profile> {
        @Override
        protected void onPostExecute(Profile profile) {
            super.onPostExecute(profile);
            pgLoading.setVisibility(View.INVISIBLE);
            if(profile != null) {
                DataRepo.getInstance().setProfile(profile);
                mIsProfileExisted = true;
                Snackbar.make(appBarLayout,
                        "Logged in as " + mProfileUID ,
                        Snackbar.LENGTH_LONG).show();
                updateUI(profile);
            } else {
                Profile p = new Profile();
                p.setProfilePhone(mProfileUID);
//                mProfile = p;
                Log.i(TAG, "First time login, please update profile ");
                Snackbar.make(appBarLayout,
                        "First time login, please update profile ",
                        Snackbar.LENGTH_LONG).show();
                mIsEditMode = true;
                mIsProfileExisted = false;
                getActivity().invalidateOptionsMenu();
            }
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgLoading.setVisibility(View.VISIBLE);
        }
    }
    private String prettyDate(String strDate) {
        if(strDate == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
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

package com.example.jbs.fragment;

import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.jbs.CommonService;
import com.example.jbs.R;
import com.example.jbs.activity.MainActivity;
import com.example.jbs.controller.GroupController;
import com.example.jbs.repo.DataRepo;
import com.example.jbs.room.Group;
import com.example.jbs.room.Profile;
import com.example.jbs.service.GroupWebService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewGroupFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = ViewGroupFragment.class.getSimpleName();
    private static final String ARG_GROUPID = "GroupUID";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean mIsCreateNew = true;
    private String mGroupUID;
    private String mParam2;
    private boolean mIsEditMode;
    private GroupWebService mGroupService;
    private Group mGroup;
    @BindView(R.id.pgLoading)
    ProgressBar pgLoading;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.tvGroupName)
    TextView tvGroupName;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvBuildingName)
    TextView tvBuildingName;
    @BindView(R.id.tvAddress1)
    TextView tvAddress1;
    @BindView(R.id.tvAddress2)
    TextView tvAddress2;
    @BindView(R.id.tvPostalCode)
    TextView tvPostalCode;
    @BindView(R.id.btnSaveChange)
    Button btnSaveChange;

    private OnFragmentInteractionListener mListener;

    public ViewGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param groupUID Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewGroupFragment newInstance(String groupUID, String param2) {
        ViewGroupFragment fragment = new ViewGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUPID, groupUID);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupUID = getArguments().getString(ARG_GROUPID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_group, container, false);
        ButterKnife.bind(this, rootView);
        // Set Toolbar
        useToolBar(toolBar, true);
        init();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.group_detail_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.i(TAG, "Prepare Menu" );
        menu.findItem(R.id.menu_group_save).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                ((MainActivity)getActivity()).onBackPressed();
                return true;
            case R.id.menu_group_save:
                Log.i(TAG, "Add new group");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void init() {
        mGroupService = GroupController.getInstance().getmGroupService();
        if(mGroupUID != null && !mGroupUID.isEmpty()) {
            mIsCreateNew = false;
            new PrepareTask().execute(mGroupUID);
        } else {
            mIsCreateNew = true;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @OnClick(R.id.btnSaveChange)
    void onSaveChangeClick() {
        if(mIsCreateNew) {
            createNew();
        } else {
            saveChange();
        }
    }

    void saveChange() {
        updateModel();
        Log.i(TAG, "Save Change Group: " + mGroupUID + " new Name: " + mGroup.getGroupUID());
        Call<Group> updateGroup = mGroupService.updateGroup(mGroupUID, mGroup);
        updateGroup.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                Toast.makeText(getActivity(), "Update group success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Toast.makeText(getActivity(), "Update group failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    void createNew() {
        Log.i(TAG, "Create New Group");
        updateModel();
        String profileUID = DataRepo.getInstance().getProfile().getProfileUID();
        Call<Group> createGroup = mGroupService.createGroup(profileUID, mGroup);
        createGroup.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                Toast.makeText(getActivity(), "Create group success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Toast.makeText(getActivity(), "Create group failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    @OnClick(R.id.btnChangeAvatar)
    void onChangeAvatarClick() {

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void updateModel(){
        if(mGroup == null) {
            mGroup = new Group();
        }
        mGroup.setGroupPhone(this.tvPhoneNumber.getText().toString());
        mGroup.setGroupName(this.tvGroupName.getText().toString());
        mGroup.setGroupEmail(this.tvEmail.getText().toString());
        mGroup.setBuildingName(this.tvBuildingName.getText().toString());
        mGroup.setAddress1(this.tvAddress1.getText().toString());
        mGroup.setAddress2(this.tvAddress2.getText().toString());
        mGroup.setPostalCode(this.tvPostalCode.getText().toString());
    }
    private void updateUI(Group group) {
        if (group == null)
            return;
        if(group.getGroupLogoURL() != null && !group.getGroupLogoURL().isEmpty()) {
            try {
                final String encodedURL = CommonService.SERVICE_BASE_URL + "group/" +
                        URLEncoder.encode(group.getGroupLogoURL(), "UTF-8") + "/avatar";
                Glide.with(getActivity())
                        .load(encodedURL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.imgAvatar);
            } catch (UnsupportedEncodingException exp) {
                Log.e(TAG, exp.getMessage());
            }
        }
        this.tvPhoneNumber.setText(group.getGroupPhone());
        this.tvGroupName.setText(group.getGroupName());
        this.tvEmail.setText(group.getGroupEmail());
        this.tvBuildingName.setText(group.getBuildingName());
        this.tvAddress1.setText(group.getAddress1());
        this.tvAddress2.setText(group.getAddress2());
        this.tvPostalCode.setText(group.getPostalCode());
    }
    private class PrepareTask extends AsyncTask<String, Integer, Group> {
        @Override
        protected void onPostExecute(Group group) {
            super.onPostExecute(group);
            pgLoading.setVisibility(View.INVISIBLE);
            if(group != null) {
                mGroup = group;
                updateUI(mGroup);
            } else {
                Log.i(TAG, "First time login, please update profile ");
                mIsEditMode = true;
                getActivity().invalidateOptionsMenu();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Group doInBackground(String... strings) {
            Call<Group> getGroup = mGroupService.getGroup(strings[0]);
            Log.i(TAG, "Begin request");
            Response<Group> response;
            try {
                response = getGroup.execute();
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
}

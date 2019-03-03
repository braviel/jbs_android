package com.example.jbs.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.jbs.CommonService;
import com.example.jbs.R;
import com.example.jbs.activity.MainActivity;
import com.example.jbs.controller.GroupController;
import com.example.jbs.controller.ProfileController;
import com.example.jbs.room.GroupMember;
import com.example.jbs.room.Profile;
import com.example.jbs.service.GroupWebService;
import com.example.jbs.service.ProfileWebService;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InviteToGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InviteToGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteToGroupFragment extends BaseFragment
implements CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = InviteToGroupFragment.class.getSimpleName();
    private static final String ARG_GROUP_UID = "groupuid";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mGroupUID;
    private String mParam2;
    private Profile mProfile;
    private ProfileWebService mprofileService;
    private GroupWebService mGroupService;
    private boolean isInviteAsAdmin;
    @BindView(R.id.lbPlaceHolder)
    TextView lbPlaceHolder;
    @BindView(R.id.teMemberUID)
    TextInputEditText teMemberUID;
    @BindView(R.id.ctnProfileInfo)
    MaterialCardView ctnProfile;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.swAdmin)
    SwitchCompat swAdmin;
    @BindView(R.id.btnInvite)
    Button btnInvite;
    //
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.lbCommonName)
    TextView lbCommonName;
    @BindView(R.id.lbFirstName)
    TextView lbFirstName;
    @BindView(R.id.lbLastName)
    TextView lbLastName;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    private OnFragmentInteractionListener mListener;

    public InviteToGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InviteToGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InviteToGroupFragment newInstance(String param1, String param2) {
        InviteToGroupFragment fragment = new InviteToGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_UID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupUID = getArguments().getString(ARG_GROUP_UID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_invite_to_group, container, false);
        mprofileService = ProfileController.getInstance().getProfileService();
        mGroupService = GroupController.getInstance().getmGroupService();
        ButterKnife.bind(this, rootView);
        useToolBar(toolbar, true);
        swAdmin.setOnCheckedChangeListener(this);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    void updateUI (Profile profile) {
        if(profile != null) {
            lbPlaceHolder.setVisibility(View.INVISIBLE);
            ctnProfile.setVisibility(View.VISIBLE);
            lbCommonName.setText(profile.getCommonName());
            lbFirstName.setText(profile.getFirstName());
            lbLastName.setText(profile.getLastName());
            try {
                final String encodedURL = CommonService.SERVICE_BASE_URL + "profile/" +
                        URLEncoder.encode(profile.getProfileUID(), "UTF-8") + "/avatar";
                CommonService.getInstance().displayImage(getActivity(), imgAvatar, encodedURL);
//                Glide.with(getActivity())
//                        .load(encodedURL)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(this.imgAvatar);
            } catch(UnsupportedEncodingException exp) {
                Log.e(TAG, exp.getMessage());
            }
        }
    }
    @OnClick(R.id.btnSearch)
    void onSearch() {
        String memberUID = teMemberUID.getText().toString();
        Call<Profile> getProfile = mprofileService.getProfile(memberUID);
        getProfile.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                mProfile = response.body();
                if(mProfile != null) {
                    updateUI(mProfile);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btnInvite)
    void onInvite() {
//        Toast.makeText(getActivity(), "Invite member", Toast.LENGTH_LONG).show();
        String memberUID = teMemberUID.getText().toString();
        Call<GroupMember> inviteMember = mGroupService.inviteMember(mGroupUID,memberUID,
                isInviteAsAdmin?"Y":"N");
        inviteMember.enqueue(new Callback<GroupMember>() {
            @Override
            public void onResponse(Call<GroupMember> call, Response<GroupMember> response) {
                int code = response.code();
                String msg = response.message();
                if(code == 200) {
                    Toast.makeText(getActivity(),"Member Invited", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GroupMember> call, Throwable t) {
                Toast.makeText(getActivity(), "InviteFailed" +t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.i(TAG, "Swicth Changed: " + isChecked);
        isInviteAsAdmin = isChecked;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.getItemId() == android.R.id.home ) {
            ((MainActivity)getActivity()).onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

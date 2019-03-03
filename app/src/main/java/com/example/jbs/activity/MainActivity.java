package com.example.jbs.activity;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jbs.MyFragmentActivity;
import com.example.jbs.R;
import com.example.jbs.fragment.GroupFragment;
import com.example.jbs.fragment.InviteToGroupFragment;
import com.example.jbs.fragment.ListGroupFragment;
import com.example.jbs.fragment.MyGroupRecyclerViewAdapter;
import com.example.jbs.fragment.ProfileMenuFragment;
import com.example.jbs.fragment.ViewEventFragment;
import com.example.jbs.fragment.ViewGroupFragment;
import com.example.jbs.fragment.ViewProfileFragment;
import com.example.jbs.fragment.dummy.DummyContent;
import com.example.jbs.room.Group;
import com.example.jbs.room.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//import javax.inject.Inject;

public class MainActivity extends MyFragmentActivity implements
        ViewProfileFragment.OnFragmentInteractionListener,
        ViewEventFragment.OnFragmentInteractionListener,
        GroupFragment.OnFragmentInteractionListener,
        ProfileMenuFragment.OnFragmentInteractionListener,
        ListGroupFragment.OnListFragmentInteractionListener,
        ViewGroupFragment.OnFragmentInteractionListener,
        InviteToGroupFragment.OnFragmentInteractionListener
{
    public static String TAG = MainActivity.class.getSimpleName();
//    private static String PROFILE_UID = "+6585536798";
    @BindView(R.id.bot_navigation)
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences pref = getSharedPreferences(getString(R.string.kJbsPref), MODE_PRIVATE);
        String profileUID = pref.getString( getString(R.string.kJbsProfileUID), "");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_work:
                        GroupFragment groupFragment = GroupFragment.newInstance(profileUID,"");
                        replaceFragment(groupFragment, R.id.ctnFragment ,GroupFragment.TAG);
                        return true;
                    case R.id.action_event:
                        ViewEventFragment viewEventFragment = ViewEventFragment.newInstance(profileUID,"");
                        replaceFragment(viewEventFragment, R.id.ctnFragment ,ViewEventFragment.TAG);
                        return true;
                    case R.id.action_chat:
                        return true;
                    case R.id.action_profile:
                        Log.i(TAG, "Navigate to ProfileMenu");
                        if(profileUID == null || profileUID.isEmpty()) {
                            ViewProfileFragment viewProfileFragment = ViewProfileFragment.newInstance("");
                            replaceFragment(viewProfileFragment, R.id.ctnFragment ,ViewProfileFragment.TAG);
                        } else {
                            ProfileMenuFragment profileMenuFragment = ProfileMenuFragment.newInstance("", "");
                            replaceFragment(profileMenuFragment, R.id.ctnFragment, ProfileMenuFragment.TAG);
                        }
                        return true;
                    case R.id.action_search:
                        return true;
                }
                return false;
            }
        });

        if(profileUID == null || profileUID.isEmpty()) {
            ViewProfileFragment viewProfileFragment = ViewProfileFragment.newInstance("");
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(ViewProfileFragment.TAG)
                    .replace(R.id.ctnFragment, viewProfileFragment)
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.action_profile);
        } else {
            ViewEventFragment viewEventFragment = ViewEventFragment.newInstance("","");
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(viewEventFragment.TAG)
                    .replace(R.id.ctnFragment, viewEventFragment)
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.action_event);
        }
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(int action, Group item) {
        switch (action) {
            case 1:
                ViewGroupFragment viewGroupFragment = ViewGroupFragment.newInstance(item.getGroupUID(), "");
                replaceFragment(viewGroupFragment, R.id.ctnFragment, ViewGroupFragment.TAG);
            break;
            case 2:
                Toast.makeText(this, "TODO: Invite User to group!", Toast.LENGTH_LONG).show();
                break;
        }
    }
//    private void configureDagger(){
//        AndroidInjection.inject(this);
//    }
}

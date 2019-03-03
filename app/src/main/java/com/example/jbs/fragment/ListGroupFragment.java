package com.example.jbs.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jbs.R;
import com.example.jbs.activity.MainActivity;
import com.example.jbs.controller.GroupController;
import com.example.jbs.controller.ProfileController;
import com.example.jbs.fragment.dummy.DummyContent;
import com.example.jbs.fragment.dummy.DummyContent.DummyItem;
import com.example.jbs.repo.DataRepo;
import com.example.jbs.room.Group;
import com.example.jbs.room.Profile;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListGroupFragment extends BaseFragment implements
        MyGroupRecyclerViewAdapter.OnGroupListInteraction
{
    public static final String TAG = ListGroupFragment.class.getSimpleName();
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private String mProfileUID;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListGroupFragment() {
    }

    // TODO: Customize parameter initialization
    public static ListGroupFragment newInstance(int columnCount) {
        ListGroupFragment fragment = new ListGroupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_list, container, false);
        ButterKnife.bind(this, rootView);
        // Set Toolbar
        useToolBar(toolBar, true);
        //
        mProfileUID = DataRepo.getInstance().getProfile().getProfileUID();
        // Set the adapter
        if (recyclerView != null) {
            Context context = recyclerView.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            new PrepareTask().execute(mProfileUID);
        }
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.group_list_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.i(TAG, "Prepare Menu" );
        menu.findItem(R.id.menu_group_add).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                ((MainActivity)getActivity()).onBackPressed();
                break;
            case R.id.menu_group_add:
                ViewGroupFragment viewGroupFragment = ViewGroupFragment.newInstance("","");
                ((MainActivity)getActivity()).replaceFragment(viewGroupFragment,
                        R.id.ctnFragment, ViewGroupFragment.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupItemClick(Group group) {
        InviteToGroupFragment inviteToGroupFragment = InviteToGroupFragment.newInstance(group.getGroupUID(),"");
        ((MainActivity)getActivity()).replaceFragment(inviteToGroupFragment, R.id.ctnFragment, InviteToGroupFragment.TAG);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int action, Group item);
    }
    private void initGroupView(List<Group> groups) {
        DataRepo.getInstance().setGroups(groups);
        recyclerView.setAdapter(new MyGroupRecyclerViewAdapter(groups, mListener, this));
    }
    private class PrepareTask extends AsyncTask<String, Integer, List<Group>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pgLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Group> doInBackground(String... strings) {
            List<Group> g = GroupController.getInstance().getGroupsByMemberId(strings[0]);
            return g;
        }

        @Override
        protected void onPostExecute(List<Group> groups) {
            super.onPostExecute(groups);
            if(groups != null) {
                initGroupView(groups);
//                UpdateUI(profile);
            }
//            pgLoading.setVisibility(View.INVISIBLE);
        }
    }
}

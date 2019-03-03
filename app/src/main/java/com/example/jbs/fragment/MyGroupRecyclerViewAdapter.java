package com.example.jbs.fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbs.activity.MainActivity;
import com.example.jbs.fragment.ListGroupFragment.OnListFragmentInteractionListener;
import com.example.jbs.fragment.dummy.DummyContent.DummyItem;

import java.util.List;
import com.example.jbs.R;
import com.example.jbs.room.Group;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGroupRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = MyGroupRecyclerViewAdapter.class.getSimpleName();
    private final List<Group> mGroups;
    private final OnListFragmentInteractionListener mListener;
    private final OnGroupListInteraction mListListener;

    public MyGroupRecyclerViewAdapter(List<Group> items,
                                      OnListFragmentInteractionListener listener,
                                      OnGroupListInteraction listListener
    ) {
        mGroups = items;
        mListener = listener;
        mListListener = listListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mGroups.get(position);
        holder.lbItemName.setText(mGroups.get(position).getGroupName());
        holder.lbItemDesc.setText(mGroups.get(position).getGroupPhone());
        holder.lbItemBrief.setText(mGroups.get(position).getGroupEmail());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Log.i(TAG, "Click Group ID: " + holder.mItem.getGroupUID());
                    mListener.onListFragmentInteraction(1, holder.mItem);
                }
            }
        });
        holder.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListListener) {
                    Log.i(TAG, "Click Group ID: " + holder.mItem.getGroupUID());
//                    mListener.onListFragmentInteraction(2, holder.mItem);
                    mListListener.onGroupItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView imgLogo;
        public final TextView lbItemName;
        public final TextView lbItemDesc;
        public final TextView lbItemBrief;
        public final Button btnInvite;
        public Group mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imgLogo = view.findViewById(R.id.imgAvatar);
            lbItemName = (TextView) view.findViewById(R.id.lbItemName);
            lbItemDesc = (TextView) view.findViewById(R.id.lbItemDesc);
            lbItemBrief = (TextView) view.findViewById(R.id.lbItemBrief);
            btnInvite = (Button) view.findViewById(R.id.btnInvite);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lbItemDesc.getText() + "'";
        }
    }
    public interface OnGroupListInteraction {
        void onGroupItemClick(Group group);
    }
}

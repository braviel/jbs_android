package com.example.jbs.controller;

import android.util.Log;

import com.example.jbs.CommonService;
import com.example.jbs.room.Group;
import com.example.jbs.room.Profile;
import com.example.jbs.service.GroupWebService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GroupController {
    public static String TAG = GroupController.class.getSimpleName();

    public GroupWebService getmGroupService() {
        return mGroupService;
    }

    GroupWebService mGroupService;
    private static final GroupController ourInstance = new GroupController();
    private GroupController() {
        Retrofit retrofit = CommonService.getInstance().initWebservice();
        mGroupService = retrofit.create(GroupWebService.class);
    }
    public static GroupController getInstance() {
        return ourInstance;
    }
    public Group getGroup(String groupUID) {
        Call<Group> getProfile = mGroupService.getGroup(groupUID);
        try {
            Response<Group> resp = getProfile.execute();
            int code = resp.code();
            if(code == 200) {
                return resp.body();
            } else {
                return null;
            }
        } catch (IOException ioExc) {
            Log.e(TAG, ioExc.getMessage());
        }
        return null;
    }
    public List<Group> getGroupsByMemberId(String profileUID) {
        Call<List<Group>> getProfile = mGroupService.getGroupsByMemberId(profileUID);
        try {
            Response<List<Group>> resp = getProfile.execute();
            int code = resp.code();
            if(code == 200) {
                return resp.body();
            } else {
                return null;
            }
        } catch (IOException ioExc) {
            Log.e(TAG, ioExc.getMessage());
        }
        return null;
    }
}


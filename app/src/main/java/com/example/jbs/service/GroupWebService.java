package com.example.jbs.service;

import com.example.jbs.room.Group;
import com.example.jbs.room.GroupMember;
import com.example.jbs.room.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GroupWebService {
    @GET("/group")
    Call<List<Group>> getGroups();

    @GET("/group/{id}")
    Call<Group> getGroup(@Path("id") String groupUID);

    @POST("/group/{id}")
    Call<Group> createGroup(@Path("id") String creatorUID, @Body Group group);

    @PUT("/group/{id}")
    Call<Group> updateGroup(@Path("id") String groupUID, @Body Group group);

    @GET("/group/listByMemberId/{profileUID}")
    Call<List<Group>> getGroupsByMemberId(@Path("profileUID") String profileUID);

    @POST("/group/{groupUID}/invite/{profileUID}/isAdmin/{isAdmin}")
    Call<GroupMember> inviteMember(@Path("groupUID") String groupUID,
                                   @Path("profileUID") String profileUID,
                                   @Path("isAdmin") String isAdmin);
}

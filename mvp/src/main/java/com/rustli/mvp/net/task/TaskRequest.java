package com.rustli.mvp.net.task;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TaskRequest {


    @FormUrlEncoded
    @POST("/api/data")
    Flowable<TaskResponse> addTask(
            @Field("taskId") String taskId,
            @Field("taskName") String taskName,
            @Field("taskType") int taskType);


    @FormUrlEncoded
    @POST("/api/data")
    Flowable<TaskResponse> deleteTask(
            @Field("taskId") String taskId,
            @Field("taskName") String taskName,
            @Field("taskType") int taskType);
}

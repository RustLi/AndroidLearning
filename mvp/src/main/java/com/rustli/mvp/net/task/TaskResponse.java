package com.rustli.mvp.net.task;

public class TaskResponse extends ITaskResponse {

    public DataBean data;

    public static class DataBean {
        public int type;
        public String name;
        public String des;
    }
}

package com.rustli.jetpack.mvvm;

public class DataModel {
    private String id;
    private OnCallbackListener mOnCallbackListener;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataModel(OnCallbackListener onCallbackListener) {
        this.mOnCallbackListener = onCallbackListener;
    }

    public void loadModel(String id){
        setId(id);
        if (mOnCallbackListener != null) {
            mOnCallbackListener.onSuccess(this);
        }
    }

    interface OnCallbackListener{
        void onSuccess(DataModel dataModel);
    }
}

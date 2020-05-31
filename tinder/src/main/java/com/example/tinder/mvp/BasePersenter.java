package com.example.tinder.mvp;

public class BasePersenter<V extends BaseView> {
    private V mBaseView;

    public void bindView(V view){
        if(view!= null){
            this.mBaseView = view;
        }
    }

    public void unBindView(){
        this.mBaseView = null;
    }

    public V getmBaseView(){
        return mBaseView;
    }
}

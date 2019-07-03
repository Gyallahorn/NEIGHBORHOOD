package com.esphereal.bair.funloot.retrofit;
import com.esphereal.bair.funloot.LoginActivity;


public class FunlootCallBack {
    public interface Callback{
        void callinBack();
    }
    Callback callback;
    public void registerCallBack(Callback callback){
        this.callback=callback;
    }
    void doSomething(){
        callback.callinBack();
    }
}

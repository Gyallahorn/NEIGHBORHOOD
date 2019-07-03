package com.esphereal.bair.funloot.profile;

import android.util.Log;

import com.esphereal.bair.funloot.retrofit.RetrofitSingleton;
import com.esphereal.bair.funloot.retrofit.FunlootUser;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSingletone {
    private static final String TAG = "ProfileSingletone";


    //singletone
    private static volatile ProfileSingletone instance;

    public static ProfileSingletone getInstance() {
        ProfileSingletone localInstance = instance;
        if (localInstance == null) {
            synchronized (ProfileSingletone.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ProfileSingletone();
                }
            }
        }
        return localInstance;
    }

    private FunlootUser user;

    public void SetUser(FunlootUser user) {
        this.user = user;
    }

    public void GetUser(final ProfileCallBack callback) {
        if (user == null) {
            RetrofitSingleton.Companion.getInstance().GetUser(new Callback<FunlootUser>() {
                @Override
                public void onResponse(Call<FunlootUser> call, Response<FunlootUser> response) {
                    if (response.isSuccessful()) {

                        user = response.body();
                        if (callback != null)
                            callback.onBackData(user);
                    } else {
                        Log.d(TAG, "Response Error " + response.message());
                    }

                }

                @Override
                public void onFailure(Call<FunlootUser> call, Throwable t) {

                }
            });


        } else if (callback != null)
            callback.onBackData(user);

    }


}

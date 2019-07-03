package com.esphereal.bair.neighborhood.profile;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esphereal.bair.funloot.R;
import com.esphereal.bair.neighborhood.retrofit.FunlootUser;
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton;
import com.esphereal.bair.neighborhood.retrofit.FunlootUser;
import com.esphereal.bair.neighborhood.retrofit.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private ProgressBar mProgressBar;
    private ConstraintLayout mProfileContent;

    private EditText mProfileNameEdit;
    private RadioGroup mProfileGenderRadio;
    private Spinner mProfileAgeSpinner;
    private Spinner mProfileRegionSpinner;
    private Spinner mProfileRolesSpinner;
    private Spinner mProfileSportTypesSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProgressBar = findViewById(R.id.profile_progressbar);
        mProfileContent = findViewById(R.id.profile_content);
        mProfileAgeSpinner = findViewById(R.id.profile_age_spinner);
        mProfileNameEdit = findViewById(R.id.profile_name_edit);
        mProfileGenderRadio = findViewById(R.id.profile_gender_radio_group);
        mProfileRegionSpinner = findViewById(R.id.profile_region_spinner);
        mProfileRolesSpinner = findViewById(R.id.profile_roles_spinner);
        mProfileSportTypesSpinner = findViewById(R.id.profile_sport_types_spinner);

        findViewById(R.id.back_button_top_panel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNewUserData();
            }
        });

        ProfileSingletone.getInstance().GetUser(new ProfileCallBack() {
            @Override
            public void onBackData(FunlootUser user) {
                parseResponse(user);
            }
        });
    }

    private void sendNewUserData() {
        FunlootUser user = createUserBody();
        RetrofitSingleton.Companion.getInstance().PostUser(new Callback<FunlootUser>() {
            @Override
            public void onResponse(Call<FunlootUser> call, Response<FunlootUser> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, " get successful response  when send new user data");
                    ProfileSingletone.getInstance().SetUser(response.body());
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Данные сохранены", Toast.LENGTH_SHORT);
                    toast.show();
                    onBackPressed();
                } else {
                    Log.d(TAG, " get fail response  when send new user data");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Не удалось сохранить данные", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<FunlootUser> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка соединения", Toast.LENGTH_LONG);
                toast.show();
            }
        }, user);
    }

    private FunlootUser createUserBody() {
        FunlootUser body = new FunlootUser();
        body.setFullName(mProfileNameEdit.getText().toString());
        int checkedId = mProfileGenderRadio.getCheckedRadioButtonId();

        if (checkedId == R.id.profile_male_radio_button) {
            body.setGender(true);
        } else
            body.setGender(false);

        body.setAge(mProfileAgeSpinner.getSelectedItem().toString());
        body.setRegion(mProfileRegionSpinner.getSelectedItem().toString());
        body.setUserType(mProfileRolesSpinner.getSelectedItem().toString());
        body.setSportType(mProfileSportTypesSpinner.getSelectedItem().toString());
        return body;
    }

    private void parseResponse(FunlootUser user) {
        Log.d(TAG, "start parsing");
        try {
            FunlootUser body = user;
            String userName = body.getDisplayName();
            ((TextView) findViewById(R.id.user_name_text)).setText(userName);
            mProfileGenderRadio.clearCheck();
            if (body.getGender() != null) {
                int gender = body.getGender() ? R.id.profile_male_radio_button : R.id.profile_female_radio_button;

                mProfileGenderRadio.check(gender);
            } else {
                mProfileGenderRadio.check(R.id.profile_male_radio_button);
            }

            mProfileNameEdit.setText(body.getFullName());
            mProfileAgeSpinner.setSelection(((ArrayAdapter<String>) mProfileAgeSpinner.getAdapter()).getPosition(body.getAge()));
            mProfileRegionSpinner.setSelection(((ArrayAdapter<String>) mProfileRegionSpinner.getAdapter()).getPosition(body.getRegion()));
            mProfileRolesSpinner.setSelection(((ArrayAdapter<String>) mProfileRolesSpinner.getAdapter()).getPosition(body.getUserType()));
            mProfileSportTypesSpinner.setSelection(((ArrayAdapter<String>) mProfileSportTypesSpinner.getAdapter()).getPosition(body.getSportType()));


        } catch (Exception e) {
            Log.d("TAG", "json parisng error " + e.getMessage());
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ошибка", Toast.LENGTH_LONG);
            toast.show();
        }
        mProgressBar.setVisibility(View.GONE);
        mProfileContent.setVisibility(View.VISIBLE);
    }
}

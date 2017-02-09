package app.com.neil.youarethevoter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by swapnilparashar on 11/20/2016.
 */

public class SettingsMenu extends AppCompatActivity {
    int userId,locationId;
    String userName,userEmail,userPass;
    Long userDOB;
    ArrayList<Integer> interestGroupIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbarsettings, null);

        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(mCustomView, lp1);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f6b221")));
        Intent i = getIntent();
        userId = i.getIntExtra("USERID",0);
        userName = i.getStringExtra("USERNAME");
        userDOB = i.getLongExtra("USERDOB",0);
        userPass = i.getStringExtra("USERPASS");
        userEmail = i.getStringExtra("USEREMAIL");
        locationId = i.getIntExtra("USERLOCATION",0);
        interestGroupIds = i.getIntegerArrayListExtra("USERINTERESTGROUPID");
        Log.d("Usename","username"+userName);

    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.wrongIcon) {
            finish();
        }
        if (v.getId() == R.id.tvAccountSettings) {
            Intent i = new Intent(SettingsMenu.this,AccountSettings.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("USERID",userId);
            i.putExtra("USERNAME",userName);
            i.putExtra("USERDOB",userDOB);
            i.putExtra("USERPASS",userPass);
            i.putExtra("USEREMAIL",userEmail);
            i.putExtra("USERLOCATION",locationId);
            startActivity(i);
            finish();
        }
        if (v.getId() == R.id.tvUpdateInterestGroup) {
            Intent i = new Intent(SettingsMenu.this,UpdateInterestGroupSettings.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("USERID",userId);
            i.putExtra("USEREMAIL",userEmail);
            i.putExtra("USERLOCATION",locationId);
            i.putIntegerArrayListExtra("USERINTERESTGROUPID",interestGroupIds);
            startActivity(i);
            finish();

        }
        if (v.getId() == R.id.tvTermsConditions) {
            Toast.makeText(SettingsMenu.this,"Coming Soon",Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.tvLogout) {
            Intent i = new Intent(SettingsMenu.this,Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }
}

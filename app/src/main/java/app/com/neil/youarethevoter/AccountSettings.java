package app.com.neil.youarethevoter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.util.DateTime;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.MyApi;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static android.R.attr.offset;
import static com.google.api.client.util.DateTime.parseRfc3339;

/**
 * Created by swapnilparashar on 11/20/2016.
 */

public class AccountSettings extends AppCompatActivity {
    EditText etName, etDOB;
    int userId,locationId;
    Long userDOB;
    String userName,userEmail,userPass;
    private Spinner mySpinner;
    String sName = "";
    Date sDOB = null;
    Integer selectedLocationId;
    Button bEditDetail,bUpdateDetail;
    Boolean bRegisterEnabled = true;

    private static final String DATE_PATTERN = "^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
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
        userPass = i.getStringExtra("USERPASS");
        userDOB = i.getLongExtra("USERDOB",0);
        userEmail = i.getStringExtra("USEREMAIL");
        locationId = i.getIntExtra("USERLOCATION",0);
        bEditDetail = (Button) findViewById(R.id.editASButton);
        bUpdateDetail = (Button) findViewById(R.id.updateASButton);
        etName = (EditText) findViewById(R.id.etASName);
        etDOB = (EditText) findViewById(R.id.etASDOB);
        mySpinner = (Spinner) findViewById(R.id.spinnerASLocation);
        etName.setEnabled(false);
        etDOB.setEnabled(false);
        mySpinner.setEnabled(false);
        mySpinner.setClickable(false);
        Log.d("Usename","username"+userName);
        if(userName!=null)
            etName.setText(userName);
        if(userDOB!=null) {
            Date date = new Date(userDOB);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String userDOBString = dateFormat.format(date);
            etDOB.setText(userDOBString);
        }

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country_label, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //mySpinner.setAdapter(adapter);
        if (locationId>=0) {
            mySpinner.setSelection(locationId-1);
        }

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                // Here you get the current item (a User object) that is selected by its position
                String selectedVal = getResources().getStringArray(R.array.country_arrays)[position];
                selectedLocationId = Integer.parseInt(getResources().getStringArray(R.array.country_label)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.wrongIcon) {
            finish();
        }
        if (v.getId() == R.id.editASButton) {
            etName.setEnabled(true);
            etDOB.setEnabled(true);
            mySpinner.setEnabled(true);
            mySpinner.setClickable(true);
            bEditDetail.setVisibility(View.INVISIBLE);
            bUpdateDetail.setVisibility(View.VISIBLE);
        }
        if (v.getId() == R.id.updateASButton) {
            sName = "" + etName.getText();


            if (!etDOB.getText().toString().matches(DATE_PATTERN)) {
                Toast.makeText(getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG).show();
            } else {

                try {
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String sDOBString = dateFormat1.format(dateFormat.parse(etDOB.getText().toString()));
                    sDOB = dateFormat1.parse(sDOBString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (bRegisterEnabled) {
                    User userToRegister = new User();
                    userToRegister.setUserName(sName);
                    userToRegister.setUserEmail(userEmail);
                    userToRegister.setUserPassword(userPass);
                    Log.d("useremail","useremail"+userEmail);
                    //userToRegister.setUserDOB(new DateTime(sDOB, TimeZone.getTimeZone("Europe/Dublin")));
                    Log.d("sDOB", sDOB.toString());
                    DateTime dateTime = new DateTime(sDOB);
                    int tzShift = dateTime.getTimeZoneShift();
                    sDOB = new Date(sDOB.getTime()+ TimeUnit.MINUTES.toMillis(tzShift));
                    Log.d("sDOB after shift", sDOB.toString());
                    Log.d("datetime",dateTime.toString());
                    Log.d("tzShift",""+tzShift);
                    userToRegister.setUserDOB(new DateTime(sDOB));
                    Log.d("dob","dob"+userToRegister.getUserDOB());
                    Log.d("timezone","timezone"+TimeZone.getDefault().toString());
                    userToRegister.setLocationID(selectedLocationId);
                    bRegisterEnabled = false;
                    new AccountSettings.EndpointsAsyncTask().execute(new Pair<Context, User>(AccountSettings.this, userToRegister));
                } else {
                    Toast.makeText(getApplicationContext(), "Please wait - Registeration in Progress", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, User>, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, User>... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://youarethevoter-1384.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            context = params[0].first;
            User userForRegistration = params[0].second;
            try {
                return myApiService.updateUser(userForRegistration).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contains("PASS")) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                bRegisterEnabled = true;
                Intent i = new Intent(AccountSettings.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
        }
    }
}

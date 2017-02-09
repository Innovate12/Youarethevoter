package app.com.neil.youarethevoter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.MyApi;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.model.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by swapnilparashar on 11/5/2016.
 */

public class Login extends AppCompatActivity {

    Button bLogin;
    EditText etEmail, etPassword;
    TextView tvSignup;
    ImageView ivRighttick, ivHidepasswordicon, ivShowpasswordicon;
    String sEmail = "";
    String sPassword = "";
    Boolean bLoginEnabled = true;
    User userResult = new User();
    public static final String LOGINPREFERENCES = "LoginPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setBackgroundDrawable(null);
        sharedpreferences = getApplicationContext().getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        setContentView(R.layout.login);
        bLogin = (Button) findViewById(R.id.buttonLogin);
        etEmail = (EditText) findViewById(R.id.etEmailLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        ivHidepasswordicon = (ImageView) findViewById(R.id.ivHidePasswordLogin);
        ivShowpasswordicon = (ImageView) findViewById(R.id.ivShowPasswordLogin);
        tvSignup = (TextView) findViewById(R.id.tvSignUpText);
        if(sharedpreferences.getString("loginid","")!=null || sharedpreferences.getString("loginid","")!="")
            etEmail.setText(sharedpreferences.getString("loginid",""));
        else
            etEmail.setText("");
        Log.d("loginid","loginid---"+sharedpreferences.getString("loginid",""));
        Log.d("password","password--"+sharedpreferences.getString("pass",""));
        if(sharedpreferences.getString("pass","")!=null || sharedpreferences.getString("pass","")!="")
            etPassword.setText(sharedpreferences.getString("pass",""));
        else
            etPassword.setText("");
        String signupLink = getString(R.string.signup);
        String yourString = getString(R.string.registerText, signupLink);
        tvSignup.setText(Html.fromHtml(yourString));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tvSignup.getText());
        //Spannable sp = new SpannableString(tvSignup.getText());

        ClickableSpan click = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.d("in click","in click");
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        };

        int endindex = tvSignup.getText().toString().indexOf(signupLink)+signupLink.length();
        spannableStringBuilder.setSpan(click,
                tvSignup.getText().toString().indexOf(signupLink), endindex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignup.setText(spannableStringBuilder);
        tvSignup.setMovementMethod(LinkMovementMethod.getInstance());

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmail = "" + etEmail.getText();
                sPassword = "" + etPassword.getText();
                if ("".equals(sEmail) || "".equals(sPassword)) {
                    Toast.makeText(getApplicationContext(), "Please fill all the Login fields", Toast.LENGTH_LONG).show();
                } else {
                    if (bLoginEnabled) {
                        User userToLogin = new User();
                        userToLogin.setUserEmail(sEmail);
                        userToLogin.setUserPassword(sPassword);
                        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        userToLogin.setUserDeviceID(android_id);
                        bLoginEnabled = false;
                        editor.putString("loginid",sEmail);
                        editor.putString("pass",sPassword);
                        editor.apply();
                        Log.d("login emailid","login emailid"+userToLogin.getUserEmail());
                        Log.d("login password","login password"+userToLogin.getUserPassword());
                        new EndpointsAsyncTask().execute(new Pair<Context, User>(Login.this, userToLogin));
                    } else {
                        Toast.makeText(getApplicationContext(), "Please wait - Login still in Progress", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ivHidepasswordicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivShowpasswordicon.setVisibility(View.VISIBLE);
                ivHidepasswordicon.setVisibility(View.INVISIBLE);
                etPassword.setTransformationMethod(null);

            }
        });
        ivShowpasswordicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHidepasswordicon.setVisibility(View.VISIBLE);
                ivShowpasswordicon.setVisibility(View.INVISIBLE);
                etPassword.setTransformationMethod(new PasswordTransformationMethod());

            }
        });
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, User>, Void, User> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected User doInBackground(Pair<Context, User>... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://youarethevoter-1384.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            context = params[0].first;
            User userForLogin = params[0].second;
            try {
                return myApiService.loginUser(userForLogin).execute();
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(User result) {
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            bLoginEnabled = true;
            Log.d("result message","result message"+result.getMessage());
            if (result.getMessage().contains("PASS")) {
                Intent i = new Intent(Login.this, InterestGroupList.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",(ArrayList<Integer>) result.getListOfInterestGroups());

//                Log.d("Usename","usernamelogin"+result.getListOfInterestGroups().get(0));
                i.putExtra("USERID",result.getUserID());
                i.putExtra("USERNAME",result.getUserName());
                Log.d("Usename","usernamelogin"+result.getUserID());
                i.putExtra("USEREMAIL",result.getUserEmail());
                i.putExtra("USERPASS",result.getUserPassword());
                i.putExtra("USERDOB", result.getUserDOB().getValue());
                i.putExtra("USERLOCATION",result.getLocationID());
                startActivity(i);
            } else if (result.getMessage().contains("ERROR - No such Email Registered")) {
                Toast.makeText(context, "Please Register first", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Incorrect Username/Password. Please try again", Toast.LENGTH_LONG).show();
            }
        }
    }

}

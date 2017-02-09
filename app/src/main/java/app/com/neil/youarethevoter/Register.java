package app.com.neil.youarethevoter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by swapnilparashar on 11/8/2016.
 */

public class Register extends AppCompatActivity {
    Button bRegister;
    EditText etName, etEmail, etPassword, etDOB;
    TextView tvLoginHere;
    ImageView ivRighttick, ivHidepasswordicon, ivShowpasswordicon;
    String sName = "";
    String sEmail = "";
    String sPassword = "";
    Date sDOB = null;
    Boolean bRegisterEnabled = true;
    private Spinner mySpinner;
    Integer selectedLocationId;
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z]).{6,20})";
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String DATE_PATTERN = "^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    //private SpinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setBackgroundDrawable(null);
        bRegister = (Button) findViewById(R.id.buttonRegister);
        etName = (EditText) findViewById(R.id.etName);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        ivRighttick = (ImageView) findViewById(R.id.ivRighttickicon);
        ivHidepasswordicon = (ImageView) findViewById(R.id.ivhidepassword);
        ivShowpasswordicon = (ImageView) findViewById(R.id.ivshowpassword);
        mySpinner = (Spinner) findViewById(R.id.spinnerLocation);
        tvLoginHere = (TextView) findViewById(R.id.tvSignUpText);
        //String selectedVal = getResources().getStringArray(R.array.country_arrays)[mySpinner.getSelectedItemPosition()];
        // Toast.makeText(Register.this, "ID: " + selectedVal,
        //   Toast.LENGTH_SHORT).show();
        String signupLink = getString(R.string.clickforlogin);
        String yourString = getString(R.string.clickheretext, signupLink);
        tvLoginHere.setText(Html.fromHtml(yourString));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tvLoginHere.getText());
        //Spannable sp = new SpannableString(tvSignup.getText());

        ClickableSpan click = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.d("in click","in click");
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        };

        int endindex = tvLoginHere.getText().toString().indexOf(signupLink)+signupLink.length();
        spannableStringBuilder.setSpan(click,
                tvLoginHere.getText().toString().indexOf(signupLink), endindex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLoginHere.setText(spannableStringBuilder);
        tvLoginHere.setMovementMethod(LinkMovementMethod.getInstance());
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
        /*
        SpinnerDTO[] users = new SpinnerDTO[4];

        users[0] = new SpinnerDTO();
        users[0].setId(1);
        users[0].setName("Connacht");

        users[1] = new SpinnerDTO();
        users[1].setId(2);
        users[1].setName("Leinster");

        users[2] = new SpinnerDTO();
        users[2].setId(3);
        users[2].setName("Munster");

        users[3] = new SpinnerDTO();
        users[3].setId(4);
        users[3].setName("Ulster");*/


        // Initialize the adapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the Users array (Your data)
        //adapter = new SpinAdapter(Register.this,
        //      R.layout.custom_spinnerlayout,
        //    users);
        //mySpinner = (Spinner) findViewById(R.id.spinnerLocation);
        //mySpinner.setAdapter(adapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item
        /*final ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.custom_spinnerlayout, users);
        SpinnerDTO user1 = (SpinnerDTO) ( (Spinner) findViewById(R.id.spinnerLocation) ).getSelectedItem();
        Toast.makeText(Register.this, "ID: " + user1.getId() + "\nName: " + user1.getName(),
                Toast.LENGTH_SHORT).show();
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                // Here you get the current item (a User object) that is selected by its position
                SpinnerDTO user = (SpinnerDTO)userAdapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(Register.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });*/
        /*final ArrayAdapter<SpinnerDTO> dataAdapter = new ArrayAdapter<SpinnerDTO>(this,
                android.R.layout.simple_spinner_item, users);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(dataAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                SpinnerDTO user = dataAdapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(Register.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });*/

        /*etEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Log.d("email", "email" + sEmail);
                Log.d("etemail", "etemail" + etEmail.getText().toString());
                if (etEmail.getText().toString().matches(EMAIL_PATTERN) && s.length() > 0) {
                    ivRighttick.setVisibility(View.VISIBLE);
                } else {
                    ivRighttick.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });*/

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = "" + etName.getText();
                sEmail = "" + etEmail.getText().toString().trim();
                Log.d("email", "email" + sEmail);
                sPassword = "" + etPassword.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

                /*try {
                    if(!etDOB.getText().toString().equals("")) {
                        if(etDOB.getText().toString().matches(DATE_PATTERN)) {
                            String sDOBString = dateFormat1.format(dateFormat.parse(etDOB.getText().toString()));
                            sDOB = dateFormat1.parse(sDOBString);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                pattern = Pattern.compile(PASSWORD_PATTERN);

                if ("".equals(sName) || "".equals(sEmail) || "".equals(sPassword) || etDOB.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all the Registration fields", Toast.LENGTH_LONG).show();
                } else if (!pattern.matcher(sPassword).matches()) {
                    etPassword.setBackgroundResource(R.drawable.editviewbgerror);
                    Toast.makeText(getApplicationContext(), "Please fulfil password requirements", Toast.LENGTH_LONG).show();
                } else if (!sEmail.matches(EMAIL_PATTERN)) {
                    Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_LONG).show();
                } else if (!etDOB.getText().toString().matches(DATE_PATTERN)) {
                    Toast.makeText(getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG).show();
                } else {

                    try {
                        String sDOBString = dateFormat1.format(dateFormat.parse(etDOB.getText().toString()));
                        sDOB = dateFormat1.parse(sDOBString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (bRegisterEnabled) {
                        User userToRegister = new User();
                        userToRegister.setUserName(sName);

                        //userToRegister.setUserDOB(new DateTime(sDOB, TimeZone.getTimeZone("Europe/Dublin")));
                        DateTime dateTime = new DateTime(sDOB);
                        int tzShift = dateTime.getTimeZoneShift();
                        sDOB = new Date(sDOB.getTime()+ TimeUnit.MINUTES.toMillis(tzShift));
                        Log.d("sDOB after shift", sDOB.toString());
                        Log.d("datetime",dateTime.toString());
                        Log.d("tzShift",""+tzShift);
                        userToRegister.setUserDOB(new DateTime(sDOB));

                        userToRegister.setLocationID(selectedLocationId);
                        userToRegister.setUserEmail(sEmail);
                        userToRegister.setUserPassword(sPassword);
                        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        userToRegister.setUserDeviceID(android_id);
                        bRegisterEnabled = false;
                        new EndpointsAsyncTask().execute(new Pair<Context, User>(Register.this, userToRegister));
                    } else {
                        Toast.makeText(getApplicationContext(), "Please wait - Registeration in Progress", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    /*public class SpinAdapter extends ArrayAdapter<SpinnerDTO>{

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private SpinnerDTO[] values;

        public SpinAdapter(Context context, int textViewResourceId,
                           SpinnerDTO[] values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        public int getCount(){
            return values.length;
        }

        public SpinnerDTO getItem(int position){
            return values[position];
        }

        public long getItemId(int position){
            return position;
        }


        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            View myView;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.custom_spinnerlayout, null);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            TextView label = (TextView) myView.findViewById(R.id.tvLocation);
            label.setText(values[position].getName());

            // And finally return your dynamic (or custom) view for each spinner item
            return myView;
        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            View myView;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.custom_spinnerlayout, null);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            TextView label = (TextView) myView.findViewById(R.id.tvLocation);
            label.setText(values[position].getName());

            // And finally return your dynamic (or custom) view for each spinner item
            return myView;
        }
    }*/

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
                return myApiService.registerUser(userForRegistration).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.contains("PASS")) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                bRegisterEnabled = true;
                Intent i = new Intent(Register.this, InterestGroup.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("EmailId", sEmail);
                Log.d("email final", "email final: " + sEmail);
                startActivity(i);
            } else {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                bRegisterEnabled = true;
            }
        }
    }
}

package app.com.neil.youarethevoter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.MyApi;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swapnilparashar on 11/20/2016.
 */

public class UpdateInterestGroupSettings extends AppCompatActivity {
    ArrayList<Integer> interestGroupIds;
    int userId;
    String userEmail;
    List<Integer> mSelectedValList = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interestgroup_settings);
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
        userEmail = i.getStringExtra("USEREMAIL");
        interestGroupIds = i.getIntegerArrayListExtra("USERINTERESTGROUPID");

        GridView listView = (GridView) findViewById(R.id.gridUpdateInterestGroup);
        final UpdateInterestGroupSettings.OrderGridViewAdapter adapter = new UpdateInterestGroupSettings.OrderGridViewAdapter(UpdateInterestGroupSettings.this);
        listView.setAdapter(adapter);
        //int setSelected = 0;
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                adapter.onItemSelect(arg0, arg1, arg2, arg3);
            }
        });
    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.wrongIcon) {
            finish();
        }
        if (v.getId() == R.id.updateIGDetail) {
            for(int i=0;i<mSelectedValList.size();i++){
                Log.d("mselectedval","mselectedval"+mSelectedValList.get(i));
            }
            User userToRegister = new User();
            userToRegister.setUserEmail(userEmail);
            userToRegister.setUserID(userId);
            userToRegister.setListOfInterestGroups(mSelectedValList);
            new UpdateInterestGroupSettings.EndpointsAsyncTask().execute(new Pair<Context, User>(UpdateInterestGroupSettings.this, userToRegister));
        }
    }

    class OrderGridViewAdapter extends BaseAdapter {
        private Context MContext;

        ArrayList<Integer> mSelected = new ArrayList<Integer>();

        public OrderGridViewAdapter(Context C) {
            MContext = C;
            if(interestGroupIds!=null) {
                for(int i:interestGroupIds) {
                    Log.d("i","i"+i);
                    mSelected.add(i-1);
                    mSelectedValList.add(i);
                }

            }

        }


        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public void onItemSelect(AdapterView<?> parent, View v, int pos, long id) {

            Integer position = new Integer(pos);
            ImageView ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            TextView tvInterestGroup = (TextView) v.findViewById(R.id.tv_interestGroupText);
            if (mSelected.contains(position)) {
                mSelected.remove(mSelected.indexOf(position));
                mSelectedValList.remove(mSelectedValList.indexOf(position+1));

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivIcon.getLayoutParams();
                // update view (v) state here
                ivIcon.setImageDrawable(null);

                ivIcon.setBackgroundResource(R.drawable.plus_icon);
                int width = ivIcon.getWidth();
                layoutParams.setMargins(0, 20, 16, 20);
                //layoutParams.width = width - 3;
                layoutParams.width = 27;
                ivIcon.setLayoutParams(layoutParams);
                tvInterestGroup.setBackgroundResource(R.color.lightgrey);

            } else {
                mSelected.add(position);
                mSelectedValList.add(Integer.parseInt(ids[position]));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivIcon.getLayoutParams();
                // update view (v) state here
                ivIcon.setImageDrawable(null);

                ivIcon.setBackgroundResource(R.drawable.blacktick_icon);
                layoutParams.setMargins(0, 15, 15, 15);
                layoutParams.width = 30;
                ivIcon.setLayoutParams(layoutParams);
                tvInterestGroup.setBackgroundResource(R.drawable.textviewbg);

            }
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View myView;


            LayoutInflater inflater = (LayoutInflater)MContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.interestgroup_gridlayout, null);

            // Add The Text!!!
            TextView tvText = (TextView)myView.findViewById(R.id.tv_interestGroupText);
            tvText.setText(names[position] );
            // Add The Ids!!!
            TextView tvId = (TextView)myView.findViewById(R.id.tv_interestGroupId);
            tvId.setText(ids[position] );

            ImageView ivIcon = (ImageView)myView.findViewById(R.id.ivIcon);

            // Set view highlight here, based on if it is selected or not..

                if (mSelected.contains(position)) {
                   // mSelectedValList.add(Integer.parseInt(ids[position]));
                    // update view (v) state here
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivIcon.getLayoutParams();
                    // update view (v) state here
                    ivIcon.setImageDrawable(null);

                    ivIcon.setBackgroundResource(R.drawable.blacktick_icon);
                    layoutParams.setMargins(0, 15, 15, 15);
                    layoutParams.width = 30;
                    ivIcon.setLayoutParams(layoutParams);

                    tvText.setBackgroundResource(R.drawable.textviewbg);

                } else {
                    // update view (v) state here


                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivIcon.getLayoutParams();
                    ivIcon.setImageDrawable(null);

                    ivIcon.setBackgroundResource(R.drawable.plus_icon);
                    int width = ivIcon.getWidth();
                    layoutParams.setMargins(0, 20, 16, 20);
                    layoutParams.width = 27;
                    ivIcon.setLayoutParams(layoutParams);
                    tvText.setBackgroundResource(R.color.lightgrey);
                }


            return myView;
        }

        private String[] names={"Healthcare","Education","Economy","Culture","Environment","Business","Social","Foreign Policy"};
        private String[] ids = {"1","2","3","4","5","6","7","8"};
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
            User userInterestGroup = params[0].second;
            try {
                return myApiService.updateUserInterests(userInterestGroup).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contains("ERROR")) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateInterestGroupSettings.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }
}

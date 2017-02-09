package app.com.neil.youarethevoter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
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
 * Created by swapnilparashar on 11/4/2016.
 */

public class InterestGroup extends AppCompatActivity {
    GridView mGrid;
    String emailId="";
    List<Integer> mSelectedValList = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.interest_group);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setBackgroundDrawable(null);
        Intent i = getIntent();
        emailId = i.getStringExtra("EmailId");
        /*mGrid = (GridView) findViewById(R.id.gridInterestGroup);
        mGrid.setAdapter(new AppsAdapter());
        mGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mGrid.setMultiChoiceModeListener(new MultiChoiceModeListener());*/

        //View view = inflater.inflate(R.layout.g, null);
        GridView listView = (GridView) findViewById(R.id.gridInterestGroup);
        final OrderGridViewAdapter adapter = new OrderGridViewAdapter(InterestGroup.this);
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


        //return view;
    }

    public void clickContinueVote(View view) {
        User userToRegister = new User();
        userToRegister.setUserEmail(emailId);
        userToRegister.setListOfInterestGroups(mSelectedValList);
        new InterestGroup.EndpointsAsyncTask().execute(new Pair<Context, User>(InterestGroup.this, userToRegister));

    }

    class OrderGridViewAdapter extends BaseAdapter {
        private Context MContext;

        ArrayList<Integer> mSelected = new ArrayList<Integer>();
        public OrderGridViewAdapter(Context C){
            MContext = C;

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
            ImageView ivIcon = (ImageView)v.findViewById(R.id.ivIcon);
            TextView tvInterestGroup = (TextView) v.findViewById(R.id.tv_interestGroupText);
            if(mSelected.contains(position)) {
                mSelected.remove(position);
                mSelectedValList.remove(position);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)ivIcon.getLayoutParams();
                // update view (v) state here
                ivIcon.setImageDrawable(null);

                ivIcon.setBackgroundResource(R.drawable.plus_icon);
                int width = ivIcon.getWidth();
                layoutParams.setMargins(0,20,16,20);
                //layoutParams.width = width-3;
                layoutParams.width = 27;
                ivIcon.setLayoutParams(layoutParams);
                tvInterestGroup.setBackgroundResource(R.color.lightgrey);

            }
            else {
                mSelected.add(position);
                mSelectedValList.add(Integer.parseInt(ids[position]));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)ivIcon.getLayoutParams();
                // update view (v) state here
                ivIcon.setImageDrawable(null);

                ivIcon.setBackgroundResource(R.drawable.blacktick_icon);
                layoutParams.setMargins(0,15,15,15);
                layoutParams.width = 30;
                ivIcon.setLayoutParams(layoutParams);
                tvInterestGroup.setBackgroundResource(R.color.yellow);
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
            if(mSelected.contains(position)) {
                // update view (v) state here
                //ivIcon.setBackgroundResource(R.drawable.righttick_icons);
            }
            else {
                // update view (v) state here
                //ivIcon.setBackgroundResource(R.drawable.plus_icon);
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivIcon.getLayoutParams();
            ivIcon.setImageDrawable(null);

            ivIcon.setBackgroundResource(R.drawable.plus_icon);
            int width = ivIcon.getWidth();
            layoutParams.setMargins(0, 20, 16, 20);
            //layoutParams.width = width - 3;
            layoutParams.width = 27;
            ivIcon.setLayoutParams(layoutParams);
            tvText.setBackgroundResource(R.color.lightgrey);
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
                Intent i = new Intent(InterestGroup.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }
}

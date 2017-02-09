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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.MyApi;
import com.neil.swapnilparashar.youarethevoter.backend.myApi.model.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by swapnilparashar on 11/7/2016.
 */

public class InterestGroupList extends AppCompatActivity{
    ListView lv;
    Context context;
    ArrayList<Integer> interestGroupIds;
    int userId,locationId;
    String userName, userEmail, userPass;
    Long userDOB;
    public static String [] questMenuName={"Healthcare","Education","Economy","Culture","Environment","Business","Social","Foreign Policy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interestgroup_list);
        Intent i = getIntent();
        interestGroupIds = i.getIntegerArrayListExtra("INTERESTGROUPIDLIST");
//        Log.d("Usename","iglist"+interestGroupIds.get(0));
        userId = i.getIntExtra("USERID",0);
        userName = i.getStringExtra("USERNAME");
        userDOB = i.getLongExtra("USERDOB",0);
        userEmail = i.getStringExtra("USEREMAIL");
        userPass = i.getStringExtra("USERPASS");
        locationId = i.getIntExtra("USERLOCATION",0);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        ArrayList<String> interestGroupName = new ArrayList<String>();
        if(interestGroupIds==null){
            Toast.makeText(InterestGroupList.this, "update interest groups", Toast.LENGTH_LONG).show();
        } else {
            for (Integer itId : interestGroupIds) {
                Log.d("id", "id" + itId);
                String itName = questMenuName[itId - 1];
                interestGroupName.add(itName);
            }
            context=this;
            lv=(ListView) findViewById(R.id.lvInterestGroup);
            lv.setAdapter(new InterestGroupCustomAdapter(this, interestGroupName, interestGroupIds));
        }

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        /*TextView tvQuestion = (TextView) mCustomView.findViewById(R.id.tvQuestion);
        tvQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(InterestGroupList.this, QuestionVoteList.class);
                startActivity(i);
            }
        });*/

        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(mCustomView,lp1);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323742")));







    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.menuIcon) {
            Intent i = new Intent(InterestGroupList.this,SettingsMenu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("Usename","username"+userName);

            i.putExtra("USERID",userId);
            i.putExtra("USERNAME",userName);
            i.putExtra("USERDOB",userDOB);
            i.putExtra("USERPASS",userPass);
            i.putExtra("USERLOCATION",locationId);
            i.putExtra("USEREMAIL",userEmail);
            i.putIntegerArrayListExtra("USERINTERESTGROUPID",interestGroupIds);
//            Log.d("Usename","igids"+interestGroupIds.get(0));
            startActivity(i);
        }

    }

    public class InterestGroupCustomAdapter extends BaseAdapter {
        ArrayList<String> result;
        ArrayList<Integer> resultId;
        Context context;
        private LayoutInflater inflater=null;
        public InterestGroupCustomAdapter(InterestGroupList mainActivity, ArrayList<String> prgmNameList, ArrayList<Integer> questId) {
            // TODO Auto-generated constructor stub
            result=prgmNameList;
            context=mainActivity;
            resultId=questId;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return result.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder
        {
            TextView tv;
            ImageView img;
            TextView tvId;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.custom_listlayout, null);
            holder.tv=(TextView) rowView.findViewById(R.id.tvQuestion);
            holder.tvId=(TextView) rowView.findViewById(R.id.tv_questionId);
            holder.img=(ImageView) rowView.findViewById(R.id.ivArrow);
            holder.tv.setText(result.get(position));
            holder.tvId.setText(resultId.get(position).toString());
            //holder.img.setImageResource(imageId[position]);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //new EndpointsAsyncTask().execute(new Pair<Context, User>(InterestGroupList.this, userToLogin));
                    Intent i = new Intent(InterestGroupList.this, QuestionsMenuList.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("INTERESTGROUPID",resultId.get(position));
                    i.putExtra("USERID",userId);
                    i.putExtra("USERNAME",userName);
                    i.putExtra("USERDOB",userDOB);
                    i.putExtra("USERPASS",userPass);
                    i.putExtra("USERLOCATION",locationId);
                    i.putExtra("USEREMAIL",userEmail);
                    i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",interestGroupIds);
                    startActivity(i);
                }
            });
            return rowView;
        }

    }

    /*class EndpointsAsyncTask extends AsyncTask<Pair<Context, User>, Void, User> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected User doInBackground(Pair<Context, User>... params) {
            if(myApiService == null) {  // Only do this once
                VotesApi.Builder builder = new VotesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
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
                i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",(ArrayList<Integer>) result.getListOfInterestGroups());
                startActivity(i);
            } else if (result.getMessage().contains("ERROR")) {
                Toast.makeText(context, "Please Register first", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Incorrect Username/Password. Please try again", Toast.LENGTH_LONG).show();
            }
        }
    }*/

}

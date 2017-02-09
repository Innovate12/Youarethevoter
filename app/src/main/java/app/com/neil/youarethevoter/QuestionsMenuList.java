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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.neil.swapnilparashar.youarethevoter.backend.votesApi.VotesApi;
import com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails;
import com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetailsCollection;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by swapnilparashar on 11/6/2016.
 */

public class QuestionsMenuList extends AppCompatActivity {

    ListView lv;
    Context context;
    int interestGroupId;
    ArrayList<Integer> interestGroupIds;
    int userId,locationId;
    String userName, userEmail, userPass;
    Long userDOB;

    public static String [] questMenuName={"Open questions", "Results of closed questions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_menulist);
        Intent i = getIntent();
        interestGroupId = i.getIntExtra("INTERESTGROUPID",0);
        userId = i.getIntExtra("USERID",0);
        userName = i.getStringExtra("USERNAME");
        userDOB = i.getLongExtra("USERDOB",0);
        userEmail = i.getStringExtra("USEREMAIL");
        userPass = i.getStringExtra("USERPASS");
        locationId = i.getIntExtra("USERLOCATION",0);
        interestGroupIds = i.getIntegerArrayListExtra("INTERESTGROUPIDLIST");
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        /*TextView tvQuestion = (TextView) mCustomView.findViewById(R.id.tvQuestion);
        tvQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsMenuList.this, InterestGroupList.class);
                startActivity(i);
            }
        });*/

        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(mCustomView,lp1);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323742")));

        context=this;
        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, questMenuName));
    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.menuIcon) {
            Intent i = new Intent(QuestionsMenuList.this,SettingsMenu.class);
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

    public class CustomAdapter extends BaseAdapter {
        String [] result;
        Context context;
        int [] imageId;
        private LayoutInflater inflater=null;
        public CustomAdapter(QuestionsMenuList mainActivity, String[] prgmNameList) {
            // TODO Auto-generated constructor stub
            result=prgmNameList;
            context=mainActivity;
            //imageId=prgmImages;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return result.length;
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
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.custom_listlayout, null);
            holder.tv=(TextView) rowView.findViewById(R.id.tvQuestion);
            holder.img=(ImageView) rowView.findViewById(R.id.ivArrow);
            holder.tv.setText(result[position]);
            //holder.img.setImageResource(imageId[position]);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    /*if(position==1) {
                        Object[] paramVal = {interestGroupId,false};
                        new EndpointsAsyncTask().execute(new Pair<Context, Object[]>(QuestionsMenuList.this, paramVal));
                    } else {
                        Object[] paramVal1 = {interestGroupId,true};
                        new EndpointsAsyncTask().execute(new Pair<Context, Object[]>(QuestionsMenuList.this, paramVal1));
                    }*/
                    Intent i = new Intent(QuestionsMenuList.this, QuestionVoteList.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("INTERESTGROUPID",interestGroupId);
                    i.putExtra("USERNAME",userName);
                    i.putExtra("USERDOB",userDOB);
                    i.putExtra("USERPASS",userPass);
                    i.putExtra("USERLOCATION",locationId);
                    i.putExtra("USEREMAIL",userEmail);
                    i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",interestGroupIds);
                    Log.d("position","position"+position);
                    if(position==1)
                        i.putExtra("QUESTMENUSTATUS",false);
                    else
                        i.putExtra("QUESTMENUSTATUS",true);
                    i.putExtra("USERID",userId);
                    startActivity(i);
                }
            });
            return rowView;
        }

    }

    /*class EndpointsAsyncTask extends AsyncTask<Pair<Context, Object[]>, Void, VoteDetailsCollection> {
        private VotesApi myApiService = null;
        private Context context;

        @Override
        protected VoteDetailsCollection doInBackground(Pair<Context, Object[]>... params) {
            if(myApiService == null) {  // Only do this once
                VotesApi.Builder builder = new VotesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://youarethevoter-1384.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            context = params[0].first;
            Object[] paramVal = params[0].second;
            Boolean status = (Boolean) paramVal[0];
            Integer igId = (Integer) paramVal[1];
            try {
                return myApiService.voteList(status,igId).execute();
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(VoteDetailsCollection result) {
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            //bLoginEnabled = true;
            Log.d("result message","result message"+result.getMessage());
            if (result.getMessage().contains("PASS")) {
                Intent i = new Intent(QuestionsMenuList.this, QuestionVoteList.class);
                i.putExtra("INTERESTGROUPIDLIST",(ArrayList<VoteDetails>) result.getItems());
                startActivity(i);
            } else if (result.getMessage().contains("ERROR")) {
                Toast.makeText(context, "Please Register first", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Incorrect Username/Password. Please try again", Toast.LENGTH_LONG).show();
            }
        }
    }*/
}


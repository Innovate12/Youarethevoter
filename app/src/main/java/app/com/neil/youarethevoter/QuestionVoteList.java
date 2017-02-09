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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swapnilparashar on 11/7/2016.
 */

public class QuestionVoteList extends AppCompatActivity {

    ListView lv;
    Context context;
    int interestGroupId;
    ArrayList<Integer> interestGroupIds;
    int userId,locationId;
    String userName, userEmail, userPass;
    Long userDOB;
    Boolean queststatus;

    //xpublic static String [] questMenuName={"gdkhfhkgfjh hjfhjfkhfhgc", "gfytf hjfjkfyf jhfhgdfyt", "ghfghfyg hjgjkf hjfkn hjff"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_votelist);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        Intent i = getIntent();
        interestGroupId = i.getIntExtra("INTERESTGROUPID",0);
        userId = i.getIntExtra("USERID",0);
        userName = i.getStringExtra("USERNAME");
        userDOB = i.getLongExtra("USERDOB",0);
        userEmail = i.getStringExtra("USEREMAIL");
        userPass = i.getStringExtra("USERPASS");
        locationId = i.getIntExtra("USERLOCATION",0);
        interestGroupIds = i.getIntegerArrayListExtra("INTERESTGROUPIDLIST");
        queststatus = i.getBooleanExtra("QUESTMENUSTATUS",false);
        Log.d("queststatus","queststatus"+queststatus);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        /*TextView tvQuestion = (TextView) mCustomView.findViewById(R.id.tvQuestion);
        tvQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionVoteList.this, QuestionVote.class);
                startActivity(i);
            }
        });*/

        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(mCustomView,lp1);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323742")));
        Object[] paramVal = {interestGroupId,queststatus};
        new EndpointsAsyncTask().execute(new Pair<Context, Object[]>(QuestionVoteList.this, paramVal));
        context=this;

        //lv.setAdapter(new QuestionVoteListCustomAdapter(QuestionVoteList.this, questMenuName));
    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.menuIcon) {
            Intent i = new Intent(QuestionVoteList.this,SettingsMenu.class);
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

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, Object[]>, Void, List<VoteDetails>> {
        private VotesApi myApiService = null;
        private Context context;

        @Override
        protected List<VoteDetails> doInBackground(Pair<Context, Object[]>... params) {
            if(myApiService == null) {  // Only do this once
                VotesApi.Builder builder = new VotesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://youarethevoter-1384.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            context = params[0].first;
            Object[] paramVal = params[0].second;
            Log.d("paramVal[0]","paramVal[0]"+paramVal[0]);
            Log.d("paramVal[1]","paramVal[1]"+paramVal[1]);
            Boolean status = (Boolean) paramVal[1];
            Integer igId = (Integer) paramVal[0];
            try {
                return myApiService.voteList(status,igId).execute().getItems();
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(List<VoteDetails> result) {
            if(result!=null) {
                lv = (ListView) findViewById(R.id.lvQuestionVoteList);
                Log.d("result", "result" + result.get(0).getVoteSummary());
                lv.setAdapter(new QuestionVoteListCustomAdapter(QuestionVoteList.this, result));
            } else {
                Toast.makeText(context, "No Questions List", Toast.LENGTH_LONG).show();
                /*Intent i = new Intent(QuestionVoteList.this, QuestionsMenuList.class);
                i.putExtra("INTERESTGROUPID",interestGroupId);
                i.putExtra("USERID",userId);
                startActivity(i);*/
                finish();
            }
        }
    }

    public class QuestionVoteListCustomAdapter extends BaseAdapter {
        List<VoteDetails> result;
        Context context;
        int [] imageId;
        private LayoutInflater inflater=null;
        public QuestionVoteListCustomAdapter(QuestionVoteList mainActivity, List<VoteDetails> prgmNameList) {
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
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.custom_listlayout, null);
            holder.tv=(TextView) rowView.findViewById(R.id.tvQuestion);
            holder.img=(ImageView) rowView.findViewById(R.id.ivArrow);
            holder.tv.setText(result.get(position).getVoteSummary());
            //holder.img.setImageResource(imageId[position]);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(queststatus==true) {

                        Intent i = new Intent(QuestionVoteList.this, QuestionVote.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("VOTESUMMARY", result.get(position).getVoteSummary());
                        i.putExtra("VOTENAME", result.get(position).getVoteName());
                        i.putExtra("VOTELINK", result.get(position).getVoteLink());
                        i.putExtra("VOTEHASHTAG", result.get(position).getVoteHashTag());
                        i.putExtra("VOTEA", result.get(position).getVoteA());
                        i.putExtra("VOTEB", result.get(position).getVoteB());
                        i.putExtra("VOTEID", result.get(position).getVoteID());
                        i.putExtra("USERID", userId);
                        i.putExtra("COUNTA", result.get(position).getCountA());
                        i.putExtra("COUNTB", result.get(position).getCountB());
                        i.putExtra("USERID",userId);
                        i.putExtra("USERNAME",userName);
                        i.putExtra("USERDOB",userDOB);
                        i.putExtra("USERPASS",userPass);
                        i.putExtra("USERLOCATION",locationId);
                        i.putExtra("USEREMAIL",userEmail);
                        i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",interestGroupIds);
                        startActivity(i);
                    } else {
                        int iA = result.get(position).getCountA();
                        int iB = result.get(position).getCountB();
                        int iTotal = iA + iB;
                        int iPercentA = (iA*100)/iTotal;
                        int iPercentB = 100-iPercentA;
                        Intent i = new Intent(QuestionVoteList.this, VoteRegistered.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.d("poll id action 2--", "poll id action 2-- " + result.get(position).getVoteID());
                        Log.d("voteSummary--", "voteSummary-- " + result.get(position).getVoteSummary());
                        i.putExtra("VOTEID", result.get(position).getVoteID());
                        i.putExtra("PERCENTYES", iPercentA);
                        i.putExtra("PERCENTNO", iPercentB);
                        i.putExtra("VOTESUMMARY", result.get(position).getVoteSummary());
                        i.putExtra("VOTELINK", result.get(position).getVoteLink());
                        i.putExtra("VOTEHASHTAG", result.get(position).getVoteHashTag());
                        i.putExtra("USERID",userId);
                        i.putExtra("USERNAME",userName);
                        i.putExtra("USERDOB",userDOB);
                        i.putExtra("USERPASS",userPass);
                        i.putExtra("USERLOCATION",locationId);
                        i.putExtra("USEREMAIL",userEmail);
                        i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",interestGroupIds);
                        startActivity(i);
                    }
                }
            });
            return rowView;
        }

    }
}

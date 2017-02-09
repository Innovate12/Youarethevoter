package app.com.neil.youarethevoter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.neil.swapnilparashar.youarethevoter.backend.votesApi.VotesApi;
import com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by swapnilparashar on 11/7/2016.
 */

public class QuestionVote extends AppCompatActivity {
    String voteSummary, voteLink, voteName,voteHashTag;
    int voteId, voteA, voteB, countA, countB;
    TextView questText;
    Button bYes, bNo;
    int iVoteHistory = 0;
    int iVoteCurrent = 0;
    boolean bReady = false;
    ArrayList<Integer> interestGroupIds;
    int userId,locationId;
    String userName, userEmail, userPass;
    Long userDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_vote);
        Intent i = getIntent();
        voteSummary = i.getStringExtra("VOTESUMMARY");
        voteName = i.getStringExtra("VOTENAME");
        voteLink = i.getStringExtra("VOTELINK");
        voteHashTag = i.getStringExtra("VOTEHASHTAG");
        voteId = i.getIntExtra("VOTEID",0);
        voteA = i.getIntExtra("VOTEA",0);
        voteB = i.getIntExtra("VOTEB",0);
        countA = i.getIntExtra("COUNTA",0);
        countB = i.getIntExtra("COUNTB",0);
        userId = i.getIntExtra("USERID",0);
        userId = i.getIntExtra("USERID",0);
        userName = i.getStringExtra("USERNAME");
        userDOB = i.getLongExtra("USERDOB",0);
        userEmail = i.getStringExtra("USEREMAIL");
        userPass = i.getStringExtra("USERPASS");
        locationId = i.getIntExtra("USERLOCATION",0);
        interestGroupIds = i.getIntegerArrayListExtra("INTERESTGROUPIDLIST");
        questText = (TextView) findViewById(R.id.tvQuestText);
        bYes = (Button) findViewById(R.id.buttonYes);
        bNo = (Button) findViewById(R.id.buttonNo);
        bYes.setEnabled(false);
        bNo.setEnabled(false);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(mCustomView, lp1);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323742")));
        questText.setText(voteSummary);
        new EndpointsAsyncTask().execute(new Pair<Context, Integer>(QuestionVote.this, voteId));

    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.menuIcon) {
            Intent i = new Intent(QuestionVote.this,SettingsMenu.class);
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


    public void clickYesBtn(View view) {
        if (bReady) {
            if (iVoteCurrent == 1) {
                //bYes.setPressed(false);
                //bYes.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                //iVoteCurrent = 0;
            } else if (iVoteCurrent == 2) {
                //bYes.setPressed(true);
                bYes.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                //bNo.setPressed(false);
                bNo.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                iVoteCurrent = 1;
            } else {
                //bYes.setPressed(true);
                bYes.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                iVoteCurrent = 1;
            }
        }
        submit();
    }
    public void clickNoBtn(View view) {
        if(bReady) {
            if (iVoteCurrent == 2) {
                //bNo.setPressed(false);
                //bNo.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                //iVoteCurrent = 0;
            } else if (iVoteCurrent == 1) {
                //bNo.setPressed(true);
                bNo.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                //bYes.setPressed(false);
                bYes.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                iVoteCurrent = 2;
            } else {
                //bNo.setPressed(true);
                bNo.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                iVoteCurrent = 2;
            }
        }
        submit();
    }
    public void submit() {
        if(bReady) {
            bReady = false;
            VoteDetails vd = new VoteDetails();
            vd.setVoteID(voteId);
            vd.setUserID(userId);
            vd.setVoteA(0);
            vd.setVoteB(0);
            /*if(iVoteCurrent == 0) {
                Toast.makeText(getApplicationContext(), "Please select a vote first", Toast.LENGTH_LONG).show();
                bReady = true;
            } else {*/
                if(iVoteCurrent == 1) {
                    if(iVoteHistory == 1) {
                        Toast.makeText(getApplicationContext(), "You have already voted this option", Toast.LENGTH_LONG).show();
                        bReady = true;
                        // Just displaying the results
                        int iA = countA;
                        int iB = countB;
                        int iTotal = iA + iB;
                        int iPercentA = (iA*100)/iTotal;
                        int iPercentB = 100-iPercentA;
                        Intent i = new Intent(QuestionVote.this, VoteRegistered.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.d("poll id action 2--", "poll id action 2-- " + voteId);
                        Log.d("voteSummary--", "voteSummary-- " + voteSummary);
                        i.putExtra("VOTEID", voteId);
                        i.putExtra("PERCENTYES", iPercentA);
                        i.putExtra("PERCENTNO", iPercentB);
                        i.putExtra("VOTESUMMARY", voteSummary);
                        i.putExtra("VOTELINK", voteLink);
                        i.putExtra("VOTEHASHTAG", voteHashTag);
                        i.putExtra("USERID",userId);
                        i.putExtra("USERNAME",userName);
                        i.putExtra("USERDOB",userDOB);
                        i.putExtra("USERPASS",userPass);
                        i.putExtra("USERLOCATION",locationId);
                        i.putExtra("USEREMAIL",userEmail);
                        i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",interestGroupIds);
                        //bReady = true;
                        iVoteHistory = iVoteCurrent;
                        startActivity(i);
                    } else if(iVoteHistory == 2) {
                        //vd.setCountA(1);
                        //vd.setCountB(-1);
                        vd.setVoteA(1);
                        vd.setVoteB(-1);
                        new EndpointsAsyncTask2().execute(new Pair<Context, VoteDetails>(QuestionVote.this, vd));
                    } else {
                        //vd.setCountA(1);
                        //vd.setCountB(0);
                        vd.setVoteA(1);
                        vd.setVoteB(0);
                        new EndpointsAsyncTask2().execute(new Pair<Context, VoteDetails>(QuestionVote.this, vd));
                    }
                } else {
                    if(iVoteHistory == 2) {
                        Toast.makeText(getApplicationContext(), "You have already voted this option", Toast.LENGTH_LONG).show();
                        bReady = true;
                        // Just displaying the results
                        int iA = countA;
                        int iB = countB;
                        int iTotal = iA + iB;
                        int iPercentA = (iA*100)/iTotal;
                        int iPercentB = 100-iPercentA;
                        Intent i = new Intent(QuestionVote.this, VoteRegistered.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.d("poll id action 2--", "poll id action 2-- " + voteId);
                        Log.d("voteSummary--", "voteSummary-- " + voteSummary);
                        i.putExtra("VOTEID", voteId);
                        i.putExtra("PERCENTYES", iPercentA);
                        i.putExtra("PERCENTNO", iPercentB);
                        i.putExtra("VOTESUMMARY", voteSummary);
                        i.putExtra("VOTELINK", voteLink);
                        i.putExtra("VOTEHASHTAG", voteHashTag);
                        i.putExtra("USERID",userId);
                        i.putExtra("USERNAME",userName);
                        i.putExtra("USERDOB",userDOB);
                        i.putExtra("USERPASS",userPass);
                        i.putExtra("USERLOCATION",locationId);
                        i.putExtra("USEREMAIL",userEmail);
                        i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",interestGroupIds);
                        //bReady = true;
                        iVoteHistory = iVoteCurrent;
                        startActivity(i);
                    } else if(iVoteHistory == 1) {
                        //vd.setCountA(-1);
                        //vd.setCountB(1);
                        vd.setVoteB(1);
                        vd.setVoteA(-1);
                        new EndpointsAsyncTask2().execute(new Pair<Context, VoteDetails>(QuestionVote.this, vd));
                    } else {
                        //vd.setCountA(0);
                        //vd.setCountB(1);
                        vd.setVoteB(1);
                        vd.setVoteA(0);
                        new EndpointsAsyncTask2().execute(new Pair<Context, VoteDetails>(QuestionVote.this, vd));
                    }
                //}
            }
        } else {
            Toast.makeText(getApplicationContext(),"Please wait - Voting in Progress",Toast.LENGTH_LONG).show();
        }
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, Integer>, Void, VoteDetails> {
        private VotesApi myApiService = null;
        private Context context;

        @Override
        protected VoteDetails doInBackground(Pair<Context, Integer>... params) {
            if(myApiService == null) {  // Only do this once
                VotesApi.Builder builder = new VotesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://youarethevoter-1384.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            context = params[0].first;
            int voteId = params[0].second;
            Log.d("userid","userid"+userId);
            Log.d("voteId","voteId"+voteId);
            try {
                Log.d("invote","invote");
                return myApiService.voteDetails(userId, voteId).execute();
            } catch (IOException e) {
                Log.d("in catch","in catch"+e.toString());
                return null;
            }
        }
        @Override
        protected void onPostExecute(VoteDetails result) {
            bYes.setEnabled(true);
            bNo.setEnabled(true);
            Log.d("voteA","voteA"+result.getVoteA());
            countA = result.getCountA();
            countB = result.getCountB();
            if(result.getVoteA()==0 && result.getVoteB()==0) {
                iVoteHistory=0;
                iVoteCurrent=0;
            } else if(result.getVoteA()==1) {
                iVoteHistory=1;
                iVoteCurrent=1;
                bYes.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            } else {
                iVoteHistory=2;
                iVoteCurrent=2;
                bNo.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            }
            bReady = true;
        }
    }

    class EndpointsAsyncTask2 extends AsyncTask<Pair<Context, VoteDetails>, Void, String> {
        private VotesApi myApiService = null;
        private Context context;
        int voteACal=0, voteBCal=0;
        @Override
        protected String doInBackground(Pair<Context, VoteDetails>... params) {
            if(myApiService == null) {  // Only do this once
                VotesApi.Builder builder = new VotesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://youarethevoter-1384.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            context = params[0].first;
            VoteDetails vd = params[0].second;
            voteACal = vd.getVoteA();
            voteBCal = vd.getVoteB();
            try {
                return myApiService.voteAction(vd).execute().getData();
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if(result==null) {
                Toast.makeText(getApplicationContext(), "Sorry NULL returned", Toast.LENGTH_LONG).show();
            }
            else if (result.contains("PASS")) {
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                int iA = countA+voteACal;
                int iB = countB+voteBCal;
                countA = iA;
                countB = iB;
                int iTotal = iA + iB;
                int iPercentA = (iA*100)/iTotal;
                int iPercentB = 100-iPercentA;
                Intent i = new Intent(QuestionVote.this, VoteRegistered.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("poll id action 2--", "poll id action 2-- " + voteId);
                Log.d("voteSummary--", "voteSummary-- " + voteSummary);
                i.putExtra("VOTEID", voteId);
                i.putExtra("PERCENTYES", iPercentA);
                i.putExtra("PERCENTNO", iPercentB);
                i.putExtra("VOTESUMMARY", voteSummary);
                i.putExtra("VOTELINK", voteLink);
                i.putExtra("VOTEHASHTAG", voteHashTag);
                i.putExtra("USERID",userId);
                i.putExtra("USERNAME",userName);
                i.putExtra("USERDOB",userDOB);
                i.putExtra("USERPASS",userPass);
                i.putExtra("USERLOCATION",locationId);
                i.putExtra("USEREMAIL",userEmail);
                i.putIntegerArrayListExtra("INTERESTGROUPIDLIST",interestGroupIds);
                bReady = true;
                iVoteHistory = iVoteCurrent;
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Sorry something went wrong - please try again\n"+result, Toast.LENGTH_LONG).show();
                bReady = true;
            }
        }
    }
}

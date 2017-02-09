package app.com.neil.youarethevoter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by swapnilparashar on 11/7/2016.
 */

public class VoteRegistered extends AppCompatActivity {
    int percentYes=0, percentNo=0;
    String voteSummary="", voteLink="", voteHashTag="";
    TextView tvQuestion;
    ImageView ivTwitterShare;
    ArrayList<Integer> interestGroupIds;
    int userId,locationId;
    String userName, userEmail, userPass;
    Long userDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_registered);
        Intent i = getIntent();
        voteSummary = i.getStringExtra("VOTESUMMARY");
        userId = i.getIntExtra("USERID",0);
        userName = i.getStringExtra("USERNAME");
        userDOB = i.getLongExtra("USERDOB",0);
        userEmail = i.getStringExtra("USEREMAIL");
        userPass = i.getStringExtra("USERPASS");
        locationId = i.getIntExtra("USERLOCATION",0);
        interestGroupIds = i.getIntegerArrayListExtra("INTERESTGROUPIDLIST");
        Log.d("votesummary","votesummary"+voteSummary);
        voteLink = i.getStringExtra("VOTELINK");
        voteHashTag = i.getStringExtra("VOTEHASHTAG");
        percentYes = i.getIntExtra("PERCENTYES",0);
        Log.d("percentyes","percentyes"+percentYes);
        percentNo = i.getIntExtra("PERCENTNO",0);
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

        ivTwitterShare = (ImageView) findViewById(R.id.ivTwitterLogo);
        tvQuestion = (TextView) findViewById(R.id.tvQuestVoteText);
        tvQuestion.setText(voteSummary);

        float percentYesFloat = ((float)percentYes/100);
        float percentNoFloat = ((float)percentNo/100);

        TextView view = (TextView) findViewById(R.id.tvYes);

        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) view.getLayoutParams();
        view.setText("YES \n"+percentYes+"%");
        view.setTextSize(11);
        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view.setBackgroundResource(R.color.yellow);
// This will currently return null, if it was not constructed from XML.
        Log.d("widthpercent","widthpercent"+view.getText());
        Log.d("widthpercent","widthpercent"+percentYesFloat);
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = percentYesFloat-0.05f;


        TextView view1 = (TextView) findViewById(R.id.tvNo);
        view1.setText("NO \n"+percentNo+"%");
        view1.setTextSize(11);
        view1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        PercentRelativeLayout.LayoutParams params1 = (PercentRelativeLayout.LayoutParams) view1.getLayoutParams();
// This will currently return null, if it was not constructed from XML.
        PercentLayoutHelper.PercentLayoutInfo info1 = params1.getPercentLayoutInfo();
        Log.d("widthpercent","widthpercent"+(percentNoFloat-0.05f));
        info1.widthPercent = percentNoFloat - 0.05f;


        if((percentNoFloat-0.05f)<=0.1f) { Log.d("widthpercent","in no <0.1f"+(percentNoFloat-0.05f));
            info1.widthPercent = 0.12f;
            info.widthPercent = 0.78f;
        }
        if((percentYesFloat-0.05f)<=0.1f) { Log.d("widthpercent","in yes <0.1f"+(percentYesFloat-0.05f));
            info.widthPercent = 0.12f;
            info1.widthPercent = 0.78f;
        }
        view.requestLayout();
        view1.requestLayout();

        ivTwitterShare.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/intent/tweet?url="+"VOTED on "+voteSummary+"&hashtags="+voteHashTag));
                startActivity(intent);
            }
        });

    }

    public void clickEvent(View v) {
        if (v.getId() == R.id.menuIcon) {
            Intent i = new Intent(VoteRegistered.this,SettingsMenu.class);
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
}

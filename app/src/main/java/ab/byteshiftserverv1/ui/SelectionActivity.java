package ab.byteshiftserverv1.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.estimote.sdk.SystemRequirementsChecker;

import ab.byteshiftserverv1.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.mapDemoButton) Button mMapDemoButton;
    @Bind(R.id.rewardDemoButton) Button mRewardDemoButton;
    @Bind(R.id.userDemoButton) Button mUserDemoButton;
    private ImageView mLogo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        mLogo1 = (ImageView) findViewById(R.id.imageView);
        logoAnim1();
        ButterKnife.bind(this);
        mMapDemoButton.setOnClickListener(this);
        mRewardDemoButton.setOnClickListener(this);
        mUserDemoButton.setOnClickListener(this);
        openAnimation1();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }


    private void openAnimation4() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUserDemoButton.animate().translationY(0).setDuration(1200);

            }
        }, 300);
    }

    private void openAnimation3() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRewardDemoButton.animate().translationY(0).setDuration(1200);
                openAnimation4();
            }
        }, 300);
    }

    private void openAnimation2() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapDemoButton.animate().translationY(0).setDuration(1200);
                openAnimation3();
            }
        }, 300);
    }

    private void logoAnim1() {
        mLogo1.animate().alpha(1).setDuration(1000);
    }

    private void openAnimation1() {
        mMapDemoButton.animate().translationY(3000).translationX(0).setDuration(1);
        mRewardDemoButton.animate().translationY(3000).translationX(0).setDuration(1);
        mUserDemoButton.animate().translationY(3000).translationX(0).setDuration(1);
        openAnimation2();
        logoAnim1();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLogo1 = (ImageView) findViewById(R.id.imageView);
        logoAnim1();
        openAnimation1();
    }

    @Override
    public void onClick(View v) {
        if (v == mMapDemoButton) {
            mMapDemoButton.animate().translationX(3000).setDuration(600);
            mRewardDemoButton.animate().translationX(-3000).setDuration(600);
            mUserDemoButton.animate().translationX(-3000).setDuration(600);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SelectionActivity.this, ChosenActivity.class);
                    intent.putExtra("map", "map");
                    startActivity(intent);
                }
            }, 600);
        }
        if (v == mRewardDemoButton) {
            mMapDemoButton.animate().translationX(-3000).setDuration(600);
            mRewardDemoButton.animate().translationX(3000).setDuration(600);
            mUserDemoButton.animate().translationX(-3000).setDuration(600);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SelectionActivity.this, ChosenActivity.class);
                    intent.putExtra("reward", "reward");
                    startActivity(intent);
                }
            }, 600);
        }
        if (v == mUserDemoButton) {
            mMapDemoButton.animate().translationX(-3000).setDuration(600);
            mRewardDemoButton.animate().translationX(-3000).setDuration(600);
            mUserDemoButton.animate().translationX(3000).setDuration(600);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SelectionActivity.this, ChosenActivity.class);
                    intent.putExtra("user", "user");
                    startActivity(intent);
                }
            }, 600);
        }
    }

}

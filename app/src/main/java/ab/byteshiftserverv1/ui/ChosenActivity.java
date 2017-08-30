package ab.byteshiftserverv1.ui;

import android.content.Context;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ab.byteshiftserverv1.BeaconMajor;
import ab.byteshiftserverv1.BeaconMinor;
import ab.byteshiftserverv1.Constants;
import ab.byteshiftserverv1.R;
import ab.byteshiftserverv1.model.Beacon;
import ab.byteshiftserverv1.model.Visit;
import ab.byteshiftserverv1.service.ApiService;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChosenActivity extends AppCompatActivity {
    public ArrayList<Beacon> mBeacons = new ArrayList<>();
    public ArrayList<Visit> mVisits = new ArrayList<>();
    private BeaconManager beaconManager;
    private Region region;
    public CountDownTimer rewardTimer;
    public int currentUserStatus;
    public int currentUserVisitCount;
    private static final int MAX_WIDTH = 300;
    private static final int MAX_HEIGHT = 300;
    private boolean timerExists;
    public long timeRemaining;
    public int bc1;
    public int bc2;
    public int bc3;
    public int bc4;
    public int rc;

    @Bind(R.id.textField1) TextView mTextField1;
    @Bind(R.id.textField2) TextView mTextField2;
    @Bind(R.id.location1) ImageView mLocation1;
    @Bind(R.id.location2) ImageView mLocation2;
    @Bind(R.id.location3) ImageView mLocation3;
    @Bind(R.id.location4) ImageView mLocation4;
    @Bind(R.id.map) ImageView mMap;
    @Bind(R.id.profileImage) ImageView mProfileImage;
    @Bind(R.id.userName) TextView mUserName;
    @Bind(R.id.beacon1Stats) TextView mBeacon1;
    @Bind(R.id.beacon2Stats) TextView mBeacon2;
    @Bind(R.id.beacon3Stats) TextView mBeacon3;
    @Bind(R.id.beacon4Stats) TextView mBeacon4;
    @Bind(R.id.rewardCount) TextView mRewardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen);
        ButterKnife.bind(this);
        getUserStats();
        bc1 = 0;
        bc2 = 0;
        bc3 = 0;
        bc4 = 0;
        rc = 0;
        currentUserStatus = 0;
        currentUserVisitCount = 0;
        timerExists = false;
        timeRemaining = 60000;
        initVisibility();
        if (getIntent().hasExtra("reward")){
            getSupportActionBar().setTitle("Proximity Timer Demo");
            mTextField1.setVisibility(View.VISIBLE);
            mTextField2.setVisibility(View.VISIBLE);
            mTextField1.setText("time until reward: ");
            mTextField2.setText(" " + timeRemaining / 1000 + " ");
        }
        if (getIntent().hasExtra("map")){
            getSupportActionBar().setTitle("Proximity Map Demo");
            mMap.setVisibility(View.VISIBLE);
        }
        if (getIntent().hasExtra("user")){
            getSupportActionBar().setTitle("Unique User Demo");
            mUserName.setText("userName");
            mRewardCount.setVisibility(View.VISIBLE);
            mBeacon1.setVisibility(View.VISIBLE);
            mBeacon2.setVisibility(View.VISIBLE);
            mBeacon3.setVisibility(View.VISIBLE);
            mBeacon4.setVisibility(View.VISIBLE);
            mUserName.setVisibility(View.VISIBLE);
            mProfileImage.setVisibility(View.VISIBLE);

        }
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<com.estimote.sdk.Beacon> list) {
                if (!list.isEmpty()) {
                    getBeacons();
                    Log.d("logging range", list.get(0).toString());
                    beaconMap(list.get(0).getMajor(), list.get(0).getMinor());
                    pushBeaconStats(list.get(0).getMajor(), list.get(0).getMinor());
                    if (!timerExists){
                        durationReward(timerExists);
                        timerExists = true;
                    }
                    Log.d("logging range", Constants.BEACON_UUID);
                    Log.d("Demo App: ", Constants.BEACON_UUID);
                } else {
                    if (timerExists){
                        durationReward(timerExists);
                        timerExists = false;
                    }
                }
            }
        });
        region = new Region("ranged region", UUID.fromString(Constants.BEACON_UUID), null, null);
    }

    public void beaconMap(int major, int minor){
        if (getIntent().hasExtra("map")){
            if ((major == BeaconMajor.beacon1)&&(minor == BeaconMinor.beacon1)){
                getVisits();
                mLocation1.setVisibility(View.VISIBLE);
                mLocation2.setVisibility(View.GONE);
                mLocation3.setVisibility(View.GONE);
                mLocation4.setVisibility(View.GONE);
            }
            if ((major == BeaconMajor.beacon2)&&(minor == BeaconMinor.beacon2)){
                getVisits();
                mLocation2.setVisibility(View.VISIBLE);
                mLocation1.setVisibility(View.GONE);
                mLocation3.setVisibility(View.GONE);
                mLocation4.setVisibility(View.GONE);
            }
            if ((major == BeaconMajor.beacon3)&&(minor == BeaconMinor.beacon3)){
                getVisits();
                mLocation3.setVisibility(View.VISIBLE);
                mLocation2.setVisibility(View.GONE);
                mLocation1.setVisibility(View.GONE);
                mLocation4.setVisibility(View.GONE);
            }
            if ((major == BeaconMajor.beacon4)&&(minor == BeaconMinor.beacon4)){
                getVisits();
                mLocation4.setVisibility(View.VISIBLE);
                mLocation2.setVisibility(View.GONE);
                mLocation3.setVisibility(View.GONE);
                mLocation1.setVisibility(View.GONE);
            }
        }
    }

    public void durationReward(boolean switcher) {
        if (!switcher){
            rewardTimer = new CountDownTimer(timeRemaining, 1000) {
                public void onTick(long millisUntilFinished) {
                    mTextField1.setText("time until reward: ");
                    mTextField2.setText(" " + millisUntilFinished / 1000 + " ");
                    timeRemaining = millisUntilFinished;
                }
                public void onFinish() {
                    mTextField2.setText("done!");
                    timeRemaining = 60000;
                    pushReward();
                    durationReward(false);
                }
            }.start();
        } else {
            rewardTimer.cancel();
        }
    }

    private void pushBeaconStats(int major, int minor){
        if ((major == BeaconMajor.beacon1)&&(minor == BeaconMinor.beacon1)){
            //// TODO: 8/11/17 update fidebase user beacon stats
            updateStats();
        }
        if ((major == BeaconMajor.beacon2)&&(minor == BeaconMinor.beacon2)){
            //// TODO: 8/11/17 update fidebase user beacon stats
            updateStats();
        }
        if ((major == BeaconMajor.beacon3)&&(minor == BeaconMinor.beacon3)){
            //// TODO: 8/11/17 update fidebase user beacon stats
            updateStats();
        }
        if ((major == BeaconMajor.beacon4)&&(minor == BeaconMinor.beacon4)){
            //// TODO: 8/11/17 update fidebase user beacon stats
            updateStats();
        }
    }

    private void pushReward(){
        //// TODO: 8/11/17 update fidebase user beacon stats
        updateStats();
    }

    private void updateStats(){

    }

    public void getUserStats(){

    }

    private void initVisibility(){
        mTextField1.setVisibility(View.GONE);
        mTextField2.setVisibility(View.GONE);
        mMap.setVisibility(View.GONE);
        mLocation1.setVisibility(View.GONE);
        mLocation2.setVisibility(View.GONE);
        mLocation3.setVisibility(View.GONE);
        mLocation4.setVisibility(View.GONE);
        mRewardCount.setVisibility(View.GONE);
        mBeacon1.setVisibility(View.GONE);
        mBeacon2.setVisibility(View.GONE);
        mBeacon3.setVisibility(View.GONE);
        mBeacon4.setVisibility(View.GONE);
        mUserName.setVisibility(View.GONE);
        mProfileImage.setVisibility(View.GONE);
    }

    @Override
    protected void onPause(){
        beaconManager.stopRanging(region);
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                beaconManager.startRanging(region);
            }
        });
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    private void getBeacons() {
        final ApiService serverService = new ApiService();
        serverService.getBeacons(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mBeacons = serverService.processBeaconResults(response);
                ChosenActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] beaconNames = new String[mBeacons.size()];
                        String[] beaconIds = new String[mBeacons.size()];
                        String[] beaconIdentities = new String[mBeacons.size()];
                        for (int i = 0; i < beaconNames.length; i++) {
                            beaconNames[i] = mBeacons.get(i).getBeaconName();
                            beaconIds[i] = mBeacons.get(i).getBeaconId();
                            beaconIdentities[i] = mBeacons.get(i).getBeaconIdentityNumber();
                            Log.d("beaconName", beaconNames[i]);
                            Log.d("beaconId", beaconIds[i]);
                            Log.d("beaconIdentity", beaconIdentities[i]);
                        }
                    }
                });
            }
        });
    }
    private void getVisits() {
        final ApiService serverService = new ApiService();
        serverService.getVisits(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mVisits = serverService.processVisitResults(response);
                ChosenActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] visitDay = new String[mVisits.size()];
                        String[] visitEnter = new String[mVisits.size()];
                        String[] visitExit = new String[mVisits.size()];
                        String[] visitBeacon = new String[mVisits.size()];
                        String[] visitUser = new String[mVisits.size()];
                        for (int i = 0; i < visitDay.length; i++) {
                            visitDay[i] = mVisits.get(i).getDay();
                            visitEnter[i] = mVisits.get(i).getEnterTime();
                            visitExit[i] = mVisits.get(i).getExitTime();
                            visitUser[i] = mVisits.get(i).getUserId();
                            visitBeacon[i] = mVisits.get(i).getBeaconId();
                            Log.d("visitDay", visitDay[i]);
                            Log.d("visitEnter", visitEnter[i]);
                            Log.d("visitExit", visitExit[i]);
                            Log.d("visitUser", visitUser[i]);
                            Log.d("visitBeacon", visitBeacon[i]);
                        }
                    }
                });
            }
        });
    }
}

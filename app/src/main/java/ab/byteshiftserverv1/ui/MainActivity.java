package ab.byteshiftserverv1.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsChecker;

import java.io.IOException;
import java.util.ArrayList;

import ab.byteshiftserverv1.R;
import ab.byteshiftserverv1.model.User;
import ab.byteshiftserverv1.service.ApiService;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public ArrayList<User> mUsers = new ArrayList<>();
    private ProgressDialog mDialog;
    String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.landingLogo)
    ImageView mLandingLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getUsers();
        openAnimationLogo();
        createProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getUsers() {
        final ApiService serverService = new ApiService();
        serverService.getUsers(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mUsers = serverService.processUserResults(response);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] userNames = new String[mUsers.size()];
                        String[] userIds = new String[mUsers.size()];
                        String[] userEmail = new String[mUsers.size()];
                        String[] userStatus = new String[mUsers.size()];
                        for (int i = 0; i < userNames.length; i++) {
                            userNames[i] = mUsers.get(i).getFirstName() + " " + mUsers.get(i).getLastName();
                            userIds[i] = mUsers.get(i).getUserId();
                            userEmail[i] = mUsers.get(i).getUserEmail();
                            userStatus[i] = mUsers.get(i).getUserStatus();
                            Log.d("userName", userNames[i]);
                            Log.d("userId", userIds[i]);
                            Log.d("userEmail", userEmail[i]);
                            Log.d("userStatus", userStatus[i]);
                        }
                    }
                });
            }
        });
    }

    private void createProgressDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Authorizing...");
        mDialog.setMessage("Checking user status...");
        mDialog.setCancelable(false);
    }

    private void toSelection() {
        Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
        startActivity(intent);
    }

    private void openAnimationLogo(){
        mLandingLogo.animate().setDuration(2000).alpha(1f);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toSelection();
            }
        }, 2500);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }
}

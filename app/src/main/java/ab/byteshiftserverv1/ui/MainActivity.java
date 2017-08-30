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

import ab.byteshiftserverv1.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mDialog;
    String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.landingLogo)
    ImageView mLandingLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        openAnimationLogo();
        createProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
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

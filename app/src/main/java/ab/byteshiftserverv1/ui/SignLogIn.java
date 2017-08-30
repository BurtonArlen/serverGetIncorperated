package ab.byteshiftserverv1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ab.byteshiftserverv1.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SignLogIn extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.emailEnter) EditText mEmailEnter;
    @Bind(R.id.passwordEnter) EditText mPasswordEnter;
    @Bind(R.id.passwordConfirm) EditText mPasswordConfirm;
    @Bind(R.id.signLogInButton) Button mSignLogButton;
    @Bind(R.id.noAccountText1) TextView mNoAccountText1;
    @Bind(R.id.noAccountText2) TextView mNoAccountText2;
    @Bind(R.id.titleText) TextView mTitleText;
    private boolean authStateBoolean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_log_in);
        ButterKnife.bind(this);
        logInMode();
        setClickListeners();
    }
    
    public void setClickListeners(){
        mNoAccountText1.setOnClickListener(this);
        mNoAccountText2.setOnClickListener(this);
        mSignLogButton.setOnClickListener(this);
    }
    
    public void logInMode(){
        authStateBoolean = true;
        mPasswordConfirm.setVisibility(View.GONE);
        mTitleText.setText("Log In");
        mNoAccountText1.setText("Don't have an account?");
        mNoAccountText2.setText("Sign up here!");
    }
    
    public void signUpMode(){
        authStateBoolean = false;
        mPasswordConfirm.setVisibility(View.VISIBLE);
        mTitleText.setText("Sign Up");
        mNoAccountText1.setText("Already have an account?");
        mNoAccountText2.setText("Log in here!");
    }
    
    public void checkAuth(){
        String emailVal = mEmailEnter.getText().toString();
        String passwordVal = mPasswordEnter.getText().toString();
        String passwordConfirmVal = mPasswordConfirm.getText().toString();
        if (authStateBoolean){
            if (emailVal.equalsIgnoreCase("")){
                mEmailEnter.setError("Please Enter Your Email");
            }
            if (passwordVal.equalsIgnoreCase("")){
                mPasswordEnter.setError("Please Enter Your Password");
            }
            // TODO: 8/30/17 send email and password to sever for auth 
            // TODO: 8/30/17 return user data and send to Main Activity
        } else {
            if (emailVal.equalsIgnoreCase("")){
                mEmailEnter.setError("Please Enter Your Email");
            }
            if (passwordVal.equalsIgnoreCase("")){
                mPasswordEnter.setError("Please Enter Your Password");
            }
            if (passwordConfirmVal.equalsIgnoreCase("")){
                mPasswordConfirm.setError("Please Confirm Your Password");
            }
            // TODO: 8/30/17 send email, password, and password confirmation to server for user creation 
            // TODO: 8/30/17 return user data and send to Tutorial Activity 
        }
    }
    
    @Override
    public void onClick(View v){
        if ((v == mNoAccountText1)||(v == mNoAccountText2)){
            signUpMode();
        }
        if (v == mSignLogButton){
            checkAuth();
        }
    }
}

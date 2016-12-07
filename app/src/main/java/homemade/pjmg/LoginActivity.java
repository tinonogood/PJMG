package homemade.pjmg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //Login View
    private EditText mEdtLoginUser;
    private EditText mEdtLoginPasswd;
    private Button mBtnLogin;
    private Button mBtnSignUp;

    //Sign Up View
    private EditText mEdtSignUser;
    private EditText mEdtSignPasswd;
    private EditText mEdtSignEMail;
    private Button mBtnSubmit;
    private Button mBtnRefill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(!checkLocalUsage()){
            setLoginView();
        }
        else{
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(it);
            this.finish();
        }

    }

    private void setLoginView(){
        setContentView(R.layout.activity_login);
        mEdtLoginUser = (EditText) findViewById(R.id.edt_user);
        mEdtLoginPasswd = (EditText) findViewById(R.id.edt_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnSignUp = (Button) findViewById(R.id.btn_sign);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*TODO
                * 1.Link Database and check whether the account is correct or not
                *   if(correct)
                *       load user profile
                *       make local database
                * */
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSignUpView();
            }
        });
    }

    private void setSignUpView(){
        setContentView(R.layout.sign_up);

        mEdtSignUser = (EditText) findViewById(R.id.edt_sign_user);
        mEdtSignPasswd = (EditText) findViewById(R.id.edt_sign_password);
        mEdtSignEMail = (EditText) findViewById(R.id.edt_sign_email);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnRefill = (Button) findViewById(R.id.btn_refill);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*  TODO
                *   Link server database to check the usage of the account
                *   Duplicate name and email are not allowed.
                *
                *   if(create account success)
                *       login and change contentView to MainActivity
                * */
            }
        });

        mBtnRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtSignUser.setText("");
                mEdtSignPasswd.setText("");
                mEdtSignEMail.setText("");

                Log.d("Sign Up", "clear all");
            }
        });

        localTest();
    }

    private boolean checkLocalUsage(){
        /*  Use SharePreference to store user profile.
        *   Hence, we check ACCOUNT value in user to known whether local data is exist or not
        * */
        boolean hasLocalFile;

        SharedPreferences localLogin = getSharedPreferences("user", MODE_PRIVATE);
        hasLocalFile = localLogin.getString("ACCOUNT", null) != null;

        return hasLocalFile;
    }

    private void localTest(){
        /* Build local test account */

        LinearLayout layout = (LinearLayout) findViewById(R.id.sign_up_layout);
        Button btnLocal = new Button(this);
        btnLocal.setText("Local");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEdtSignUser.getText().length() == 0 || mEdtSignPasswd.getText().length() == 0){
                    Toast.makeText(LoginActivity.this,"Account or Password cannot be blank.",Toast.LENGTH_SHORT).show();
                }
                else {

                    SharedPreferences userData = getSharedPreferences("user", MODE_PRIVATE);
                    userData.edit()
                            .putString(User.ACCOUNT.toString(), mEdtSignUser.getText().toString())
                            .putString(User.PASSWORD.toString(), mEdtSignPasswd.getText().toString())
                            .commit();

                    if (mEdtSignEMail.getText().length() != 0) {
                        userData.edit()
                                .putString(User.EMAIL.toString(), mEdtSignEMail.getText().toString())
                                .commit();
                    }

                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                    LoginActivity.this.finish();
                }
            }
        });

        layout.addView(btnLocal,params);
    }
}

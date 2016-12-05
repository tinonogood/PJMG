package homemade.pjmg;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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
                /*TODO
                * 1.Link Database and check whether the account has already been used or not;
                *
                *   if(account not exist)
                *       make account
                *   if(local database not exist)
                *       make local database
                *       build user profile
                * */
            }
        });
    }

    private void setSignUpView(){
        setContentView(R.layout.sign_up);

        mEdtSignUser = (EditText) findViewById(R.id.edt_sign_user);
        mEdtLoginPasswd = (EditText) findViewById(R.id.edt_sign_password);
        mEdtSignEMail = (EditText) findViewById(R.id.edt_sign_email);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnRefill = (Button) findViewById(R.id.btn_refill);
    }

    private boolean checkLocalUsage(){
        /*
        final String DATABASE_NAME = getResources().getString(R.string.database_name);
        final String USER_TABLE_NAME = getResources().getString(R.string.client_table_name);

        LocalDBConnection clientDbOpenHelper = new LocalDBConnection(getApplicationContext(), DATABASE_NAME , null, 1);
        SQLiteDatabase clientDB = clientDbOpenHelper.getWritableDatabase();
        Log.d("Database","clientDB - " + clientDB);

        Cursor cursor = clientDB.query(USER_TABLE_NAME,null,null,null,null,null,null);

        Log.d("Database", "cursor -  " + cursor);

        cursor.moveToFirst();

        Log.d("Database", "data count: " + cursor.getCount());

        clientDB.close();
        */

        /*  Use SharePreference to store user profile.
        *   Hence, we check ACCOUNT value in user to known whether local data is exist or not
        * */
        boolean hasLocalFile = false;

        SharedPreferences localLogin = getSharedPreferences("user", MODE_PRIVATE);

        if(localLogin.getString("ACCOUNT",null) == null){
            hasLocalFile = false;
        }
        else
            hasLocalFile = true;

        return hasLocalFile;
    }
}

package homemade.pjmg;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView mTxTAccount;
    TextView mTxtEmail;
    ListView listView;

    private String userAccount;
    private String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();
        initDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_profile:
                SharedPreferences userData = getSharedPreferences("user",MODE_PRIVATE);
                String user = userData.getString(User.ACCOUNT.toString(),null);
                String email = userData.getString(User.EMAIL.toString(),null);

                AlertDialog.Builder profile = new AlertDialog.Builder(MainActivity.this);
                profile.setTitle("User");
                profile.setMessage(user + "\n" + email);
                profile.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                profile.show();

                return true;

            case R.id.menu_item_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Logout");
                dialog.setMessage("Sure to Logout?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /* Clear local user profile and task DB */
                        SharedPreferences userData = getSharedPreferences(User.PJMG_DB.toString(), MODE_PRIVATE);
                        userData.edit()
                                .clear()
                                .commit();

                        Intent it = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(it);
                        MainActivity.this.finish();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                return true;

            default:
                return false;
        }
    }

    private void initActionBar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("主頁");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));

        setSupportActionBar(toolbar);

        ActionBar actBar;
        /* If actBar is not null, set HomeButton*/
        if( (actBar = getSupportActionBar()) !=null ) {
            actBar.setHomeButtonEnabled(true);
            actBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDrawer(){
        /* Handle actionbar toggle */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        /* Set user info in the navi drawer header */

        mTxTAccount = (TextView)findViewById(R.id.drawer_id);
        mTxtEmail = (TextView)findViewById(R.id.drawer_email);




    }

    private void initContentView(){

    }
}

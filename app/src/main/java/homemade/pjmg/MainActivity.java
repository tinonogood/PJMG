package homemade.pjmg;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    /* navigation drawer */
    private DrawerLayout drawerLayout;
    private TextView mTxTAccount;
    private TextView mTxtEmail;
    private ListView listView;

    /* content tabs */
    private TabLayout mTabs;
    private ViewPager mViewPager;

    private String userAccount;
    private String emailAddress;
    private ArrayList<HashMap<String,String>> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();
        initDrawer();
        initTabsView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

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
        toolbar.setTitle("Main");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this,R.color.white));

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

        SharedPreferences sharedPreferences = getSharedPreferences(User.PJMG_DB.toString(),MODE_PRIVATE);

        userAccount = sharedPreferences.getString(User.ACCOUNT.toString(),"");
        emailAddress = sharedPreferences.getString(User.EMAIL.toString(),"");

        mTxTAccount.setText(userAccount);
        mTxtEmail.setText(emailAddress);

        /* Initialize list */
        listView = (ListView) findViewById(R.id.drawer_list);

        projectList = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> item = new HashMap<>();
        item.put("icon",Integer.toString(android.R.drawable.ic_menu_add));
        item.put("title","Add Project");
        projectList.add(item);

        for(int i=0;i<5;i++){
            HashMap <String,String> project = new HashMap<>();
            project.put("icon",Integer.toString(android.R.drawable.star_off));
            project.put("title","Project " + i);
            projectList.add(project);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,projectList,R.layout.listview_layout,new String[]{"icon","title"},new int[]{R.id.imgIcon,R.id.txtItem});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){

                }
                else{
                    setTitle(projectList.get(i).get("title"));
                }
                drawerLayout.closeDrawers();
            }

            private void setTitle(String title){
                getSupportActionBar().setTitle(title);
            }

        });


    }

    private void initTabsView(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            private final String [] TABSNAME = {"Item 1", "Item 2", "Item 3", "Item 4"};

            @Override
            public int getCount() {
                return TABSNAME.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TABSNAME[position];
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = getLayoutInflater().inflate(R.layout.viewerpager,container,false);
                TextView title = (TextView) view.findViewById(R.id.item_title);
                title.setText(String.valueOf(position+1));
                container.addView(view);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }


        });

        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mViewPager);

    }

}

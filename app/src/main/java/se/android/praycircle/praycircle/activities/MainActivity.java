package se.android.praycircle.praycircle.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import se.android.praycircle.praycircle.R;
import se.android.praycircle.praycircle.adapters.PagerAdapter;
import se.android.praycircle.praycircle.helpers.HelperClass;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAnalytics mFaAnalytics;
    private FirebaseAuth auth;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFaAnalytics = FirebaseAnalytics.getInstance(this);
        auth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            if(!HelperClass.isProfileDone(this)){
                startActivity(new Intent(this, CreateUserProfileActivity.class));
            }
        }



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_prayers)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pray_groups)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.prayer_watch)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Go to create myPrayers activity..
            }
        });


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("TAB_TEST", " tab: " + tab.getPosition());
                if(tab.getPosition()== 0){
                    fab.setVisibility(View.VISIBLE);
                }else{
                    fab.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public Dialog createProfileDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = this.getLayoutInflater().inflate(R.layout.activity_create_user_profile, null);

        builder.setCancelable(true);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setView(view);
        return alertDialog;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:

                break;

            case R.id.action_logout:
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.edit_profile:
                startActivity(new Intent(getApplicationContext(), CreateUserProfileActivity.class));
                break;

            case  R.id.nav_gallery:

                break;

            case R.id.nav_slideshow:

                break;

            case R.id.nav_manage:

                break;

            case R.id.nav_share:

                break;

            case R.id.nav_send:

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

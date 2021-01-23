package com.example.financemanagementapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.material.navigation.NavigationView;

public class MainDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //firebase auth object
    //private FirebaseAuth firebaseAuth;

    //view objects
    //private TextView textViewUserEmail;
    //private Button buttonLogout;

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //FOR FRAGMENTS
    private Fragment fragmentCategories;
    private Fragment fragmentSMSIntegration;
    private Fragment fragmentPayablesAndReceivables;

    //FOR DATAS
    private static final int FRAGMENT_CATEGORIES = 0;
    private static final int FRAGMENT_SMSINTEGRATION = 1;
    private static final int FRAGMENT_PAYABLESANDRECEIVABLES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        /*
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        //textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        //buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if logout is pressed
                if(view == buttonLogout){
                    //logging out the user
                    firebaseAuth.signOut();
                    //closing activity
                    finish();
                    //starting login activity
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });*/

        // Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        // Show First Fragment
        //this.showFirstFragment();

    }

    @Override
    public void onBackPressed() {
        //Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.mainDashboard:
                Intent signUp = new Intent(getApplicationContext(), MainDashboard.class);
                startActivity(signUp);
                break;
            case R.id.categories:
                this.showFragment(FRAGMENT_CATEGORIES);
                break;
            case R.id.SMSIntegration:
                this.showFragment(FRAGMENT_SMSINTEGRATION);
                break;
            case R.id.payablesAndReceivables:
                this.showFragment(FRAGMENT_PAYABLESANDRECEIVABLES);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    //Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    // Show first fragment when activity is created
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            // Show News Fragment
            //this.showFragment(FRAGMENT_NEWS);
            // Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    // Show fragment according an Identifier

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_CATEGORIES :
                this.showCategoriesFragment();
                break;
            case FRAGMENT_SMSINTEGRATION:
                this.showSMSIntegrationFragment();
                break;
            case FRAGMENT_PAYABLESANDRECEIVABLES:
                this.showPayablesAndReceivablesFragment();
                break;
            default:
                break;
        }
    }

    // ---

    // Create each fragment page and show it

    private void showCategoriesFragment(){
        if (this.fragmentCategories == null) this.fragmentCategories = CategoriesFragment.newInstance();
        this.startTransactionFragment(this.fragmentCategories);
    }

    private void showSMSIntegrationFragment(){
        if (this.fragmentSMSIntegration == null) this.fragmentSMSIntegration = SMSIntegrationFragment.newInstance();
        this.startTransactionFragment(this.fragmentSMSIntegration);
    }

    private void showPayablesAndReceivablesFragment(){
        if (this.fragmentPayablesAndReceivables == null) this.fragmentPayablesAndReceivables = PayablesAndReceivablesFragment.newInstance();
        this.startTransactionFragment(this.fragmentPayablesAndReceivables);
    }

    // ---

    // Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
}
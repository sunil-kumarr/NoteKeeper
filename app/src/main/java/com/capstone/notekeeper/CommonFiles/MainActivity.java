package com.capstone.notekeeper.CommonFiles;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.notekeeper.AskQuestionModule.AddQuestionBottomSheet;
import com.capstone.notekeeper.AskQuestionModule.BlogQueryFragment;
import com.capstone.notekeeper.BuyAndSellModule.BuyProductListFragment;
import com.capstone.notekeeper.StudyLibararyModule.HomeFragment;
import com.capstone.notekeeper.QuizModule.PlacementQuizFragment;
import com.capstone.notekeeper.BuyAndSellModule.SellFragment;
import com.capstone.notekeeper.StudyLibararyModule.UploadNotesFragment;
import com.capstone.notekeeper.R;
import com.capstone.notekeeper.authentication.LoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , AddQuestionBottomSheet.SendQuestionToActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private ActionBar mActionBar;
    private int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);

        } else {
            getLastLocationOfUser();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        displaySelectedScreen(R.id.nav_home);
        if (currentFirebaseUser != null) {
            View headerView = navigationView.getHeaderView(0);
            TextView userName = headerView.findViewById(R.id.user_name);
            userName.setText(currentFirebaseUser.getDisplayName());
            userName.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocationOfUser();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void getLastLocationOfUser() {
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                wayLatitude = location.getLatitude();
                wayLongitude = location.getLongitude();

            }
        });
    }

    private boolean displaySelectedScreen(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_logout:
                signout();
                break;
            case R.id.nav_home:
                mActionBar.setTitle("NoteKeeper");
                fragment = new HomeFragment();
                break;
            case R.id.nav_buy:
                mActionBar.setTitle("Buy Products");
                fragment = new BuyProductListFragment();
                break;
            case R.id.nav_sell:
                mActionBar.setTitle("Post products");
                fragment = new SellFragment();
                break;
            case R.id.nav_brainer:
                mActionBar.setTitle("Practice Quiz");
                fragment = new PlacementQuizFragment();
                break;
            case R.id.nav_upload:
                mActionBar.setTitle("Upload Study Materials");
                fragment = new UploadNotesFragment();
                break;
            case R.id.nav_ask_quora:
                mActionBar.setTitle("Ask Questions");
                fragment = new BlogQueryFragment();
                break;
            case R.id.nav_Share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "www.capstone.com");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share Us"));
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                break;
            case R.id.nav_faq:
                startActivity(new Intent(MainActivity.this, FAQActivity.class));
                break;
            default:
//                    Toast.makeText(this, "dee", Toast.LENGTH_SHORT).show();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "frag");
            ft.addToBackStack(null);
            ft.commit();
        }
        drawerLayout.closeDrawers();
        return false;
    }

    private void signout() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure?")
                .setTitle("Logout!")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface pDialogInterface, int pI) {
                        getSharedPreferences("login_data", MODE_PRIVATE)
                                .edit()
                                .putBoolean("login", false)
                                .putBoolean("phone", false)
                                .apply();
                        auth.signOut();
                    }
                }).create().show();

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        return displaySelectedScreen(menuItem.getItemId());
    }

    @Override
    public void onQuestionComplete(String inputText) {
        BlogQueryFragment blogQueryFragment = (BlogQueryFragment) getSupportFragmentManager().findFragmentByTag("frag");
        if (blogQueryFragment != null) {
            blogQueryFragment.sendQuestion(inputText);
        } else {
            Toast.makeText(this, "Question Not Added", Toast.LENGTH_SHORT).show();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

package com.capstone.notekeeper.Activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.notekeeper.Adapter.NotesAdapter;
import com.capstone.notekeeper.Fragments.AddInputDialogFragment;
import com.capstone.notekeeper.Fragments.BlogQueryFragment;
import com.capstone.notekeeper.Fragments.BuyProductFragment;
import com.capstone.notekeeper.Fragments.HomeFragment;
import com.capstone.notekeeper.Fragments.ProfileFragment;
import com.capstone.notekeeper.Fragments.SellFragment;
import com.capstone.notekeeper.Fragments.UploadNotesFragment;
import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;
import com.capstone.notekeeper.authentication.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
  , AddInputDialogFragment.AddInputDialogListener , NotesAdapter.DownloadData {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private TextView userEmail;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        };

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout ,R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        displaySelectedScreen(R.id.nav_home);
        if(currentFirebaseUser!=null)
        {
            View headerView = navigationView.getHeaderView(0);
            userEmail = headerView.findViewById(R.id.user_email_id);
            userEmail.setText(currentFirebaseUser.getEmail());
        }
    }

    private boolean displaySelectedScreen( int id) {
        Fragment fragment = null;
        switch(id)
        {
            case R.id.nav_logout:
                  signout();
                  break;
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_buy:
                fragment = new BuyProductFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_sell:
                fragment = new SellFragment();
                break;
            case R.id.nav_brainer:
                startActivity(new Intent(MainActivity.this, BrainPrepActivity.class));
                 break;
            case R.id.nav_upload:
                 fragment = new UploadNotesFragment();
                 break;
            case R.id.nav_ask_quora:
                fragment = new BlogQueryFragment();
                break;
            default:
//                    Toast.makeText(this, "dee", Toast.LENGTH_SHORT).show();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment,"frag");
            ft.commit();
        }
        drawerLayout.closeDrawers();
        return false;
    }

    private void signout() {
        auth.signOut();
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
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
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
        return  displaySelectedScreen(menuItem.getItemId());
    }
    @Override
    public long DownloadNotes(NotesDetails fileUrl) {
        long output = 0;
        Toast.makeText(this, "Download Started...", Toast.LENGTH_SHORT).show();
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl.getFileLink()));
        request.setTitle(fileUrl.getTitle());
        request.setDescription(fileUrl.getDescription());
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "NoteKeeper");
        if (downloadManager != null) {
            output = downloadManager.enqueue(request);
        }
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadcomplete,intentFilter);
        return output;
    }
    BroadcastReceiver downloadcomplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Toast.makeText(context, "Download Complete.", Toast.LENGTH_SHORT).show();
            }
        }
    } ;
    @Override
    public void onFinishAddInput(String inputText) {
        BlogQueryFragment blogQueryFragment =(BlogQueryFragment) getSupportFragmentManager().findFragmentByTag("frag");
        if (blogQueryFragment != null) {
            blogQueryFragment.getQuestion(inputText);
        }
        else{
            Toast.makeText(this, "Question Not Added", Toast.LENGTH_SHORT).show();
        }
    }
}

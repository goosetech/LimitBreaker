package goosetech.limitbreaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private static String TAG = "debugging_MainActivity";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String uid;
    private String name, email, username;
    TextView greetings, emailView, usernameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        Get user info from database. This is done by making attaching a ValueEventListener to
        the database reference
        */
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Make ValueEventListener
//        Log.w(TAG,"Making event listener");
        Log.w(TAG,"Adding event listener to database ref");
        mDatabase.child("profiles").child("users").child(uid).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG,"Getting datasnapshot...");
                        name = dataSnapshot.child("fullName").getValue(String.class);
                        email = dataSnapshot.child("email").getValue(String.class);
                        username = dataSnapshot.child("username").getValue(String.class);

                        greetings = findViewById(R.id.greet);
                        String text = getResources().getString(R.string.hi_1_s, username);
                        emailView.setText(email);
                        greetings.setText(text);
                        usernameView.setText(name);
//                      Log.w(TAG,"Got datasnapshot. Updating navigation menu");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG,"ValueEventListener error!"+databaseError.getMessage());
                    }
                });
        //It takes a bit of time for the variables to be stored

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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Init menu views
        ImageView profilePhoto = findViewById(R.id.nav_menu_photo);
        usernameView = findViewById(R.id.nav_menu_name);
        emailView = findViewById(R.id.nav_menu_email);
        Log.w(TAG,"Updating menu");
//        usernameView.setText(name);
//        emailView.setText(email);
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.update_profile) {
            // Handle the camera action
        } else if (id == R.id.location_history) {

        } else if (id == R.id.activity_log) {

        }  else if (id == R.id.nav_share) {

        }
        else if (id == R.id.sign_out){
            mAuth.getInstance().signOut();
            Log.w(TAG,"Signing out!");
            Intent login = new Intent (this,LoginActivity.class);
            startActivity(login);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

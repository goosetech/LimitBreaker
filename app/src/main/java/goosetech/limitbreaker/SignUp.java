package goosetech.limitbreaker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    final private static String TAG = "debugging_SignUp";
    private  EditText fullname,username, emailView,password1,password2,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Init firebase stuff
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Get views that are needed
        fullname = findViewById(R.id.fullname_edit_text);
        username = findViewById(R.id.username_edit_text);
        emailView = findViewById(R.id.email_edit_text);
        password1 = findViewById(R.id.password1_edit_text);
        password2 = findViewById(R.id.password2_edit_text);
        phone = findViewById(R.id.phone_edit_text);
        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 = password1.getText().toString();
                String pass2 = password2.getText().toString();
                String emailText = emailView.getText().toString();
                Log.w(TAG,pass1+" "+pass2);
                //Verify that passwords match
                if(pass1.equals(pass2)){
                    Toast.makeText(SignUp.this,"Adding new user...",
                            Toast.LENGTH_LONG).show();
                    String fname = fullname.getText().toString();
                    String uname = username.getText().toString();
                    String phoneNum = phone.getText().toString();
                    user newUser = new user(fname,uname,emailText,phoneNum);
                    createUser(emailText,pass1,newUser);
                }
                else{
                    Toast.makeText(SignUp.this,"Passwords do not match!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void createUser(String email, String password, final user newUser) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.w(TAG, "User created successfully");
                            //Now that user is made store info into database
                            Log.w(TAG,"Attempting to store user...");
                            mDatabase.child("profiles").child("users").child(mAuth.getUid()).setValue(newUser);
                            Log.w(TAG,"User stored");
                            Toast.makeText(SignUp.this, "User created!",
                                    Toast.LENGTH_SHORT).show();
                            //Set up a timeout to give user chance to view Toast
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                           Intent mainActivity = new Intent(
                                                   SignUp.this,MainActivity.class);
                                           startActivity(mainActivity);
                                        }
                                    },
                                    300);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "User was not created"+ task.getException());
                            Toast.makeText(SignUp.this, "Error registering your data",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

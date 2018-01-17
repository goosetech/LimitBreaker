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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    final private static String TAG = "debugging_SignUp";
    private  EditText fullname,username, emailView,password1,password2,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
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
                //Verify that passwords match
                Log.w(TAG,pass1+" "+pass2);
                if(pass1.equals(pass2)){
                    Toast.makeText(SignUp.this,"Adding new user...",
                            Toast.LENGTH_LONG).show();
                    String fname = fullname.getText().toString();
                    String uname = username.getText().toString();
                    String phoneNum = phone.getText().toString();
                    createUser(fname,uname,emailText,pass1,phoneNum);
                }
                else{
                    Toast.makeText(SignUp.this,"Passwords do not match!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void createUser(String fullname, String username, String email,
                           String password, String phoneNum) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Go back to sign in activity
                            Toast.makeText(SignUp.this, "User created!",
                                    Toast.LENGTH_SHORT).show();
                            Intent signIn = new Intent(SignUp.this, LoginActivity.class);
                            startActivity(signIn);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        //Now that user is made store ser info into database
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Set reference to fullname node and store value
        DatabaseReference myRef = database.getReference("fullname");
        myRef.setValue(fullname);
        //Set reference to username node and store value
        myRef = database.getReference("username");
        myRef.setValue(username);
        //Set reference to email node and store value
        myRef = database.getReference("email");
        myRef.setValue(email);
        //Set reference to phoneNum node and store value
        myRef = database.getReference("phoneNum");
        myRef.setValue(phoneNum);
    }
}

package sar.tollsy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText Editemail;
    private EditText EditPassword;
    private TextView Signin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            //profile user
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Editemail = (EditText) findViewById(R.id.Editemail);
        EditPassword = (EditText) findViewById(R.id.EditPassword);
        Signin = (TextView) findViewById(R.id.Signin);
        buttonRegister.setOnClickListener(this);
        Signin.setOnClickListener(this);
    }


    private void registerUser() {
        String email = Editemail.getText().toString().trim();
        String password = EditPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));}
                        else{
                            Toast.makeText(MainActivity.this,"Could not Register",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister)
            registerUser();
       if (view == Signin) {
           startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
package sar.tollsy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonsignin;
    public Context context;
    private EditText EdittextEmail;
    private EditText EdittextPassword;
    private TextView Textviewsignup;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            //profile user
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        EdittextEmail = (EditText) findViewById(R.id.EdittextEmail);
        EdittextPassword = (EditText) findViewById(R.id.EdittextPassword);
        buttonsignin = (Button) findViewById(R.id.buttonsignin);
        Textviewsignup = (TextView) findViewById(R.id.Textviewsignup);

        buttonsignin.setOnClickListener(this);
        Textviewsignup.setOnClickListener(this);

    }
        private void userlogin()
        {
            String email = EdittextEmail.getText().toString().trim();
            String password = EdittextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //Start Profile Activity
                                Toast.makeText(LoginActivity.this,"Signed in",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),Features.class));
                            }
                        }
                    });
        }

    @Override
    public void onClick(View view) {
        if(view == buttonsignin) {
            userlogin();
        }
        if(view == Textviewsignup)
        {
            finish();
            Intent main = new Intent(context,MainActivity.class);
            context.startActivity(main);
        }
    }
}

package com.tereshkoff.passwordmanager.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.R;

public class SignupActivity extends Activity {

    private EditText usernameEditText2;
    private EditText passwordEditText2;
    private Button signupButton;
    private TextView loginLink;

    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        usernameEditText2 = (EditText) findViewById(R.id.usernameEditText2);
        passwordEditText2 = (EditText) findViewById(R.id.passwordEditText2);
        signupButton = (Button) findViewById(R.id.signupButton);
        loginLink = (TextView) findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = usernameEditText2.getText().toString();
        String password = passwordEditText2.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 700);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = usernameEditText2.getText().toString();
        String password = passwordEditText2.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            usernameEditText2.setError("at least 3 characters");
            valid = false;
        } else {
            usernameEditText2.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEditText2.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordEditText2.setError(null);
        }

        return valid;
    }

}

package com.tereshkoff.passwordmanager.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.AES.UtilsEncryption;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.json.JsonFilesWorker;
import com.tereshkoff.passwordmanager.utils.Constants;

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

        //usernameEditText2 = (EditText) findViewById(R.id.usernameEditText2);
        passwordEditText2 = (EditText) findViewById(R.id.passwordEditText2);
        signupButton = (Button) findViewById(R.id.signupButton);
        //loginLink = (TextView) findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        /*loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });*/

        passwordEditText2.setTransformationMethod(null);
        passwordEditText2.setSelection(passwordEditText2.getText().length());
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
        progressDialog.setMessage("Создание аккаунта...");
        progressDialog.show();

        String password = passwordEditText2.getText().toString();

        JsonFilesWorker.writeToFile(Constants.MPW_FILENAME, UtilsEncryption.encrypt(password));

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if (validate())
                        {
                            onSignupSuccess();
                        }

                        progressDialog.dismiss();
                    }
                }, 700);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); // if login success
        SharedPreferences.Editor e = preferences.edit(); // for test
        e.putBoolean("hasVisited", true);
        e.commit();

        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Ошибка при регистрации.", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public boolean validate() {
        boolean valid = true;

        String password = passwordEditText2.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEditText2.setError("Пароль должен быть от 1 до 10 символов!");
            valid = false;
        } else {
            passwordEditText2.setError(null);
        }

        return valid;
    }

}

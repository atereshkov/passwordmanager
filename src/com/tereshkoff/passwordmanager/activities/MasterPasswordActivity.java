package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.tereshkoff.passwordmanager.AES.UtilsEncryption;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.json.JsonFilesWorker;
import com.tereshkoff.passwordmanager.mail.RetreiveMailTask;
import com.tereshkoff.passwordmanager.utils.ConnectionDetector;
import com.tereshkoff.passwordmanager.utils.Constants;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MasterPasswordActivity extends Activity {

    private EditText newPasswordEdit;
    private EditText newPasswordEdit2;
    private EditText oldPasswordEdit;
    private CheckBox sendEmailCheckBox;
    private EditText emailEdit;
    private Button changePasswordButton;

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = this;
    String rec, subject, textMessage;

    private boolean sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_password_activity);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        newPasswordEdit = (EditText) findViewById(R.id.newPasswordEdit);
        newPasswordEdit2 = (EditText) findViewById(R.id.newPasswordEdit2);
        oldPasswordEdit = (EditText) findViewById(R.id.oldPasswordEdit);
        sendEmailCheckBox = (CheckBox) findViewById(R.id.sendEmailCheckBox);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);

        emailEdit.setVisibility(View.GONE);
        sendEmail = false;
        emailEdit.setEnabled(false);
        sendEmailCheckBox.setEnabled(false);

        sendEmailCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    emailEdit.setVisibility(View.VISIBLE);
                    sendEmail = true;
                }
                else
                {
                    emailEdit.setVisibility(View.GONE);
                    sendEmail = false;
                }
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPassword = UtilsEncryption.decrypt(JsonFilesWorker.readFile(Constants.PWDIRECTORY, Constants.MPW_FILENAME));

                if (!isEmpty(newPasswordEdit) && !isEmpty(newPasswordEdit2) && !isEmpty(oldPasswordEdit))
                {
                    if (newPasswordEdit.getText().toString().equals(newPasswordEdit2.getText().toString()))
                    {
                        if (oldPassword != null && oldPassword.equals(oldPasswordEdit.getText().toString()))
                        {
                            JsonFilesWorker.writeToFile(Constants.MPW_FILENAME,
                                    UtilsEncryption.encrypt(newPasswordEdit.getText().toString()));

                            Toast.makeText(getApplicationContext(), "Пароль успешно изменен!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Старый пароль неверен!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Новые пароли не совпадают!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Вы не заполнили данные!", Toast.LENGTH_LONG).show();
                }

                /*
                if (sendEmailCheckBox.isChecked())
                {
                    if (!isEmpty(emailEdit))
                    {
                        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());

                        if (connectionDetector.checkInternetConnection())
                        {
                            rec = emailEdit.getText().toString();
                            subject = "HPassword master-password";
                            textMessage = newPasswordEdit.getText().toString();

                            Properties props = new Properties();
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.socketFactory.port", "465");
                            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.port", "465");

                            session = Session.getDefaultInstance(props, new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("testfrom3541@gmail.com", "p1234p1234");
                                }
                            });

                            //pdialog = ProgressDialog.show(context, "", "Отправка...", true);

                            RetreiveMailTask task = new RetreiveMailTask(session, pdialog, context, rec, subject, textMessage);
                            task.execute();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Проверьте соединение с интернетом!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "E-mail адрес пуст!", Toast.LENGTH_LONG).show();
                    }
                }
                */

            }
        });

    }

    private boolean isEmpty(EditText etText)
    {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

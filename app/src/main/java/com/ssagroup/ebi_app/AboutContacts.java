package com.ssagroup.ebi_app;

/**
 * Created by User on 8/3/2016.
 */

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AboutContacts extends AppCompatActivity {
    private EditText emailEditText;
    public static final String EXTRA_POSITION = "position";

    private EditText messageField;
    private EditText nameField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_contacts);
        emailEditText = (EditText) findViewById(R.id.email_field);
        messageField = (EditText) findViewById(R.id.message_field);
        nameField = (EditText) findViewById(R.id.name_field);

        findViewById(R.id.button_add).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                final String email = emailEditText.getText().toString();
                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid Email Address");
//                    emailEditText.setError(Html.fromHtml("<font color='blue'>Invalid Email Address</font>"));
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, emailEditText.getText());
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@ssagroup.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "New email from " + nameField.getText());
                    intent.putExtra(Intent.EXTRA_TEXT, messageField.getText());
                    try {
                        startActivity(Intent.createChooser(intent, "Send mail..."));
                    }
                    catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(AboutContacts.this, "There are no email clients installed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();

        String[] contactAddress = resources.getStringArray(R.array.contact_address);
        TextView contactAddres =  (TextView) findViewById(R.id.contact_address);
        contactAddres.setText(contactAddress[postion % contactAddress.length]);

        String[] contactPhones = resources.getStringArray(R.array.contact_phone);
        TextView contactPhone =  (TextView) findViewById(R.id.contact_phone);
        contactPhone.setText(contactPhones[postion % contactPhones.length]);

        String[] contactEmails = resources.getStringArray(R.array.contact_email);
        TextView contactEmail =  (TextView) findViewById(R.id.contact_email);
        contactEmail.setText(contactEmails[postion % contactEmails.length]);
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

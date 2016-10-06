package com.example.jassyjaaas.bunani_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by JassyJaaas on 10/5/2016.
 */
public class SignUp extends AppCompatActivity {
    EditText txtFname, txtLname, txtUname, txtEmail, txtPword , txtCpword;
    Button btnSignup;
    DatabaseAdapter DatabaseAdapter;

    private Toast popToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        txtFname = (EditText) findViewById(R.id.fname_txt);
        txtLname = (EditText) findViewById(R.id.lname_txt);
        txtUname = (EditText) findViewById(R.id.uname_txt);
        txtEmail = (EditText) findViewById(R.id.email_txt);
        txtPword = (EditText) findViewById(R.id.pword_txt);
        txtCpword = (EditText) findViewById(R.id.cpword_txt);
        btnSignup = (Button) findViewById(R.id.signup_btn);

        DatabaseAdapter = new DatabaseAdapter(this);
        DatabaseAdapter = DatabaseAdapter.open();


        assert btnSignup != null;
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String uname = txtUname.getText().toString();
                String savePassword = DatabaseAdapter.getEmailforsignup(email);
                String savePassword1 = DatabaseAdapter.getUsernameforsignup(uname);

                if(!isValidEmail(txtEmail.getText().toString())) {
                    Toast.makeText(SignUp.this,"Invalid Email",Toast.LENGTH_LONG).show();
                } else if(!isValidPassword(txtPword.getText().toString())) {
                    Toast.makeText(SignUp.this, "Password needs to be at least 8 characters", Toast.LENGTH_LONG).show();
                }
                else if (!txtPword.getText().toString().equals(txtCpword.getText().toString())) {
                    Toast.makeText(SignUp.this, "Password does not match", Toast.LENGTH_LONG).show();
                }
                else if(!isValidFname(txtFname.getText().toString())) {
                    Toast.makeText(SignUp.this,"Invalid Firstname",Toast.LENGTH_LONG).show();
                }
                else if(!isValidLname(txtLname.getText().toString())) {
                    Toast.makeText(SignUp.this,"Invalid Lastname",Toast.LENGTH_LONG).show();
                }
                else if(uname.equals(savePassword1)|email.equals(savePassword)){
                    Toast.makeText(SignUp.this,"Username or Email already exists",Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseAdapter.insertEntry(txtFname.getText().toString(),txtLname.getText().toString(),txtUname.getText().toString(),txtEmail.getText().toString(),txtPword.getText().toString());
                    popToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
                    popToast.setText("Account Successfully Created ");
                    popToast.show();

                    Intent intent = new Intent(SignUp.this,MainActivity.class );
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pword) {
        if (pword != null && pword.length() >= 8) {
            return true;
        }
        return false;

    }
    private boolean isValidFname(String fname) {
        String FNAME_PATTERN = "^ ([A-Za-z] *)+$";
        Pattern pattern = Pattern.compile(FNAME_PATTERN);
        Matcher matcher = pattern.matcher(fname);
        return matcher.matches();
    }

    private boolean isValidLname(String lname) {
        String LNAME_PATTERN = "^([A-Za-z] *)+$";
        Pattern pattern = Pattern.compile(LNAME_PATTERN);
        Matcher matcher = pattern.matcher(lname);
        return matcher.matches();
    }

}

package com.example.jassyjaaas.bunani_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnShow;
    EditText txtEmail, txtPword;
    TextView txtSignup;

    DatabaseAdapter DatabaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=(Button)findViewById(R.id.login_btn);
        txtEmail=(EditText)findViewById(R.id.email_txt);
        txtPword=(EditText)findViewById(R.id.pword_txt);
        txtSignup=(TextView)findViewById(R.id.signup_txt);
        btnShow = (Button)findViewById(R.id.show_btn);

        DatabaseAdapter = new DatabaseAdapter(this);
        DatabaseAdapter = DatabaseAdapter.open();

        assert  btnLogin != null;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String pword = txtPword.getText().toString();
                String uname = txtEmail.getText().toString();
                String savePassword = DatabaseAdapter.getSinlgeEntry(email);
                String savePassword1 = DatabaseAdapter.getUsername(uname);

                if (pword.equals(savePassword1)|pword.equals(savePassword)) {
                    Toast.makeText(MainActivity.this, uname + " has logged in. \n Password: " + pword, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, OnTouch.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid Email/Password ", Toast.LENGTH_LONG).show();
                }

            }

        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }

        });

        btnShow.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        final int cursor = txtPword.getSelectionStart();
                                        int event = motionEvent.getAction();

                                        switch (event) {
                                            case MotionEvent.ACTION_DOWN:
                                                txtPword.setTransformationMethod(null);
                                                txtPword.setSelection(cursor);
                                                break;
                                            case MotionEvent.ACTION_UP:
                                                txtPword.setTransformationMethod(new PasswordTransformationMethod());
                                                txtPword.setSelection(cursor);
                                                break;
                                        }
                                        return false;
                                    }
                                }
        );
    }
}

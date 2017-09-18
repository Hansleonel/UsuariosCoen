package com.example.codehans.usuarioscoen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtV_user, edtV_password, edtV_DNI;
    private Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtV_DNI = (EditText) findViewById(R.id.edtV_DNI);
        edtV_password = (EditText) findViewById(R.id.edtV_password);
        edtV_user = (EditText) findViewById(R.id.edtV_username);

        btn_registrar = (Button)findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}

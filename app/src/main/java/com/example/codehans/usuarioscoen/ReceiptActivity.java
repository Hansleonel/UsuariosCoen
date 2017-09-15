package com.example.codehans.usuarioscoen;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class ReceiptActivity extends AppCompatActivity {

    private CardView cardView_accept_alert;
    private Button btn_accept_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        btn_accept_alert = (Button)findViewById(R.id.btn_card_main1_action1);
        btn_accept_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"ACEPTADO",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}

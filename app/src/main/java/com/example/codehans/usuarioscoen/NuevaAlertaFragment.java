package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaAlertaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaAlertaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaAlertaFragment extends Fragment {

    private EditText editText_ubicacion;
    private EditText editText_message;
    private Button btn_alert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_nueva_alerta, container, false);

        editText_ubicacion = (EditText) relativeLayout.findViewById(R.id.edtV_Ubicacion);
        editText_message = (EditText) relativeLayout.findViewById(R.id.edtV_Message);
        btn_alert = (Button) relativeLayout.findViewById(R.id.btn_accept_alert);
        btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ReceiptActivity.class);
                startActivity(i);
            }
        });
        editText_ubicacion.setText("");
        return relativeLayout;
    }
}

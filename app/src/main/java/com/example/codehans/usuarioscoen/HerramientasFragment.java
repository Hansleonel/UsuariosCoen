package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HerramientasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HerramientasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HerramientasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout = (RelativeLayout)inflater.inflate(R.layout.fragment_herramientas,container,false);
        return relativeLayout;
    }
}

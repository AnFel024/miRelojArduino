package com.example.mirelojprogramable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class Estado3 extends Fragment implements View.OnClickListener {
    //ListView item_tono;
    //String listaTono[] = {"Cucaracha", "Charanga", "Desesperante", "Preterito", "Memoria", "Disco", "RAM", "SSD", "El disco esta caliente", "Gusanito", "Varon", "Distrital", "Tropel", "Ensueño", "Tono 4", "Tono 10-12", "Tono 14"};
    private String comando;
    private OnFragmentInteractionListener mListener;
    public RadioButton tono1, tono5, tono2, tono3, tono4, tono6, tono7, tono10, tono8, tono9, tono11, tono15, tono12, tono13, tono14;
    private OutputStream outputStream;
    public Estado3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputStream = ((ConexionActivity)getActivity()).getOutputStream();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myView = inflater.inflate(R.layout.fragment_estado3, container, false);
        tono1 = (RadioButton) myView.findViewById(R.id.tono1);
        tono1.setOnClickListener(this);
        tono5 = (RadioButton) myView.findViewById(R.id.tono5);
        tono5.setOnClickListener(this);
        tono2 = (RadioButton) myView.findViewById(R.id.tono2);
        tono2.setOnClickListener(this);
        tono3 = (RadioButton) myView.findViewById(R.id.tono3);
        tono3.setOnClickListener(this);
        tono4 = (RadioButton) myView.findViewById(R.id.tono4);
        tono4.setOnClickListener(this);
        tono6 = (RadioButton) myView.findViewById(R.id.tono6);
        tono6.setOnClickListener(this);
        tono7 = (RadioButton) myView.findViewById(R.id.tono7);
        tono7.setOnClickListener(this);
        tono8 = (RadioButton) myView.findViewById(R.id.tono8);
        tono8.setOnClickListener(this);
        tono9 = (RadioButton) myView.findViewById(R.id.tono9);
        tono9.setOnClickListener(this);
        tono10 = (RadioButton) myView.findViewById(R.id.tono10);
        tono10.setOnClickListener(this);
        tono11 = (RadioButton) myView.findViewById(R.id.tono11);
        tono11.setOnClickListener(this);
        tono15 = (RadioButton) myView.findViewById(R.id.tono15);
        tono15.setOnClickListener(this);
        tono12 = (RadioButton) myView.findViewById(R.id.tono12);
        tono12.setOnClickListener(this);
        tono13 = (RadioButton) myView.findViewById(R.id.tono13);
        tono13.setOnClickListener(this);
        tono14 = (RadioButton) myView.findViewById(R.id.tono14);
        tono14.setOnClickListener(this);


        return myView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        comando = "n";
        try {
            if (v == tono3) {
                outputStream.write(comando.getBytes());
                outputStream.write('3');
            }

            if (v == tono1) {
                outputStream.write(comando.getBytes());
                outputStream.write('1');
            }

            if (v == tono2) {
                outputStream.write(comando.getBytes());
                outputStream.write('2');
            }

            if (v == tono5) {
                outputStream.write(comando.getBytes());
                outputStream.write('5');
            }

            if (v == tono4) {
                outputStream.write(comando.getBytes());
                outputStream.write('4');
            }

            if (v == tono6) {
                outputStream.write(comando.getBytes());
                outputStream.write('6');
            }

            if (v == tono7) {
                outputStream.write(comando.getBytes());
                outputStream.write('7');
            }

            if (v == tono8) {
                outputStream.write(comando.getBytes());
                outputStream.write('8');
            }

            if (v == tono9) {
                outputStream.write(comando.getBytes());
                outputStream.write('9');
            }

            if (v == tono14) {
                outputStream.write(comando.getBytes());
                outputStream.write('e');
            }

            if (v == tono13) {
                outputStream.write(comando.getBytes());
                outputStream.write('d');
            }

            if (v == tono11) {
                outputStream.write(comando.getBytes());
                outputStream.write('b');
            }

            if (v == tono12) {
                outputStream.write(comando.getBytes());
                outputStream.write('c');
            }

            if (v == tono15) {
                outputStream.write(comando.getBytes());
                outputStream.write('f');
            }

            if (v == tono10) {
                outputStream.write(comando.getBytes());
                outputStream.write('a');
            }
        } catch (IOException e){
            Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

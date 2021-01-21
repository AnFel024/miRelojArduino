package com.example.mirelojprogramable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class Estado2 extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    public RadioButton LuzAzul, LuzBlanca, LuzRoja, LuzVerde, LuzTodas;

    private OutputStream outputStream;
    private String comando;


    public Estado2() {
        }

    public static Estado2 newInstance(String param1, String param2) {
        Estado2 fragment = new Estado2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View myView = inflater.inflate(R.layout.fragment_estado2, container, false);
        LuzAzul = (RadioButton) myView.findViewById(R.id.luzAzul);
        LuzAzul.setOnClickListener(this);
        LuzBlanca = (RadioButton) myView.findViewById(R.id.luzBlanca);
        LuzBlanca.setOnClickListener(this);
        LuzRoja = (RadioButton) myView.findViewById(R.id.luzRoja);
        LuzRoja.setOnClickListener(this);
        LuzVerde = (RadioButton) myView.findViewById(R.id.luzVerde);
        LuzVerde.setOnClickListener(this);
        LuzTodas = (RadioButton) myView.findViewById(R.id.luzVerde);
        LuzTodas.setOnClickListener(this);

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

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

        comando = "0";
        if (v == LuzVerde) {
            comando = "1";
            try {
                outputStream.write(comando.getBytes());
            }
            catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == LuzAzul) {
            comando = "2";
            try {
                outputStream.write(comando.getBytes());
            }
            catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == LuzRoja) {
            comando = "3";
            try {
                outputStream.write(comando.getBytes());
            }
            catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == LuzBlanca) {
            comando = "4";
            try {
                outputStream.write(comando.getBytes());
            }
            catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == LuzTodas) {
            comando = "s";
            try {
                outputStream.write(comando.getBytes());
            }
            catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

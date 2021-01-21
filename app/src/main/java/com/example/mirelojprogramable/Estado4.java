package com.example.mirelojprogramable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Estado4.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Estado4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estado4 extends Fragment implements View.OnClickListener {
    public TextView TxtFecha;
    public Button Establecer;
    private static final String CERO = "0";
    public TimePicker tHora;
    public static int Hora_final, Minuto_Final;
    public OutputStream outputStream;
    public InputStream inputStream;
    public String comando;
    public String horaAl, envio;
    public ImageButton obtener_Hora, btnApagar;
    public TimePicker recogerHora;

    private OnFragmentInteractionListener mListener;

    public Estado4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Estado4.
     */
    // TODO: Rename and change types and number of parameters
    public static Estado4 newInstance(String param1, String param2) {
        Estado4 fragment = new Estado4();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar laHora = Calendar.getInstance();
        Hora_final = laHora.get(Calendar.HOUR);
        Minuto_Final = laHora.get(Calendar.MINUTE);
        outputStream = ((ConexionActivity)getActivity()).getOutputStream();
        inputStream = ((ConexionActivity)getActivity()).getInputStream();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_estado4, container, false);
        obtener_Hora = (ImageButton) myView.findViewById(R.id.ib_obtener_hora3);
        obtener_Hora.setOnClickListener((View.OnClickListener) this);
        recogerHora = (TimePicker) myView.findViewById(R.id.hora2);
        btnApagar = (ImageButton) myView.findViewById(R.id.btnApagar);
        btnApagar.setOnClickListener(this);

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public void establecerHora(){
        String horaFormateada =  (recogerHora.getHour() < 10)? String.valueOf(CERO + recogerHora.getHour()) : String.valueOf(recogerHora.getHour());
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (recogerHora.getMinute() < 10)? String.valueOf(CERO + recogerHora.getMinute()):String.valueOf(recogerHora.getMinute());
        envio = horaFormateada + minutoFormateado;
    }


    @Override
    public void onClick(View v) {

        if(v == btnApagar){
            comando = "o";
            try{
                outputStream.write(comando.getBytes());
            }catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }

        if(v == obtener_Hora){
            comando = "t";
            try {
                establecerHora();
                outputStream.write(comando.getBytes());
                outputStream.write(envio.getBytes());

            }
            catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(getActivity(), String.valueOf(fechaSt.length()), Toast.LENGTH_SHORT).show();
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

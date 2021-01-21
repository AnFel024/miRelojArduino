package com.example.mirelojprogramable;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Estado1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Estado1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estado1 extends Fragment implements View.OnClickListener {
    public TextView TxtFecha, TxtHora;
    public Button Establecer;
    private static final String CERO = "0";
    public static int Hora_final, Minuto_Final, Segundo_Final, Dia_Final, Mes_Final, Año_Final;
    public OutputStream outputStream;
    public boolean mensaje;
    public String horaSt;
    public String fechaSt;
    public String comando;
    public ImageButton obtener_Hora, obtener_Fecha;

    private OnFragmentInteractionListener mListener;

    public Estado1() {
    }

    public static Estado1 newInstance(String param1, String param2) {
        Estado1 fragment = new Estado1();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar laHora = Calendar.getInstance();

        Hora_final = laHora.get(Calendar.HOUR);
        Minuto_Final = laHora.get(Calendar.MINUTE);
        Segundo_Final = laHora.get(Calendar.SECOND);
        Dia_Final = laHora.get(Calendar.DAY_OF_MONTH);
        Mes_Final = laHora.get(Calendar.MONTH);
        Año_Final = laHora.get(Calendar.YEAR);

        outputStream = ((ConexionActivity)getActivity()).getOutputStream();

        /*if(!(((ConexionActivity)getActivity()).isMensaje())){
            establecerHora();
            establecerFecha();
            ((ConexionActivity)getActivity()).setMensaje(true);
            //Establecer las variables como globales
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_estado1, container, false);
        Establecer = (Button) myView.findViewById(R.id.establecerHora);
        Establecer.setOnClickListener(this);
        obtener_Fecha = (ImageButton) myView.findViewById(R.id.ib_obtener_fecha);
        obtener_Fecha.setOnClickListener(this);
        obtener_Hora = (ImageButton) myView.findViewById(R.id.ib_obtener_hora);
        obtener_Hora.setOnClickListener(this);
        horaSt = ((ConexionActivity)getActivity()).getHoraSt();
        fechaSt = ((ConexionActivity)getActivity()).getFechaSt();

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //TxtHora = (EditText) getView().findViewById(R.id.estHora);
        //TxtMinuto = (EditText) getView().findViewById(R.id.estMinuto);
        //TxtSegundo = (EditText) getView().findViewById(R.id.estSegundo);
        TxtHora = (TextView) getView().findViewById(R.id.hora);
        TxtFecha = (TextView) getView().findViewById(R.id.txtFecha);
        fechaSt = ((ConexionActivity)getActivity()).getFechaSt();
        horaSt = ((ConexionActivity)getActivity()).getHoraSt();
        try {
            TxtHora.setText(horaSt.charAt(0) + horaSt.charAt(1) + " : " + horaSt.charAt(2) + horaSt.charAt(3));
            TxtFecha.setText(fechaSt.charAt(0) + fechaSt.charAt(1) + fechaSt.charAt(2) +
                    fechaSt.charAt(3) + fechaSt.charAt(4) + " / " + fechaSt.charAt(5) + fechaSt.charAt(6) + " / " + fechaSt.charAt(7) + fechaSt.charAt(8));

        } catch (IndexOutOfBoundsException e){
            establecerFecha();
            establecerHora();
        }


    }


    public void establecerHora() {

        int hora = 0, minuto = 0, segundo = 0;

        Calendar laHora = Calendar.getInstance();

        hora = laHora.get(Calendar.HOUR_OF_DAY);
        minuto = laHora.get(Calendar.MINUTE);
        segundo = laHora.get(Calendar.SECOND);

        TimePickerDialog recogerHora = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado

                Hora_final = Integer.parseInt(horaFormateada);
                Minuto_Final = Integer.parseInt(minutoFormateado);
                Segundo_Final = 0;

                TxtHora.setText(Hora_final + " : " + Minuto_Final);

                horaSt = String.valueOf(horaFormateada) + String.valueOf(minutoFormateado);
                ((ConexionActivity)getActivity()).setHoraSt(horaSt);

            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.setCanceledOnTouchOutside(false);
        recogerHora.setCancelable(false);
        recogerHora.show();
    }

    private void establecerFecha(){

        Calendar laHora = Calendar.getInstance();

        final int dia = laHora.get(Calendar.DAY_OF_MONTH);
        final int mes = laHora.get(Calendar.MONTH);
        final int anio = laHora.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado

                TxtFecha.setText(year + " / " + mesFormateado + " / " + diaFormateado);


                Dia_Final = Integer.parseInt(diaFormateado);
                Mes_Final = Integer.parseInt(mesFormateado);

                fechaSt = mesFormateado + diaFormateado + year;
                ((ConexionActivity)getActivity()).setFechaSt(fechaSt);

            }
        },anio, mes, dia);

        recogerFecha.setCanceledOnTouchOutside(false);
        recogerFecha.setCancelable(false);
        recogerFecha.show();
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

    @Override
    public void onClick(View v) {

        if(v == Establecer){
            //char h1 = horaSt.charAt(0);
            //char h2 = horaSt.charAt(1);

            //char h1 = '0';
            String h2 = "";
            comando = "h";
            try {
                outputStream.write(comando.getBytes());
                //outputStream.write(h1);
                outputStream.write(horaSt.getBytes());
                outputStream.write(fechaSt.getBytes());

            }
            catch (IOException e){
                Toast.makeText(getActivity(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(getActivity(), String.valueOf(fechaSt.length()), Toast.LENGTH_SHORT).show();
        }

        if(v== obtener_Hora){
            establecerHora();
        }

        if (v == obtener_Fecha){
            establecerFecha();
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

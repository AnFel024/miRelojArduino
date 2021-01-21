package com.example.mirelojprogramable;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class ConexionActivity extends AppCompatActivity implements View.OnClickListener,   Estado1.OnFragmentInteractionListener,
        Estado2.OnFragmentInteractionListener, Estado3.OnFragmentInteractionListener, Estado4.OnFragmentInteractionListener {

    public static final String estado = "Conectado";
    public ProgressBar BarraEspera;
    public TextView MAC;
    public Button Estado1, Estado2, Estado3, Estado4;
    public Switch pantalla;
    public boolean regresar = false;
    private int bandera = 0;
    private final String DEVICE_ADDRESS = "98:D3:61:FD:4B:8C"; //Direccion del BT HC 05
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    public String horaSt;
    public String fechaSt;
    private volatile boolean mensaje;
    private int alarma = 0;

    public Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);///Hacer fragmentos dentro de fragmentos////
        getSupportActionBar().setTitle("Se encuentra " + estado);
        pantalla = (Switch) findViewById(R.id.pantalla);
        pantalla.setOnClickListener(this);
        MAC = (TextView) findViewById(R.id.BTMAC);
        socket = null;
        BarraEspera = (ProgressBar) findViewById(R.id.barraEspera);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.abc);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BT();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        horaSt = "";
        fechaSt = "";
        mensaje = false;
        datos = new Bundle();
        pantalla.setChecked(true);
        pantalla.setText("Pantalla Encendida");

        BottomNavigationView btmView = findViewById(R.id.btmView);
        //fragmentoSeleccionado(new Estado1(), "Humedales");
        btmView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.btn_hora){
                    fragmentoSeleccionado(new Estado1(), "Establecer Hora - Conectado");
                }

                if(item.getItemId() == R.id.btn_luces){
                    fragmentoSeleccionado(new Estado2(), "Establecer Luces - Conectado");
                }

                if(item.getItemId() == R.id.btn_tono){
                    fragmentoSeleccionado(new Estado3(), "Establecer Tono - Conectado");
                }

                if(item.getItemId() == R.id.btn_alarma){
                    fragmentoSeleccionado(new Estado4(), "Establecer Alarma - Conectado");
                }

                return true;
            }
        });

        BT();
    }

    public void BT() {
        if(!estaConectado()) {
            resetConnection();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", String.valueOf(estaConectado()));
                }
            }, 2000);
            if (BTinit()) {
                if (BTconnect()) {
                    MAC.setText("La direccion del dispositivo es: " + DEVICE_ADDRESS);
                    Estado1 Es5 = new Estado1();
                    FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                    transaction5.replace(R.id.contenedor, Es5);
                    transaction5.commit();
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setTitle(Html.fromHtml("<font color='#D60000'>Error</font>"));
                    alerta.setMessage(Html.fromHtml("<font color='#000000'>No hay direccion similar en los dispositivos asociados</font>"));
                    alerta.setPositiveButton(Html.fromHtml("<font color='#D60000'>ACEPTAR</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pasarActivity("Verifique si el dispositivo BT se encuentra encendido y asociado al ETD");
                        }
                    });

                    AlertDialog alert = alerta.create();
                    alert.setCanceledOnTouchOutside(false);
                    alert.setCancelable(false);
                    alert.show();
                }
            }
        }
    }

    private boolean estaConectado() {
        boolean band = false;
        if(bandera>0){
            try {
                if(!socket.getOutputStream().equals(outputStream))
                    band = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return band;
    }


    @Override
    public void onStart(){
        super.onStart();

    }

    public void pasarActivity(String msg){
        Intent salto = new Intent(this, MainActivity.class);
        startActivity(salto);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void onBackPressed (){

        AlertDialog.Builder atras = new AlertDialog.Builder(this);
        atras.setTitle(Html.fromHtml("<font color='#D60000'>Regresar</font>"));
        atras.setMessage(Html.fromHtml("<font color='#000000'>¿Desea salir de la pantalla de configuracion del reloj?</font>"));
        atras.setPositiveButton(Html.fromHtml("<font color='#D60000'>SI</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pasarActivity("Conexion Finalizada");
                BTdisconnect();
            }
        });

        atras.setNegativeButton(Html.fromHtml("<font color='#D60000'>NO</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = atras.create();
        dialog.show();
    }

    public boolean isMensaje() { return mensaje; }

    public void setMensaje(boolean mensaje) { this.mensaje = mensaje; }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() { return inputStream; }

    public String getHoraSt() { return horaSt; }

    public void setHoraSt(String horaSt) { this.horaSt = horaSt; }

    public String getFechaSt() { return fechaSt; }

    public void setFechaSt(String fechaSt) { this.fechaSt = fechaSt; }

    public int getAlarma() {return alarma;}

    public void setAlarma(int alarma) {this.alarma = alarma;}

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_off){
            BTdisconnect();
            pasarActivity("Se ha desconectado el dispositivo");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (pantalla.isChecked() && (alarma != 1)) {
            Toast.makeText(this, "Encender", Toast.LENGTH_SHORT).show();
            pantalla.setText(R.string.pantalla_ON);
            try {
                outputStream.write('p');
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }

        if (!pantalla.isChecked() && (alarma != 1)) {
            Toast.makeText(this, "Apagar", Toast.LENGTH_SHORT).show();
            pantalla.setText(R.string.pantalla_OFF);
            try {
                outputStream.write('q');
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Verifique la conexion", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void fragmentoSeleccionado(Fragment fragmento, String title){
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragmento)
                .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        getSupportActionBar().setTitle(title);
    }

    public void uuid(){

        try {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            Method getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
            ParcelUuid[] uuids = (ParcelUuid[]) getUuidsMethod.invoke(adapter, null);

            if(uuids != null) {
                for (ParcelUuid uuid : uuids) {
                    Log.d("tag", "UUID: " + uuid.getUuid().toString());
                }
            }else{
                Log.d("TAG", "Uuids no encontrados, asegura habilitar Bluetooth!");
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean BTinit()
    {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            Toast.makeText(this, "No se soporta el bluethoot!!", Toast.LENGTH_SHORT).show();
        }

        if(!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter,0);

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            Toast.makeText(this, "Conecte el Bluethoot Primero!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device = iterator;
                    found = true;
                    break;
                }

                else{
                    found = false;
                    //break;
                }
            }
        }

        return found;
    }

    public boolean BTconnect()
    {
        boolean connected = true;
        try
        {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); //Creates a socket to handle the outgoing connection
            if(!socket.isConnected()) {
                socket.connect();
                bandera++;
            }
            Toast.makeText(this,"Conexion Exitosa", Toast.LENGTH_LONG).show();


        }
        catch(IOException e)
        {
            e.printStackTrace();
            connected = false;
        }

        if(connected)
        {
            try
            {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
                inputStream = socket.getInputStream();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return connected;
    }

    public boolean BTdisconnect(){
        boolean FinProceso = false;
        if(resetConnection())
            FinProceso = true;
        return FinProceso;
    }

    private boolean resetConnection() {
        boolean fin = false;

        if (outputStream != null) {
            try {outputStream.close();} catch (Exception e) {}
            outputStream = null;
        }

        if (socket != null) {
            try {socket.close();} catch (Exception e) {}
            socket = null;
        }

        if(socket == null && outputStream == null){
            fin = true;
        }

        return fin;
    }

    public void onDestroy() {
        super.onDestroy();
        if(!BTdisconnect())
            Toast.makeText(getApplicationContext(), "El dispositivo ya se encuentra desconectado", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Conexion Finalizada!..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

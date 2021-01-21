package com.example.mirelojprogramable;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String estado = "Desconectado";
    public int state;
    public BluetoothAdapter mbluetoothAdapter;
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String accion = intent.getAction();
            if(accion.equals(mbluetoothAdapter.ACTION_STATE_CHANGED))
                state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mbluetoothAdapter.ERROR);

            switch (state){
                case BluetoothAdapter.STATE_OFF:
                    Toast.makeText(MainActivity.this, "El Bluethoot se encuentra apagado", Toast.LENGTH_SHORT).show();
                    break;

                case BluetoothAdapter.STATE_TURNING_OFF:
                    Toast.makeText(MainActivity.this, "Apagando Bluethoot...", Toast.LENGTH_SHORT).show();
                    break;

                case BluetoothAdapter.STATE_ON:
                    Toast.makeText(MainActivity.this, "El Bluethoot se encuentra encendido", Toast.LENGTH_SHORT).show();
                    break;

                case BluetoothAdapter.STATE_TURNING_ON:
                    Toast.makeText(MainActivity.this, "Encendiendo Bluethoot...", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Se encuentra " + estado);

        mbluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    protected void onDestroy(Bundle savedInstanceState){
        Toast.makeText(this, "Se ha detenido la conexion", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_on){
            if(true)
            {
                Intent salto = new Intent(this, ConexionActivity.class);
                final AlertDialog.Builder cuadro = new AlertDialog.Builder(MainActivity.this);

                View nView = getLayoutInflater().inflate(R.layout.barra_progreso, null);
                /*ConstraintLayout relativeLayout = (ConstraintLayout) findViewById(R.id.main);
                nView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                relativeLayout.addView(nView);*/
                cuadro.setView(nView);
                final AlertDialog fnal = cuadro.create();
                fnal.setCanceledOnTouchOutside(true);
                fnal.show();

                startActivity(salto);
                setContentView(R.layout.activity_conexion);

            }

            else
                Toast.makeText(MainActivity.this,"Active primero el Bluethoot!!!", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean stateOnOFF(){
        boolean exito = false;
        if(mbluetoothAdapter == null)
            Toast.makeText(MainActivity.this,"No es posible realizar la conexion", Toast.LENGTH_SHORT).show();

        if(!mbluetoothAdapter.isEnabled()){

            Toast.makeText(MainActivity.this,"Encendiendo...", Toast.LENGTH_SHORT).show();
            Intent activarBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(activarBT);

            IntentFilter BTactivo = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTactivo);

        }

        if(mbluetoothAdapter.isEnabled()){

            exito = true;

            /*Toast.makeText(MainActivity.this,"Apagando...", Toast.LENGTH_SHORT).show();
            mbluetoothAdapter.disable();

            IntentFilter BTactivo = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTactivo);*/
        }

        return exito;
    }

    @Override
    public void onClick(View v) {
    }
}

package com.essoft.akllevuygulamas;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter myBluetooth;
    private Set<BluetoothDevice> pairedDevices;
    Button btnBluetooth,btnPair;
    ListView deviceList;
    public static String EXTRA_ADDRESS="device_address";
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBluetooth=BluetoothAdapter.getDefaultAdapter();
        btnBluetooth=findViewById(R.id.btnBluetooth);
        btnPair=findViewById(R.id.btnPair);
        deviceList=findViewById(R.id.deviceList);

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothState();
            }
        });

        btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDevice();
            }
        });
    }

    private void listDevice() {
        pairedDevices=myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        if(pairedDevices.size()>0) {
            for(BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName()+"\n"+bt.getAddress());
            }
        }else {
            Toast.makeText(getApplicationContext(), "Eşleşmiş Cihaz Yok", Toast.LENGTH_SHORT).show();
        }
        final ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(selectDevice);

    }

    private void bluetoothState() {
        if(myBluetooth==null) {
            Toast.makeText(getApplicationContext(),"Bluetooth Cihazı Yok",Toast.LENGTH_SHORT).show();
        }
        if(!myBluetooth.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
            Toast.makeText(getApplicationContext(),"Bluetooth Açıldı",Toast.LENGTH_SHORT).show();
        }
        if(myBluetooth.isEnabled()) {
            myBluetooth.disable();
            Toast.makeText(getApplicationContext(),"Bluetooth Kapatıldı",Toast.LENGTH_SHORT).show();
        }
    }

    public AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String info=((TextView) view).getText().toString();
            String address=info.substring(info.length()-17);

            Intent comIntent=new Intent(MainActivity.this,Comunication.class);
            comIntent.putExtra(EXTRA_ADDRESS,address);
            startActivity(comIntent);
        }
    };
}
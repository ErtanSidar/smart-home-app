package com.essoft.akllevuygulamas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Comunication extends AppCompatActivity {

    String address=null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth=null;
    BluetoothSocket btSocket=null;
    BluetoothDevice remoteDevice;
    BluetoothServerSocket mmServer;

    private boolean isBtConnected=false;
    static final UUID myUUID =UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    ImageButton openSystem, closeSystem,  openHomeLight, closeHomeLight, openFenceLight, closeFenceLight,
    openPoolLight,closePoolLight,openCamelliaLight, closeCamelliaLight,openAlarm, openDoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunication);

        Intent newIntent = getIntent();
        address=newIntent.getStringExtra(MainActivity.EXTRA_ADDRESS);

        new BTbaglan().execute();

        openSystem=findViewById(R.id.openSystem);
        closeSystem=findViewById(R.id.closeSystem);
        openHomeLight=findViewById(R.id.openHomeLight);
        closeHomeLight=findViewById(R.id.closeHomeLight);
        openFenceLight=findViewById(R.id.openFenceLight);
        closeFenceLight=findViewById(R.id.closeFenceLight);
        openPoolLight=findViewById(R.id.openPoolLight);
        closePoolLight=findViewById(R.id.closePoolLight);
        openCamelliaLight=findViewById(R.id.openCamelliaLight);
        closeCamelliaLight=findViewById(R.id.closeCamelliaLight);
        openAlarm=findViewById(R.id.openAlarm);
        openDoor=findViewById(R.id.openDoor);


        openSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("1".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        closeSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("2".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        openHomeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("3".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        closeHomeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("4".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        openFenceLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("5".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        closeFenceLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("6".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        openPoolLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("7".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        closePoolLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("8".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        openCamelliaLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("9".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        closeCamelliaLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("a".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        openAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("b".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });
        openDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("c".toString().getBytes());
                    }catch (IOException e) {

                    }
                }
            }
        });

    }


    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                // msg("Error");
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Disconnect();
    }


    private class BTbaglan extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Comunication.this, "Baglanıyor...", "Lütfen Bekleyin");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice cihaz = myBluetooth.getRemoteDevice(address);
                    btSocket = cihaz.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();


                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                // msg("Baglantı Hatası, Lütfen Tekrar Deneyin");
                Toast.makeText(getApplicationContext(), "Bağlantı Hatası Tekrar Deneyin", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //   msg("Baglantı Basarılı");
                Toast.makeText(getApplicationContext(), "Bağlantı Başarılı", Toast.LENGTH_SHORT).show();

                isBtConnected = true;
            }
            progress.dismiss();
        }

    }
}

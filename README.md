# Smart Home Application
Technology and internet, which are constantly developing from the past to the present, are used in many areas of life. 
With this development, there is a rapid use and demand increase in smart home systems. 
In this context, they are systems that aim to facilitate people's lives, offer and maintain a more comfortable and economical living space. 
Optional different usage areas are available. Arduino UNO is used for lighting, security and temperature control in the smart home system in this study we have conducted. The mobile application, developed in Java language for the users to have authority and control over the system, provides ease of use, easy and understandable, and at the same time, complete control of the smart home system can be easily provided by the user.

### In the project, the function-function descriptions of the mobile application codes we wrote in Java are included.
1. In the function below, the bluetooth status is controlled by the myBluetooth variable, 
it turns off if it is on, turns on if it is off, and gives a message if there is no device.
```
 private void bluetoothState() {
        if(myBluetooth==null) {
            Toast.makeText(getApplicationContext(),"Not Found Device",Toast.LENGTH_SHORT).show();
        }
        if(!myBluetooth.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
            Toast.makeText(getApplicationContext(),"Bluetooth Turned On",Toast.LENGTH_SHORT).show();
        }
        if(myBluetooth.isEnabled()) {
            myBluetooth.disable();
            Toast.makeText(getApplicationContext(),"Bluetooth Turned Off",Toast.LENGTH_SHORT).show();
        }
    }
```
2. In the function below, pairedDevices = myBluetooth.getBondeDevices() takes the visible devices and keeps them in the variable, 
then lists these devices with their names and addresses if there is more than one device. If there is no device, the user is informed that there is no device.
```
     private void listDevice() {
        pairedDevices=myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        if(pairedDevices.size()>0) {
            for(BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName()+"\n"+bt.getAddress());
            }
        }else {
            Toast.makeText(getApplicationContext(), "No Paired Device", Toast.LENGTH_SHORT).show();
        }
        final ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(selectDevice);
    }
```
3.The device used in the function below is selected and sent to the Intent class and the Comunication class together with the address of the device, 
and it is made ready for operation.
```
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
```
4. In the function below, we create an object to send data to the bluetooth device we paired.
```
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
```
5. In the function shown below, the btsocket object is checked if it has a connection, btSocket.getOutputStream().write(“1”.toString().getBytes()); 
It converts the 1 value we give with a string value and sends it in the form of bytes. 
This sent response is made to the processes defined in the arduino set. In this way, we can write as many functions as we need.
```
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
```
6. In the function below, in case of a possible error in the bluetooth connection, the user is informed.
```
@Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                // msg("Connection Error, Please try again");
                Toast.makeText(getApplicationContext(), "Connection Error Please try again", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //   msg("Connection Success");
                Toast.makeText(getApplicationContext(), "Connection Success", Toast.LENGTH_SHORT).show();

                isBtConnected = true;
            }
            progress.dismiss();
        }
```
## That is so funny! :joy:

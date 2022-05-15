package uz.ilhomjon.arduinobluetoothcontroller.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.util.Log
import android.widget.Toast
import uz.ilhomjon.arduinobluetoothcontroller.utils.MyData.btSocket
import uz.ilhomjon.arduinobluetoothcontroller.utils.MyData.myBluetooth
import uz.ilhomjon.arduinobluetoothcontroller.utils.MyData.myUUID
import java.nio.charset.Charset


//bluetooth
@SuppressLint("MissingPermission")
fun bluetooth_connect_device(context: Context):String {
    var address = ""
    var name = ""
    try {
        myBluetooth = BluetoothAdapter.getDefaultAdapter()
        address = myBluetooth?.getAddress()!!
        val pairedDevices = myBluetooth?.getBondedDevices()
        if (pairedDevices?.size!! > 0) {
            for (bt in pairedDevices!!) {
                address = bt.address.toString()
                name = bt.name.toString()
            }
        }
    } catch (we: java.lang.Exception) {
        return ""
    }

    try {
        myBluetooth = BluetoothAdapter.getDefaultAdapter() //get the mobile bluetooth device
        val dispositivo =
            myBluetooth!!.getRemoteDevice(address) //connects to the device's address and checks if it's available
        btSocket =
            dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID) //create a RFCOMM (SPP) connection
        btSocket!!.connect()
        return "BT Name: $name\nBT Address: $address"
    } catch (e: java.lang.Exception) {
        return ""
    }
}

fun sendSocket(context: Context, message:String){
    try {
        btSocket?.outputStream?.write(message.toByteArray())
    } catch (e: java.lang.Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}

fun readInfoSocket():String{
    val inputStream = btSocket?.inputStream
    var byteCount = inputStream?.available()!!
    if (byteCount>0){
        val rawBytes = ByteArray(byteCount)
        inputStream.read(rawBytes)
        val string = String(rawBytes, Charset.forName("UTF-8"))
        Log.d("Aurdino", "readInfo: $string")
        return string
    }else{
        return ""
    }
}
package uz.ilhomjon.arduinobluetoothcontroller.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import java.util.*

object MyData {

    //bluetooth
    var btSocket: BluetoothSocket? = null
    var myBluetooth: BluetoothAdapter? = null
    val myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

}
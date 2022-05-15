package uz.ilhomjon.arduinobluetoothcontroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import uz.ilhomjon.arduinobluetoothcontroller.utils.bluetooth_connect_device
import uz.ilhomjon.arduinobluetoothcontroller.utils.readInfoSocket
import uz.ilhomjon.arduinobluetoothcontroller.utils.sendSocket
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var btnSend:Button
    lateinit var btnSave:Button
    lateinit var edtSend:EditText
    lateinit var edtCount:EditText
    lateinit var tvInfo:TextView
    
    lateinit var handler: Handler
    var delay = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        btnSend=findViewById<Button>(R.id.btn_send)
        btnSave=findViewById<Button>(R.id.btn_save)
        edtSend=findViewById<EditText>(R.id.edt_send)
        edtCount=findViewById<EditText>(R.id.edt_count)
        tvInfo=findViewById<TextView>(R.id.tv_message)
        
        bluetooth_connect_device(this)
        
        btnSend.setOnClickListener { 
            sendSocket(this, edtSend.text.toString())
            Toast.makeText(this, "Send ${edtSend.text.toString()}", Toast.LENGTH_SHORT).show()
        }
        
        btnSave.setOnClickListener { 
            try {
                delay = edtCount.text.toString().toInt()
            }catch (e:Exception){
                Toast.makeText(this, "Raqam not'g'ri kiritnldi", Toast.LENGTH_SHORT).show()
            }
        }
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, delay.toLong())
    }
    
    val runnable = object : Runnable{
        override fun run() {
            val r = readInfoSocket()
            if (r ==""){
                tvInfo.text = "No message"
            }else{
                tvInfo.text = r
            }
            handler.postDelayed(this, delay.toLong())
        }
    }

}
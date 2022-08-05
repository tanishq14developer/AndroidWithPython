package com.tanishq.appwithpy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.tanishq.appwithpy.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var item:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))

        }

        val list = listOf("hi","en")
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {

                item = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


      binding.submit.setOnClickListener {
          val py = Python.getInstance()
          val module = py.getModule("texttospeech")
          try {
              val bytes = module.callAttr("texttospeech", item,binding.editText.text.toString())
              binding.text.text = bytes.toString()


          }catch (e:Exception){
              Log.e("error", "onCreate: $e")
              Toast.makeText(applicationContext,"$e",Toast.LENGTH_SHORT).show()
          }
      }




    }
}

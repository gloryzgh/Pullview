package com.ghzhang.pullview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)

    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_1->{
                Toast.makeText(this,"btn1",Toast.LENGTH_LONG).show()
            }
            R.id.btn_2->{
                Toast.makeText(this,"btn2",Toast.LENGTH_LONG).show()
            }
            R.id.btn_3->{
                Toast.makeText(this,"btn3",Toast.LENGTH_LONG).show()
            }
            R.id.btn_4->{
                Toast.makeText(this,"btn4",Toast.LENGTH_LONG).show()
            }
            R.id.btn_5->{
                Toast.makeText(this,"btn5",Toast.LENGTH_LONG).show()
            }
            R.id.btn_6 ->{
                Toast.makeText(this,"btn6",Toast.LENGTH_LONG).show()
            }
        }

    }
}

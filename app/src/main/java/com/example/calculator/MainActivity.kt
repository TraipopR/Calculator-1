package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlus.setOnClickListener {
            calc(Sign.PLUS)
        }
        btnMinus.setOnClickListener {
            calc(Sign.MINUS)
        }
        btnMultiply.setOnClickListener {
            calc(Sign.MULTIPLY)
        }
        btnDivider.setOnClickListener {
            calc(Sign.DIVIDER)
        }
        btnModulo.setOnClickListener {
            calc(Sign.MODULO)
        }
        btnClear.setOnClickListener {
            inputNum01.text.clear()
            inputNum02.text.clear()
            txtResult.text = ""
            txtSign.text = ""
        }

    }

    private fun check(num1:String, num2: String, sign: Sign): Boolean {
        var error: String = ""
        if (num1.isNullOrBlank() || num2.isNullOrBlank()) {
            error = "Please input Number 01 and Number 02.!!!"
        } else if (num2.toDouble() == 0.0 && (sign == Sign.DIVIDER || sign == Sign.MODULO)) {
            error = "Do not Number 02 is 0 or null!!!"
        }
        if (!error.isNullOrEmpty())
            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
        return error == ""
    }

    private fun calc(sign: Sign) {
        var num1 = inputNum01.text.toString()
        var num2 = inputNum02.text.toString()

        if (check(num1, num2, sign)) {
            txtSign.text = sign.sign
            var n1 = num1.toDouble()
            var n2 = num2.toDouble()

            val result: Double = when (sign) {
                Sign.PLUS -> n1 + n2
                Sign.MINUS -> n1 - n2
                Sign.MULTIPLY -> n1 * n2
                Sign.DIVIDER -> n1 / n2
                Sign.MODULO -> n1 % n2
            }
            txtResult.text = result.toString()
        } else {
            txtResult.text = ""
            txtSign.text = ""
        }
    }

    enum class Sign(val sign: String) {
        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDER("/"),
        MODULO("%")
    }

}
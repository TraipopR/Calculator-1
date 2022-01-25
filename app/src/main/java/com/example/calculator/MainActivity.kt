package com.example.calculator

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var number1 = ""
    private var number2 = ""
    private var sign: Sign = Sign.Empty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnNumber = listOf(btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine)
        var btnSign = listOf(btnPlus, btnMinus, btnMultiply, btnDivider, btnMod)

        val format = DecimalFormat("0.#######");
        for (button in btnNumber) {
            button.setOnClickListener {
                if (sign == Sign.Empty) {
                    if (number1.length >= 13) return@setOnClickListener
                    number1 = format.format("$number1${button.text}".toDouble())
                    txtDisplay.text = formatNumber(number1)
                } else {
                    if (number2.length >= 13) return@setOnClickListener
                    number2 = format.format("$number2${button.text}".toDouble())
                    println(formatNumber(number2))
                    txtDisplay.text = formatNumber(number2)
                }
            }
        }
        btnDot.setOnClickListener {
            if (sign == Sign.Empty && !number1.contains(".")) {
                txtDisplay.text = formatNumber(number1) + "."
                number1 += "."
            } else if (!number2.contains(".")) {
                txtDisplay.text = formatNumber(number2) + "."
                number2 += "."
            }
        }
        btnDel.setOnClickListener {
            if (sign == Sign.Empty) {
                number1 = number1.dropLast(1)
                txtDisplay.text = formatNumber(number1)
            } else {
                number2 = number2.dropLast(1)
                txtDisplay.text = formatNumber(number2)
            }
            if (txtDisplay.text.isEmpty()) {
                txtDisplay.text = "0"
            }
        }
        btnClear.setOnClickListener {
            number1 = ""
            number2 = ""
            sign = Sign.Empty
            txtDisplay.text = ""
            txtDisplay0.text = ""
        }
        for (button in btnSign) {
            button.setOnClickListener {
                txtDisplay0.text = "${formatNumber(number1)} ${button.text}"
            }
        }
    }

    private fun formatNumber(num: String): String {
        val locale = Locale("en", "UK")

        val symbols = DecimalFormatSymbols(locale)
        symbols.decimalSeparator = '.'
        symbols.groupingSeparator = ','

        val format = DecimalFormat("#,##0.#######", symbols);
        return format.format(num.toDouble())
    }

    private fun display(num: String) {
        txtDisplay.text = formatNumber(num)
    }

    enum class Sign(val value: String) {
        Plus("+"),
        Minus("-"),
        Multiply("X"),
        Divider("/"),
        Modulo("%"),
        Empty("")
    }
}

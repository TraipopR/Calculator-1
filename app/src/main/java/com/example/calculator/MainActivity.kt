package com.example.calculator

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.TypedValue
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var number1 = ""
    private var number2 = ""
    private var sign: Sign = Sign.Empty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtDisplay.movementMethod = ScrollingMovementMethod()
        txtDisplay0.movementMethod = ScrollingMovementMethod()

        var btnNumber = listOf(btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine)
        var btnSign = listOf(btnPlus, btnMinus, btnMultiply, btnDivide, btnMod)

        for (button in btnNumber) {
            button.setOnClickListener {
                if (sign == Sign.Empty) {
                    if (number1.length >= 13) return@setOnClickListener
                    number1 = formatDecimal("${number1}${button.text}")
                    txtDisplay.text = formatNumber(number1)
                } else {
                    if (number2.length >= 13) return@setOnClickListener
                    number2 = formatDecimal("${number2}${button.text}")
                    txtDisplay.text = formatNumber(number2)
                }
            }
        }
        btnDot.setOnClickListener {
            if (sign == Sign.Empty && !number1.contains(".")) {
                txtDisplay.text = formatNumber(number1) + "."
                number1 += "."
                return@setOnClickListener
            }
            number2 = number2.ifEmpty { txtDisplay.text.toString() }
            if (!number2.contains(".")) {
                txtDisplay.text = formatNumber(number2) + "."
                number2 += "."
            }
        }
        btnDel.setOnClickListener {
            if (sign == Sign.Empty) {
                number1 = formatDecimal(number1.dropLast(1))
                txtDisplay.text = formatNumber(number1)
            } else {
                number2 = formatDecimal(number2.dropLast(1))
                txtDisplay.text = formatNumber(number2)
            }
        }
        btnClear.setOnClickListener {
            number1 = ""
            number2 = ""
            sign = Sign.Empty
            txtDisplay.text = "0"
            txtDisplay0.text = ""
        }
        for (button in btnSign) {
            button.setOnClickListener {
                var temp = Sign.values().find { it.value == button.text.toString() }!!
                if (sign != Sign.Empty) {
                    number2 = number2.ifEmpty { number1 }
                    calculate()
                }
                sign = temp
                txtDisplay0.text = "${formatNumber(number1)} ${button.text}"
                number2 = ""
            }
        }
        btnEqual.setOnClickListener {
            number2 = number2.ifEmpty { number1 }
            txtDisplay0.text = "${formatNumber(number1)} ${sign.value} ${formatNumber(number2)} ="
            calculate()
        }
    }

    private fun formatNumber(num: Double): String {
        return formatNumber(num.toString())
    }
    private fun formatNumber(num: String): String {
        val locale = Locale("en", "UK")

        val symbols = DecimalFormatSymbols(locale)
        symbols.decimalSeparator = '.'
        symbols.groupingSeparator = ','

        val format = DecimalFormat("#,##0.#######", symbols);
        return format.format(num.toDouble())
    }
    private fun formatDecimal(num: String): String {
        val format = DecimalFormat("0.#######");
        return format.format(num.ifEmpty { "0" }.toDouble())
    }
    private fun calculate() {
        if (sign != Sign.Empty) {
            if ((sign == Sign.Divide || sign == Sign.Modulo) && number2 == "0") {
                txtDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, "36".toFloat())
                txtDisplay.text = "Cannot divide/modulo by zero!!!"
                return
            }
            var temp1 = number1.toDouble()
            var temp2 = number2.toDouble()
            var result: Double = when(sign) {
                Sign.Plus -> temp1 + temp2
                Sign.Minus -> temp1 - temp2
                Sign.Multiply -> temp1 * temp2
                Sign.Divide -> temp1 / temp2
                Sign.Modulo -> temp1 % temp2
                else -> 0.0
            }
            txtDisplay.text = formatNumber(result)
            txtDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, if (txtDisplay.text.length >= 15) "36".toFloat() else "48".toFloat())
            number1 = result.toString()
        }
    }

    enum class Sign(val value: String) {
        Plus("+"),
        Minus("-"),
        Multiply("X"),
        Divide("/"),
        Modulo("%"),
        Empty("")
    }
}

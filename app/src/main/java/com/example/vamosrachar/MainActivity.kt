package com.example.vamosrachar

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edValorConta = findViewById<EditText>(R.id.editValorConta)
        val edQtdPessoas = findViewById<EditText>(R.id.editQtdPessoas)
        val valorFinal = findViewById<TextView>(R.id.valorFinal)

        fun calcularConta(conta: Double, pessoas: Int): Double {
            if (pessoas > 1) {
                return conta / pessoas
            }
            return conta
        }

        edValorConta.addTextChangedListener (
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!edQtdPessoas.text.isNullOrEmpty() && !edValorConta.text.isNullOrEmpty()) {
                        val valor = String.format(
                            "%.2f",
                            calcularConta(
                                c.toString().toDouble(),
                                edQtdPessoas.text.toString().toInt()
                            )
                        )
                        valorFinal.text = "R$ " + valor
                    }

                }

                override fun afterTextChanged(c: Editable?) {
                }
            }
        )
    }
}
package com.example.vamosrachar

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import android.content.Intent


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

        val btnCompartilhar = findViewById<FloatingActionButton>(R.id.btnShare)
        val btnTTS = findViewById<FloatingActionButton>(R.id.btnTTS)

        btnCompartilhar!!.setOnClickListener {
            val intent = Intent()

            intent.setAction(Intent.ACTION_SEND)
            intent.setType("text/plain")

            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Segundo meus cálculos, deu " + valorFinal.text.toString() + " para cada."
            )

            startActivity(intent)
        }
    }
}
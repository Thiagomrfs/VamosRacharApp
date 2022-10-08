package com.example.vamosrachar

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    var tts: TextToSpeech? = null
    var btnTTS: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

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
        btnTTS = findViewById(R.id.btnTTS)

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

        btnTTS!!.setOnClickListener {
            chamarTTS(valorFinal.text.toString())
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale("pt", "BR"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","A língua especificada não é suportada!")
            } else {
                btnTTS!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Inicialização falhou!")
        }
    }

    fun chamarTTS(value: String){
        var message = "Separando a conta, ficou " + value + " para cada."
        tts!!.speak(
            message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            ""
        )
    }

    public override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
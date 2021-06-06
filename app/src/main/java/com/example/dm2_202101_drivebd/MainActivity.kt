package com.example.dm2_202101_drivebd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import  com.android.volley. *
import  com.android.volley.toolbox.StringRequest
import  com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSalvar.setOnClickListener {
            salvarDadosNaPlanilha ()
        }

    }

    private  fun  salvarDadosNaPlanilha () {
        val nomeD = edtNome.text.toString (). trim ()
        val codigoD = edtCodigo.text.toString (). trim ()
        val CPF = edtCPF.text.toString (). trim ()
        val RG = edtRG.text.toString (). trim ()

        // O endereço do script é aquele fornecido na implantação
        val stringRequest: StringRequest =  object  :  StringRequest (Request . Method . POST ,
            "https://script.google.com/macros/s/AKfycbxUi3Q6he60LwajHn1YMjx8ennwlpr21N14KJr6b_2jMTgNE3y5DvQKKmlZHEe6IiwlZQ/exec" ,
            object : Response.Listener< String > {
            override  fun  onResponse ( response :  String ) {
                if (response ==  " sucesso " ) {
                    Toast.makeText ( this@MainActivity, " Dados salvos com sucesso! " , Toast . LENGTH_SHORT ) .show ()
                } else {
                    Toast .makeText ( this@MainActivity, " Erro ao salvar dados! " , Toast . LENGTH_SHORT ) .show ()

                    Log.e ( " REPOSTA " , response);
                }
            }
        },
        object  : Response.ErrorListener {
            override  fun  onErrorResponse ( erro: VolleyError?) {
                //Log .e ( " REPOSTA " , error?.message);
            }
        }
        ) {
            override  fun  getParams (): MutableMap < String , String > {
                val params =  HashMap < String , String> ()
                params.put ( " ação " , " adicionar " );
                params.put ( " nome " , nomeD);
                params.put ( " codigo " , codigoD);
                params.put ( " CPF " , CPF);
                params.put ( " RG " , RG);
                return params
            }
        }

        val socketTimeOut =  50000
        val retryPolicy: RetryPolicy = DefaultRetryPolicy (socketTimeOut, 0 , DefaultRetryPolicy . DEFAULT_BACKOFF_MULT )
        stringRequest.setRetryPolicy (retryPolicy)
        val queue: RequestQueue  =  Volley.newRequestQueue ( this )
        queue.add (stringRequest)
    }
}


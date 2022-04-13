package com.diegocampos.ejercicioapipersonalizado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.diegocampos.ejercicioapipersonalizado.databinding.ActivityMainBinding
import com.diegocampos.ejercicioapipersonalizado.retrofit.AveDeChile
import com.diegocampos.ejercicioapipersonalizado.retrofit.AveDeChileAPIService
import com.diegocampos.ejercicioapipersonalizado.retrofit.RestEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {
            if(binding.txtAve.text.toString() != ""){
                binding.progressBar.visibility = View.VISIBLE
                invocarAPI(binding.txtAve.text.toString())
            }
            else{
                Toast.makeText(applicationContext,
                    "Ingrese un Ave",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }
    //
    // **** Los Uid de las aves para la búsqueda los saqué de https://aves.ninjas.cl/api/birds con
    // el Json Edito online porque no busca por nombre aún ****
    //
    private fun invocarAPI(ave: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada: AveDeChileAPIService = RestEngine.getRestEngine().create(AveDeChileAPIService::class.java)
            val resultado: Call<AveDeChile> = llamada.obtenerAve(ave)
            val p: AveDeChile? = resultado.execute().body()

            if(p != null){
                runOnUiThread {
                    binding.txtUid.text = p.uid.toString()
                    binding.txtName.text = "Nombre en Español:  "
                    binding.txtName.append(p.name.spanish + "\n")
                    binding.txtName.append("Nombre en Inglés:  ")
                    binding.txtName.append(p.name.english+ "\n")
                    binding.txtName.append("Nombre en Latín:  ")
                    binding.txtName.append(p.name.latin+ "\n")
                    binding.txtSpecies.text = p.species
                    binding.txtDidUknow.text = "¿Sabías que?\n "+p.didyouknow

                    CoroutineScope(Dispatchers.IO).launch {
                        val x: Int = async {
                            construirImagenes(p)
                        }.await()
                    }

                    runOnUiThread { binding.progressBar.visibility = View.GONE }

                }
            }
            else{
                runOnUiThread {
                    Toast.makeText(applicationContext,
                        "No se encontraron resultados...",
                        Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    fun construirImagenes(p: AveDeChile): Int{
        runOnUiThread {
            Glide.with(applicationContext)
                .load(p.images.main)
                .override(400, 400)
                .into(binding.imageView1);


            Glide.with(applicationContext)
                .load(p.images.main) //imagen repetida por arreglar
                .override(400, 400)
                .into(binding.imageView2);
        }
        return 1
    }

}
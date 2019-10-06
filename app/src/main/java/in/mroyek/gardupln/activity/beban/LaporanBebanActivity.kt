package `in`.mroyek.gardupln.activity.beban

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.model.BayResponse
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_laporan_beban.*
import java.util.*


class LaporanBebanActivity : AppCompatActivity() {
    private var adapterTransmisi: FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>? = null
    private var adapterTrafo: FirestoreRecyclerAdapter<BayResponse, TrafoHolder>? = null
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    val modelist: MutableList<TransmisiResponse>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_beban)
        init()
        upBeban()
    }

    private fun upBeban() {
        val queryTransmisi: Query = db!!.collection("Bay")
                .whereGreaterThan("namabay", "transmisi")
                .whereGreaterThan("namabay", "trafo")
        val transmisiResponse = FirestoreRecyclerOptions.Builder<TransmisiResponse>()
                .setQuery(queryTransmisi, TransmisiResponse::class.java)
                .build()
        /*val queryTrafo: Query = db.collection("Bay").whereGreaterThan("namabay", "trafo")
        val trafoResponse = FirestoreRecyclerOptions.Builder<BayResponse>()
                .setQuery(queryTrafo, BayResponse::class.java)
                .build()*/
        adapterTransmisi = object : FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>(transmisiResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmisiHolder {
                val viewTransmisi = LayoutInflater.from(parent.context).inflate(R.layout.item_transmisi, parent, false)
                return TransmisiHolder(viewTransmisi)
            }

            override fun onBindViewHolder(transmisiHolder: TransmisiHolder, position: Int, response: TransmisiResponse) {
                transmisiHolder.bindData(response)
                /* if (response.namabay!!.contains("transmisi") && response.namabay!!.contains("trafo")) {
                 }*/
                transmisiHolder.ettransmisi_i.text.toString()
                btn_upload_beban.setOnClickListener {

                }
            }

            override fun getItemCount(): Int {
                return modelist!!.size
            }
        }
        adapterTransmisi!!.notifyDataSetChanged()
        rv_beban_transmisi.adapter = adapterTransmisi

        /*adapterTrafo = object : FirestoreRecyclerAdapter<BayResponse, TrafoHolder>(trafoResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafoHolder {
                val viewTrafo = LayoutInflater.from(parent.context).inflate(R.layout.item_trafo, parent, false)
                return TrafoHolder(viewTrafo)
            }

            override fun onBindViewHolder(trafoHolder: TrafoHolder, p1: Int, response: BayResponse) {
                if (response.namabay!!.contains("trafo")) {
                    trafoHolder.bindData(response)
                }
            }
        }
        adapterTrafo!!.notifyDataSetChanged()
        rv_beban_trafo.adapter = adapterTrafo*/

    }


    @SuppressLint("WrongConstant")
    private fun init() {
        val layoutTransmisi = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val layoutTrafo = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_beban_transmisi.layoutManager = layoutTransmisi
        rv_beban_trafo.layoutManager = layoutTrafo
    }

    class TransmisiHolder(viewTransmisi: View) : RecyclerView.ViewHolder(viewTransmisi) {
        var tvTransmisi: TextView = viewTransmisi.findViewById(R.id.tv_beban_transmisi)
        val id: String = UUID.randomUUID().toString()
        var ettransmisi_i: EditText = viewTransmisi.findViewById(R.id.et_transmisi_I)
        var ettransmisi_u: EditText = viewTransmisi.findViewById(R.id.et_transmisi_U)
        @SuppressLint("ResourceType")
        fun bindData(response: TransmisiResponse) {
            tvTransmisi.text = response.namabay
        }
    }

    class TrafoHolder(viewTrafo: View) : RecyclerView.ViewHolder(viewTrafo) {
        var tvTrafo: TextView = viewTrafo.findViewById(R.id.tv_beban_trafo)
        fun bindData(response: BayResponse) {
            tvTrafo.text = response.namabay
        }
    }

    override fun onStart() {
        super.onStart()
        adapterTransmisi!!.startListening()
//        adapterTrafo!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterTransmisi!!.stopListening()
//        adapterTrafo!!.stopListening()
    }

    private fun upload(i: String, u: String, ini: String, p: String, q: String) {
        btn_upload_beban.setOnClickListener {
            val doc: HashMap<String, Any> = hashMapOf(
                    "u" to u,
                    "i" to i,
                    "p" to p,
                    "q" to q,
                    "in" to ini,
                    "beban" to ""
            )
            val id: String = db!!.collection("Laporan Beban").document().id
            /*   val batch: WriteBatch = db.batch()
               val transmisi = db.collection("Laporan Beban").document(id)
               batch.set(transmisi, doc)
               batch.commit()*/
            db.collection("Laporan Beban").document(id).set(doc)
                    .addOnCompleteListener { Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
//                    batch.commit()
        }
    }
}

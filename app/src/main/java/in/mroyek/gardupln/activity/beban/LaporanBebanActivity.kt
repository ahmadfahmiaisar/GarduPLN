package `in`.mroyek.gardupln.activity.beban

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.model.BayResponse
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_laporan_beban.*
import kotlinx.android.synthetic.main.item_trafo.view.*
import kotlinx.android.synthetic.main.item_transmisi.*
import java.util.*

class LaporanBebanActivity : AppCompatActivity() {
    private var adapterTransmisi: FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>? = null
    private var adapterTrafo: FirestoreRecyclerAdapter<BayResponse, TrafoHolder>? = null
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    lateinit var transmisi_U: String
    lateinit var transmisi_I: String
    lateinit var transmisi_P: String
    lateinit var transmisi_Q: String
    lateinit var transmisi_In: String
    lateinit var transmisi_Beban: String
    lateinit var trans: String
    lateinit var idTransmisi: String
    lateinit var trafo_U: String
    lateinit var trafo_I: String
    lateinit var trafo_P: String
    lateinit var trafo_Q: String
    lateinit var trafo_In: String
    lateinit var trafo_Beban: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_beban)
        init()
        upBeban()
    }

    private fun upBeban() {
        val queryTransmisi: Query = db!!.collection("Bay").whereGreaterThan("namabay", "transmisi")
        val transmisiResponse = FirestoreRecyclerOptions.Builder<TransmisiResponse>()
                .setQuery(queryTransmisi, TransmisiResponse::class.java)
                .build()
        val queryTrafo: Query = db.collection("Bay").whereGreaterThan("namabay", "trafo")
        val trafoResponse = FirestoreRecyclerOptions.Builder<BayResponse>()
                .setQuery(queryTrafo, BayResponse::class.java)
                .build()
        adapterTransmisi = object : FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>(transmisiResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmisiHolder {
                val viewTransmisi = LayoutInflater.from(parent.context).inflate(R.layout.item_transmisi, parent, false)
                return TransmisiHolder(viewTransmisi)
            }

            override fun onBindViewHolder(transmisiHolder: TransmisiHolder, p1: Int, response: TransmisiResponse) {
                if (response.namabay.toString().contains("transmisi")) {
                    transmisiHolder.bindData(response)
                    trans = response.namabay.toString()
//                    transmisi_I = transmisiHolder.et_transmisi_i.setText()
                    transmisi_U = response.u.toString()
                    transmisi_I = response.i.toString()
                    transmisi_P = response.p.toString()
                    transmisi_Q = response.q.toString()
                    transmisi_In = response.`in`.toString()
                    transmisi_Beban = response.namabay.toString()
                }
                btn_upload_beban.setOnClickListener {
                    val doc = hashMapOf(
                            "beban" to trans,
                            "u" to transmisi_U,
                            "i" to transmisi_I,
                            "p" to transmisi_P,
                            "q" to transmisi_Q,
                            "in" to transmisi_In,
                            "beban" to ""
                    )
                    db.collection("Laporan Beban").document(transmisi_U).set(doc)
                            .addOnCompleteListener { Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show() }
                            .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
                }
            }
        }
        adapterTransmisi!!.notifyDataSetChanged()
        rv_beban_transmisi.adapter = adapterTransmisi

        adapterTrafo = object : FirestoreRecyclerAdapter<BayResponse, TrafoHolder>(trafoResponse) {
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
        rv_beban_trafo.adapter = adapterTrafo

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
        val et_transmisi_u: EditText = viewTransmisi.findViewById(R.id.et_transmisi_U)
        val et_transmisi_i: EditText = viewTransmisi.findViewById(R.id.et_transmisi_I)
        val et_transmisi_p: EditText = viewTransmisi.findViewById(R.id.et_transmisi_P)
        val et_transmisi_q: EditText = viewTransmisi.findViewById(R.id.et_transmisi_Q)
        val et_transmisi_in: EditText = viewTransmisi.findViewById(R.id.et_transmisi_In)
        val et_transmisi_beban: EditText = viewTransmisi.findViewById(R.id.et_transmisi_beban)
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
        adapterTrafo!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterTransmisi!!.stopListening()
        adapterTrafo!!.stopListening()
    }
}

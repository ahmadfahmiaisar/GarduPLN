package `in`.mroyek.gardupln.activity.beban

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.history.HistoryTransmisi1Activity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_laporan_beban.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class LaporanBebanActivity : AppCompatActivity() {
    /*private var adapterTransmisi: FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>? = null
    private var adapterTrafo: FirestoreRecyclerAdapter<BayResponse, TrafoHolder>? = null
    val modelist: MutableList<TransmisiResponse>? = mutableListOf()*/
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_beban)
        et_transmisi_beban1.setOnClickListener {
            val beban1 = (et_transmisi_I1.text.toString().toDouble() / et_transmisi_In1.text.toString().toDouble())* 100/100
            et_transmisi_beban1.text = "$beban1 %"
        }
        et_transmisi_beban2.setOnClickListener {
            val beban2 = (et_transmisi_I2.text.toString().toDouble() / et_transmisi_In2.text.toString().toDouble())* 100/100
            et_transmisi_beban2.text = beban2.toString()
        }
        et_transmisi_beban3.setOnClickListener {
            val beban3 = (et_transmisi_I3.text.toString().toDouble() / et_transmisi_In3.text.toString().toDouble())* 100/100
            et_transmisi_beban3.text = beban3.toString()
        }
        et_transmisi_beban4.setOnClickListener {
            val beban4 = (et_transmisi_I4.text.toString().toDouble() / et_transmisi_In4.text.toString().toDouble())* 100/100
            et_transmisi_beban4.text = beban4.toString()
        }
        et_transmisi_beban5.setOnClickListener {
            val beban5 = (et_transmisi_I5.text.toString().toDouble() / et_transmisi_In5.text.toString().toDouble()) * (100/100)
            et_transmisi_beban5.text = beban5.toString()
        }
        val date = SimpleDateFormat("dd/M/yyyy")
        val curent = date.format(Date())
        tv_date.text = curent
        upload()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar, menu)
        return true
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ab_history -> startActivity(Intent(applicationContext, HistoryBebanActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun upload() {
        btn_upload_beban.setOnClickListener {
            val chek: Int = rg_time_beban.checkedRadioButtonId
            val valuetime: RadioButton = findViewById(chek)
            if (chek != -1) {
                val doc: HashMap<String, Any?> = hashMapOf(
                        "tanggal" to tv_date.text.toString().trim(),
                        "waktu" to valuetime.text.toString().trim(),
                        "transmisi1" to "transmisi "+et_beban_transmisi1.text.toString().trim(),
                        "u1" to et_transmisi_U1.text.toString().trim(),
                        "i1" to et_transmisi_I1.text.toString().trim(),
                        "p1" to et_transmisi_P1.text.toString().trim(),
                        "q1" to et_transmisi_Q1.text.toString().trim(),
                        "in1" to et_transmisi_In1.text.toString().trim(),
                        "beban1" to et_transmisi_beban1.text.toString().trim(),

                        "transmisi2" to "transmisi "+et_beban_transmisi2.text.toString().trim(),
                        "u2" to et_transmisi_U2.text.toString().trim(),
                        "i2" to et_transmisi_I2.text.toString().trim(),
                        "p2" to et_transmisi_P2.text.toString().trim(),
                        "q2" to et_transmisi_Q2.text.toString().trim(),
                        "in2" to et_transmisi_In2.text.toString().trim(),
                        "beban2" to et_transmisi_beban2.text.toString().trim(),

                        "transmisi3" to "transmisi "+et_beban_transmisi3.text.toString().trim(),
                        "u3" to et_transmisi_U3.text.toString().trim(),
                        "i3" to et_transmisi_I3.text.toString().trim(),
                        "p3" to et_transmisi_P3.text.toString().trim(),
                        "q3" to et_transmisi_Q3.text.toString().trim(),
                        "in3" to et_transmisi_In3.text.toString().trim(),
                        "beban3" to et_transmisi_beban3.text.toString().trim(),

                        "transmisi4" to "trafo " +et_beban_transmisi4.text.toString().trim(),
                        "u4" to et_transmisi_U4.text.toString().trim(),
                        "i4" to et_transmisi_I4.text.toString().trim(),
                        "p4" to et_transmisi_P4.text.toString().trim(),
                        "q4" to et_transmisi_Q4.text.toString().trim(),
                        "in4" to et_transmisi_In4.text.toString().trim(),
                        "beban4" to et_transmisi_beban4.text.toString().trim(),

                        "transmisi5" to "trafo "+et_beban_transmisi5.text.toString().trim(),
                        "u5" to et_transmisi_U5.text.toString().trim(),
                        "i5" to et_transmisi_I5.text.toString().trim(),
                        "p5" to et_transmisi_P5.text.toString().trim(),
                        "q5" to et_transmisi_Q5.text.toString().trim(),
                        "in5" to et_transmisi_In5.text.toString().trim(),
                        "beban5" to et_transmisi_beban5.text.toString().trim()
                )
                db!!.collection("Laporan Beban").document().set(doc)
                        .addOnCompleteListener { Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show() }
                        .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
            }
//            val id: String = db!!.collection("Laporan Beban").document().id
            /*   val batch: WriteBatch = db.batch()
               val transmisi = db.collection("Laporan Beban").document(id)
               batch.set(transmisi, doc)
               batch.commit()*/
        }
    }

    private fun upBeban() {
        /*val queryTransmisi: Query = db!!.collection("Bay")
                .whereGreaterThan("namabay", "transmisi")
                .whereGreaterThan("namabay", "trafo")
        val transmisiResponse = FirestoreRecyclerOptions.Builder<TransmisiResponse>()
                .setQuery(queryTransmisi, TransmisiResponse::class.java)
                .build()
        val queryTrafo: Query = db.collection("Bay").whereGreaterThan("namabay", "trafo")
        val trafoResponse = FirestoreRecyclerOptions.Builder<BayResponse>()
                .setQuery(queryTrafo, BayResponse::class.java)
                .build()*/
        /*adapterTransmisi = object : FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>(transmisiResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmisiHolder {
                val viewTransmisi = LayoutInflater.from(parent.context).inflate(R.layout.item_transmisi, parent, false)
                return TransmisiHolder(viewTransmisi)
            }

            override fun onBindViewHolder(transmisiHolder: TransmisiHolder, position: Int, response: TransmisiResponse) {
                transmisiHolder.bindData(response)
                *//* if (response.namabay!!.contains("transmisi") && response.namabay!!.contains("trafo")) {
                 }*//*
                transmisiHolder.ettransmisi_i.text.toString()
                btn_upload_beban.setOnClickListener {

                }
            }

            override fun getItemCount(): Int {
                return modelist!!.size
            }
        }*/
        /*adapterTransmisi!!.notifyDataSetChanged()
        rv_beban_transmisi.adapter = adapterTransmisi*/

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


    /*@SuppressLint("WrongConstant")
    private fun init() {
        val layoutTransmisi = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val layoutTrafo = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_beban_transmisi.layoutManager = layoutTransmisi
        rv_beban_trafo.layoutManager = layoutTrafo
    }*/

    /*class TransmisiHolder(viewTransmisi: View) : RecyclerView.ViewHolder(viewTransmisi) {
        var tvTransmisi: TextView = viewTransmisi.findViewById(R.id.tv_beban_transmisi)
        val id: String = UUID.randomUUID().toString()
        var ettransmisi_i: EditText = viewTransmisi.findViewById(R.id.et_transmisi_I)
        var ettransmisi_u: EditText = viewTransmisi.findViewById(R.id.et_transmisi_U)
        @SuppressLint("ResourceType")
        fun bindData(response: TransmisiResponse) {
            tvTransmisi.text = response.namabay
        }
    }*/

    /*class TrafoHolder(viewTrafo: View) : RecyclerView.ViewHolder(viewTrafo) {
        var tvTrafo: TextView = viewTrafo.findViewById(R.id.tv_beban_trafo)
        fun bindData(response: BayResponse) {
            tvTrafo.text = response.namabay
        }
    }*/

/*    override fun onStart() {
        super.onStart()
        adapterTransmisi!!.startListening()
//        adapterTrafo!!.startListening()
    }*/

    /*override fun onStop() {
        super.onStop()
        adapterTransmisi!!.stopListening()
//        adapterTrafo!!.stopListening()
    }*/

}

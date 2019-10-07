package `in`.mroyek.gardupln.activity.beban

import `in`.mroyek.gardupln.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
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
import kotlinx.android.synthetic.main.activity_laporan_beban.tv_date
import kotlinx.android.synthetic.main.activity_laporan_beban2.*
import kotlinx.android.synthetic.main.item_transmisi.*
import kotlinx.android.synthetic.main.item_transmisi.view.*
import java.text.SimpleDateFormat
import java.util.*


class LaporanBebanActivity2 : AppCompatActivity(), View.OnClickListener {
    lateinit var adapterTransmisi: FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>
    val modelist: MutableList<TransmisiResponse>? = mutableListOf()
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_beban2)

        val date = SimpleDateFormat("dd/M/yyyy")
        val curent = date.format(Date())
        tv_date.text = curent

        init()
        upBeban()

        btnUpload.setOnClickListener(this)
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

    private fun upBeban() {
        val queryTransmisi: Query = db!!.collection("Bay")

        //TODO iki iseh urung iso filter
        val transmisiResponse = FirestoreRecyclerOptions.Builder<TransmisiResponse>()
                .setQuery(queryTransmisi, TransmisiResponse::class.java)
                .build()
        Log.d("TES RESPONSE", "RESPONSE = $transmisiResponse")
        adapterTransmisi = object : FirestoreRecyclerAdapter<TransmisiResponse, TransmisiHolder>(transmisiResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmisiHolder {
                val viewTransmisi = LayoutInflater.from(parent.context).inflate(R.layout.item_transmisi, parent, false)
                return TransmisiHolder(viewTransmisi)
            }

            override fun onBindViewHolder(transmisiHolder: TransmisiHolder, position: Int, response: TransmisiResponse) {
                Log.d("COBA", "AAAAAAAAAA")

                transmisiHolder.bindData(response)

                if (response.namabay!!.contains("transmisi") || response.namabay!!.contains("trafo")) {

                }

            }

        }
        adapterTransmisi.notifyDataSetChanged()
        rvBebanTransmisi.adapter = adapterTransmisi


    }

    private fun upload() {
        val childCount = rvBebanTransmisi.childCount
        for (i in 0..childCount){
            val childHolder =  rvBebanTransmisi.findViewHolderForLayoutPosition(i)

            val doc = hashMapOf(
                    "namabay" to tv_beban_transmisi.text.toString().trim(),
                    "u" to childHolder?.itemView?.et_transmisi_U?.text.toString().trim(),
                    "i" to childHolder?.itemView?.et_transmisi_I?.text.toString().trim(),
                    "p" to childHolder?.itemView?.et_transmisi_P?.text.toString().trim(),
                    "q" to childHolder?.itemView?.et_transmisi_Q?.text.toString().trim()
            )

            db!!.collection("Bay").document().set(doc)
                    .addOnCompleteListener { Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }


        }


    }

    @SuppressLint("WrongConstant")
    private fun init() {
        val layoutTransmisi = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvBebanTransmisi.layoutManager = layoutTransmisi
    }

    class TransmisiHolder(viewTransmisi: View) : RecyclerView.ViewHolder(viewTransmisi) {

        var tvTransmisi: TextView = viewTransmisi.findViewById(R.id.tv_beban_transmisi)
        val id: String = UUID.randomUUID().toString()
        var ettransmisi_u: EditText = viewTransmisi.findViewById(R.id.et_transmisi_U)
        var ettransmisi_i: EditText = viewTransmisi.findViewById(R.id.et_transmisi_I)
        var ettransmisi_p: EditText = viewTransmisi.findViewById(R.id.et_transmisi_P)
        var ettransmisi_q: EditText = viewTransmisi.findViewById(R.id.et_transmisi_Q)

        @SuppressLint("ResourceType")
        fun bindData(response: TransmisiResponse) {
            tvTransmisi.text = response.namabay
            ettransmisi_u.setText(response.u)
            ettransmisi_i.setText(response.i)
            ettransmisi_p.setText(response.p)
            ettransmisi_q.setText(response.q)
        }
    }



    override fun onStart() {
        super.onStart()
        adapterTransmisi.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterTransmisi.stopListening()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnUpload -> {
                upload()
            }
        }
    }
}

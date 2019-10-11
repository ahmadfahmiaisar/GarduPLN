package `in`.mroyek.gardupln.activity.fuad

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.beban.HistoryBebanActivity
import `in`.mroyek.gardupln.activity.beban.LaporanBebanResponses
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_laporan_beban.tv_date
import kotlinx.android.synthetic.main.activity_laporan_beban2.*
import kotlinx.android.synthetic.main.item_transmisi.view.*
import java.text.SimpleDateFormat
import java.util.*


class LaporanBebanActivity2 : AppCompatActivity(), View.OnClickListener {
    lateinit var adapterTransmisi: FirestoreRecyclerAdapter<LaporanBebanResponses, TransmisiHolder>
    //    val modelist: MutableList<LaporanBebanResponses>? = mutableListOf()
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    //    lateinit var id: String
    lateinit var date: DatePickerDialog.OnDateSetListener

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_beban2)
        init()
        setTanggal()
        upBeban()
        btnUpload.setOnClickListener(this)
    }

    private fun setTanggal() {
        val myCalendar = Calendar.getInstance()
        date = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            tv_date.text = sdf.format(myCalendar.time)
        }
        tv_date.setOnClickListener {
            DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
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
        val query = db!!.collection("Bay")
                .whereGreaterThanOrEqualTo("namabay", "trafo")
        query.whereGreaterThanOrEqualTo("namabay", "transmisi")
//        query.whereLessThanOrEqualTo("namabay", "trafo")
        val queryResponse = FirestoreRecyclerOptions.Builder<LaporanBebanResponses>()
                .setQuery(query, LaporanBebanResponses::class.java)
                .build()
        adapterTransmisi = object : FirestoreRecyclerAdapter<LaporanBebanResponses, TransmisiHolder>(queryResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmisiHolder {
                val viewTransmisi = LayoutInflater.from(parent.context).inflate(R.layout.item_transmisi, parent, false)
                return TransmisiHolder(viewTransmisi)
            }

            override fun onBindViewHolder(transmisiHolder: TransmisiHolder, position: Int, response: LaporanBebanResponses) {
                transmisiHolder.bindData(response)
            }
        }
        adapterTransmisi.notifyDataSetChanged()
        rvBebanTransmisi.adapter = adapterTransmisi
    }

    //    for(int i = 0; i < itemCount; i++){}
    private fun upload() {
        val itemCount = rvBebanTransmisi.adapter!!.itemCount - 1
        val childCountsRV = 0..itemCount
        childCountsRV.forEach { it ->
            val childHolder = rvBebanTransmisi.findViewHolderForAdapterPosition(it)
            val cheid = rg_time_beban.checkedRadioButtonId
            val valueRg = findViewById<RadioButton>(cheid)
            val namabay = childHolder?.itemView?.tv_beban_transmisi?.text.toString()
            val u = childHolder?.itemView?.et_transmisi_U?.text.toString().trim()
            val i = childHolder?.itemView?.et_transmisi_I?.text.toString().trim()
            val p = childHolder?.itemView?.et_transmisi_P?.text.toString().trim()
            val q = childHolder?.itemView?.et_transmisi_Q?.text.toString().trim()
            val `in` = childHolder?.itemView?.et_transmisi_In?.text.toString().trim()
            val beban = childHolder?.itemView?.et_transmisi_beban?.text.toString().trim()

            val childnamabay = rvBebanTransmisi.findViewHolderForAdapterPosition(it)?.itemView?.findViewById<TextView>(R.id.tv_beban_transmisi)?.text.toString().trim()

            if (checkduplicatDoc(tv_date.text.toString())) {
                db!!.collection("Bay").document(tv_date.text.toString())
                        .update("laporan $namabay", FieldValue.arrayUnion(u, i, p, q, beban))
                        .addOnCompleteListener { Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show() }
                        .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
            } else{
                val doc = hashMapOf(
                        "namabay" to namabay,
                        "tanggal" to tv_date.text.toString().trim(),
                        "waktu" to valueRg.text.toString().trim(),
                        "laporan $namabay" to arrayListOf(u, i, p, q, beban)
                )
                db!!.collection("Bay").document(tv_date.text.toString())
                        .set(doc)
                        .addOnCompleteListener { Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show() }
                        .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun checkduplicatDoc(tanggal: String): Boolean {
        val itemcount = rvBebanTransmisi.adapter?.itemCount
        var hasil = false

        for (i in 0..itemcount!!) {
            val tanggale = rvBebanTransmisi.findViewHolderForAdapterPosition(i)?.itemView?.findViewById<TextView>(R.id.tv_beban_transmisi)?.text.toString()

            if (tanggal.equals(tanggale, true)) {
                return true
            }
        }
        return hasil
    }

    @SuppressLint("WrongConstant")
    private fun init() {
        val layoutTransmisi = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvBebanTransmisi.layoutManager = layoutTransmisi
    }

    class TransmisiHolder(viewTransmisi: View?) : RecyclerView.ViewHolder(viewTransmisi!!) {
        var tvTransmisi: TextView = viewTransmisi!!.findViewById(R.id.tv_beban_transmisi)
        @SuppressLint("ResourceType")
        fun bindData(response: LaporanBebanResponses) {
            tvTransmisi.text = response.namabay.toString()
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
        if (rg_time_beban.checkedRadioButtonId != -1) {
            when (p0?.id) {
                R.id.btnUpload -> {
                    upload()
                }
            }
        } else {
            Toast.makeText(applicationContext, "isi waktunya dulu", Toast.LENGTH_SHORT).show()
        }
    }
}
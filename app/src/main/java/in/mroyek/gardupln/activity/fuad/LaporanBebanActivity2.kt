package `in`.mroyek.gardupln.activity.fuad

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.beban.LaporanBebanResponses
import `in`.mroyek.gardupln.activity.history.HistoryBebanActivity
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_laporan_beban.tv_date
import kotlinx.android.synthetic.main.activity_laporan_beban2.*
import kotlinx.android.synthetic.main.item_transmisi.view.*
import java.text.SimpleDateFormat
import java.util.*


class LaporanBebanActivity2 : AppCompatActivity(), View.OnClickListener {
    lateinit var adapterTransmisi: FirestoreRecyclerAdapter<LaporanBebanResponses, TransmisiHolder>
    //    val modelist: MutableList<LaporanBebanResponses>? = mutableListOf()
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    //    lateinit var tanggal: String
    lateinit var date: DatePickerDialog.OnDateSetListener

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_beban2)
        init()
        setTanggal()
        getdata()
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

    private fun getdata() {
        val query = db!!.collection("Bay")
                .whereGreaterThanOrEqualTo("namabay", "trafo")
        query.whereGreaterThanOrEqualTo("namabay", "transmisi")
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

    private fun upload() {
        val itemCount = rvBebanTransmisi.adapter?.itemCount
        val childCountsRV = 0.rangeTo(itemCount!!)
        childCountsRV.forEach {
            val childHolder = rvBebanTransmisi.findViewHolderForLayoutPosition(it)
            val cheid = rg_time_beban.checkedRadioButtonId
            val valueRg = findViewById<RadioButton>(cheid)
            val namabay = childHolder?.itemView?.tv_beban_transmisi?.text.toString().trim()
            val u = childHolder?.itemView?.et_transmisi_U?.text.toString().trim()
            val i = childHolder?.itemView?.et_transmisi_I?.text.toString().trim()
            val p = childHolder?.itemView?.et_transmisi_P?.text.toString().trim()
            val q = childHolder?.itemView?.et_transmisi_Q?.text.toString().trim()
            val `in` = childHolder?.itemView?.et_transmisi_In?.text.toString().trim()
            val beban = childHolder?.itemView?.et_transmisi_beban?.text.toString().trim()

            val date = tv_date.text.toString().trim()
            val listArraynya = "laporan $namabay"
            /*val doc = hashMapOf(
                    "namabay" to namabay,
                    "tanggal" to tv_date.text.toString().trim(),
                    "waktu" to valueRg.text.toString().trim(),
                    listArraynya to arrayListOf(u, i, p, q, beban, `in`)
            )*/
            val doc = hashMapOf(
                    "namabay" to namabay,
                    "tanggal" to tv_date.text.toString().trim(),
                    "waktu" to valueRg.text.toString().trim(),
                    "u" to u,
                    "i" to i,
                    "p" to p,
                    "q" to q,
                    "in" to `in`,
                    "beban" to beban
            )
            val docpor = hashMapOf(
                    "tanggal" to tv_date.text.toString().trim(),
                    "waktu" to valueRg.text.toString().trim()
            )
            var row = it
            db!!.collection("Laporin").document(date).set(docpor)
            if (namabay != "null") {
                db.collection("Laporin").document(date).collection("Laporr").document(namabay.trim()).set(doc, SetOptions.merge())
                        .addOnCompleteListener {
//                            Toast.makeText(applicationContext, "hm oke", Toast.LENGTH_SHORT).show()
//                            Log.d("CUK", "Row ke $row")
                        }.addOnFailureListener {
                            Log.d("CUK ERROR", "ERROR ke $row dan $it")
                            db.collection("Laporin").document(date).collection("Laporr").document(namabay.trim()).set(doc, SetOptions.merge())
                        }
            }else {
                if(it==3){
                    db.collection("Laporin").document(date).collection("Laporr").document(namabay.trim()).set(doc, SetOptions.merge())
                }
                Log.d("CUK dikiro Null", "ERROR ke $row")
            }
//            return@forEach

            /*val documentReference = db!!.collection("Laporin").document(date)
            documentReference.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
//                            Toast.makeText(applicationContext, "Document = ISI", Toast.LENGTH_SHORT).show()
                            if (cekduplikat2(document)) {
                                Toast.makeText(applicationContext, "Tanggal Duplikat", Toast.LENGTH_SHORT).show()
                                db.collection("Laporin").document(date)
                                        .update(listArraynya, FieldValue.delete())
                                        .addOnCompleteListener { Toast.makeText(applicationContext, "updated", Toast.LENGTH_SHORT).show() }
                                        .addOnFailureListener {
                                            Toast.makeText(applicationContext, "gagal ini", Toast.LENGTH_SHORT).show()
                                        }
                                db.collection("Laporin").document(date)
                                        .set(doc, SetOptions.merge())
                                        .addOnCompleteListener { Toast.makeText(applicationContext, "okeeh hehe", Toast.LENGTH_SHORT).show() }
                                        .addOnFailureListener {
                                            Toast.makeText(applicationContext, "gagal ini 2", Toast.LENGTH_SHORT).show()
                                        }

                                Log.d("MASUK", "MASUK DUPLIKAT")
                            } else {
                                db.collection("Laporin").document(date)
                                        .update(listArraynya, FieldValue.arrayUnion(u, i, p, q, beban, `in`))
                                        .addOnCompleteListener { Toast.makeText(applicationContext, "updateddddd", Toast.LENGTH_SHORT).show() }
                                        .addOnFailureListener {
                                            Toast.makeText(applicationContext, "gagal ini", Toast.LENGTH_SHORT).show()
                                        }
                                Toast.makeText(applicationContext, "Tidak ada field", Toast.LENGTH_SHORT).show()
                                Log.d("MASUK", "MASUK TIDAK DUPLIKAT")
                            }
                        } else {
                            Log.d("MASUK", "MASUK TIDAK ADA DOKUMEN $it")

                            db.collection("Laporin").document(date)
                                    .set(doc, SetOptions.merge())
                                    .addOnCompleteListener {
                                        Toast.makeText(applicationContext, "okeeh 2", Toast.LENGTH_SHORT).show()
                                        Log.d("MASUK", "OKED $it")
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(applicationContext, "gagal ini 2", Toast.LENGTH_SHORT).show()
                                    }

                            Toast.makeText(applicationContext, "Document = NULL $it", Toast.LENGTH_SHORT).show()
                        }

                    }*/
        }
    }

    private fun checkduplicat(listarraynya: String): Boolean {
        val count = rvBebanTransmisi.adapter?.itemCount
        var hasil = false

        for (posisine in 0..count!!) {
            val namalist = rvBebanTransmisi.findViewHolderForAdapterPosition(posisine)?.itemView?.findViewById<TextView>(R.id.tv_beban_transmisi)?.text.toString()
            if (listarraynya.contains(namalist)) {
                hasil = true
            }
        }
        return hasil
    }

    fun cekduplikat2(document: DocumentSnapshot): Boolean {
        var hasil = false
        val count = rvBebanTransmisi.adapter?.itemCount
//        count?.minus(1)

//        var string = ""
        for (i in 0..count!!) {
            val namalist = rvBebanTransmisi.findViewHolderForAdapterPosition(i)?.itemView?.findViewById<TextView>(R.id.tv_beban_transmisi)?.text.toString()
//            string += "FIELD $namalist = ${document.contains("laporan $namalist")} | "
//            Toast.makeText(applicationContext, "Duplikasi", Toast.LENGTH_SHORT).show()
            if (document.contains("laporan $namalist")) {
//                Toast.makeText(applicationContext, "Duplikasi", Toast.LENGTH_SHORT).show()
                hasil = true
            }
        }

//        Toast.makeText(applicationContext, "DAFTAR = $string", Toast.LENGTH_SHORT).show()

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

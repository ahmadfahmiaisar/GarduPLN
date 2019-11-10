package `in`.mroyek.gardupln.activity.history.gangguan

import `in`.mroyek.gardupln.R
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_gangguan_history.*

class DetailGangguanHistory : AppCompatActivity() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var tanggal: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_gangguan_history)
        val intent = intent.extras
        tanggal = intent?.get("tanggal").toString()
        getData()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.copy, menu)
        return true
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.copy -> {
                copyTextToClipboard()
                Toast.makeText(applicationContext, "sudah tersalin", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    var bulkText: String = ""
    private fun copyTextToClipboard() {
        bulkText += "${tv_Bay_gangguan.text} \n"
        bulkText += "${tv_Waktu_gangguan.text} \n"
        bulkText += "${tv_Tanggal_gangguan.text} \n"
        bulkText += "${tv_Sinyal_gangguan.text} \n"
        bulkText += "${tv_CounterGangguan_gangguan.text} \n"
        bulkText += "${tv_CounterPmt_gangguan.text} \n"
        bulkText += "${tv_CounterLa_gangguan.text} \n"
        bulkText += "${tv_Bpadam_gangguan.text} \n"
        bulkText += "${tv_BNormal_gangguan.text} \n"
        bulkText += "${tv_Analisa_gangguan.text} \n"
        val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("Laporan", bulkText)
        clipboardManager.setPrimaryClip(clipData)
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        val docref = db.collection("Gangguan").document(tanggal)
        docref.get().addOnCompleteListener {
            if (it.isSuccessful){
                val doc: DocumentSnapshot? = it.result
                val bay = doc?.get("bay").toString()
                val tanggal = doc?.get("tanggal").toString()
                val waktu = doc?.get("waktu").toString()
                val sinyal = doc?.get("signal").toString()
                val fl = doc?.get("fl").toString()
                val catatan = doc?.get("catatan").toString()
                val contPMT = doc?.get("conPMT").toString()
                val contGangguan = doc?.get("conGangguan").toString()
                val contLa = doc?.get("conLa").toString()
                val bpadam = doc?.get("bpadam").toString()
                val bnormal = doc?.get("bnormal").toString()
                val analisa = doc?.get("analisa").toString()
                tv_Bay_gangguan.text = "Nama bay: $bay"
                tv_Tanggal_gangguan.text = "Tanggal: $tanggal"
                tv_Waktu_gangguan.text = "Waktu: $waktu"
                tv_Fl_gangguan.text = "FL: $fl"
                tv_Catatan_gangguan.text = "Catatan: \n $catatan"
                tv_CounterPmt_gangguan.text = "Counter PMT \n ${contPMT.replace("_n", "\n")}"
                tv_CounterGangguan_gangguan.text = "Counter PMT \n ${contGangguan.replace("_n", "\n")}"
                tv_CounterLa_gangguan.text = "Counter PMT \n ${contLa.replace("_n", "\n")}"
                tv_Sinyal_gangguan.text = "Sinyal \n "+ sinyal.replace("_n", "\n")
                tv_Bpadam_gangguan.text = "Beban Sebelum Padam \n $bpadam  A"
                tv_BNormal_gangguan.text = "Beban Setelah Normal \n $bnormal  A"
                tv_Analisa_gangguan.text = "Analisa Penyebab \n $analisa"
            }
        }
    }
}

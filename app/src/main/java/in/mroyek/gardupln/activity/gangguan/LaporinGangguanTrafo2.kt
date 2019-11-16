package `in`.mroyek.gardupln.activity.gangguan

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.GarduActivity
import `in`.mroyek.gardupln.activity.history.gangguan.HistoryGangguanActivity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_laporin_gangguan_trafo2.*
import kotlinx.android.synthetic.main.layout_cekbok_trafo2.*
import kotlinx.android.synthetic.main.layout_counter_trafo2.*
import java.text.SimpleDateFormat
import java.util.*

class LaporinGangguanTrafo2 : AppCompatActivity() {

    lateinit var date: DatePickerDialog.OnDateSetListener
    lateinit var time: TimePickerDialog.OnTimeSetListener
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    lateinit var idgardu: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporin_gangguan_trafo2)
        val sharePref: SharedPreferences = getSharedPreferences("idgardunya", 0)
        idgardu = sharePref.getString(GarduActivity.IDGARDU, "").toString()
        setDateTime()
        upload(idgardu)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar, menu)
        return true
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ab_history -> startActivity(Intent(applicationContext, HistoryGangguanActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setDateTime() {
        val calendar = Calendar.getInstance()
        date = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val dateFormat = "dd-MM-yyyy"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
            tvDate_gangguan_trafo2.text = simpleDateFormat.format(calendar.time)
        }
        tvDate_gangguan_trafo2.setOnClickListener {
            DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        time = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR, hour)
            calendar.set(Calendar.MINUTE, minute)
            val timeFormat = "HH:mm"
            val sdf = SimpleDateFormat(timeFormat, Locale.US)
            tvTime_gangguan_trafo2.text = sdf.format(calendar.time)
        }
        tvTime_gangguan_trafo2.setOnClickListener { TimePickerDialog(this, time, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show() }
    }
    private fun upload(idgardu: String) {
        var msg = ""
        btn_lapor_gangguan_trafo2.setOnClickListener {
            msg = getCekBox(msg)
            val countPMT = "R: ${et_counterPMT_R_trafo2.text} _n S: ${et_counterPMT_S_trafo2.text} _n T: ${et_counterPMT_T_trafo2.text} _n "
            val countGangguan = "R: ${et_counterGangguan_R_trafo2.text} _n S: ${et_counterGangguan_S_trafo2.text} _n T: ${et_counterGangguan_T_trafo2.text} _n "
            val countLa = "R: ${et_counterLA_R_trafo2.text} _n S: ${et_counterLA_S_trafo2.text} _n T: ${et_counterLA_T_trafo2.text} _n "
            val docLapor = hashMapOf(
                    "bay" to "Trafo 2",
                    "tanggal" to tvDate_gangguan_trafo2.text.toString(),
                    "waktu" to tvTime_gangguan_trafo2.text.toString(),
                    "signal" to msg,
//                    "fl" to et_gangguan_FL_trafo2.text.toString(),
                    "catatan" to et_gangguan_Catatan_trafo2.text.toString(),
                    "conPMT" to countPMT,
                    "conGangguan" to countGangguan,
                    "conLa" to countLa,
                    "bpadam" to et_gangguan_bebanPadam_trafo2.text.toString(),
                    "bnormal" to et_gangguan_bebanNormal_trafo2.text.toString(),
                    "analisa" to et_gangguan_analisa_trafo2.text.toString()
            )
            val documentPath = tvDate_gangguan_trafo2.text.toString()
            if (documentPath.isEmpty()) {
                Toast.makeText(applicationContext, "isi dulu tanggalnya", Toast.LENGTH_SHORT).show()
            } else {
                db!!.collection("Gardu").document(idgardu).collection("Gangguan").document(documentPath).set(docLapor)
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                btn_lapor_gangguan_trafo2.isEnabled = false
                btn_lapor_gangguan_trafo2.setText("sudah ke upload")
                val toast = Toast.makeText(applicationContext, "sudah ke upload", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }

    private fun getCekBox(msg: String): String {
        var msg1 = msg
        if (cek1_trafo2.isChecked) msg1 += "-${cek1_trafo2.text} \n"
        if (cek2_trafo2.isChecked) msg1 += "-${cek2_trafo2.text} \n"
        if (cek3_trafo2.isChecked) msg1 += "-${cek3_trafo2.text} \n"
        if (cek4_trafo2.isChecked) msg1 += "-${cek4_trafo2.text} \n"
        if (cek5_trafo2.isChecked) msg1 += "-${cek5_trafo2.text} \n"
        if (cek6_trafo2.isChecked) msg1 += "-${cek6_trafo2.text} \n"
        if (cek7_trafo2.isChecked) msg1 += "-${cek7_trafo2.text} \n"
        if (cek8_trafo2.isChecked) msg1 += "-${cek8_trafo2.text} \n"
        if (cek9_trafo2.isChecked) msg1 += "-${cek9_trafo2.text} \n"
        if (cek10_trafo2.isChecked) msg1 += "-${cek10_trafo2.text} \n"
        if (cek11_trafo2.isChecked) msg1 += "-${cek11_trafo2.text} \n"
        if (cek12_trafo2.isChecked) msg1 += "-${cek12_trafo2.text} \n"
        if (cek13_trafo2.isChecked) msg1 += "-${cek13_trafo2.text} \n"
        if (cek14_trafo2.isChecked) msg1 += "-${cek14_trafo2.text} \n"
        if (cek15_trafo2.isChecked) msg1 += "-${cek15_trafo2.text} \n"
        if (cek16_trafo2.isChecked) msg1 += "-${cek16_trafo2.text} \n"
        if (cek17_trafo2.isChecked) msg1 += "-${cek17_trafo2.text} \n"
        if (cek18_trafo2.isChecked) msg1 += "-${cek18_trafo2.text} \n"
        if (cek19_trafo2.isChecked) msg1 += "-${cek19_trafo2.text} \n"
        if (cek20_trafo2.isChecked) msg1 += "-${cek20_trafo2.text} \n"
        return msg1
    }
}

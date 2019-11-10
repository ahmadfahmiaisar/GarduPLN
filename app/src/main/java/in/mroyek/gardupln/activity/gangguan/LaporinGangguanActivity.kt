package `in`.mroyek.gardupln.activity.gangguan

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.history.beban.HistoryBebanActivity
import `in`.mroyek.gardupln.activity.history.gangguan.HistoryGangguanActivity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_laporin_gangguan.*
import kotlinx.android.synthetic.main.layout_cekbok.*
import kotlinx.android.synthetic.main.layout_counter.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class LaporinGangguanActivity : AppCompatActivity() {


    lateinit var date: DatePickerDialog.OnDateSetListener
    lateinit var time: TimePickerDialog.OnTimeSetListener
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    //    lateinit var adapterGangguan = FirestoreRecyclerAdapter<>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporin_gangguan)
        Log.d("HINT", "text ${tvDate_gangguan.text.isNullOrEmpty()}")
        setDateTime()
        upload()
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
            tvDate_gangguan.text = simpleDateFormat.format(calendar.time)
        }
        tvDate_gangguan.setOnClickListener {
            DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        time = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR, hour)
            calendar.set(Calendar.MINUTE, minute)
            val timeFormat = "HH:mm"
            val sdf = SimpleDateFormat(timeFormat, Locale.US)
            tvTime_gangguan.text = sdf.format(calendar.time)
        }
        tvTime_gangguan.setOnClickListener { TimePickerDialog(this, time, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show() }
    }

    private fun upload() {
        var msg = ""
        btn_lapor_gangguan.setOnClickListener {
            msg = getCekBox(msg)
            /*if (cek_r_phase_trip.isChecked) msg += "${cek_r_phase_trip.text} _n "
            if (cek_s_phase_trip.isChecked) msg += "${cek_s_phase_trip.text} _n "
            if (cek_t_phase_trip.isChecked) msg += "${cek_t_phase_trip.text} _n "
            if (cek_vt_alarm.isChecked) msg += "${cek_vt_alarm.text} _n "
            if (cek_relay_inoperative.isChecked) msg += "${cek_relay_inoperative.text} _n "*/
            val countPMT = "R: ${et_counterPMT_R.text} _n S: ${et_counterPMT_S.text} _n T: ${et_counterPMT_T.text} _n "
            val countGangguan = "R: ${et_counterGangguan_R.text} _n S: ${et_counterGangguan_S.text} _n T: ${et_counterGangguan_T.text} _n "
            val countLa = "R: ${et_counterLA_R.text} _n S: ${et_counterLA_S.text} _n T: ${et_counterLA_T.text} _n "
            val docLapor = hashMapOf(
                    "bay" to "bay",
                    "tanggal" to tvDate_gangguan.text.toString(),
                    "waktu" to tvTime_gangguan.text.toString(),
                    "signal" to msg,
                    "fl" to et_gangguan_FL.text.toString(),
                    "catatan" to et_gangguan_Catatan.text.toString(),
                    "conPMT" to countPMT,
                    "conGangguan" to countGangguan,
                    "conLa" to countLa,
                    "bpadam" to et_gangguan_bebanPadam.text.toString(),
                    "bnormal" to et_gangguan_bebanNormal.text.toString(),
                    "analisa" to et_gangguan_analisa.text.toString()
            )
            /*val docDate = hashMapOf(
                    "tanggal" to tvDate_gangguan.text.toString(),
                    "waktu" to tvTime_gangguan.text.toString()
            )*/
            val documentPath = tvDate_gangguan.text.toString()
            if (documentPath.isEmpty()) {
                Toast.makeText(applicationContext, "isi dulu tanggalnya", Toast.LENGTH_SHORT).show()
            } else {
                db!!.collection("Gangguan").document(documentPath).set(docLapor)
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                btn_lapor_gangguan.isEnabled = false
                btn_lapor_gangguan.setText("sudah ke upload")
                val toast = Toast.makeText(applicationContext, "sudah ke upload", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }

    private fun getCekBox(msg: String): String {
        var msg1 = msg
        if (cek_r_phase_trip.isChecked) msg1 += "${cek_r_phase_trip.text} \n"
        if (cek_s_phase_trip.isChecked) msg1 += "${cek_s_phase_trip.text} \n"
        if (cek_t_phase_trip.isChecked) msg1 += "${cek_t_phase_trip.text} \n"
        if (cek_vt_alarm.isChecked) msg1 += "${cek_vt_alarm.text} \n"
        if (cek_relay_inoperative.isChecked) msg1 += "${cek_relay_inoperative.text} \n"
        if (cek_aided_trip.isChecked) msg1 += "${cek_aided_trip.text} \n"
        if (cek_zone2_tmdelay_trip.isChecked) msg1 += "${cek_zone3_tm_delaytrip.text} \n"
        if (cek_trip_on_close.isChecked) msg1 += "${cek_trip_on_close.text} \n"
        if (cek_psb.isChecked) msg1 += "${cek_psb.text} \n"
        if (cek_mcb_trip.isChecked) msg1 += "${cek_mcb_trip.text} \n"
        if (cek_backup_protect_trip.isChecked) msg1 += "${cek_backup_protect_trip.text} \n"
        if (cek_trip_coil_sup.isChecked) msg1 += "${cek_trip_coil_sup.text} \n"
        if (cek_mk_acdc_mcb_trip.isChecked) msg1 += "${cek_mk_acdc_mcb_trip.text} \n"
        if (cek_cb_low_pressure_alarm.isChecked) msg1 += "${cek_cb_low_pressure_alarm.text} \n"
        if (cek_general_lockout.isChecked) msg1 += "${cek_general_lockout.text} \n"
        if (cek_dc_fail.isChecked) msg1 += "${cek_dc_fail.text} \n"
        if (cek_cb_pole_discrepancy.isChecked) msg1 += "${cek_cb_pole_discrepancy.text} \n"
        if (cek_ac_fail.isChecked) msg1 += "${cek_ac_fail.text} \n"
        if (cek_vt_source_failure.isChecked) msg1 += "${cek_vt_source_failure.text} \n"
        return msg1
    }
}

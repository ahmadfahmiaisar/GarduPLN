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
import kotlinx.android.synthetic.main.activity_laporin_gangguan_purworejo.*
import kotlinx.android.synthetic.main.layout_cekbok_purworejo.*
import kotlinx.android.synthetic.main.layout_counter_purworejo.*
import java.text.SimpleDateFormat
import java.util.*

class LaporinGangguanPurworejo : AppCompatActivity() {

    lateinit var date: DatePickerDialog.OnDateSetListener
    lateinit var time: TimePickerDialog.OnTimeSetListener
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    lateinit var idgardu: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporin_gangguan_purworejo)
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
            tvDate_gangguan_purworejo.text = simpleDateFormat.format(calendar.time)
        }
        tvDate_gangguan_purworejo.setOnClickListener {
            DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        time = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR, hour)
            calendar.set(Calendar.MINUTE, minute)
            val timeFormat = "HH:mm"
            val sdf = SimpleDateFormat(timeFormat, Locale.US)
            tvTime_gangguan_purworejo.text = sdf.format(calendar.time)
        }
        tvTime_gangguan_purworejo.setOnClickListener { TimePickerDialog(this, time, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show() }
    }

    private fun upload(idgardu: String) {
        var msg = ""
        btn_lapor_gangguan_purworejo.setOnClickListener {
            msg = getCekBox(msg)
            val countPMT = "R: ${et_counterPMT_R_purworejo.text} _n S: ${et_counterPMT_S_purworejo.text} _n T: ${et_counterPMT_T_purworejo.text} _n "
            val countGangguan = "R: ${et_counterGangguan_R_purworejo.text} _n S: ${et_counterGangguan_S_purworejo.text} _n T: ${et_counterGangguan_T_purworejo.text} _n "
            val countLa = "R: ${et_counterLA_R_purworejo.text} _n S: ${et_counterLA_S_purworejo.text} _n T: ${et_counterLA_T_purworejo.text} _n "
            val docLapor = hashMapOf(
                    "bay" to "Purworejo",
                    "tanggal" to tvDate_gangguan_purworejo.text.toString(),
                    "waktu" to tvTime_gangguan_purworejo.text.toString(),
                    "signal" to msg,
                    "fl" to et_gangguan_FL_purworejo.text.toString(),
                    "catatan" to et_gangguan_Catatan_purworejo.text.toString(),
                    "conPMT" to countPMT,
                    "conGangguan" to countGangguan,
                    "conLa" to countLa,
                    "bpadam" to et_gangguan_bebanPadam_purworejo.text.toString(),
                    "bnormal" to et_gangguan_bebanNormal_purworejo.text.toString(),
                    "analisa" to et_gangguan_analisa_purworejo.text.toString()
            )
            val documentPath = tvDate_gangguan_purworejo.text.toString()
            if (documentPath.isEmpty()) {
                Toast.makeText(applicationContext, "isi dulu tanggalnya", Toast.LENGTH_SHORT).show()
            } else {
                db!!.collection("Gardu").document(idgardu).collection("Gangguan").document(documentPath).set(docLapor)
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                btn_lapor_gangguan_purworejo.isEnabled = false
                btn_lapor_gangguan_purworejo.setText("sudah ke upload")
                val toast = Toast.makeText(applicationContext, "sudah ke upload", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }

    private fun getCekBox(msg: String): String {
        var msg1 = msg
        if (cek_r_phase_trip_purworejo.isChecked) msg1 += "-${cek_r_phase_trip_purworejo.text} \n"
        if (cek_s_phase_trip_purworejo.isChecked) msg1 += "-${cek_s_phase_trip_purworejo.text} \n"
        if (cek_t_phase_trip_purworejo.isChecked) msg1 += "-${cek_t_phase_trip_purworejo.text} \n"
        if (cek_vt_alarm_purworejo.isChecked) msg1 += "-${cek_vt_alarm_purworejo.text} \n"
        if (cek_relay_inoperative_purworejo.isChecked) msg1 += "-${cek_relay_inoperative_purworejo.text} \n"
        if (cek_aided_trip_purworejo.isChecked) msg1 += "-${cek_aided_trip_purworejo.text} \n"
        if (cek_zone2_tmdelay_trip_purworejo.isChecked) msg1 += "-${cek_zone3_tm_delaytrip_purworejo.text} \n"
        if (cek_zone3_tm_delaytrip_purworejo.isChecked) msg1 += "-${cek_zone3_tm_delaytrip_purworejo.text} \n"
        if (cek_trip_on_close_purworejo.isChecked) msg1 += "-${cek_trip_on_close_purworejo.text} \n"
        if (cek_psb_purworejo.isChecked) msg1 += "-${cek_psb_purworejo.text} \n"
        if (cek_mcb_trip_purworejo.isChecked) msg1 += "-${cek_mcb_trip_purworejo.text} \n"
        if (cek_backup_protect_trip_purworejo.isChecked) msg1 += "-${cek_backup_protect_trip_purworejo.text} \n"
        if (cek_trip_coil_sup_purworejo.isChecked) msg1 += "-${cek_trip_coil_sup_purworejo.text} \n"
        if (cek_mk_acdc_mcb_trip_purworejo.isChecked) msg1 += "-${cek_mk_acdc_mcb_trip_purworejo.text} \n"
        if (cek_cb_low_pressure_alarm_purworejo.isChecked) msg1 += "-${cek_cb_low_pressure_alarm_purworejo.text} \n"
        if (cek_general_lockout_purworejo.isChecked) msg1 += "-${cek_general_lockout_purworejo.text} \n"
        if (cek_dc_fail_purworejo.isChecked) msg1 += "-${cek_dc_fail_purworejo.text} \n"
        if (cek_cb_pole_discrepancy_purworejo.isChecked) msg1 += "-${cek_cb_pole_discrepancy_purworejo.text} \n"
        if (cek_ac_fail_purworejo.isChecked) msg1 += "-${cek_ac_fail_purworejo.text} \n"
        if (cek_vt_source_failure_purworejo.isChecked) msg1 += "-${cek_vt_source_failure_purworejo.text} \n"
        return msg1
    }
}

package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.beban.LaporanBebanActivity
import `in`.mroyek.gardupln.activity.fuad.LaporanBebanActivity2
import `in`.mroyek.gardupln.activity.gangguan.LaporanGangguanActivity
import `in`.mroyek.gardupln.activity.history.HistoryTransmisi1Activity
import `in`.mroyek.gardupln.activity.inspeksi1.Inspeksi1Activity
import `in`.mroyek.gardupln.activity.inspeksi2.Inspeksi2Activity
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        cv_inspeksi_satu.setOnClickListener(this)
        cv_inspeksi_dua.setOnClickListener(this)
        cv_laporan_beban.setOnClickListener(this)
        cv_laporan_gangguan.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.baycrud, menu)
        return true
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.kebaycrud -> startActivity(Intent(applicationContext, BayActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.cv_inspeksi_satu -> startActivity(Intent(applicationContext, Inspeksi1Activity::class.java))
            R.id.cv_inspeksi_dua -> startActivity(Intent(applicationContext, Inspeksi2Activity::class.java))
            R.id.cv_laporan_beban -> startActivity(Intent(applicationContext, LaporanBebanActivity2::class.java))
            R.id.cv_laporan_gangguan -> startActivity(Intent(applicationContext, LaporanGangguanActivity::class.java))
        }
    }
}

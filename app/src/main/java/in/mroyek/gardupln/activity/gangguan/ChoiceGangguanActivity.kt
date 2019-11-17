package `in`.mroyek.gardupln.activity.gangguan

import `in`.mroyek.gardupln.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_choice_gangguan.*

class ChoiceGangguanActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_gangguan)
        choice_Gombong.setOnClickListener(this)
        choice_purworejo.setOnClickListener(this)
        choice_trafo1.setOnClickListener(this)
        choice_trafo2.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.choice_Gombong -> startActivity(Intent(applicationContext, LaporinGangguanGombong::class.java))
            R.id.choice_purworejo -> startActivity(Intent(applicationContext, LaporinGangguanPurworejo::class.java))
            R.id.choice_trafo1 -> startActivity(Intent(applicationContext, LaporinGangguanTrafo1::class.java))
            R.id.choice_trafo2 -> startActivity(Intent(applicationContext, LaporinGangguanTrafo2::class.java))
            R.id.choice_trafo3 -> startActivity(Intent(applicationContext, LaporinGangguanTrafo3::class.java))
        }
    }
}

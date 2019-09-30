package `in`.mroyek.gardupln.activity.inspeksi1

import `in`.mroyek.gardupln.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_diameter1.*

class Diameter1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diameter1)

        val intent = intent.extras
        val coba = intent!!.getString("title")
        tv_coba.text = coba
    }
}
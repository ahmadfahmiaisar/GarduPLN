package `in`.mroyek.gardupln

import `in`.mroyek.gardupln.activity.BayActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLanjut.setOnClickListener { startActivity(Intent(applicationContext, BayActivity::class.java)) }
    }

}
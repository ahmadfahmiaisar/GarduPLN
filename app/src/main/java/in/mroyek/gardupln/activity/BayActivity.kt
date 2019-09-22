package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.fragment.tambahbay.TambahBayFragment
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bay.*
import kotlinx.android.synthetic.main.activity_bay.view.*

class BayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bay)

        btn_tambahBay.setOnClickListener {
            startActivity(Intent(applicationContext, TambahBayActivity::class.java)) }
    }
}

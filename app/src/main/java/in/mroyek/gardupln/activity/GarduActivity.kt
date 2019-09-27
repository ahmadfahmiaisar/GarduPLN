package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.crud.CrudGarduActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gardu.*

class GarduActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gardu)
        tvTambahBay.setOnClickListener { startActivity(Intent(applicationContext, BayActivity::class.java)) }
        btn_crudGardu.setOnClickListener { startActivity(Intent(applicationContext, CrudGarduActivity::class.java)) }
    }

}
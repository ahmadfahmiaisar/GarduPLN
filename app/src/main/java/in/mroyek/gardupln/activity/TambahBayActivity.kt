package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.fragment.tambahbay.DiameterFragment
import `in`.mroyek.gardupln.fragment.tambahbay.TrafoFragment
import `in`.mroyek.gardupln.fragment.tambahbay.TransmisiFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_tambah_bay.*

class TambahBayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_bay)

        btn_transmisi.setOnClickListener {
            ll_transmisi.visibility = View.VISIBLE
            ll_diameter.visibility = View.GONE
            btn_close.setOnClickListener { ll_transmisi.visibility = View.GONE }
        }
        btn_diameter.setOnClickListener {
            ll_diameter.visibility = View.VISIBLE
            ll_transmisi.visibility = View.GONE
            btn_close_diameter.setOnClickListener { ll_diameter.visibility = View.GONE }
        }
        /*btn_transmisi.setOnClickListener { container(TransmisiFragment(), "trasmisi") }
        btn_diameter.setOnClickListener { container(DiameterFragment(), "diameter") }
        btn_trafo.setOnClickListener { container(TrafoFragment(), "trafo") }*/
    }

   /* private fun container(replacefragment: Fragment, tag: String) {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val replace = mFragmentManager.findFragmentByTag(replacefragment::class.java.simpleName)
        if (replace != replacefragment) {
            mFragmentTransaction.replace(R.id.bay_frame_container, replacefragment, tag)
            mFragmentTransaction.commit()
        }
    }*/
}

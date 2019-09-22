package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.fragment.tambahbay.TambahBayFragment
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

            if (btn_tambahBay.text == "Tutup") {
                bay_frame_container.visibility = View.GONE
                btn_tambahBay.text = "Tambah Bay"
            } else if (btn_tambahBay.text == "Tambah Bay") {
                bay_frame_container.visibility = View.VISIBLE
                btn_tambahBay.text = "Tutup"
            }
        }
        container()
    }

    private fun container() {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mHomeFragment = TambahBayFragment()
        val fragment = mFragmentManager.findFragmentByTag(TambahBayFragment::class.java.simpleName)

        if (fragment !is TambahBayFragment) {
            mFragmentTransaction.add(R.id.bay_frame_container, mHomeFragment, TambahBayFragment::class.java.simpleName)
            mFragmentTransaction.commit()
        }
    }
}

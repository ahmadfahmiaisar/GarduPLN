package `in`.mroyek.gardupln.activity.crud

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.BayActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crud_bay.*
import kotlinx.android.synthetic.main.activity_crud_bay.btn_choose_transmisi
import kotlinx.android.synthetic.main.activity_crud_bay.btn_close_transmisi
import kotlinx.android.synthetic.main.activity_crud_bay.ll_transmisi
import kotlinx.android.synthetic.main.fragment_transmisi.*
import java.util.*

class CrudBayActivity : AppCompatActivity() {

    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_bay)

        btn_transmisi.setOnClickListener {
            ll_transmisi.visibility = View.VISIBLE
            ll_diameter.visibility = View.GONE
            ll_trafo.visibility = View.GONE
            btn_close_transmisi.setOnClickListener { ll_transmisi.visibility = View.GONE }
            btn_choose_transmisi.setOnClickListener {
                val id: String = UUID.randomUUID().toString()
                val etbay = et_bay_transmisi.text.toString().trim()
                val doc = hashMapOf(
                        "id" to id,
                        "bay" to "transmisi $etbay"
                )
                db!!.collection("Bay").document().set(doc)
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show()
                            backtoBay()
                        }
                        .addOnFailureListener { Toast.makeText(applicationContext, "yaah gagal", Toast.LENGTH_SHORT).show() }
            }
        }
        btn_diameter.setOnClickListener {
            ll_diameter.visibility = View.VISIBLE
            ll_transmisi.visibility = View.GONE
            ll_trafo.visibility = View.GONE
            btn_close_diameter.setOnClickListener { ll_diameter.visibility = View.GONE }
            btn_choose_diameter.setOnClickListener {
                val id: String = UUID.randomUUID().toString()
                val etbay = et_bay_diameter.text.toString().trim()
                val doc = hashMapOf(
                        "id" to id,
                        "bay" to "diameter $etbay"
                )
                db!!.collection("Bay").document().set(doc)
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show()
                            backtoBay()
                        }
                        .addOnFailureListener { Toast.makeText(applicationContext, "yaah gagal", Toast.LENGTH_SHORT).show() }
            }
        }

        btn_trafo.setOnClickListener {
            ll_trafo.visibility = View.VISIBLE
            ll_transmisi.visibility = View.GONE
            ll_diameter.visibility = View.GONE
            btn_close_trafo.setOnClickListener { ll_trafo.visibility = View.GONE }
            btn_choose_trafo.setOnClickListener {
                val id: String = UUID.randomUUID().toString()
                val etbay = et_bay_trafo.text.toString().trim()
                val doc = hashMapOf(
                        "id" to id,
                        "bay" to "trafo $etbay"
                )
                db!!.collection("Bay").document().set(doc)
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "okeeh", Toast.LENGTH_SHORT).show()
                            backtoBay()
                        }
                        .addOnFailureListener { Toast.makeText(applicationContext, "yaah gagal", Toast.LENGTH_SHORT).show() }
            }
        }
        /*btn_transmisi.setOnClickListener { container(TransmisiFragment(), "trasmisi") }
        btn_diameter.setOnClickListener { container(DiameterFragment(), "diameter") }
        btn_trafo.setOnClickListener { container(TrafoFragment(), "trafo") }*/
    }

    private fun backtoBay() {
        startActivity(Intent(applicationContext, BayActivity::class.java))
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

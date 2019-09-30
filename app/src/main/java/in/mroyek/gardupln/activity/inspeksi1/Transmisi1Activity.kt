package `in`.mroyek.gardupln.activity.inspeksi1

import `in`.mroyek.gardupln.R
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_transmisi1.*
import java.util.*

class Transmisi1Activity : AppCompatActivity(), OnClickListener {
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmisi1)
        init()
        upSpring()
        //TODO spring dah kelar lanjutin yang sama
    }

    private fun upSpring() {
        btn_upload_spring.setOnClickListener {
            val checkid: Int = rg_spring.checkedRadioButtonId
            if (checkid != -1) {
                val valuespring: RadioButton = findViewById(checkid)
                val id: String = UUID.randomUUID().toString()
                val phasa = tv_setgetphasa.text.toString().trim()
                val doc = hashMapOf(
                        "id" to id,
                        "phasa" to phasa,
                        "spring" to valuespring.text.toString().trim()
                )
                db!!.collection("Coba").document().set(doc)
                        .addOnSuccessListener { Toast.makeText(applicationContext, "oke", Toast.LENGTH_SHORT).show() }
                        .addOnFailureListener { Toast.makeText(applicationContext, "oke", Toast.LENGTH_SHORT).show() }
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init() {
        tv_phasaS.setOnClickListener(this)
        tv_phasaR.setOnClickListener(this)
        tv_phasaT.setOnClickListener(this)
        tv_spring.setOnClickListener(this)
        tv_hidrolik.setOnClickListener(this)
        tv_pneumatic.setOnClickListener(this)
        tv_sfg.setOnClickListener(this)
        tv_oil.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_phasaR -> {
                tv_phasaR.setBackgroundColor(Color.RED)
                tv_phasaS.setBackgroundColor(Color.TRANSPARENT)
                tv_phasaT.setBackgroundColor(Color.TRANSPARENT)
                tv_setgetphasa.text = tv_phasaR.text.toString().trim()
                layout_choice2.visibility = View.VISIBLE

            }
            R.id.tv_phasaS -> {
                tv_phasaS.setBackgroundColor(Color.RED)
                tv_phasaR.setBackgroundColor(Color.TRANSPARENT)
                tv_phasaT.setBackgroundColor(Color.TRANSPARENT)
                tv_setgetphasa.text = tv_phasaS.text.toString().trim()
                layout_choice2.visibility = View.VISIBLE

            }
            R.id.tv_phasaT -> {
                tv_phasaT.setBackgroundColor(Color.RED)
                tv_phasaS.setBackgroundColor(Color.TRANSPARENT)
                tv_phasaR.setBackgroundColor(Color.TRANSPARENT)
                tv_setgetphasa.text = tv_phasaT.text.toString().trim()
                layout_choice2.visibility = View.VISIBLE

            }
            R.id.tv_spring -> {
                layout_spring.visibility = View.VISIBLE
                tv_spring.setBackgroundColor(Color.RED)
                layout_hidrolik.visibility = View.GONE
            }
            R.id.tv_hidrolik -> {
                layout_hidrolik.visibility = View.VISIBLE
                layout_spring.visibility = View.GONE
            }
            else -> {
            }
        }
    }

}
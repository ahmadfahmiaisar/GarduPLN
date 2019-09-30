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
import kotlin.collections.HashMap

class Transmisi1Activity : AppCompatActivity(), OnClickListener {
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmisi1)
        init()
        upSpring()
        upHidro()
        upPneu()
        upSfg()
        upOil()
    }

    private fun upOil() {
        btn_upload_oil.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val levelMinyak = rg_oil.checkedRadioButtonId
            if (levelMinyak != -1) {
                val doc = hashMapOf(
                        "id" to id,
                        "oil" to "oil",
                        "phasa" to phasa,
                        "levelMinyak" to levelMinyak
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun upSfg() {
        btn_upload_sfg.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val gasSfg = et_tekananGasSfg.text.toString().trim()
            val statusSfg = rg_sfg.checkedRadioButtonId
            if (statusSfg != -1) {
                val doc = hashMapOf(
                        "id" to id,
                        "phasa" to phasa,
                        "sfg" to "sfg",
                        "tekananGasSfg" to gasSfg,
                        "statusSfg" to statusSfg
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun upPneu() {
        btn_upload_pneumatic.setOnClickListener {
            val minyakPneu: Int = rg_minyak_pneumatic.checkedRadioButtonId
            val motorPneu: Int = rg_motor_pneumatic.checkedRadioButtonId
            val id: String = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val tekananPneu = et_tekanan_pneumatic.text.toString().trim()
            val jamPneu = et_jam_pneumatic.text.toString().trim()
            val kaliPneu = et_kali_pneumatic.text.toString().trim()
            if (minyakPneu != -1 && motorPneu != -1) {
                val doc = hashMapOf(
                        "id" to id,
                        "pneumatic" to "pneumatic",
                        "phasa" to phasa,
                        "tekananPneumatic" to tekananPneu,
                        "levelMinyak" to minyakPneu,
                        "kaliMotor" to "$jamPneu, $kaliPneu",
                        "statusMotor" to motorPneu

                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun upHidro() {
        btn_upload_hidro.setOnClickListener {
            val minyakHidro: Int = rg_minyak_hidrolik.checkedRadioButtonId
            val pompaHidro: Int = rg_pompa_hidro.checkedRadioButtonId
            val id: String = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val tekananHidrolik = et_tekanan_hidrolik.text.toString().trim()
            val jamHidro = et_jam_hidrolik.text.toString().trim()
            val kaliHidro = et_kali_hidrolik.text.toString().trim()
            if (minyakHidro != -1 && pompaHidro != -1) {
                val doc = hashMapOf(
                        "id" to id,
                        "hydraulic" to "hydraulic",
                        "phasa" to phasa,
                        "tekananHydrolic" to tekananHidrolik,
                        "levelMinyak" to minyakHidro,
                        "kaliPompa" to "$jamHidro, $kaliHidro",
                        "statusPompa" to pompaHidro
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun docUpload(doc: HashMap<String, Any>) {
        db!!.collection("Coba").document().set(doc)
                .addOnSuccessListener { Toast.makeText(applicationContext, "oke", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(applicationContext, "oke", Toast.LENGTH_SHORT).show() }
    }

    private fun upSpring() {
        btn_upload_spring.setOnClickListener {
            val checkid: Int = rg_spring.checkedRadioButtonId
            val valuespring: RadioButton = findViewById(checkid)
            val id: String = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            if (checkid != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "id" to id,
                        "phasa" to phasa,
                        "spring" to "spring",
                        "status" to valuespring.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
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

    override fun onClick(view: View?) = when (view!!.id) {
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
            layout_hidrolik.visibility = View.GONE
            layout_pneumatic.visibility = View.GONE
            layout_sfg.visibility = View.GONE
            layout_oil.visibility = View.GONE
        }
        R.id.tv_hidrolik -> {
            layout_hidrolik.visibility = View.VISIBLE
            layout_spring.visibility = View.GONE
            layout_pneumatic.visibility = View.GONE
            layout_sfg.visibility = View.GONE
            layout_oil.visibility = View.GONE
        }
        R.id.tv_pneumatic -> {
            layout_pneumatic.visibility = View.VISIBLE
            layout_spring.visibility = View.GONE
            layout_hidrolik.visibility = View.GONE
            layout_sfg.visibility = View.GONE
            layout_oil.visibility = View.GONE
        }
        R.id.tv_sfg -> {
            layout_sfg.visibility = View.VISIBLE
            layout_spring.visibility = View.GONE
            layout_hidrolik.visibility = View.GONE
            layout_pneumatic.visibility = View.GONE
            layout_oil.visibility = View.GONE
        }
        R.id.tv_oil -> {
            layout_oil.visibility = View.VISIBLE
            layout_spring.visibility = View.GONE
            layout_hidrolik.visibility = View.GONE
            layout_pneumatic.visibility = View.GONE
            layout_sfg.visibility = View.GONE
        }
        else -> {
        }
    }

}
package `in`.mroyek.gardupln.activity.inspeksi1

import `in`.mroyek.gardupln.R
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_transmisi1.*
import kotlinx.android.synthetic.main.hidrolayout.*
import kotlinx.android.synthetic.main.oillayout.*
import kotlinx.android.synthetic.main.pneulayout.*
import kotlinx.android.synthetic.main.sfglayout.*
import kotlinx.android.synthetic.main.springlayout.*
import java.util.*
import kotlin.collections.HashMap

class Transmisi1Activity : AppCompatActivity(), OnClickListener {
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    lateinit var title: String
    lateinit var idgardu: String
    lateinit var idbay: String
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar, menu)
        return true
    }

    private fun upOil() {
        btn_upload_oil.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val levelMinyak = rg_oil.checkedRadioButtonId
            if (levelMinyak != -1) {
                val doc = hashMapOf(
                        "idgardu" to id,
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
                        "idgardu" to id,
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
                        "idgardu" to id,
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
                        "idgardu" to id,
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

    private fun upSpring() {
        btn_upload_spring.setOnClickListener {
            val checkid: Int = rg_spring.checkedRadioButtonId
            val valuespring: RadioButton = findViewById(checkid)
            val id: String = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            if (checkid != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
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
        val intent = intent.extras
        title = intent!!.getString("title").toString()
        idgardu = intent.getString("idgardu").toString()
        idbay = intent.getString("idbay").toString()
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
            defaultchoice()
            tv_phasaR.setBackgroundColor(Color.RED)
            tv_setgetphasa.text = tv_phasaR.text.toString().trim()
        }
        R.id.tv_phasaS -> {
            defaultchoice()
            tv_phasaS.setBackgroundColor(Color.RED)
            tv_setgetphasa.text = tv_phasaS.text.toString().trim()
        }
        R.id.tv_phasaT -> {
            defaultchoice()
            tv_phasaT.setBackgroundColor(Color.RED)
            tv_setgetphasa.text = tv_phasaT.text.toString().trim()
        }
        R.id.tv_spring -> {
            defaultView()
            layout_spring.visibility = View.VISIBLE
        }
        R.id.tv_hidrolik -> {
            defaultView()
            layout_hidrolik.visibility = View.VISIBLE
        }
        R.id.tv_pneumatic -> {
            defaultView()
            layout_pneumatic.visibility = View.VISIBLE
        }
        R.id.tv_sfg -> {
            defaultView()
            layout_sfg.visibility = View.VISIBLE
        }
        R.id.tv_oil -> {
            defaultView()
            layout_oil.visibility = View.VISIBLE
        }
        else -> {
        }
    }

    private fun docUpload(doc: HashMap<String, Any>) {
        db!!.collection("Gardu").document(idgardu).collection("Bay").document(idbay).collection(title).document().set(doc)
                .addOnSuccessListener { Toast.makeText(applicationContext, "oke", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
    }

    private fun defaultchoice() {
        tv_phasaS.setBackgroundColor(Color.TRANSPARENT)
        tv_phasaR.setBackgroundColor(Color.TRANSPARENT)
        tv_phasaT.setBackgroundColor(Color.TRANSPARENT)
        layout_choice2.visibility = View.VISIBLE
    }

    private fun defaultView() {
        layout_hidrolik.visibility = View.GONE
        layout_spring.visibility = View.GONE
        layout_pneumatic.visibility = View.GONE
        layout_sfg.visibility = View.GONE
        layout_oil.visibility = View.GONE
    }
}
package `in`.mroyek.gardupln.activity.inspeksi1

import `in`.mroyek.gardupln.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_trafo1.*
import kotlinx.android.synthetic.main.activity_transmisi1.*
import kotlinx.android.synthetic.main.hidrolayout.*
import kotlinx.android.synthetic.main.oillayout.*
import kotlinx.android.synthetic.main.pneulayout.*
import kotlinx.android.synthetic.main.sfglayout.*
import kotlinx.android.synthetic.main.sisihvlayout.*
import kotlinx.android.synthetic.main.sisilvlayout.*
import kotlinx.android.synthetic.main.springlayout.*
import kotlinx.android.synthetic.main.vacumlayout.*
import java.util.*
import kotlin.collections.HashMap

class Trafo1Activity : AppCompatActivity() {
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    lateinit var title: String
    lateinit var idbay: String
    lateinit var idgardu: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trafo1)
        init()
        choiceTrafo()
    }

    private fun choiceTrafo() {
        btn_choice_HV.setOnClickListener {
            sisihv_layout.visibility = View.VISIBLE
            sisilv_layout.visibility = View.GONE
            rg_choice_lv_layout.visibility = View.GONE
            rg_choice_hv_layout.visibility = View.VISIBLE
            choiceHvLayout()
           /* uploadSpringHv()
            uploadHidroHv()
            uploadPneuHv()
            uploadPneuHv()
            uploadOilHv()*/
        }
        btn_choice_LV.setOnClickListener {
            sisilv_layout.visibility = View.VISIBLE
            sisihv_layout.visibility = View.GONE
            rg_choice_hv_layout.visibility = View.GONE
            rg_choice_lv_layout.visibility = View.VISIBLE
            choiceLvLayout()
/*
            uploadOil()
            uploadSfg()
            uploadVacum()
            uploadHidro()
            uploadSpring()
*/
        }
    }

    private fun choiceLvLayout() {
        rg_choice_lv_layout.setOnCheckedChangeListener { _, checklv ->
            val radio: RadioButton = findViewById(checklv)
            when {
                radio.text.contains("Spring") -> {
                    defaultView()
                    layout_spring.visibility = View.VISIBLE
                }
                radio.text.contains("Hydraulic") -> {
                    defaultView()
                    layout_hidrolik.visibility = View.VISIBLE
                }
                radio.text.contains("VACUM") -> {
                    defaultView()
                    layout_vacum.visibility = View.VISIBLE
                }
                radio.text.contains("SFG") -> {
                    defaultView()
                    layout_sfg.visibility = View.VISIBLE
                }
                radio.text.contains("OIL") -> {
                    defaultView()
                    layout_oil.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun choiceHvLayout() {
        rg_choice_hv_layout.setOnCheckedChangeListener { _, checkhv ->
            val radio: RadioButton = findViewById(checkhv)
            when {
                radio.text.contains("Spring") -> {
                    defaultView()
                    layout_spring.visibility = View.VISIBLE
                }
                radio.text.contains("Hydraulic") -> {
                    defaultView()
                    layout_hidrolik.visibility = View.VISIBLE
                }
                radio.text.contains("Pneumatic") -> {
                    defaultView()
                    layout_pneumatic.visibility = View.VISIBLE
                }
                radio.text.contains("SFG") -> {
                    defaultView()
                    layout_sfg.visibility = View.VISIBLE
                }
                radio.text.contains("Oil") -> {
                    defaultView()
                    layout_oil.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun uploadOilHv() {
        btn_upload_oil.setOnClickListener {
            val beban = et_hv_beban.text.toString().trim()
            val suhuOil = et_hv_suhu_topoil.text.toString().trim()
            val suhuPrimer = et_suhu_primer.text.toString().trim()
            val suhuSekunder = et_hv_suhu_sekunder.text.toString().trim()
            val chekidKipas: Int = rg_hv_statuskipas.checkedRadioButtonId
            val valueKipas: RadioButton = findViewById(chekidKipas)
            val id = UUID.randomUUID().toString()
            val checkid = rg_oil.checkedRadioButtonId
            val valueOil: RadioButton = findViewById(checkid)
            if (checkid != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "oil" to "oil",
                        "sisi" to "Sisi HV",
                        "beban" to beban,
                        "suhuTopOil" to suhuOil,
                        "suhuPrimer" to suhuPrimer,
                        "suhuSekunder" to suhuSekunder,
                        "statusKipas" to valueKipas,
                        "levelMinyak" to valueOil.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadSfgHv() {
        btn_upload_sfg.setOnClickListener {
            val beban = et_hv_beban.text.toString().trim()
            val suhuOil = et_hv_suhu_topoil.text.toString().trim()
            val suhuPrimer = et_suhu_primer.text.toString().trim()
            val suhuSekunder = et_hv_suhu_sekunder.text.toString().trim()
            val chekidKipas: Int = rg_hv_statuskipas.checkedRadioButtonId
            val valueKipas: RadioButton = findViewById(chekidKipas)
            val id = UUID.randomUUID().toString()
            val gasSfg = et_tekananGasSfg.text.toString().trim()
            val checkid = rg_sfg.checkedRadioButtonId
            val valuesfg: RadioButton = findViewById(checkid)
            if (checkid != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "sisi" to "Sisi Hv",
                        "beban" to beban,
                        "suhuTopOil" to suhuOil,
                        "suhuPrimer" to suhuPrimer,
                        "suhuSekunder" to suhuSekunder,
                        "statusKipas" to valueKipas,
                        "sfg" to "sfg",
                        "tekananGasSfg" to gasSfg,
                        "statusSfg" to valuesfg.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadPneuHv() {
        btn_upload_pneumatic.setOnClickListener {
            val beban = et_hv_beban.text.toString().trim()
            val suhuOil = et_hv_suhu_topoil.text.toString().trim()
            val suhuPrimer = et_suhu_primer.text.toString().trim()
            val suhuSekunder = et_hv_suhu_sekunder.text.toString().trim()
            val chekidKipas: Int = rg_hv_statuskipas.checkedRadioButtonId
            val valueKipas: RadioButton = findViewById(chekidKipas)
            val checkidMinyak: Int = rg_minyak_pneumatic.checkedRadioButtonId
            val checkidMotor: Int = rg_motor_pneumatic.checkedRadioButtonId
            val valueMinyak: RadioButton = findViewById(checkidMinyak)
            val valueMotor: RadioButton = findViewById(checkidMotor)
            val id: String = UUID.randomUUID().toString()
            val tekananPneu = et_tekanan_pneumatic.text.toString().trim()
            val jamPneu = et_jam_pneumatic.text.toString().trim()
            val kaliPneu = et_kali_pneumatic.text.toString().trim()
            if (checkidMinyak != -1 && checkidMotor != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "pneumatic" to "pneumatic",
                        "sisi" to "Sisi HV",
                        "beban" to beban,
                        "suhuTopOil" to suhuOil,
                        "suhuPrimer" to suhuPrimer,
                        "suhuSekunder" to suhuSekunder,
                        "statusKipas" to valueKipas,
                        "tekananPneumatic" to tekananPneu,
                        "levelMinyak" to valueMinyak.text.toString().trim(),
                        "kaliMotor" to "$jamPneu, $kaliPneu",
                        "statusMotor" to valueMotor.text.toString().trim()

                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadHidroHv() {
        btn_upload_hidro.setOnClickListener {
            val beban = et_hv_beban.text.toString().trim()
            val suhuOil = et_hv_suhu_topoil.text.toString().trim()
            val suhuPrimer = et_suhu_primer.text.toString().trim()
            val suhuSekunder = et_hv_suhu_sekunder.text.toString().trim()
            val chekidKipas: Int = rg_hv_statuskipas.checkedRadioButtonId
            val valueKipas: RadioButton = findViewById(chekidKipas)
            val checkIdminyak: Int = rg_minyak_hidrolik.checkedRadioButtonId
            val checkidPompa: Int = rg_pompa_hidro.checkedRadioButtonId
            val valueMinyak: RadioButton = findViewById(checkIdminyak)
            val valuePompa: RadioButton = findViewById(checkidPompa)
            val id: String = UUID.randomUUID().toString()
            val tekananHidrolik = et_tekanan_hidrolik.text.toString().trim()
            val jamHidro = et_jam_hidrolik.text.toString().trim()
            val kaliHidro = et_kali_hidrolik.text.toString().trim()
            if (checkIdminyak != -1 && checkidPompa != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "hydraulic" to "hydraulic",
                        "sisi" to "Sisi HV",
                        "beban" to beban,
                        "suhuTopOil" to suhuOil,
                        "suhuPrimer" to suhuPrimer,
                        "suhuSekunder" to suhuSekunder,
                        "statusKipas" to valueKipas,
                        "tekananHydrolic" to tekananHidrolik,
                        "levelMinyak" to valueMinyak.text.toString().trim(),
                        "kaliPompa" to "$jamHidro, $kaliHidro",
                        "statusPompa" to valuePompa.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadSpringHv() {
        btn_upload_spring.setOnClickListener {
            val checkid: Int = rg_spring.checkedRadioButtonId
            val valuespring: RadioButton = findViewById(checkid)
            val id: String = UUID.randomUUID().toString()
            val beban = et_hv_beban.text.toString().trim()
            val suhuOil = et_hv_suhu_topoil.text.toString().trim()
            val suhuPrimer = et_suhu_primer.text.toString().trim()
            val suhuSekunder = et_hv_suhu_sekunder.text.toString().trim()
            val chekidKipas: Int = rg_hv_statuskipas.checkedRadioButtonId
            val valueKipas: RadioButton = findViewById(chekidKipas)
            if (checkid != -1 && chekidKipas != -1) {
                val map: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "sisi" to "Sisi HV",
                        "spring" to "spring",
                        "beban" to beban,
                        "suhuTopOil" to suhuOil,
                        "suhuPrimer" to suhuPrimer,
                        "suhuSekunder" to suhuSekunder,
                        "statusKipas" to valueKipas.text.toString().trim(),
                        "statusSpring" to valuespring.text.toString().trim()
                )
                docUpload(map)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun uploadSpring() {
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

    private fun uploadOil() {
        btn_upload_oil.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val checkid = rg_oil.checkedRadioButtonId
            val valueOil: RadioButton = findViewById(checkid)
            if (checkid != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "oil" to "oil",
                        "phasa" to phasa,
                        "levelMinyak" to valueOil.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun uploadSfg() {
        btn_upload_sfg.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val gasSfg = et_tekananGasSfg.text.toString().trim()
            val checkid = rg_sfg.checkedRadioButtonId
            val valuesfg: RadioButton = findViewById(checkid)
            if (checkid != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "phasa" to phasa,
                        "sfg" to "sfg",
                        "tekananGasSfg" to gasSfg,
                        "statusSfg" to valuesfg.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadVacum() {
        btn_upload_vacum.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val tekananVacum = et_tekanan_vacum.text.toString().trim()
            val checkid = rg_vakum.checkedRadioButtonId
            val valueVakum: RadioButton = findViewById(checkid)
            if (checkid != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "phasa" to phasa,
                        "sfg" to "sfg",
                        "tekananVacum" to tekananVacum,
                        "statusVacum" to valueVakum.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadHidro() {
        btn_upload_hidro.setOnClickListener {
            val checkIdminyak: Int = rg_minyak_hidrolik.checkedRadioButtonId
            val checkidPompa: Int = rg_pompa_hidro.checkedRadioButtonId
            val valueMinyak: RadioButton = findViewById(checkIdminyak)
            val valuePompa: RadioButton = findViewById(checkidPompa)
            val id: String = UUID.randomUUID().toString()
            val phasa = tv_setgetphasa.text.toString().trim()
            val tekananHidrolik = et_tekanan_hidrolik.text.toString().trim()
            val jamHidro = et_jam_hidrolik.text.toString().trim()
            val kaliHidro = et_kali_hidrolik.text.toString().trim()
            if (checkIdminyak != -1 && checkidPompa != -1) {
                val doc: HashMap<String, Any> = hashMapOf(
                        "idgardu" to id,
                        "hydraulic" to "hydraulic",
                        "phasa" to phasa,
                        "tekananHydrolic" to tekananHidrolik,
                        "levelMinyak" to valueMinyak.text.toString().trim(),
                        "kaliPompa" to "$jamHidro, $kaliHidro",
                        "statusPompa" to valuePompa.text.toString().trim()
                )
                docUpload(doc)
            } else {
                Toast.makeText(applicationContext, "On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun docUpload(map: java.util.HashMap<String, Any>) {
        db!!.collection("Gardu").document(idgardu).collection("Bay").document(idbay).collection("Inspeksi 1").document().set(map)
                .addOnSuccessListener { Toast.makeText(applicationContext, "oke", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
    }

    private fun defaultView() {
        layout_hidrolik.visibility = View.GONE
        layout_spring.visibility = View.GONE
        layout_pneumatic.visibility = View.GONE
        layout_sfg.visibility = View.GONE
        layout_oil.visibility = View.GONE
        layout_vacum.visibility = View.GONE
    }

    private fun init() {
        val intent = intent.extras
        title = intent!!.getString("title").toString()
        idgardu = intent.getString("idgardu").toString()
        idbay = intent.getString("idbay").toString()
    }
}
package `in`.mroyek.gardupln.activity.beban

import `in`.mroyek.gardupln.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_history_beban.*

class DetailHistoryBebanActivity : AppCompatActivity() {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    internal val bebanResponseList: MutableList<BebanResponse> = ArrayList()
    lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history_beban)
        val intent = intent.extras
        id = intent?.getString("idbeban").toString()
        init()
    }

    private fun init() {
        val docRef = db.collection("Laporan Beban").document(id)
        docRef.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        val response = BebanResponse(
                                document?.getString("tanggal"),
                                document?.getString("waktu"),
                                document?.getString("transmisi1"),
                                document?.getString("u1"),
                                document?.getString("i1"),
                                document?.getString("p1"),
                                document?.getString("q1"),
                                document?.getString("beban1"),
                                document?.getString("in1"),
                                document?.getString("transmisi2"),
                                document?.getString("u2"),
                                document?.getString("i2"),
                                document?.getString("p2"),
                                document?.getString("q2"),
                                document?.getString("beban2"),
                                document?.getString("in2"),
                                document?.getString("transmisi3"),
                                document?.getString("u3"),
                                document?.getString("i3"),
                                document?.getString("p3"),
                                document?.getString("q3"),
                                document?.getString("beban3"),
                                document?.getString("in3"),
                                document?.getString("transmisi4"),
                                document?.getString("u4"),
                                document?.getString("i4"),
                                document?.getString("p4"),
                                document?.getString("q4"),
                                document?.getString("beban4"),
                                document?.getString("in4"),
                                document?.getString("transmisi5"),
                                document?.getString("u5"),
                                document?.getString("i5"),
                                document?.getString("p5"),
                                document?.getString("q5"),
                                document?.getString("beban5"),
                                document?.getString("in5")

                        )
                        bebanResponseList.add(response)
                        tv_tanggal.text = response.tanggal
                        tv_jam.text = response.waktu
                        tv_transmisi1.text = response.transmisi1
                        tv_value_U.text = response.u1
                        tv_value_I.text = response.i1
                        tv_value_P.text = response.p1
                        tv_value_Q.text = response.q1
                        tv_value_beban.text = response.beban1
                        tv_value_In.text = response.in1
                        tv_transmisi2.text = response.transmisi2
                        tv_value_U2.text = response.u2
                        tv_value_I2.text = response.i2
                        tv_value_P2.text = response.p2
                        tv_value_Q2.text = response.q2
                        tv_value_beban2.text = response.beban2
                        tv_value_In2.text = response.in2
                        tv_transmisi3.text = response.transmisi3
                        tv_value_U3.text = response.u3
                        tv_value_I3.text = response.i3
                        tv_value_P3.text = response.p3
                        tv_value_Q3.text = response.q3
                        tv_value_beban3.text = response.beban3
                        tv_value_In3.text = response.in3
                        tv_transmisi4.text = response.transmisi4
                        tv_value_U4.text = response.u4
                        tv_value_I4.text = response.i4
                        tv_value_P4.text = response.p4
                        tv_value_Q4.text = response.q4
                        tv_value_beban4.text = response.beban4
                        tv_value_In4.text = response.in4
                        tv_transmisi5.text = response.transmisi5
                        tv_value_U5.text = response.u5
                        tv_value_I5.text = response.i5
                        tv_value_P5.text = response.p5
                        tv_value_Q_5.text = response.q5
                        tv_value_beba5.text = response.beban5
                        tv_value_In5.text = response.in5

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
                }
    }

}

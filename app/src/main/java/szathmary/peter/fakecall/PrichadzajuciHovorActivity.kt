package szathmary.peter.fakecall

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.concurrent.timerTask


class PrichadzajuciHovorActivity : AppCompatActivity() {
    lateinit var meno: String
    lateinit var cislo: String
    private lateinit var vibrator: VibratorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            vibrator = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        } else {
            null
        }

        setContentView(R.layout.activity_prichadzajuci_hovor)
        retrieveMessage()
        val menoTextView: TextView = findViewById(R.id.prichadzajuciHovorMeno)
        menoTextView.text = meno

        val cisloTextView: TextView = findViewById(R.id.prichadzajuciHovorCislo)
        cisloTextView.text = cislo

        val cancelButton: FloatingActionButton = findViewById(R.id.declineFolatingActionButton)
        cancelButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                vibrator.cancel()
            }
            switchToMainActivity(cislo, meno)
        }

        val acceptButton: FloatingActionButton = findViewById(R.id.acceptFloatingActionButton)
        acceptButton.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                vibrator.cancel()
            }
            switchToTelefonatActivity(cislo, meno)
        }
        vibratePhone()
    }

    private fun vibratePhone() {

        val pattern = longArrayOf(0, 100, 1000)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                vibrator.defaultVibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
            }
        } else {
            // 0 = Repeat Indefinitely
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                vibrator.defaultVibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
            }
        }

        // To cancel the vibration, in this case ==> Cancel after 60000ms (= 1 minute)
        Timer().schedule(timerTask { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            vibrator.cancel()
        }
        }, 60000)
    }

    private fun retrieveMessage() {
        cislo = intent.getStringExtra("cislo").toString()
        meno = intent.getStringExtra("meno").toString()
    }

    private fun switchToMainActivity(cislo: String, meno: String) {
        val switchActivityIntent = Intent(this, MainActivity::class.java)
        val pref = applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString("cislo", cislo)
        editor.putString("meno", meno)
        editor.apply()

        startActivity(switchActivityIntent)
    }

    private fun switchToTelefonatActivity(cislo: String, meno: String) {
        val switchActivityIntent = Intent(this, TelefonatActivity::class.java)
        switchActivityIntent.putExtra("cislo", cislo)
        switchActivityIntent.putExtra("meno", meno)

        startActivity(switchActivityIntent)
    }
}
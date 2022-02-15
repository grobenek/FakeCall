package szathmary.peter.fakecall

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.WindowManager
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TelefonatActivity : AppCompatActivity() {
    private lateinit var cislo : String
    private lateinit var meno: String
    private var resume = false
    private var elapsedTime: Long = 0
    private var tag = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.telefonat_activity)
        retrieveMessage()

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        val menoTextView : TextView = findViewById(R.id.menoTextViewVolanie)
        menoTextView.text = meno

        val cancelButton : FloatingActionButton = findViewById(R.id.cancelActionButton)
        cancelButton.setOnClickListener {
            switchActivities(cislo)
        }
        val volamText: TextView = findViewById(R.id.volamTextView)
        volamText.text = getString(R.string.string, cislo)

        val counter : Chronometer = findViewById(R.id.timer)

        counter.onChronometerTickListener = OnChronometerTickListener {
            if (!resume) {
                val minutes: Long = (SystemClock.elapsedRealtime() - counter.base) / 1000 / 60
                val seconds: Long = (SystemClock.elapsedRealtime() - counter.base) / 1000 % 60
                elapsedTime = SystemClock.elapsedRealtime()
                Log.d(tag, "onChronometerTick: $minutes : $seconds")
            } else {
                val minutes: Long = (elapsedTime - counter.base) / 1000 / 60
                val seconds: Long = (elapsedTime - counter.base) / 1000 % 60
                elapsedTime += 1000
                Log.d(tag, "onChronometerTick: $minutes : $seconds")
            }
        }
        counter.start()

    }

   private fun retrieveMessage() {
        cislo = intent.getStringExtra("cislo").toString()
       meno = intent.getStringExtra("meno").toString()
    }

    private fun switchActivities(cislo: String) {
        val switchActivityIntent = Intent(this, MainActivity::class.java)
        switchActivityIntent.putExtra("cislo", cislo)

        val pref = applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString("cislo", cislo)
        editor.putString("meno", meno)
        editor.apply()

        startActivity(switchActivityIntent)
    }
}
package szathmary.peter.fakecall

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PrichadzajuciHovorActivity : AppCompatActivity() {
    lateinit var meno: String
    lateinit var cislo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prichadzajuci_hovor)
        retrieveMessage()
        val menoTextView: TextView = findViewById(R.id.prichadzajuciHovorMeno)
        menoTextView.text = meno

        val cisloTextView: TextView = findViewById(R.id.prichadzajuciHovorCislo)
        cisloTextView.text = cislo

        val cancelButton: FloatingActionButton = findViewById(R.id.declineFolatingActionButton)
        cancelButton.setOnClickListener {
            switchToMainActivity(cislo, meno)
        }

        val acceptButton: FloatingActionButton = findViewById(R.id.acceptFloatingActionButton)
        acceptButton.setOnClickListener{
            switchToTelefonatActivity(cislo, meno)
        }
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
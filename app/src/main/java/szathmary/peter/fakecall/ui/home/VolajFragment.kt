package szathmary.peter.fakecall.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import szathmary.peter.fakecall.MainActivity
import szathmary.peter.fakecall.R
import szathmary.peter.fakecall.databinding.FragmentVolajBinding


class VolajFragment() : Fragment() {

    var cislo = "Zle zadane cislo!"
    private var _binding: FragmentVolajBinding? = null
    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun afterTextChanged(p0: Editable?) {
            cislo = PhoneNumberUtils.formatNumber(cisloEdit.text.toString())
            callActionButton.isClickable = true
        }

    }
    private lateinit var callActionButton: FloatingActionButton
    private lateinit var cisloEdit: EditText
    private lateinit var cisloTextView: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVolajBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //AK MAS FRAGMENT, PRACUJ S UI AZ V TEJTO METODE
        super.onViewCreated(view, savedInstanceState)
        cisloEdit = view.findViewById(R.id.zadavanieCisla)
        cisloEdit.addTextChangedListener(textWatcher)
        cisloTextView = view.findViewById(R.id.cisloText)
        callActionButton = view.findViewById(R.id.callActionButton)
        //TAKTO POUZIVAJ LISTENERY
        callActionButton.setOnClickListener {
            //Skontrolujem ci je cislo spravne, ak ano, zobrazim do textEdit na ake cislo volam
            //pockam 3 sekundy a prepinam aktivitu na hovor
            //inac do textEdit napisem ze je zadane zle cislo
            if (isValidPhone(cislo)) {
                cisloTextView.text = getString(R.string.volam_cislo, cislo)
                val mainActivity: MainActivity = activity as MainActivity

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        mainActivity.switchActivities(cislo)
                    },
                    2000 // value in milliseconds
                )
            } else {
                cisloTextView.text = getString(R.string.zle_zadane_cislo)
            }
        }
    }

    private fun isValidPhone(phone: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(phone) || phone.toString().length < 10) {
            false
        } else {
            PhoneNumberUtils.isGlobalPhoneNumber(phone.toString())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
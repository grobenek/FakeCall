package szathmary.peter.fakecall.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import szathmary.peter.fakecall.R
import szathmary.peter.fakecall.databinding.FragmentVolajBinding
import java.lang.Exception
import java.lang.NullPointerException

class VolajFragment : Fragment() {

    private var _binding: FragmentVolajBinding? = null
    lateinit var callActionButton : FloatingActionButton
    lateinit var skuska : TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVolajBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //AK MAS FRAGMENT, PRACUJ S UI AZ V TEJTO METODE
        super.onViewCreated(view, savedInstanceState)
        skuska = view.findViewById(R.id.skuska)
        callActionButton = view.findViewById(R.id.callActionButton)
        //TAKTO POUZIVAJ LISTENERY
        callActionButton.setOnClickListener {
            skuska.text = "Kliknute!"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
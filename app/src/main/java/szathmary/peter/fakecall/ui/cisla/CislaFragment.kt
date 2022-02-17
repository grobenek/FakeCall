package szathmary.peter.fakecall.ui.cisla

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import szathmary.peter.fakecall.R
import szathmary.peter.fakecall.contacts.Contact
import szathmary.peter.fakecall.contacts.ContactsList
import szathmary.peter.fakecall.databinding.FragmentCislaBinding

class CislaFragment : Fragment() {

    private var _binding: FragmentCislaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCislaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvContacts : RecyclerView = view.findViewById(R.id.contactList) as RecyclerView
        val adapter = ContactsAdapter(ContactsList)
        // Attach the adapter to the recyclerview to populate items
        rvContacts.adapter = adapter
        // Set layout manager to position the items
        rvContacts.layoutManager = LinearLayoutManager(this.context)
        // That's all!

        val deleteContacts : Button = view.findViewById(R.id.deleteContactHistory)
        deleteContacts.setOnClickListener {
            val itemCount = adapter.itemCount
            ContactsList.removeAll()
            adapter.notifyItemRangeRemoved(0, itemCount)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ContactsAdapter(ContactsList: ContactsList.Companion) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val contactTextView: TextView = itemView.findViewById<TextView>(R.id.contact_name)
        val callButton: Button = itemView.findViewById<Button>(R.id.call_button)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_contact, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get the data model based on position
        val contact: Contact = ContactsList.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.contactTextView
        textView.text = contact.name + "   " + contact.number
        val button = viewHolder.callButton
        button.text = "call"
    }

    override fun getItemCount(): Int {
        return ContactsList.numberOfContacts
    }
}
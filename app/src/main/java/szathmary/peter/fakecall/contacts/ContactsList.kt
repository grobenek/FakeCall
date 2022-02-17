package szathmary.peter.fakecall.contacts


class ContactsList {
    companion object {
        var numberOfContacts = 0
        private set
        private val contactList = ArrayList<Contact>()

        fun add(contact: Contact) {
            this.contactList.add(contact)
            this.numberOfContacts++
        }

        fun removeAll() {
            for (contact in this.contactList) {
                this.remove(contact)
            }
        }

        fun remove(id: Int) {
            this.contactList.removeAt(id)
            this.numberOfContacts--
        }

        private fun remove(contact: Contact) {
            this.contactList.remove(contact)
            this.numberOfContacts--
        }

        fun get(index: Int): Contact {
            return this.contactList[index]
        }

        fun getContacts(): ArrayList<Contact> {
            val contacts = ArrayList<Contact>()
            for (contact in this.contactList) {
                contacts.add(contact)
            }
            return contacts
        }
    }
}
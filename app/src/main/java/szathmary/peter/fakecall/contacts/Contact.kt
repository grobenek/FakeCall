package szathmary.peter.fakecall.contacts

data class Contact(val pName: String, val pNumber: String){
    val name: String = pName
    val number: String = pNumber
    override fun toString(): String {
        return "$name + $number"
    }


}

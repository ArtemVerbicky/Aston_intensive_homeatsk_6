package ru.verb.aston_intensive_hometask_6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.verb.aston_intensive_hometask_6.data.ContactService
import ru.verb.aston_intensive_hometask_6.model.Contact

val emptyContact = Contact(
    id = 0,
    name = "",
    surname = "",
    phoneNumber = "",
    imageLink = ""
)

class ContactsViewModel: ViewModel() {
    private val contactService = ContactService()
    val data = contactService.data
    private val edited = MutableLiveData(emptyContact)

    fun removeContact(contact: Contact) = contactService.removeContact(contact)

    fun edit(contact: Contact) {
        edited.value = contact
    }

    fun changeContactContent(name: String, surname: String, phoneNumber: String) {
        edited.value = edited.value?.copy(name = name, surname = surname, phoneNumber = phoneNumber)
    }

    fun saveContact() {
        edited.value?.let {
            contactService.saveChanges(it)
        }
        edited.value = emptyContact
    }
}
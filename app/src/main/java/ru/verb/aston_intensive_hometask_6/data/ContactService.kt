package ru.verb.aston_intensive_hometask_6.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.javafaker.Faker
import ru.verb.aston_intensive_hometask_6.model.Contact

class ContactService {
    private val _data = MutableLiveData<List<Contact>>()
    val data: LiveData<List<Contact>> = _data

    init {
        val faker = Faker.instance()
        _data.value = (1..110).map {
            Contact(
                id = it,
                name = faker.name().firstName(),
                surname = faker.name().lastName(),
                imageLink = "https://loremflickr.com/300/300/person?random=$it",
                phoneNumber = faker.phoneNumber().phoneNumber()
            )
        }
    }

    fun removeContact(contact: Contact) {
        _data.value = _data.value?.filter {
            it.id != contact.id
        }
    }

    fun saveChanges(contact: Contact) {
        val changedContact = contact.copy(name = contact.name, surname = contact.surname, phoneNumber = contact.phoneNumber)
        val list = _data.value?.map {
            if (changedContact.id == it.id) changedContact else it
        }
        list?.let {
            _data.value = ArrayList(it)
        }
    }
}
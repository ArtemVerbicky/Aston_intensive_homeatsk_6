package ru.verb.aston_intensive_hometask_6.activities

import androidx.fragment.app.Fragment
import ru.verb.aston_intensive_hometask_6.model.Contact

fun Fragment.navigator() = requireActivity() as Navigator

interface Navigator {
    fun goBack()
    fun toContactDetailsScreen(contact: Contact)
}
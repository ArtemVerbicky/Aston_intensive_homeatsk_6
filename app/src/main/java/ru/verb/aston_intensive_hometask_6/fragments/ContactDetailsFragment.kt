package ru.verb.aston_intensive_hometask_6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.verb.aston_intensive_hometask_6.activities.navigator
import ru.verb.aston_intensive_hometask_6.databinding.FragmentContactDetailsBinding
import ru.verb.aston_intensive_hometask_6.fragments.ContactsFragment.Companion.KEY_CONTACT
import ru.verb.aston_intensive_hometask_6.model.Contact
import ru.verb.aston_intensive_hometask_6.viewmodel.ContactsViewModel

class ContactDetailsFragment : Fragment(), HasBackArrow {
    private lateinit var binding: FragmentContactDetailsBinding
    private val viewModel: ContactsViewModel by activityViewModels()

    @RequiresApi(33)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        val contact = requireArguments().getParcelable(KEY_CONTACT, Contact::class.java)
        contact?.let { editedContact ->
            with(binding) {
                nameTextEdit.setText(editedContact.name)
                surnameTextEdit.setText(editedContact.surname)
                phoneNumberTextEdit.setText(editedContact.phoneNumber)

                buttonApplyChanges.setOnClickListener {
                    viewModel.changeContactContent(
                        nameTextEdit.text.toString(),
                        surnameTextEdit.text.toString(),
                        phoneNumberTextEdit.text.toString()
                    )
                    viewModel.saveContact()
                    navigator().goBack()
                }
            }
        }
        return binding.root
    }
}
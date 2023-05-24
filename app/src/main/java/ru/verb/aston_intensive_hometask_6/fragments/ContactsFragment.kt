package ru.verb.aston_intensive_hometask_6.fragments

import android.graphics.Rect
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.verb.aston_intensive_hometask_6.activities.navigator
import ru.verb.aston_intensive_hometask_6.adapter.ContactAdapter
import ru.verb.aston_intensive_hometask_6.adapter.OnInteractionListener
import ru.verb.aston_intensive_hometask_6.databinding.FragmentContactsBinding
import ru.verb.aston_intensive_hometask_6.model.Contact
import ru.verb.aston_intensive_hometask_6.viewmodel.ContactsViewModel

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private val viewModel: ContactsViewModel by activityViewModels()

    companion object {
        const val KEY_CONTACT = "KEY_CONTACT"
        private const val DELAY = 2000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)

        val adapter = ContactAdapter(object : OnInteractionListener {
            override fun onRemove(contact: Contact) {
                viewModel.removeContact(contact)
            }

            override fun onEdit(contact: Contact) {
                viewModel.edit(contact)
                navigator().toContactDetailsScreen(contact)
            }
        })

        binding.contactsRecyclingList.adapter = adapter
        binding.contactsRecyclingList.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.contactsRecyclingList.addItemDecoration(CustomItemDecorator(40))

        viewModel.data.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
        }

        binding.contactSearch.addTextChangedListener(getDebouncedTextWatcher(adapter))

        return binding.root
    }

    private fun getDebouncedTextWatcher(adapter: ContactAdapter) = object : TextWatcher {
        var lastInput = 0L
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val currentTime = SystemClock.uptimeMillis()
            if (lastInput != 0L || currentTime - lastInput > DELAY) {
                lastInput = currentTime
                val list = viewModel.data.value?.let { contacts ->
                    s?.let { userInput ->
                        contacts.filter {
                            it.name != null && it.name.contains(userInput, true)
                        }
                    }
                }
                adapter.submitList(list)
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}

class CustomItemDecorator(private val offset: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = offset
        outRect.bottom = offset
        outRect.right = offset
    }
}
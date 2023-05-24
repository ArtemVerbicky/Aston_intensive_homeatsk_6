package ru.verb.aston_intensive_hometask_6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.verb.aston_intensive_hometask_6.databinding.CardContactBinding
import ru.verb.aston_intensive_hometask_6.model.Contact
import ru.verb.aston_intensive_hometask_6.utils.loadImage

interface OnInteractionListener {
    fun onRemove(contact: Contact)
    fun onEdit(contact: Contact)
}

class ContactAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = CardContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.itemView.tag = contact
        holder.bind(contact)
    }
}

class ContactViewHolder(
    private val binding: CardContactBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener {

    fun bind(contact: Contact) {
        with(binding) {
            contactName.text = contact.name
            contactSurname.text = contact.surname
            contactPhoneNumber.text = contact.phoneNumber
            contact.imageLink?.let { contactImage.loadImage(it) }
            root.setOnLongClickListener(this@ContactViewHolder)
            root.setOnClickListener(this@ContactViewHolder)
        }
    }

    override fun onLongClick(v: View): Boolean {
        val contact = v.tag as Contact
        onInteractionListener.onRemove(contact)
        return true
    }


    override fun onClick(v: View) {
        val contact = v.tag as Contact
        onInteractionListener.onEdit(contact)
    }
}

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}
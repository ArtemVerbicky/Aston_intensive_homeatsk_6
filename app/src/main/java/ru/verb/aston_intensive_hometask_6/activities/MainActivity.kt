package ru.verb.aston_intensive_hometask_6.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.verb.aston_intensive_hometask_6.R
import ru.verb.aston_intensive_hometask_6.databinding.ActivityMainBinding
import ru.verb.aston_intensive_hometask_6.fragments.ContactDetailsFragment
import ru.verb.aston_intensive_hometask_6.fragments.ContactsFragment
import ru.verb.aston_intensive_hometask_6.fragments.ContactsFragment.Companion.KEY_CONTACT
import ru.verb.aston_intensive_hometask_6.fragments.HasBackArrow
import ru.verb.aston_intensive_hometask_6.model.Contact

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding
    private val fragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)
    private val fragmentListener = object: FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, ContactsFragment())
                .commit()
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun toContactDetailsScreen(contact: Contact) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragment_container,
                ContactDetailsFragment().apply { arguments = bundleOf(KEY_CONTACT to contact) }
            )
            .setCustomAnimations(
                com.google.android.material.R.anim.abc_slide_in_top,
                com.google.android.material.R.anim.abc_slide_out_bottom,
                com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out
            )
            .commit()
    }

    private fun updateUi() {
        if (fragment is HasBackArrow) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }
}
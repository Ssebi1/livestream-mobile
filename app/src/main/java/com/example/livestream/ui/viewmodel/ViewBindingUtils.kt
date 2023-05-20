package com.example.livestream.ui.viewmodel

import android.app.Activity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> Fragment.viewLifecycle(): ReadWriteProperty<Fragment, T?> =
    object : ReadWriteProperty<Fragment, T?>, DefaultLifecycleObserver {

        // A backing property to hold our value
        private var binding: T? = null

        private var viewLifecycleOwner: LifecycleOwner? = null

        init {
            // Observe the View Lifecycle of the Fragment
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycle) { newLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.removeObserver(this)

                    viewLifecycleOwner = newLifecycleOwner.also {
                        it.lifecycle.addObserver(this)
                    }
                }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            // Clear out backing property just before onDestroyView
            binding = null
            super.onDestroy(owner)
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
            // Return the backing property if it's set
            return this.binding
        }

        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
            // Set the backing property
            this.binding = value
        }
    }

/**
 * Extension function for [Activity] that inflates the layout in a lazy manner.
 *
 * @param bindingInflater The inflater to use for inflating the view.
 */
inline fun <T : ViewBinding> Activity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
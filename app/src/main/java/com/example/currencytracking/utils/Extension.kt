package com.example.currencytracking.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

fun <T : ViewModel> Fragment.viewModel(clazz: KClass<T>) =
    lazy { ViewModelProvider(this)[clazz.java] }

/** Throws an [IllegalStateException] with binding lifecycle error message. */
fun bindingLifecycleError(): Nothing = throw java.lang.IllegalStateException(
    "This property is only valid between onCreateView and onDestroyView."
)
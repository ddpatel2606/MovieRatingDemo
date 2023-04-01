package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dixitpatel.movieratingdemo.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes private val layoutId: Int) : Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected abstract val bindingInvoked: ((B) -> Unit)?

    protected var mediator: TabLayoutMediator? = null

    private var snackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate<B>(inflater, layoutId, container, false).apply { bindingInvoked?.invoke(this) }
        return binding.root
    }

    protected fun collectFlows(collectors: List<KSuspendFunction0<Unit>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectors.forEach { collector ->
                    launch { collector.invoke() }
                }
            }
        }
    }

    protected fun showSnackBar(message: String, actionText: String? = null, anchor: Boolean = false, action: (() -> Unit)? = null) {
        val view = activity?.window?.decorView?.rootView

        val snackBar = view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG) }

        if (action != null) snackBar?.setAction(actionText) { action() }
        if (anchor) snackBar?.setAnchorView(R.id.tabLayout)

        this.snackbar = snackBar
        this.snackbar?.show()
    }

    override fun onPause() {
        super.onPause()
        snackbar?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mediator?.detach()
        mediator = null
        _binding = null
    }
}
package com.andrii.costsmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_costs.*
import timber.log.Timber


/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class CostsFragment : Fragment() {

    private lateinit var viewsDisposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_costs, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        initUi()
    }

    private fun initUi() {
        viewsDisposable = CompositeDisposable().apply {
            add(
                category_name_edit_text.textChanges().subscribe {
                    Timber.d("Name: [$it]")
                }
            )

            add(
                category_price_edit_text.textChanges().subscribe {
                    Timber.d("Price: [$it]")
                }
            )

            add(
                category_price_edit_text.editorActions {
                    if (it == EditorInfo.IME_ACTION_DONE) {
                        Timber.d("PRICE DONE")
                        button_submit.performClick()
                        return@editorActions true
                    }
                    false
                }.subscribe()
            )

            add(
                button_submit.clicks().subscribe {
                    Timber.d("CLICK")
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dispose()
    }

    private fun dispose() {
        viewsDisposable.dispose()
    }

    companion object {
        fun newInstance() = CostsFragment()
    }
}
package com.andrii.costsmanager.presentation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.andrii.costsmanager.R.layout
import com.andrii.costsmanager.presentation.model.CategoryModel
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_costs.button_submit
import kotlinx.android.synthetic.main.fragment_costs.category_name_autocomplete
import kotlinx.android.synthetic.main.fragment_costs.category_price_edit_text
import timber.log.Timber
import java.util.Date
import java.util.concurrent.TimeUnit


/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class CostsFragment : Fragment() {

    private val viewsDisposable = CompositeDisposable()
    private var adapterUpdateDisposable: Disposable? = null
    private var onSubmitDataFetchDisposable: Disposable? = null
    private lateinit var viewModel: CostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(CostsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layout.fragment_costs, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        updateAdapterData()

        viewsDisposable.apply {
            add(
                category_name_autocomplete.textChanges().map { it.toString() }.subscribe { text ->
                    button_submit.isEnabled = text.isNotEmpty() && category_price_edit_text.text?.isNotEmpty() == true
                    Timber.d("Name: [$text]")
                }
            )

            add(
                category_price_edit_text.textChanges().map { it.toString() }.subscribe { text ->
                    button_submit.isEnabled = text.isNotEmpty() && category_name_autocomplete.text?.isNotEmpty() == true
                    Timber.d("Price: [$text]")
                }
            )

            add(
                category_price_edit_text.editorActions {
                    if (it == EditorInfo.IME_ACTION_DONE) {
                        Timber.d("PRICE DONE")
                        button_submit.performClick()
                    }
                    false
                }.subscribe()
            )

            add(
                button_submit
                    .clicks()
                    .throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe {
                        onSubmitClick()
                        activity?.hideKeyboard()
                    }
            )
        }
    }

    private fun onSubmitClick() {
        onSubmitDataFetchDisposable?.checkAndDispose()

        onSubmitDataFetchDisposable = viewModel.saveCategory(
            CategoryModel(
                name = category_name_autocomplete.text.toString(),
                price = category_price_edit_text.text.toString().toInt(),
                date = Date()
            )
        ).subscribe(
            {
                Toast.makeText(activity, "Category is Saved", Toast.LENGTH_SHORT).show()
                Timber.d("Category is Saved")
            },
            {
                Toast.makeText(activity, "Category is NOT saved", Toast.LENGTH_SHORT).show()
                Timber.e(it, "Category is NOT saved")
            })
    }

    private fun updateAdapterData() {
        adapterUpdateDisposable?.checkAndDispose()

        adapterUpdateDisposable = viewModel.getCategories()
            .map { it.map { item -> item.name }.distinct() }
            .subscribe { list ->
                category_name_autocomplete.setAdapter(SearchAdapter(activity!!, list))
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewsDisposable.dispose()
        adapterUpdateDisposable?.checkAndDispose()
        onSubmitDataFetchDisposable?.checkAndDispose()
    }

    private fun Disposable.checkAndDispose() {
        if (!this.isDisposed) dispose()
    }

    class SearchAdapter(activity: Activity, val data: List<String>) :
        ArrayAdapter<String>(activity, android.R.layout.select_dialog_item, data)

    companion object {
        fun newInstance() = CostsFragment()
    }
}
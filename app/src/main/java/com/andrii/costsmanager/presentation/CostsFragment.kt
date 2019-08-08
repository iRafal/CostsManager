package com.andrii.costsmanager.presentation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.andrii.costsmanager.R.layout
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_costs.autocomplete_category_name
import kotlinx.android.synthetic.main.fragment_costs.button_submit
import kotlinx.android.synthetic.main.fragment_costs.category_name_edit_text
import kotlinx.android.synthetic.main.fragment_costs.category_price_edit_text
import kotlinx.android.synthetic.main.fragment_costs.toolbar
import timber.log.Timber
import java.util.Date
import java.util.concurrent.TimeUnit


/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class CostsFragment : Fragment() {

    private val disposable = CompositeDisposable()
    private lateinit var viewModel: CostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CostsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layout.fragment_costs, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        initUi()
    }

    private fun initUi() {
        disposable.apply {
            add(
                category_name_edit_text.textChanges().map { it.toString() }.subscribe { text ->
                    button_submit.isEnabled = text.isNotEmpty()
                    Timber.d("Name: [$text]")
                }
            )

            add(
                category_price_edit_text.textChanges().map { it.toString() }.subscribe { text ->
                    button_submit.isEnabled = text.isNotEmpty()
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
                    .subscribe { onSubmitClick() }
            )

            add(
                autocomplete_category_name.textChangeEvents()
                    .debounce(500L, TimeUnit.MILLISECONDS)
                    .map { it.toString() }
                    .filter { it.length >= 2 }
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .retry()
                    .subscribe {
                        viewModel.getCategories().subscribe { list ->
                            autocomplete_category_name.setAdapter(SearchAdapter(activity!!, list))
                        }
                    }
            )
        }
    }

    class SearchAdapter(activity: Activity, val data: List<CategoryModel>) :
        ArrayAdapter<String>(activity, android.R.layout.select_dialog_item, data.map { it.name })

    private fun onSubmitClick() {
        val disp = viewModel.saveCategory(
            CategoryModel(
                name = category_name_edit_text.text.toString(),
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
        disposable.add(disp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dispose()
    }

    private fun dispose() {
        disposable.dispose()
    }

    companion object {
        fun newInstance() = CostsFragment()
    }
}
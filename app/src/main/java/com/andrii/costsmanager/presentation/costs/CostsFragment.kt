package com.andrii.costsmanager.presentation.costs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.andrii.costsmanager.R.layout
import com.andrii.costsmanager.data.storage.CategoryDataBase
import com.andrii.costsmanager.data.storage.CategoryLocalRepository
import com.andrii.costsmanager.presentation.model.CategoryModel
import com.andrii.costsmanager.presentation.util.getViewModel
import com.andrii.costsmanager.presentation.util.hideKeyboard
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_costs.button_submit
import kotlinx.android.synthetic.main.fragment_costs.category_name_autocomplete
import kotlinx.android.synthetic.main.fragment_costs.category_price_edit_text
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.LazyThreadSafetyMode.NONE


/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class CostsFragment : Fragment(), CostsContract.View {

    private val compositeDisposable = CompositeDisposable()

    private val viewModel: CostsViewModel by lazy(mode = NONE) {
        val db = CategoryDataBase.getInstance(context!!)
        val localRepository = CategoryLocalRepository(db.categoryDao())
        activity!!.getViewModel { CostsViewModelImpl(localRepository) }
    }

    private val presenter: CostsContract.Presenter by lazy(mode = NONE) { CostsPresenter(this) }

    override val categoryName: String
        get() = category_name_autocomplete.text?.toString() ?: ""

    override val categoryPriceAsString: String
        get() = category_price_edit_text.text?.toString() ?: ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout.fragment_costs, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate()
        initUi()
    }

    private fun initUi() {
        compositeDisposable.apply {
            add(
                category_name_autocomplete.textChanges().map { it.toString() }.subscribe { text ->
                    button_submit.isEnabled =
                        text.isNotEmpty() && categoryPriceAsString.isNotEmpty() == true
                    Timber.d("Name: [$text]")
                }
            )

            add(
                category_price_edit_text.textChanges().map { it.toString() }.subscribe { text ->
                    button_submit.isEnabled = text.isNotEmpty() && categoryName.isNotEmpty() == true
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
                        presenter.onSubmitClick { model -> viewModel.saveCategory(model) }
                        activity?.hideKeyboard()
                    }
            )
        }
    }

    override fun loadCategories(loadListener: (List<CategoryModel>) -> Unit) {
        viewModel.getCategories().observe(this, androidx.lifecycle.Observer { categories ->
            loadListener(categories)
        })
    }

    override fun updateAutocomplete(categoriesNames: List<String>) {
        category_name_autocomplete.setAdapter(SearchAdapter(activity!!, categoriesNames))
    }

    override fun showMessage(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        fun newInstance() = CostsFragment()
    }
}

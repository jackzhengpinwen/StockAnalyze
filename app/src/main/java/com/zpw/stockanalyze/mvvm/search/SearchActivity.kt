package com.zpw.stockanalyze.mvvm.search

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.shencoder.loadingdialog.LoadingDialog
import com.zpw.stockanalyze.R
import com.zpw.stockanalyze.databinding.ActivitySearchBinding
import com.zpw.stockanalyze.mvvm.search.editlibrary.BSearchEdit
import com.zpw.stockanalyze.mvvm.search.searchview.SearchView
import com.zpw.stockanalyze.mvvm.utils.AppInjection
import com.zpw.stockanalyze.mvvm.view.BaseMvvmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber


class SearchActivity: BaseMvvmActivity<SearchViewModel, ActivitySearchBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_search
    lateinit var bSearchEdit: BSearchEdit
    lateinit var searchView: SearchView
    lateinit var stocks: ArrayList<String>
    lateinit var stockMap: HashMap<String, String>

    override fun initView() {
        searchView = findViewById(R.id.search_view)
        searchView.setOnClickSearch { stock ->
            val code: String = stockMap.get(stock) ?: stock
            viewModel.search(this, code)
        }
        searchView.setOnClickBack { finish() }
        searchView.et_search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
//                Timber.d("s is ${s}")
                val list = ArrayList<String>()
                stocks.forEach {
                    if (it.contains(s.toString())) {
                        list.add(it)
                    }
                }
                bSearchEdit.setSearchList(list)
                bSearchEdit.setTextClickListener { position, text ->
//                    Timber.d("bSearchEdit is ${text}")
                    val split = text.split(" ")
                    searchView.et_search.setText(split[1])
                    searchView.saveInfo()
                    viewModel.search(this@SearchActivity, split[0])
                }
            }

        })

        bSearchEdit = BSearchEdit(this, searchView.et_search, 200, 300)
        bSearchEdit.build()
    }

    override fun getViewModelOrFactory(): Any = AppInjection.getSearchViewModelFactory()

    override fun initObservable(viewModel: SearchViewModel) {

    }

    override fun getViewModelVariable(): Int {
        return 0
    }

    override fun loadData(viewModel: SearchViewModel) {
        searchView.et_search.requestFocus();
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchView.et_search, InputMethodManager.SHOW_IMPLICIT);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        if (viewModel.stock.isNotEmpty()) return
        val loadingDialog = LoadingDialog.createDefault(this)
        loadingDialog.show()
        lifecycleScope.async(Dispatchers.IO) {
            val stock = viewModel.getStock()
            lifecycleScope.launch(Dispatchers.Main) {
                stocks = ArrayList()
                stockMap = HashMap()
                stock.forEach {
                    stocks.add("${it.code} ${it.name}")
                    stockMap[it.name] = it.code
                }
                loadingDialog.dismiss()
            }
        }
    }
}
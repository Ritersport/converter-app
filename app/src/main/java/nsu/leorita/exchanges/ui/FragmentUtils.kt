package nsu.leorita.exchanges.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nsu.leorita.exchanges.App
import nsu.leorita.exchanges.domain.room.AppDatabase
import nsu.leorita.exchanges.domain.services.RangesService
import nsu.leorita.exchanges.ui.currenciesList.CurrenciesListViewModel
import nsu.leorita.exchanges.ui.currencyConverter.CurrencyConverterViewModel
import java.lang.IllegalStateException

class ViewModelFactory (
    private val app: App
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CurrenciesListViewModel::class.java -> {
                app.db?.let { CurrenciesListViewModel(app.rangesService, it) }
            }
            CurrencyConverterViewModel::class.java -> {
                app.db?.let { CurrencyConverterViewModel() }
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)
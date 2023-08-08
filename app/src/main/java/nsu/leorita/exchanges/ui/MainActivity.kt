package nsu.leorita.exchanges.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import nsu.leorita.exchanges.R
import nsu.leorita.exchanges.data.room.entities.CurrencyDbEntity
import nsu.leorita.exchanges.data.services.RangeServiceImpl
import nsu.leorita.exchanges.databinding.ActivityMainBinding
import nsu.leorita.exchanges.domain.model.Currency
import nsu.leorita.exchanges.domain.room.AppDatabase
import nsu.leorita.exchanges.ui.currenciesList.CurrenciesListFragment
import nsu.leorita.exchanges.ui.currenciesList.CurrenciesListViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

}
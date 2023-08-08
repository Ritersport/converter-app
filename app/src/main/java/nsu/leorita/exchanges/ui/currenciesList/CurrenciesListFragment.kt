package nsu.leorita.exchanges.ui.currenciesList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import nsu.leorita.exchanges.databinding.FragmentCurrenciesListBinding
import nsu.leorita.exchanges.domain.model.Currency
import nsu.leorita.exchanges.ui.ConvertActivity
import nsu.leorita.exchanges.ui.CurrencyAdapter
import nsu.leorita.exchanges.ui.factory

class CurrenciesListFragment : Fragment() {

    private var _binding: FragmentCurrenciesListBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private var adapter = CurrencyAdapter {
        data -> onCurrencyClicked(data)
    }

    private val viewModel: CurrenciesListViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrenciesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currencies.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.swiperefresh.setOnRefreshListener {
            viewModel.loadCurrenciesFromWeb()
            binding.swiperefresh.isRefreshing = false
        }
    }

    private fun onCurrencyClicked(data: Currency) {
        val recycleItemIntent = Intent(requireContext(), ConvertActivity::class.java)
        recycleItemIntent.putExtra("currencyName", data.name)
        recycleItemIntent.putExtra("currencyValue", data.getRange())
        startActivity(recycleItemIntent)
    }

}
package nsu.leorita.exchanges.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nsu.leorita.exchanges.databinding.ActivityConvertBinding

class ConvertActivity: AppCompatActivity(   ) {
    private var _binding: ActivityConvertBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityConvertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.convertButton.setOnClickListener {
            onConvertButtonClick()
        }
        binding.clearButton.setOnClickListener {
            onClearButtonClick()
        }
        binding.currencyName.text = intent.getStringExtra("currencyName")
    }

    private fun onConvertButtonClick() {
        binding.currency.setText("")
        if (binding.rubles.text == null) {
            binding.rubles.setText("")
        }
        if (binding.rubles.text?.isNotEmpty() == true) {
            binding.currency.setText((binding.rubles.text.toString()
                .toFloat() / intent.getFloatExtra("currencyValue", 0F)).toString())
        }
    }

    private fun onClearButtonClick() {
        binding.rubles.setText("")
        binding.currency.setText("")
    }

}
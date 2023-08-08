package nsu.leorita.exchanges

import nsu.leorita.exchanges.domain.model.Currency

interface Navigator {
    fun showConverter(currency: Currency)

    fun goBack()
}
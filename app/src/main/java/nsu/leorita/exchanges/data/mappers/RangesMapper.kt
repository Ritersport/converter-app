package nsu.leorita.exchanges.data.mappers

import nsu.leorita.exchanges.data.services.models.ExchangeMessage
import nsu.leorita.exchanges.domain.model.Currency
import nsu.leorita.exchanges.domain.model.RangesInfo

object RangesMapper {
    fun toDomen(message: ExchangeMessage) : RangesInfo {
        val ranges = HashMap<String, Currency>()
        message.valuteSet.forEach { (s, valute) ->  ranges.put(s, Currency(valute.code, valute.name, valute.nominal, valute.value, valute.previousValue))}
        return RangesInfo(message.date, ranges)
    }
}
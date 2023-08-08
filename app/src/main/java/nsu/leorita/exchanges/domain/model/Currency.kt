package nsu.leorita.exchanges.domain.model

data class Currency(
    val code: String,
    val name: String,
    val denomination: Int,
    val value: Float,
    val previousValue: Float,
) {
    fun getRange() = value / denomination
}

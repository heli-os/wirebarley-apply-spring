package apply.aaron.wirebarley.domain

import apply.aaron.wirebarley.util.notNull
import java.time.LocalDateTime


data class Transaction(
    val amount: Double,
    val currency: Currency
) {

    enum class Currency {
        USD, KRW, JPY, PHP;

        companion object {

            private val CURRENCIES = values().associateBy { it.name.lowercase() }

            fun from(currency: String): Currency {
                return CURRENCIES[currency.lowercase()].notNull { "Currency not found [currency: $currency]" }
            }
        }
    }
}

data class ExRate(
    val amount: Double,
    val lastUpdateDateTime: LocalDateTime
)

data class CalculateResult(
    val amount: Double,
    val exRateLastUpdateDateTime: LocalDateTime
)

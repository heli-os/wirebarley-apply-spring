package apply.aaron.wirebarley.`interface`

import apply.aaron.wirebarley.domain.Transaction

data class ExRateResponseDto(
    val sourceCurrency: Transaction.Currency,
    val destinationCurrency: Transaction.Currency,
    val exRateAmount: Double,
    val exRateLastUpdateEpochMillis: Long
)

data class ExRateCalculateResponseDto(
    val transaction: Transaction,
    val exRateLastUpdateEpochMillis: Long
)

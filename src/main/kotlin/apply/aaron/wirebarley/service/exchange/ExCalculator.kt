package apply.aaron.wirebarley.service.exchange

import apply.aaron.wirebarley.domain.CalculateResult
import apply.aaron.wirebarley.domain.Transaction
import org.springframework.stereotype.Service

@Service
class ExCalculator(
    private val exRateFetcher: ExRateFetcher
) {

    fun calculate(sourceTx: Transaction, destinationCurrency: Transaction.Currency): CalculateResult {
        val exRate = exRateFetcher.fetch(sourceTx.currency, destinationCurrency)
        return CalculateResult(
            amount = sourceTx.amount * exRate.amount,
            exRateLastUpdateDateTime = exRate.lastUpdateDateTime
        )
    }
}

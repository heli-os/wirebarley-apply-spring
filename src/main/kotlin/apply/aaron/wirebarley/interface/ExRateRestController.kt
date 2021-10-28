package apply.aaron.wirebarley.`interface`

import apply.aaron.wirebarley.domain.Transaction
import apply.aaron.wirebarley.service.exchange.ExCalculator
import apply.aaron.wirebarley.service.exchange.ExRateFetcher
import apply.aaron.wirebarley.util.toEpochMillis
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExRateRestController(
    private val exRateFetcher: ExRateFetcher,
    private val exRateCalculator: ExCalculator
) {

    @GetMapping(
        value = ["/exchange/api/v1/rate/{sourceCurrency}/{destinationCurrency}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun fetchExRate(
        @PathVariable sourceCurrency: String,
        @PathVariable destinationCurrency: String
    ): ExRateResponseDto {

        val sourceTransactionCurrency = Transaction.Currency.from(sourceCurrency)
        val destinationTransactionCurrency = Transaction.Currency.from(destinationCurrency)

        val exRate = exRateFetcher.fetch(
            sourceCurrency = sourceTransactionCurrency,
            destinationCurrency = destinationTransactionCurrency
        )

        return ExRateResponseDto(
            sourceCurrency = sourceTransactionCurrency,
            destinationCurrency = destinationTransactionCurrency,
            exRateAmount = exRate.amount,
            exRateLastUpdateEpochMillis = exRate.lastUpdateDateTime.toEpochMillis()
        )
    }

    @GetMapping(
        value = ["/exchange/api/v1/rate/calculate/{sourceCurrency}/{destinationCurrency}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun calculateExRate(
        @PathVariable sourceCurrency: String,
        @PathVariable destinationCurrency: String,
        @RequestParam sourceAmount: Double
    ): ExRateCalculateResponseDto {

        val sourceTransactionCurrency = Transaction.Currency.from(sourceCurrency)
        val destinationTransactionCurrency = Transaction.Currency.from(destinationCurrency)

        val calculateResult = exRateCalculator.calculate(
            sourceTx = Transaction(
                amount = sourceAmount,
                currency = sourceTransactionCurrency
            ),
            destinationCurrency = Transaction.Currency.from(destinationCurrency)
        )

        return ExRateCalculateResponseDto(
            transaction = Transaction(
                amount = calculateResult.amount,
                currency = destinationTransactionCurrency
            ),
            exRateLastUpdateEpochMillis = calculateResult.exRateLastUpdateDateTime.toEpochMillis()
        )
    }
}

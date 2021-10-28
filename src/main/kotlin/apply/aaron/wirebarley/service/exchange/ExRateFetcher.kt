package apply.aaron.wirebarley.service.exchange

import apply.aaron.wirebarley.client.currenylayer.CurrencyLayerClient
import apply.aaron.wirebarley.domain.ExRate
import apply.aaron.wirebarley.domain.Transaction
import apply.aaron.wirebarley.util.notNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class ExRateFetcher(
    private val currencyLayerClient: CurrencyLayerClient
) : InitializingBean {
    private val currency = ConcurrentHashMap<String, ExRate>()

    @Scheduled(fixedRate = FETCH_INTERVAL_MILLIS, initialDelay = FETCH_INITIAL_DELAY)
    private fun scheduled() {
        fetchExRateFromRemote()
    }

    @Value("\${currency-layer.client.access-key}")
    private lateinit var accessKey: String

    override fun afterPropertiesSet() {
        fetchExRateFromRemote()
    }

    fun fetch(sourceCurrency: Transaction.Currency, destinationCurrency: Transaction.Currency): ExRate {
        val currencyQuote = sourceCurrency.name + destinationCurrency.name

        return currency[currencyQuote].notNull { log.error("ExRate 조회 실패 [sourceCurrency=${sourceCurrency}, destinationCurrency=${destinationCurrency}]") }
    }

    private fun fetchExRateFromRemote() {
        val liveExRate = currencyLayerClient.getLiveExRate(
            access_key = accessKey,
            currencies = FETCH_TARGET_CURRENCY.joinToString()
        )

        liveExRate.quotes.forEach {
            currency.compute(it.key) { _, _ ->
                ExRate(
                    amount = it.value,
                    lastUpdateDateTime = LocalDateTime.now()
                )
            }
        }
    }

    companion object {

        val log: Logger = LoggerFactory.getLogger(ExRateFetcher::class.java)

        private val FETCH_TARGET_CURRENCY = listOf(
            Transaction.Currency.KRW,
            Transaction.Currency.JPY,
            Transaction.Currency.PHP
        )

        private const val FETCH_INTERVAL_MILLIS = 5 * 60 * 1000L
        private const val FETCH_INITIAL_DELAY = 5 * 60 * 1000L
    }
}

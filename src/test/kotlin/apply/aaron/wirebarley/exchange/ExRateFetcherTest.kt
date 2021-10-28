package apply.aaron.wirebarley.exchange

import apply.aaron.wirebarley.client.currenylayer.CurrencyLayerClient
import apply.aaron.wirebarley.client.currenylayer.ExRateResponseDto
import apply.aaron.wirebarley.domain.Transaction
import apply.aaron.wirebarley.service.exchange.ExRateFetcher
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.util.ReflectionTestUtils
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo

@ExtendWith(MockKExtension::class)
internal class ExRateFetcherTest {

    @MockK
    private lateinit var currencyLayerClient: CurrencyLayerClient

    @InjectMockKs
    private lateinit var sut: ExRateFetcher

    @BeforeEach
    fun setUp() {
        ReflectionTestUtils.setField(sut, "accessKey", ACCESS_KEY)
    }

    @Test
    fun `환율 정보를 가져올 수 있다`() {
        // given
        val sourceCurrency = Transaction.Currency.USD
        val destinationCurrency = Transaction.Currency.KRW
        every { currencyLayerClient.getLiveExRate(ACCESS_KEY, any()) } returns ExRateResponseDto(
            success = true,
            terms = "",
            privacy = "",
            timestamp = -1L,
            source = "USD",
            quotes = mapOf("USDKRW" to 1_000.0)
        )
        sut.afterPropertiesSet()

        // when
        val actual = sut.fetch(
            sourceCurrency = sourceCurrency,
            destinationCurrency = destinationCurrency
        )

        // then
        expectThat(actual.amount) isEqualTo 1_000.0
    }

    @Test
    fun `환율 정보를 가져올 수 없으면 Exception 이 발생하고 로그를 찍는다`() {
        // given
        val sourceCurrency = Transaction.Currency.USD
        val destinationCurrency = Transaction.Currency.KRW
        every { currencyLayerClient.getLiveExRate(ACCESS_KEY, any()) } returns ExRateResponseDto(
            success = true,
            terms = "",
            privacy = "",
            timestamp = -1L,
            source = "USD",
            quotes = mapOf("USDKRW" to 1_000.0)
        )

        // when
        expectThrows<IllegalArgumentException> {
            sut.fetch(
                sourceCurrency = sourceCurrency,
                destinationCurrency = destinationCurrency
            )
        }

    }

    companion object {

        private const val ACCESS_KEY = ""
    }
}

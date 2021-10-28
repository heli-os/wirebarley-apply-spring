package apply.aaron.wirebarley.exchange

import apply.aaron.wirebarley.domain.ExRate
import apply.aaron.wirebarley.domain.Transaction
import apply.aaron.wirebarley.service.exchange.ExCalculator
import apply.aaron.wirebarley.service.exchange.ExRateFetcher
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
internal class ExCalculatorTest {

    @MockK
    private lateinit var exRateFetcher: ExRateFetcher

    @InjectMockKs
    private lateinit var sut: ExCalculator

    @Test
    fun `수취금액을 계산할 수 있다`() {
        // given
        val sourceTx = Transaction(1_000.0, Transaction.Currency.USD)
        val destinationCurrency = Transaction.Currency.KRW

        every { exRateFetcher.fetch(any(), any()) } returns ExRate(1_000.0, LocalDateTime.now())

        // when
        val actual = sut.calculate(
            sourceTx = sourceTx,
            destinationCurrency = destinationCurrency
        )

        // then
        expectThat(actual.amount) isEqualTo 1_000_000.0
    }
}

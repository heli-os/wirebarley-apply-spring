package apply.aaron.wirebarley.client.currenylayer

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Component
@FeignClient(name = "currency-layer", url = "\${currency-layer.client.host}")
interface CurrencyLayerClient {

    @GetMapping(
        value = ["/live"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getLiveExRate(
        @RequestParam access_key: String,
        @RequestParam currencies: String? = null
    ): ExRateResponseDto
}

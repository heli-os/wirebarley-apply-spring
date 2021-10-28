package apply.aaron.wirebarley.client.currenylayer

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@ComponentScan(basePackageClasses = [CurrencyLayerClient::class])
@EnableFeignClients(basePackageClasses = [CurrencyLayerClient::class])
@Configuration
internal class CurrencyLayerClientConfiguration

@Import(CurrencyLayerClientConfiguration::class)
annotation class EnableCurrentLayerClients

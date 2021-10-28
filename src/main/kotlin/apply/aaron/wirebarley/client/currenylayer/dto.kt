package apply.aaron.wirebarley.client.currenylayer

data class ExRateResponseDto(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)

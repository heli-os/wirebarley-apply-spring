package apply.aaron.wirebarley.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

fun LocalDateTime.toEpochMillis(zoneId: ZoneId = ZoneOffset.UTC): Long = this.atZone(zoneId).toInstant().toEpochMilli()

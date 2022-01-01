package week1

import kotlin.math.floor
import kotlin.math.roundToInt

sealed class JsonElement
data class JsonObject(val fields: Map<String, JsonElement>) : JsonElement() {
    constructor(vararg fields: Pair<String, JsonElement>) : this(fields.toMap())
}

data class JsonArray(val elements: List<JsonElement>) : JsonElement() {
    constructor(vararg elements: JsonElement) : this(elements.toList())
}

data class JsonNumber(val value: Double) : JsonElement()
data class JsonString(val value: String) : JsonElement()
data class JsonBoolean(val value: Boolean) : JsonElement()
object JsonNull : JsonElement()

fun JsonElement.stringify(): String = when (this) {
    JsonNull -> "null"
    is JsonBoolean -> "$value"
    is JsonString -> "\"$value\""
    is JsonNumber -> if (value == floor(value)) "${value.roundToInt()}" else "$value"
    is JsonArray -> elements
        .joinToString(prefix = "[", postfix = "]", separator = ",") { value ->
            value.stringify()
        }
    is JsonObject -> fields.toList()
        .joinToString(prefix = "{", postfix = "}", separator = ",") { (name, value) ->
            "\"$name\":${value.stringify()}"
        }
}

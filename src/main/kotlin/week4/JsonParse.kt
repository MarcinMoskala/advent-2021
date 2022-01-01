package week4

import week1.*

fun parseJson(text: String): JsonObject? {
    val trimmed = text.trim()
    if (!trimmed.startsWith("{") || !trimmed.endsWith("}")) return null
    var content = trimmed.substringAfter("{").substringBeforeLast("}").trim()
    val fields = mutableMapOf<String, JsonElement>()
    while (true) {
        if (content.isEmpty()) return JsonObject(fields)
        if (!content.startsWith("\"")) return null
        val (fieldName, rest) = content.substringAfter("\"").split("\":", limit = 2)
            .also { if (it.size < 2) return null }
        val (element, restToParse) = parseJsonElement(rest) ?: return null
        fields[fieldName] = element
        content = restToParse
    }
}

private const val EVERYTHING_REGEX = "[\\s\\S]*"
private const val EVERYTHING_NON_GREEDY_REGEX = "[\\s\\S]*?"
private const val COMMA_AND_REST_REGEX = "(\\s*,\\s*)?($EVERYTHING_REGEX)"
private val NUMBER_REGEX = Regex("^(\\d+([.]\\d+)?)$COMMA_AND_REST_REGEX")
private val NULL_REGEX = Regex("^null$COMMA_AND_REST_REGEX")
private val BOOLEAN_REGEX = Regex("^(true|false)$COMMA_AND_REST_REGEX")
private val STRING_REGEX = Regex("^\"($EVERYTHING_NON_GREEDY_REGEX)\"$COMMA_AND_REST_REGEX")
private val OBJECT_REGEX = Regex("^(\\{$EVERYTHING_NON_GREEDY_REGEX})$COMMA_AND_REST_REGEX")
private val ARRAY_REGEX = Regex("^\\[($EVERYTHING_REGEX)]$COMMA_AND_REST_REGEX")

private data class ParsingResult(val element: JsonElement, val restToParse: String)

private fun parseJsonElement(text: String): ParsingResult? = when {
    text.contains(NUMBER_REGEX) -> NUMBER_REGEX.find(text)!!.groupValues
        .let { ParsingResult(JsonNumber(it[1].toDouble()), it[4]) }
    text.contains(NULL_REGEX) -> NULL_REGEX.find(text)!!.groupValues
        .let { ParsingResult(JsonNull, it[2]) }
    text.contains(BOOLEAN_REGEX) -> BOOLEAN_REGEX.find(text)!!.groupValues
        .let { ParsingResult(JsonBoolean(it[1].toBoolean()), it[3]) }
    text.contains(STRING_REGEX) -> STRING_REGEX.find(text)!!.groupValues
        .let { ParsingResult(JsonString(it[1]), it[3]) }
    text.contains(OBJECT_REGEX) -> OBJECT_REGEX.find(text)!!.groupValues
        .let {
            val (objectText, restToParse) = extractObjectText(it[0]) ?: return null
            val element = parseJson(objectText) ?: return null
            ParsingResult(element, restToParse)
        }
    text.contains(ARRAY_REGEX) -> ARRAY_REGEX.find(text)!!.groupValues
        .let {
            var content = it[1]
            val elements = mutableListOf<JsonElement>()
            while (content.isNotEmpty()) {
                val (element, rest) = parseJsonElement(content) ?: return null
                elements += element
                content = if(rest.startsWith(",")) rest.drop(1) else rest
            }
            ParsingResult(JsonArray(elements), it[2])
        }
    else -> null
}

private data class ObjectTextAndRest(val objectText: String, val restToParse: String)

private fun extractObjectText(text: String): ObjectTextAndRest? {
    var index = 1
    var openedBrackets = 1
    while (openedBrackets > 0) {
        when (text.getOrNull(index)) {
            null -> return null
            '{' -> openedBrackets++
            '}' -> openedBrackets--
        }
        index++
    }
    return ObjectTextAndRest(text.take(index), text.drop(index))
}
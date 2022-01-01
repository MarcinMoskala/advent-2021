package week4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import week1.*

class JsonParseTest {
    @Test
    fun `should parse empty object`() {
        assertEquals(JsonObject(), parseJson("""{}"""))
    }

    @Test
    fun `should parse null`() {
        assertEquals(JsonObject("n" to JsonNull), parseJson("""{"n":null}"""))
    }

    @Test
    fun `should parse number`() {
        assertEquals(JsonObject("i" to JsonNumber(1.0)), parseJson("""{"i":1}"""))
        assertEquals(JsonObject("i" to JsonNumber(10.0)), parseJson("""{"i":10}"""))
        assertEquals(JsonObject("i" to JsonNumber(3.14)), parseJson("""{"i":3.14}"""))
    }

    @Test
    fun `should parse boolean`() {
        assertEquals(JsonObject("a" to JsonBoolean(true)), parseJson("""{"a":true}"""))
        assertEquals(JsonObject("a" to JsonBoolean(false)), parseJson("""{"a":false}"""))
    }

    @Test
    fun `should parse string`() {
        assertEquals(JsonObject("str" to JsonString("Some text")), parseJson("""{"str":"Some text"}"""))
    }

    @Test
    fun `should parse empty object inside object`() {
        assertEquals(JsonObject("obj" to JsonObject(mapOf())), parseJson("""{"obj":{}}"""))
    }

    @Test
    fun `should parse empty object inside object inside object`() {
        assertEquals(
            JsonObject("obj" to JsonObject("obj2" to JsonObject())),
            parseJson("""{"obj":{"obj2":{}}}""")
        )
    }

    @Test
    fun `should parse all primitive types and ignore white spaces`() {
        assertEquals(
            JsonObject(
                "a" to JsonString("AAA"),
                "b" to JsonNumber(123.45),
                "c" to JsonBoolean(true),
                "d" to JsonBoolean(false),
                "e" to JsonNull,
            ),
            parseJson("""{"a":"AAA","b":123.45,"c":true,"d":false,"e":null}""")
        )
    }

    @Test
    fun `should parse all primitive types in object inside object`() {
        assertEquals(
            JsonObject(
                "o" to JsonObject(
                    "a" to JsonString("AAA"),
                    "b" to JsonNumber(123.45),
                    "c" to JsonBoolean(true),
                    "d" to JsonBoolean(false),
                    "e" to JsonNull,
                )
            ),
            parseJson("""{"o":{"a":"AAA","b":123.45,"c":true,"d":false,"e":null}}""")
        )
    }

    @Test
    fun `should parse array with strings`() {
        assertEquals(
            JsonObject(
                "letters" to JsonArray(
                    JsonString("A"),
                    JsonString("B"),
                    JsonString("C"),
                )
            ),
            parseJson("""{"letters":["A", "B", "C"]}""")
        )
    }

    @Test
    fun `should parse array with numbers`() {
        assertEquals(
            JsonObject(
                "numbers" to JsonArray(
                    JsonNumber(1.0),
                    JsonNumber(2.0),
                    JsonNumber(3.0),
                )
            ),
            parseJson("""{"numbers":[1,2,3]}""")
        )
    }

    @Test
    fun `should parse array with objects`() {
        assertEquals(
            JsonObject(
                "users" to JsonArray(
                    JsonObject("name" to JsonString("Marcin")),
                    JsonObject("name" to JsonString("Maja")),
                )
            ),
            parseJson("""{"users":[{"name":"Marcin"},{"name":"Maja"}]}""")
        )
    }

    @Test
    fun `should parse stringified object`() {
        val jsonElements = listOf(
            JsonObject("name" to JsonString("This is some string")),
            JsonObject(
                "a" to JsonArray(
                    JsonObject("name" to JsonString("Marcin")),
                    JsonObject("name" to JsonString("Maja")),
                )
            )
        )
        for (jsonElement in jsonElements) {
            val text = jsonElement.stringify()
            val parsed = parseJson(text)
            assertEquals(jsonElement, parsed)
        }
    }

    @Test
    fun `should return null for malformed jsons`() {
        assertEquals(null, parseJson("{name:\"AAA\"}"))
        assertEquals(null, parseJson("{\"name\":AAA}"))
        assertEquals(null, parseJson("{\"name\":\"AAA\""))
        assertEquals(null, parseJson("{\"name\"\"AAA\"}"))
    }
}
package week1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonStringifyTest {
    @Test
    fun `should stringify empty object`() {
        assertEquals("{}", JsonObject().stringify())
    }

    @Test
    fun `should stringify null`() {
        assertEquals("""{"n":null}""", JsonObject("n" to JsonNull).stringify())
    }

    @Test
    fun `should stringify number`() {
        assertEquals("""{"i":1}""", JsonObject("i" to JsonNumber(1.0)).stringify())
        assertEquals("""{"i":10}""", JsonObject("i" to JsonNumber(10.0)).stringify())
        assertEquals("""{"i":3.14}""", JsonObject("i" to JsonNumber(3.14)).stringify())
    }

    @Test
    fun `should stringify boolean`() {
        assertEquals("""{"a":true}""", JsonObject("a" to JsonBoolean(true)).stringify())
        assertEquals("""{"a":false}""", JsonObject("a" to JsonBoolean(false)).stringify())
    }

    @Test
    fun `should stringify string`() {
        assertEquals("""{"str":"Some text"}""", JsonObject("str" to JsonString("Some text")).stringify())
    }

    @Test
    fun `should stringify empty object inside object`() {
        assertEquals("""{"obj":{}}""", JsonObject("obj" to JsonObject(mapOf())).stringify())
    }

    @Test
    fun `should stringify empty object inside object inside object`() {
        assertEquals(
            """{"obj":{"obj2":{}}}""",
            JsonObject("obj" to JsonObject("obj2" to JsonObject())).stringify()
        )
    }

    @Test
    fun `should stringify all primitive types and ignore white spaces`() {
        assertEquals(
            """{"a":"AAA","b":123.45,"c":true,"d":false,"e":null}""",
            JsonObject(
                "a" to JsonString("AAA"),
                "b" to JsonNumber(123.45),
                "c" to JsonBoolean(true),
                "d" to JsonBoolean(false),
                "e" to JsonNull,
            ).stringify()
        )
    }

    @Test
    fun `should stringify all primitive types in object inside object`() {
        assertEquals(
            """{"o":{"a":"AAA","b":123.45,"c":true,"d":false,"e":null}}""",
            JsonObject(
                "o" to JsonObject(
                    "a" to JsonString("AAA"),
                    "b" to JsonNumber(123.45),
                    "c" to JsonBoolean(true),
                    "d" to JsonBoolean(false),
                    "e" to JsonNull,
                )
            ).stringify()
        )
    }

    @Test
    fun `should stringify array with strings`() {
        assertEquals(
            """{"letters":["A","B","C"]}""",
            JsonObject(
                "letters" to JsonArray(
                    JsonString("A"),
                    JsonString("B"),
                    JsonString("C"),
                )
            ).stringify()
        )
    }

    @Test
    fun `should stringify array with numbers`() {
        assertEquals(
            """{"numbers":[1,2,3]}""",
            JsonObject(
                "numbers" to JsonArray(
                    JsonNumber(1.0),
                    JsonNumber(2.0),
                    JsonNumber(3.0),
                )
            ).stringify()
        )
    }

    @Test
    fun `should stringify array with objects`() {
        assertEquals(
            """{"users":[{"name":"Marcin"},{"name":"Maja"}]}""",
            JsonObject(
                "users" to JsonArray(
                    JsonObject("name" to JsonString("Marcin")),
                    JsonObject("name" to JsonString("Maja")),
                )
            ).stringify()
        )
    }
}
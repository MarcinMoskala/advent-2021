package week1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenerateParenthesisCombinationsTests {

    @Test
    fun test() {
        assertEquals(listOf("()"), generateParenthesisCombinations(1))
        assertEquals(listOf("(())", "()()"), generateParenthesisCombinations(2).sorted())
        assertEquals(
            listOf("((()))", "(()())", "(())()", "()(())", "()()()"),
            generateParenthesisCombinations(3).sorted()
        )
        assertEquals(
            listOf(
                "(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())", "(()())()",
                "(())(())", "(())()()", "()((()))", "()(()())", "()(())()", "()()(())", "()()()()"
            ),
            generateParenthesisCombinations(4).sorted()
        )
        assertEquals(
            listOf(
                "((((()))))", "(((()())))", "(((())()))", "(((()))())", "(((())))()", "((()(())))",
                "((()()()))", "((()())())", "((()()))()", "((())(()))", "((())()())", "((())())()",
                "((()))(())", "((()))()()", "(()((())))", "(()(()()))", "(()(())())", "(()(()))()",
                "(()()(()))", "(()()()())", "(()()())()", "(()())(())", "(()())()()", "(())((()))",
                "(())(()())", "(())(())()", "(())()(())", "(())()()()", "()(((())))", "()((()()))",
                "()((())())", "()((()))()", "()(()(()))", "()(()()())", "()(()())()", "()(())(())",
                "()(())()()", "()()((()))", "()()(()())", "()()(())()", "()()()(())", "()()()()()"
            ),
            generateParenthesisCombinations(5).sorted()
        )
        assertEquals(16796, generateParenthesisCombinations(10).size)
    }
}
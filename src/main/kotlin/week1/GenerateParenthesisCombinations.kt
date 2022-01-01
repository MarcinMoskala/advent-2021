package week1

fun generateParenthesisCombinations(num: Int): List<String> =
    if (num < 1) emptyList()
    else generateParenthesisCombinationsIter("", num, 0)

fun generateParenthesisCombinationsIter(text: String, parenthesisLeft: Int, openedToClose: Int): List<String> {
    fun openParen() = generateParenthesisCombinationsIter("$text(", parenthesisLeft - 1, openedToClose + 1)
    fun closeParen() = generateParenthesisCombinationsIter("$text)", parenthesisLeft, openedToClose - 1)
    return when {
        parenthesisLeft == 0 && openedToClose == 0 -> listOf(text)
        openedToClose == 0 -> openParen()
        parenthesisLeft == 0 -> closeParen()
        else -> openParen() + closeParen()
    }
}
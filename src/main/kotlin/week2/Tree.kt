package week2

sealed class Tree<T> {
    override fun toString(): String = when (this) {
        is Leaf -> value.toString()
        is Node -> "($left, $right)"
    }
}

data class Leaf<T>(val value: T) : Tree<T>()
data class Node<T>(val left: Tree<T>, val right: Tree<T>) : Tree<T>()

fun Tree<*>.count(): Int = when (this) {
    is Leaf -> 1
    is Node -> left.count() + right.count()
}
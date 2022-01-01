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

fun Tree<*>.countAll(): Int = when (this) {
    is Leaf -> 1
    is Node -> 1 + left.count() + right.count()
}

fun Tree<*>.height(): Int = when (this) {
    is Leaf -> 1
    is Node -> 1 + maxOf(left.count(), right.count())
}

fun <T : Comparable<T>> Tree<T>.biggest(): T = when (this) {
    is Leaf -> value
    is Node -> maxOf(left.biggest(), right.biggest())
}

fun Tree<Int>.sum(): Int = when (this) {
    is Leaf -> value
    is Node -> left.sum() + right.sum()
}

operator fun <T> Tree<T>.contains(value: T): Boolean = when (this) {
    is Leaf -> this.value == value
    is Node -> left.contains(value) || right.contains(value)
}

operator fun <T> Tree<T>.plus(other: Tree<T>): Tree<T> =
    Node(this, other)

fun <T> Tree<T>.toList(): List<T> = when (this) {
    is Leaf -> listOf(value)
    is Node -> left.toList() + right.toList()
}
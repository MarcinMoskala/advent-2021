package week2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TreeOperationsTest {
    /*
            root Node
            /      \
          Node     CCC
         /    \
       AAA    BBB
     */
    private val tree1 = Node(Node(Leaf("AAA"), Leaf("BBB")), Leaf("CCC"))

    /*
                    root Node
                   /         \
               Node           Node
            /      \        /      \
          Node     CCC    Node     CCC
         /    \          /    \
       AAA    BBB      AAA    BBB
     */
    private val tree2 =
        Node(Node(Node(Leaf("AAA"), Leaf("BBB")), Leaf("CCC")), Node(Node(Leaf("AAA"), Leaf("BBB")), Leaf("CCC")))

    /*
                root Node
                /     \
              Node    DDD
            /      \
          Node     CCC
         /    \
       AAA    BBB
     */
    private val tree3 = Node(Node(Node(Leaf("AAA"), Leaf("BBB")), Leaf("CCC")), Leaf("DDD"))

    /*
                root Node
                /     \
              Node    DDD
            /      \
          Node     CCC
         /    \
       AAA    BBB
     */
    val treeInt = Node(Node(Node(Leaf(5), Leaf(3)), Leaf(4)), Leaf(3))

    @Test
    fun `Count of a leaf is 1`() {
        assertEquals(1, Leaf("AAA").count())
    }

    @Test
    fun `Count of a single node with two leafs is 2`() {
        assertEquals(2, Node(Leaf("AAA"), Leaf("BBB")).count())
    }

    @Test
    fun `Count returns number of leafs in the tree`() {
        assertEquals(3, tree1.count())
        assertEquals(6, tree2.count())
        assertEquals(4, tree3.count())
        assertEquals(4, treeInt.count())
    }

    @Test
    fun `countAll of a leaf is 1`() {
        assertEquals(1, Leaf("AAA").countAll())
    }

    @Test
    fun `countAll of a single node with two leafs is 3`() {
        assertEquals(3, Node(Leaf("AAA"), Leaf("BBB")).countAll())
    }

    @Test
    fun `countAll returns number of leafs in the tree`() {
        assertEquals(5, tree1.countAll())
    }

    @Test
    fun `Height of a leaf is 1`() {
        assertEquals(1, Leaf("AAA").height())
    }

    @Test
    fun `Height of a single node with two leafs is 2`() {
        assertEquals(2, Node(Leaf("AAA"), Leaf("BBB")).height())
    }

    @Test
    // Every level in height is one more to the result
    fun `Height returns the highest distance from the root to a leaf`() {
        assertEquals(3, tree1.height())
        assertEquals(4, tree2.height())
        assertEquals(4, tree3.height())
    }

    @Test
    fun `Biggest on leaf returns its element`() {
        assertEquals(1, Leaf(1).biggest())
        assertEquals("Z", Leaf("Z").biggest())
        assertEquals(Float.POSITIVE_INFINITY, Leaf(Float.POSITIVE_INFINITY).biggest())
    }

    @Test
    fun biggestTest() {
        assertEquals("CCC", tree1.biggest())
        assertEquals("CCC", tree2.biggest())
        assertEquals("DDD", tree3.biggest())
        assertEquals(5, treeInt.biggest())
    }

    @Test
    fun sumTest() {
        assertEquals(15, treeInt.sum())
    }

    @Test
    fun `Value of a leaf is in this leaf`() {
        assertTrue(Leaf("AAA").contains("AAA"))
    }

    @Test
    fun `Node with leafs contains values of its leafs`() {
        val tree = Node(Leaf("AAA"), Leaf("BBB"))
        assertTrue(tree.contains("AAA"))
        assertTrue(tree.contains("BBB"))
        assertTrue(!tree.contains("CCC"))
        assertTrue(!tree.contains("DDD"))
    }

    @Test
    fun `Complex cases`() {
        assertTrue("AAA" in tree1)
        assertTrue("BBB" in tree1)
        assertTrue("CCC" in tree1)
        assertTrue("CCCC" !in tree1)
        assertTrue("D" !in tree1)

        assertTrue("AAA" in tree2)
        assertTrue("BBB" in tree2)
        assertTrue("CCC" in tree2)
        assertTrue("CCCC" !in tree2)
        assertTrue("D" !in tree2)


        assertTrue("AAA" in tree3)
        assertTrue("BBB" in tree3)
        assertTrue("CCC" in tree3)
        assertTrue("DDD" in tree3)
        assertTrue("CCCC" !in tree3)
        assertTrue("D" !in tree3)
    }

    @Test
    fun `Plus of two leafs test`() {
        val tree = Leaf("AAA") + Leaf("BBB")
        assertTrue(tree is Node<*>)
        val n = tree as Node<*>
        assertTrue(n.left == Leaf("AAA"))
        assertTrue(n.right == Leaf("BBB"))
    }

    @Test
    fun `Plus just places subtrees on the left and right side without copying them`() {
        val tree = tree1 + tree2
        assertTrue(tree is Node<*>)
        val n = tree as Node<*>
        assertTrue(n.left === tree1)
        assertTrue(n.right === tree2)
    }

    @Test
    fun toListTest() {
        assertEquals(listOf("AAA", "BBB", "CCC"), tree1.toList())
        assertEquals(listOf("AAA", "BBB", "CCC", "AAA", "BBB", "CCC"), tree2.toList())
        assertEquals(listOf("AAA", "BBB", "CCC", "DDD"), tree3.toList())
        assertEquals(listOf(5, 3, 4, 3), treeInt.toList())
    }
}
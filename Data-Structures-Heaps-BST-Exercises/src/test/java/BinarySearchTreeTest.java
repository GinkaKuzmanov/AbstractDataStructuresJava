
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> bst;

    @Before
    public void beforeEachTest() {
        bst = new BinarySearchTree<>(5);
        bst.insert(2);
        bst.insert(13);
        bst.insert(8);
        bst.insert(1);
    }

    @Test
    public void testCreate() {
        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
    }


    @Test
    public void testInsert() {
        assertEquals(Integer.valueOf(13), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(2), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(8), bst.getRoot().getRight().getLeft().getValue());

    }

    @Test
    public void testConsumerEachInOrder() {

        List<Integer> elements = new ArrayList<>();

        bst.eachInOrder(elements::add);

        List<Integer> expected = new ArrayList<>(
                Arrays.asList(1, 2, 5, 8, 13)
        );

        assertEquals(expected.size(), elements.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), elements.get(i));
        }

    }

    @Test
    public void testContainsTrue() {
        assertTrue(bst.contains(8));
    }

    @Test
    public void testContainsFalse() {
        assertFalse(bst.contains(7));
    }

    @Test
    public void testSearchTrue() {
        BinarySearchTree<Integer> searchTree = bst.search(13);
        bst.insert(7);

        assertEquals(Integer.valueOf(13), searchTree.getRoot().getValue());
        assertEquals(Integer.valueOf(8), searchTree.getRoot().getLeft().getValue());

        assertFalse(searchTree.contains(7));
        assertTrue(bst.contains(7));

    }

    @Test
    public void testSearchFalse() {
        assertNull(bst.search(7));
    }

    @Test
    public void testRange() {
        List<Integer> list = bst.range(1, 7);

        List<Integer> expected = Arrays.asList(1, 2, 5);

        assertEquals(3, expected.size());

        for (Integer integer : list) {
            assertTrue(expected.contains(integer));
        }

    }

    @Test
    public void deleteMax() {
        assertTrue(bst.contains(13));
        bst.deleteMax();
        assertFalse(bst.contains(13));
    }

    @Test
    public void deleteMin() {
        assertTrue(bst.contains(1));
        bst.deleteMin();
        assertFalse(bst.contains(1));
    }

    @Test
    public void testCount(){
        assertEquals(5, bst.count());
    }

    @Test
    public void testRank() {
        //1 and 13 are deleted before this method is called
        //2,5,8 is left -> 8 have to be 3rd in Rank
        assertEquals(3, bst.rank(8));
    }

    @Test
    public void testRankMinElement(){
        //test with min non-existing element
        assertEquals(0, bst.rank(-1));

        assertEquals(1,bst.rank(2));
    }

    @Test
    public void testRankEmptyTree(){
        assertEquals(0, new BinarySearchTree<Integer>().rank(1));
    }

    @Test
    public void testFloor() {
        assertEquals(Integer.valueOf(1),bst.floor(2));
    }

    @Test
    public void testEmptyFloor(){
        assertNull(bst.floor(-1));
    }

    @Test
    public void testCeil() {
        assertEquals(Integer.valueOf(5),bst.ceil(2));
    }

    @Test
    public void testEmptyCeil(){
        assertNull(bst.ceil(20));
    }

}

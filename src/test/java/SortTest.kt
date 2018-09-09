import sorts.InsertionSort
import sorts.MergeSort
import sorts.Sort
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class SortTest {

    companion object {
        private const val MAX_SIZE=100000
    }

    @Test(timeout = 90 * 1000)
    fun insertionSortTest() {
        val insertionSort = InsertionSort()
        check(insertionSort, MAX_SIZE)
    }

    @Test(timeout = 90 * 1000)
    fun mergeSortTest() {
        val mergeSort = MergeSort()
        check(mergeSort, MAX_SIZE)
    }

    private fun check(sort: Sort, size: Int) {
        val random = Random()
        val listSuspend = ArrayList<Int>()
        val listOriginal = ArrayList<Int>()
        for (i in 0 until size) {
            val value = random.nextInt()
            listOriginal.add(value)
            listSuspend.add(value)
        }
        val startOriginal = System.currentTimeMillis()
        listOriginal.sort()
        println("Original sorted in ${System.currentTimeMillis() - startOriginal} millis")
        val startSuspend = System.currentTimeMillis()
        sort.sort(listSuspend)
        println("Suspend sorted ${System.currentTimeMillis() - startSuspend} millis")
        Assert.assertArrayEquals("Check ${sort::class.java.name}", listOriginal.toArray(), listSuspend.toArray())
    }

}
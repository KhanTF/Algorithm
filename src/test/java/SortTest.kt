import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import sorts.*
import java.util.*
import kotlin.collections.ArrayList

class SortTest {

    companion object {
        private const val MAX_SIZE = 1000000
        private var randomList = mutableListOf<Int>()

        @BeforeClass
        @JvmStatic
        fun setupRandomList() {
            val random = Random()
            for (i in 0 until MAX_SIZE) {
                val value = random.nextInt()
                randomList.add(value)
            }
        }

    }

    @Test(timeout = 90 * 1000)
    fun insertionSortTest() {
        val insertionSort = InsertionSort()
        checkSort(insertionSort, 10000)
    }

    @Test(timeout = 90 * 1000)
    fun mergeSortTest() {
        val mergeSort = MergeSort()
        checkSort(mergeSort)
    }

    @Test(timeout = 90 * 1000)
    fun coroutineMergeSortTest() {
        val mergeSort = CoroutineMergeSort()
        checkSort(mergeSort)
    }

    @Test(timeout = 90 * 1000)
    fun heapSortTest() {
        val heapSort = HeapSort()
        checkSort(heapSort)
    }

    private fun checkSort(sort: Sort, maxSize: Int = MAX_SIZE) {
        val listSuspend = ArrayList<Int>(randomList.take(maxSize))
        val listOriginal = ArrayList<Int>(randomList.take(maxSize))

        val startOriginal = System.currentTimeMillis()
        listOriginal.sort()
        println("${sort::class.java.simpleName}: Original sorted in ${System.currentTimeMillis() - startOriginal} millis")

        val startSuspend = System.currentTimeMillis()
        sort.sort(listSuspend)
        println("${sort::class.java.simpleName}: Suspend sorted ${System.currentTimeMillis() - startSuspend} millis")

        Assert.assertArrayEquals("Check ${sort::class.java.name}", listOriginal.toArray(), listSuspend.toArray())
    }

}
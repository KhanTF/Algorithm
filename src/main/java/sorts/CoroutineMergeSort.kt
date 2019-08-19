package sorts

import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
/**
 *  Processor: I5-8500
 *  If size list more than 100000,
 *  CoroutineMergeSort work twice as fast than MergeSort
 *  @see MergeSort
 *  @see CoroutineMergeSort
 */
class CoroutineMergeSort : AbstractSort() {

    override fun <T> sort(list: MutableList<T>, comparator: Comparator<T>) {
        super.sort(list, comparator)
        runBlocking(Dispatchers.IO) { sort(list, comparator, list.size / 4) }
    }

    private suspend fun <T> sort(list: MutableList<T>, comparator: Comparator<T>, minSizeToAsync: Int) {
        val sortList = div(list, comparator, minSizeToAsync)
        list.clear()
        list.addAll(sortList)
    }

    private suspend fun <T> div(list: MutableList<T>, comparator: Comparator<T>, minSizeToAsync: Int): MutableList<T> {
        val center = list.size / 2
        return if (list.size <= 1) {
            list
        } else {
            if (list.size < minSizeToAsync) {
                val sortList1 = div(list.subList(0, center), comparator, minSizeToAsync)
                val sortList2 = div(list.subList(center, list.size), comparator, minSizeToAsync)
                merge(sortList1, sortList2, comparator)
            } else {
                coroutineScope {
                    val sortList1Deferred = async { div(list.subList(0, center), comparator, minSizeToAsync) }
                    val sortList2Deferred = async { div(list.subList(center, list.size), comparator, minSizeToAsync) }
                    merge(sortList1Deferred.await(), sortList2Deferred.await(), comparator)
                }
            }
        }
    }

    private fun <T> merge(list1: MutableList<T>,
                          list2: MutableList<T>,
                          comparator: Comparator<T>): MutableList<T> {
        val tmpList = ArrayList<T>(list1.size + list2.size)
        var countFirst = 0
        var countSecond = 0

        while (countFirst < list1.size && countSecond < list2.size) {
            val first = list1[countFirst]
            val second = list2[countSecond]
            if (comparator.compare(first, second) > 0) {
                tmpList.add(second)
                countSecond++
            } else {
                tmpList.add(first)
                countFirst++
            }
        }

        if (countFirst == list1.size) {
            for (i in countSecond until list2.size) {
                tmpList.add(list2[i])
            }
        } else if (countSecond == list2.size) {
            for (i in countFirst until list1.size) {
                tmpList.add(list1[i])
            }
        }

        return tmpList
    }

}

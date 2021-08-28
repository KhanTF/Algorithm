package sorts

import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CoroutineMergeSort : AbstractSort() {

    override fun <T> sort(list: MutableList<T>, comparator: Comparator<T>) {
        super.sort(list, comparator)
        val sortList = runBlocking {
            list.chunked(list.size / 4) { chunk ->
                val mutableChunk = chunk.toMutableList()
                async(Dispatchers.IO) { div(mutableChunk, comparator) }
            }.reduce { acc, deferred ->
                async { merge(acc.await(), deferred.await(), comparator) }
            }.await()
        }
        list.clear()
        list.addAll(sortList)
    }

    private fun <T> div(list: MutableList<T>, comparator: Comparator<T>): MutableList<T> {
        val center = list.size / 2
        return if (list.size <= 1) {
            list
        } else {
            val sortList1 = div(list.subList(0, center), comparator)
            val sortList2 = div(list.subList(center, list.size), comparator)
            merge(sortList1, sortList2, comparator)
        }
    }

    private fun <T> merge(
        list1: MutableList<T>,
        list2: MutableList<T>,
        comparator: Comparator<T>
    ): MutableList<T> {
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

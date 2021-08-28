package sorts

import utils.swap

class HeapSort : AbstractSort() {

    override fun <T> sort(list: MutableList<T>, comparator: Comparator<T>) {
        for (i in list.size / 2 downTo 0) {
            heapify(list, comparator, i, list.size)
        }
        for (i in list.size - 1 downTo 1) {
            list.swap(0, i)
            heapify(list, comparator, 0, i)
        }
    }

    private fun <T> heapify(list: MutableList<T>, comparator: Comparator<T>, i: Int, size: Int) {
        var max = i
        val child1 = 2 * i + 1
        val child2 = 2 * i + 2
        if (child1 < size && comparator.compare(list[max], list[child1]) < 0) {
            max = child1
        }
        if (child2 < size && comparator.compare(list[max], list[child2]) < 0) {
            max = child2
        }
        if (max != i) {
            list.swap(max, i)
            heapify(list, comparator, max, size)
        }
    }

}
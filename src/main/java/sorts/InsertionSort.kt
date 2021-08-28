package sorts

import utils.swap

class InsertionSort : AbstractSort() {

    override fun <T> sort(list: MutableList<T>, comparator: Comparator<T>) {
        super.sort(list, comparator)
        for (i in 1 until list.size) {
            for (k in i downTo 1) {
                if (comparator.compare(list[k], list[k - 1]) < 0) {
                    list.swap(k - 1, k)
                } else {
                    break
                }
            }
        }
    }

}
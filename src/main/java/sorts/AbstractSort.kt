package sorts

import java.util.*

abstract class AbstractSort : Sort {

    final override fun <T : Comparable<T>> sort(list: MutableList<T>) {
        if (list.size <= 1) return
        val comparator = Comparator { t1: T, t2: T ->
            t1.compareTo(t2)
        }
        sort(list, comparator)
    }

    override fun <T> sort(list: MutableList<T>, comparator: Comparator<T>) {
        if (list.size <= 1) return
    }

}
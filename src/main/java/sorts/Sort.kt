package sorts

interface Sort {
    fun <T : Comparable<T>> sort(list: MutableList<T>)
    fun <T> sort(list: MutableList<T>, comparator: Comparator<T>)
}

package utils

fun <T> MutableList<T>.swap(i1: Int, i2: Int) {
    val tmp = get(i1)
    set(i1, get(i2))
    set(i2, tmp)
}
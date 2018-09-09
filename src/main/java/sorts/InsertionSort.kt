package sorts

class InsertionSort : AbstractSort() {

    override fun <T> sort(list: MutableList<T>, comparator: Comparator<T>) {
        super.sort(list, comparator)
        for (i in 1 until list.size) {
            for (k in i downTo 1) {
                val compare = comparator.compare(list[k], list[k - 1])
                if (compare < 0) {
                    swap(list, k - 1, k)
                } else {
                    break
                }
            }
        }
    }

    private fun <T> swap(list: MutableList<T>, index1: Int, index2: Int) {
        val tmp = list[index1]
        list[index1] = list[index2]
        list[index2] = tmp
    }

}
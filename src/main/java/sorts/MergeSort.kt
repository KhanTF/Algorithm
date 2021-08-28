package sorts

class MergeSort : AbstractSort() {

    override fun <T> sort(list: MutableList<T>, comparator: java.util.Comparator<T>) {
        super.sort(list, comparator)
        val sortList = div(list, comparator)
        list.clear()
        list.addAll(sortList)
    }

    private fun <T> div(list: MutableList<T>, comparator: Comparator<T>): MutableList<T> {
        val center = list.size / 2

        if (list.size <= 1)
            return list

        val sortList1 = div(list.subList(0, center), comparator)
        val sortList2 = div(list.subList(center, list.size), comparator)
        return merge(sortList1, sortList2, comparator)
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

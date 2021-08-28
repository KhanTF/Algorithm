package knightstour

import java.util.*

class KnightsTourAlgorithm(private val size: Int) {

    companion object {
        private const val STEPS = 8
    }

    fun find(xStart: Int, yStart: Int): Set<List<Position>> {
        val solution = HashSet<List<Position>>()
        step(Position(xStart, yStart, size), LinkedList(), solution)
        return solution
    }

    private val board = Array(size) { BooleanArray(size) }

    data class Position(val x: Int, val y: Int, private val size: Int) {

        fun isBounds(): Boolean = x in 0 until size && y in 0 until size

        fun step(step: Int): Position {
            return when (step) {
                0 -> Position(x + 2, y + 1, size)
                1 -> Position(x + 2, y - 1, size)
                2 -> Position(x - 2, y + 1, size)
                3 -> Position(x - 2, y - 1, size)
                4 -> Position(x + 1, y + 2, size)
                5 -> Position(x + 1, y - 2, size)
                6 -> Position(x - 1, y + 2, size)
                7 -> Position(x - 1, y - 2, size)
                else -> throw IllegalArgumentException("Invalid step")
            }
        }

    }

    private fun step(position: Position, path: LinkedList<Position>, solution: MutableSet<List<Position>>) {
        if (position.isBounds() && !board[position.x][position.y]) {
            board[position.x][position.y] = true
            path.push(position)
            if (path.size == size * size) {
                solution.add(ArrayList(path))
            } else {
                for (step in 0 until STEPS) {
                    step(position.step(step), path, solution)
                }
            }
            board[position.x][position.y] = false
            path.pop()
        }
    }

}
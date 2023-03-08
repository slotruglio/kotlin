data class MinesweeperBoard(val board: List<String>) {

    // TODO: Implement proper constructor

    fun withNumbers(): List<String> {
        //TODO("Implement this function to complete the task")
        return board.mapIndexed { row, columns ->
            columns.mapIndexed { col, c ->
                if (c == ' ') withMines(row, col) else c.toString()
            }.joinToString(separator = "")
        }
    }

    private fun withMines(row: Int, col: Int): String {
        val neighbors = (-1..1).map { row - 1 to col + it } +
                (-1..1).map { row + 1 to col + it } +
                listOf(row to col -1, row to col + 1)

        val result = neighbors.fold(0) { total, (x,y) -> when {
            x < 0 || x >= board.size  -> total
            y < 0 ||  y >= board[x].length -> total
            board[x][y] == '*' -> total + 1
            else -> total
        }}

        return if (result == 0) " " else result.toString()
    }
}

class Matrix(private val matrixAsString: String) {
    private val rows = matrixAsString.split("\n").map { string -> string.split(" ").map { char -> char.toInt() } }
    fun column(colNr: Int): List<Int> {
        //TODO("Implement this to complete the task")
        return rows.fold(listOf()) {
                acc, row -> acc.plus(row[colNr-1])
        }
    }

    fun row(rowNr: Int): List<Int> {
        //TODO("Implement this to complete the task")
        return rows[rowNr-1]
    }
}

object WordCount {

    fun phrase(phrase: String): Map<String, Int> {
        //TODO("Implement this function to complete the task")

        return phrase
            .split(" ", ",", ".", "\n")
            .filterNot(String::isBlank)
            .map { word -> word.filter {
                it.isLetterOrDigit() || it == '\''}
            }
            .map { word ->
                word
                    .lowercase()
                    .trimStart('\'')
                    .trimEnd('\'')
            }
            .groupingBy { it }
            .eachCount()

    }
}

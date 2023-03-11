object Flattener {
    fun flatten(source: Collection<Any?>): List<Any> {
        //TODO("Implement the function to complete the task")
        return source.flatMap {item ->
            if(item is List<*>) flatten(item)
            else listOf(item)
        }.filterNotNull()
    }
}

fun <T> List<T>.customAppend(list: List<T>): List<T> {
    //TODO("Implement this function to complete the task")
    return list.customFoldLeft(this) { acc, tmp -> acc+tmp}
}

fun List<Any>.customConcat(): List<Any> {
    //TODO("Implement this function to complete the task")
    return this.customFoldLeft(listOf()) { acc, tmp ->
        if (tmp is List<*>)
            acc + (tmp as List<Any>).customConcat()
        else acc + tmp
    }
}

fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> {
    //TODO("Implement this function to complete the task")
    return this.customFoldLeft(listOf()) { acc, tmp ->
        if (predicate(tmp)) acc+tmp
        else acc
    }
}

val List<Any>.customSize: Int get() = this.customFoldLeft(0) {acc, _ -> acc+1}

fun <T, U> List<T>.customMap(transform: (T) -> U): List<U> {
    //TODO("Implement this function to complete the task")
    return this.customFoldLeft(listOf()) {acc, tmp -> acc+transform(tmp)}
}

fun <T, U> List<T>.customFoldLeft(initial: U, f: (U, T) -> U): U {
    //TODO("Implement this function to complete the task")
    if(this.isEmpty()) return initial
    return this.drop(1).customFoldLeft( f(initial, this.first()) , f)
}

fun <T, U> List<T>.customFoldRight(initial: U, f: (T, U) -> U): U {
    //TODO("Implement this function to complete the task")
    return customReverse().customFoldLeft(initial) { initial, value -> f(value, initial) }
}

fun <T> List<T>.customReverse(): List<T> {
    //TODO("Implement this function to complete the task")
    return this.customFoldLeft(listOf()) {acc, tmp -> listOf(tmp)+acc}
}

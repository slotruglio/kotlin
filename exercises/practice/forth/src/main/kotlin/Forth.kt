import java.util.*
import kotlin.Exception

class Forth {

    fun evaluate(vararg line: String): List<Int> {
        //TODO("Implement this to complete the task")

        var custom = emptyMap<String, String>().toMutableMap()
        for (arg in line) {
            var text = arg.lowercase()
            if (arg.startsWith(':') && text[text.length-1] == ';') {
                val parts = text.substring(2, text.length-2).split(' ', limit = 2)
                if (parts[0].toIntOrNull() != null) throw Exception("illegal operation")

                var edited = parts[1]
                if (custom.keys.intersect(parts[1].split(' ').toSet()).isNotEmpty()) {
                    edited = custom.keys.fold(parts[1]) { initial, e -> initial.replace(e, custom.getValue(e))}
                }
                custom[parts[0]] = edited
            } else {
                custom.forEach {entry -> text = text.replace(entry.key, entry.value)}
                return evaluateSingle(text)
            }

        }
        return emptyList()
    }

    private fun evaluateSingle(word: String): List<Int> {
        val parts = word.split(' ')

        var array = mutableListOf<Int>()
        for (x in parts) {
            var conversion = x.toIntOrNull()
            if (conversion != null) array.add(conversion)
            else {
                array = when {
                    listOf("+", "-", "*", "/").contains(x) -> mutableListOf(arithmetic(array.toList(), x))
                    listOf("dup", "drop", "swap", "over").contains(x) -> manipulation(array.toList(), x)
                    else -> throw Exception("undefined operation")
                }
            }
        }

        return array.toList()
    }
    private fun arithmetic(array: List<Int>, x: String): Int {
        if (array.isEmpty()) throw Exception("empty stack")
        if (array.size == 1) throw Exception("only one value on the stack")
        return when (x) {
            "+" -> array[0] + array[1]
            "-" -> array[0] - array[1]
            "*" -> array[0] * array[1]
            "/" -> if (array[1] != 0) return array[0] / array [1]
                    else throw Exception("divide by zero")
            else -> 0
        }
    }

    private fun manipulation(array: List<Int>, x: String): MutableList<Int> {
        if (array.isEmpty()) throw Exception("empty stack")
        var local = array.toMutableList()
        if (x == "dup") local.add(array.last())
        else if (x == "drop") local.removeLast()
        else
            if (array.size == 1) throw Exception("only one value on the stack")
            when (x) {
                "swap" -> {
                    val last = local.removeLast()
                    val notLast = local.removeLast()
                    local.add(last)
                    local.add(notLast)
                }
                "over" -> local.add(local[local.size-2])

            }
        return local
    }

}

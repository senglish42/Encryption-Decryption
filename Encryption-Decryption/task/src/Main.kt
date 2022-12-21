package encryptdecrypt

import java.io.File

fun shift(it: Char, num: Int): Char {
    val pair = if (it in 'a'..'z') Pair('a', 'z') else Pair('A', 'Z')
    val a = it + num
    return a + if (a > pair.second) pair.first.code - pair.second.code - 1
    else if (a < pair.first) pair.second.code - pair.first.code + 1 else 0
}

fun output(arr: Array<String>, out: (String) -> Unit = ::println) {
    return out(arr[2].map{
        val num = arr[1].toInt() * if (arr[0] == "enc") 1 else -1
        when (arr.last()) {
            "shift" -> if ("[a-zA-Z]".toRegex().matches(it.toString())) shift(it, num) else it
            else -> it + num
        }
    }.joinToString(""))
}

fun main(args: Array<String>) {
    val arr = arrayOf("enc", "0", "", "", "", "shift")
    val flags = listOf("-mode", "-key", "-data", "-in", "-out", "-alg")
    for (i in arr.indices) { args.indexOf(flags[i]).let { if (it in 0 until args.lastIndex) arr[i] = args[it + 1] }}
    if (arr[2].isEmpty() && arr[3].isNotEmpty()) {
        File(arr[3]).let { if (!it.exists()) { arr[1] = "0"; arr[2] = "Error" } else arr[2] = it.readText() }
    }
    if (arr[4].isNotEmpty()) output(arr, File(arr[4])::writeText) else output(arr)
}
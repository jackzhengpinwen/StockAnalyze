package com.zpw.stockanalyze

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val string = "600519.SH\n" +
            "300750.SZ\n" +
            "600036.SH\n" +
            "601318.SH\n" +
            "601166.SH\n" +
            "601012.SH\n" +
            "000858.SZ\n" +
            "000333.SZ\n" +
            "300059.SZ\n" +
            "603259.SH\n" +
            "600900.SH\n" +
            "600887.SH\n" +
            "002594.SZ\n" +
            "600030.SH\n" +
            "601288.SH\n" +
            "601899.SH\n" +
            "601398.SH\n" +
            "002415.SZ\n" +
            "601888.SH\n" +
            "600276.SH\n" +
            "000651.SZ\n" +
            "002475.SZ\n" +
            "601319.SH\n" +
            "000568.SZ\n" +
            "002714.SZ\n" +
            "000725.SZ\n" +
            "601328.SH\n" +
            "000001.SZ\n" +
            "300760.SZ\n" +
            "002142.SZ\n" +
            "600048.SH\n" +
            "000002.SZ\n" +
            "600309.SH\n" +
            "600000.SH\n" +
            "601816.SH\n" +
            "600438.SH\n" +
            "600016.SH\n" +
            "002812.SZ\n" +
            "603501.SH\n" +
            "300274.SZ\n" +
            "300014.SZ\n" +
            "600031.SH\n" +
            "300498.SZ\n" +
            "000792.SZ\n" +
            "002460.SZ\n" +
            "300122.SZ\n" +
            "600809.SH\n" +
            "600111.SH\n" +
            "603288.SH\n" +
            "002129.SZ\n" +
            "601668.SH\n" +
            "603799.SH\n" +
            "300124.SZ\n" +
            "600837.SH\n" +
            "601919.SH\n" +
            "002241.SZ\n" +
            "002049.SZ\n" +
            "002466.SZ\n" +
            "600436.SH\n" +
            "002709.SZ\n" +
            "601857.SH\n" +
            "600690.SH\n" +
            "002271.SZ\n" +
            "002352.SZ\n" +
            "600585.SH\n" +
            "603986.SH\n" +
            "601601.SH\n" +
            "002230.SZ\n" +
            "300142.SZ\n" +
            "601688.SH\n" +
            "688981.SH\n" +
            "601088.SH\n" +
            "002304.SZ\n" +
            "600919.SH\n" +
            "600406.SH\n" +
            "000063.SZ\n" +
            "300015.SZ\n" +
            "000338.SZ\n" +
            "601169.SH\n" +
            "601211.SH\n" +
            "601988.SH\n" +
            "600104.SH\n" +
            "002371.SZ\n" +
            "600089.SH\n" +
            "601985.SH\n" +
            "600893.SH\n" +
            "000100.SZ\n" +
            "600383.SH\n" +
            "002027.SZ\n" +
            "600196.SH\n" +
            "601229.SH\n" +
            "600745.SH\n" +
            "600703.SH\n" +
            "600019.SH\n" +
            "600050.SH\n" +
            "601658.SH\n" +
            "600028.SH\n" +
            "688599.SH\n" +
            "601939.SH\n" +
            "600570.SH\n" +
            "601390.SH\n" +
            "601225.SH\n" +
            "600905.SH\n" +
            "300347.SZ\n" +
            "601818.SH\n" +
            "002821.SZ\n" +
            "601766.SH\n" +
            "002459.SZ\n" +
            "600660.SH\n" +
            "601009.SH\n" +
            "600346.SH"
    @Test
    fun addition_isCorrect() {
        val str = string.replace(".SH", "").replace(".SZ", "")
        println(str)
    }

    @Test
    fun reverseStr() {
        val  s = "abcd"
        val k = 2
        val n = s.length
        val ss = s.toCharArray()
        var right = 0
        while (right < n) {
            if ((right + 1) % (2 * k) == 0) {
                // 反转这 2k 字符中的前 k 个字符
                reverse(ss, right + 1 - 2 * k, right + 1 - k - 1)
            } else if (right + 1 > n - k) {
                // 剩余字符全部反转
                reverse(ss, right, n - 1)
                break
            } else if (right > n - 2 * k && right < n - k) {
                // 反转前 k 个字符
                reverse(ss, right, right + k - 1)
                break
            }
            right++
        }
        println(String(ss))
    }

    fun reverse(arr: CharArray, left: Int, right: Int) {
        var left = left
        var right = right
        while (left < right) {
            val temp = arr[left]
            arr[left] = arr[right]
            arr[right] = temp
            left++
            right--
        }
    }
}
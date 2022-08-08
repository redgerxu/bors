package io.github.narutopig.bors.util

class Roman {
    // definitely original
    // https://www.javatpoint.com/convert-roman-to-integer-in-java
    // https://www.javatpoint.com/convert-integer-to-roman-numerals-in-java
    private fun value(r: Char): Int {
        if (r == 'I') return 1
        if (r == 'V') return 5
        if (r == 'X') return 10
        if (r == 'L') return 50
        if (r == 'C') return 100
        if (r == 'D') return 500
        return if (r == 'M') 1000 else -1
    }

    //function to convert roman to integer
    fun romanToInt(s: String): Int {
        //variable to store the sum
        var total = 0
        //loop iterate over the string (given roman numeral)
        //getting value from symbol s1[i]
        for (i in s.indices) {
            val s1 = value(s[i])
            //getting value of symbol s2[i+1]
            total = if (i + 1 < s.length) {
                val s2 = value(s[i + 1])
                //comparing the current character from its right character
                if (s1 >= s2) {
                    //if the value of current character is greater or equal to the next symbol
                    total + s1
                } else {
                    //if the value of the current character is less than the next symbol
                    total - s1
                }
            } else {
                total + s1
            }
        }
        //returns corresponding integer value
        return total
    }

    fun intToRoman(num: Int): Int {
        var num = num
        val values = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        val romanLetters = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
        val roman = StringBuilder()
        for (i in values.indices) {
            while (num >= values[i]) {
                num -= values[i]
                roman.append(romanLetters[i])
            }
        }
        return Integer.parseInt(roman.toString())
    }
}
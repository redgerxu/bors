package io.github.narutopig.bors.util

import org.bukkit.BanList
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

object General {
    private val map = TreeMap<Int, String>()

    init {
        map[1000] = "M"
        map[900] = "CM"
        map[500] = "D"
        map[400] = "CD"
        map[100] = "C"
        map[90] = "XC"
        map[50] = "L"
        map[40] = "XL"
        map[10] = "X"
        map[9] = "IX"
        map[5] = "V"
        map[4] = "IV"
        map[1] = "I"
    }

    fun itemStack(material: Material?, amount: Int): ItemStack {
        return ItemStack(material!!, amount)
    }

    fun power(base: Int, exponent: Int): Int {
        var res = 1
        for (i in 0 until exponent) {
            res *= base
        }
        return res
    }

    fun toRoman(number: Int): String? {
        // thanks Ben-Hur Langoni Junior
        // https://stackoverflow.com/questions/12967896/converting-integers-to-roman-numerals-java
        val l = map.floorKey(number)
        return if (number == l) {
            map[number]
        } else map[l] + toRoman(number - l)
    }

    fun banPlayer(player: Player, reason: String, date: Date?, type: BanList.Type?) {
        Bukkit.getBanList(type!!).addBan(player.name, reason, date, "Console")
        player.kickPlayer("Banned for reason: $reason")
    }

    fun romanToInteger(roman: String): Int {
        val numbersMap: MutableMap<Char, Int> = HashMap()
        numbersMap['I'] = 1
        numbersMap['V'] = 5
        numbersMap['X'] = 10
        numbersMap['L'] = 50
        numbersMap['C'] = 100
        numbersMap['D'] = 500
        numbersMap['M'] = 1000
        var result = 0
        for (i in roman.indices) {
            val ch = roman[i] // Current Roman Character

            //Case 1
            result += if (i > 0 && numbersMap[ch]!! > numbersMap[roman[i - 1]]!!) {
                numbersMap[ch]!! - 2 * numbersMap[roman[i - 1]]!!
            } else numbersMap[ch]!!
        }
        return result
    }
}
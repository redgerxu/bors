package io.github.narutopig.util

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

    fun <K, V> printMap(map: Map<K, V>) {
        for ((key, value) in map) {
            println(key.toString() + ":" + value.toString())
        }
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
}
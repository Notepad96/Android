package com.example.timer

class TimeRecord(var lap: Int, var lapTime: Int, var allTime: Int) {
    override fun toString(): String {
        return "$lap - $lapTime - $allTime"
    }
}
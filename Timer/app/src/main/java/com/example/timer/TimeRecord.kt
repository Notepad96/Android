package com.example.timer

class TimeRecord(var lap: Int, var lapTime: String, var allTime: String) {
    override fun toString(): String {
        return "$lap - $lapTime - $allTime"
    }
}
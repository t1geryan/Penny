package io.github.t1geryan.penny.ui.contracts

object InputFilters {

    val cost = object : Filter {

        override fun filter(input: String): String {
            val cleaned = input.filter { it.isDigit() || it == '.' }

            var result = ""
            for (c in cleaned) {
                val candidate = result + c
                if (isValid(result, c, candidate)) {
                    result = candidate
                }
            }

            return result
        }

        private fun isValid(current: String, next: Char, candidate: String): Boolean {
            if (current == "0" && next.isDigit()) return false
            if (next == '.' && current.contains('.')) return false

            val dotIndex = candidate.indexOf('.')
            if (dotIndex != -1) {
                val fractionLength = candidate.length - dotIndex - 1
                if (fractionLength > 2) return false
            }

            return true
        }
    }

    interface Filter {

        fun filter(input: String): String
    }
}

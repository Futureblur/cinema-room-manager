package cinema

val cinema = mutableListOf<MutableList<Char>>()
var rows = 0
var seats = 0
var purchasedSeats = 0
var income = 0

fun showSeats() {
    println()
    println("Cinema:")

    for (x in 0..rows) {
        for (y in 0..seats) {
            if (x == 0 && y == 0) {
                print(" ")
            } else if (y == 0) {
                print(x)
            } else if (x == 0) {
                print(" $y")
            } else {
                print(" ${cinema[x - 1][y - 1]}")
            }
        }
        println()
    }
}

fun buyTicket() {
    println("Enter a row number:")
    val row = readLine()!!.toInt()

    println("Enter a seat number in that row:")
    val seat = readLine()!!.toInt()

    val total = rows * seats

    val cost: Int = if (total <= 60) {
        10
    } else {
        val frontHalf = rows / 2
        if (row in rows downTo frontHalf + 1) {
            8
        } else {
            10
        }
    }

    if (row > rows || seat > seats) {
        println("Wrong input!")
        return
    }

    // Mark corresponding seat as taken
    for (x in 0 until rows) {
        for (y in 0 until seats) {
            if (x == row - 1 && y == seat - 1) {
                if (cinema[x][y] != 'B')
                    cinema[x][y] = 'B'
                else {
                    println("That ticket has already been purchased!")
                    buyTicket()
                }
            }
        }
    }

    purchasedSeats++
    income += cost

    println()
    println("Ticket price: $$cost")
}

fun printOptions() {
    println(
        """
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent()
    )
}

fun showStatistics() {
    println("Number of purchased tickets: $purchasedSeats")

    val percentage =
        try {
            (purchasedSeats.toFloat() / (rows * seats)) * 100f
        } catch (e: ArithmeticException) {
            0.00
        }

    val percentageStr = "%.2f".format(percentage)

    println("Percentage: ${percentageStr}%")
    println("Current income: $$income")

    val total = rows * seats
    val totalIncome = if (total <= 60) {
        10
    } else {
        val frontHalf = rows / 2
        val backHalf = rows - frontHalf
        frontHalf * 10 * seats + backHalf * 8 * seats
    }

    println("Total income: $${totalIncome}")
}

fun main() {
    println("Enter the number of rows:")
    rows = readLine()!!.toInt()

    println("Enter the number of seats in each row:")
    seats = readLine()!!.toInt()

    // Fill in list
    for (x in 0 until rows) {
        cinema.add(mutableListOf())
        for (y in 0 until seats) {
            cinema[x].add('S')
        }
    }

    printOptions()

    do {
        val input = readLine()!!.toInt()

        when (input) {
            0 -> continue
            1 -> showSeats()
            2 -> buyTicket()
            3 -> showStatistics()
        }

        println()
        printOptions()
    } while (input != 0)
}
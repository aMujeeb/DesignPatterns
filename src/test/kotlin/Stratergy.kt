import org.junit.jupiter.api.Test
import java.util.*

//A class behavior or Algorithm can be changed at run time
//Objects contain algorithm logic
//Context object contain algorithmic object

class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(message : String) {
        println(stringFormatterStrategy(message))
    }
}

val lowerCaseFormatter = { it : String -> it.lowercase(Locale.getDefault()) }
val upperCaseFormatter = { it : String -> it.uppercase(Locale.getDefault()) }

class StrategyTest {
    @Test
    fun testStrategy()  {
        val inputString = "Improve your coding skills by learning Software Design Patterns applied to Kotlin and Android development"

        val  lowerCasePrinter = Printer(lowerCaseFormatter)
        lowerCasePrinter.printString(inputString)

        val  upperCasePrinter = Printer(upperCaseFormatter)
        upperCasePrinter.printString(inputString)
    }

}
import org.junit.jupiter.api.Test

//Attach new behavior to an object without altering existing code

interface ICoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine : ICoffeeMachine {
    override fun makeSmallCoffee() {
        println("Normal Coffee Machine : Make Small")
    }

    override fun makeLargeCoffee() {
        println("Normal Coffee Machine : Make Large")
    }
}

//Decorator
class EnhancedNormalCoffeeMachine(private val coffeeMachine: NormalCoffeeMachine) : ICoffeeMachine by coffeeMachine {
    //Overriding behavior
    override fun makeSmallCoffee() {
        println("Enhanced Coffee Machine : Make Small ")
    }

    fun makeMilkCoffee() {
        println("Enhanced Coffee Machine : Make Milk Coffee ")
        coffeeMachine.makeSmallCoffee()
        println("Enhanced Coffee Machine : Adding Milk ")
    }
}

class TestDecorator {
    @Test
    fun testDecorator() {
        val normalCoffeeMachine = NormalCoffeeMachine()
        val enhancedNormalCoffeeMachine = EnhancedNormalCoffeeMachine(normalCoffeeMachine)

        enhancedNormalCoffeeMachine.makeSmallCoffee()
        println("---------------------")
        enhancedNormalCoffeeMachine.makeLargeCoffee()
        println("---------------------")
        enhancedNormalCoffeeMachine.makeMilkCoffee()
    }
}
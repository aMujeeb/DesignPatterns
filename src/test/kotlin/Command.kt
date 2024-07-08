import org.junit.jupiter.api.Test

//A request is wrapped in an object that contains all request info
//The command object is passed to the correct handler

interface Command {
    fun execute()
}

class OrderAddCommand(private val id : Long) : Command {
    override fun execute() {
        println("Adding order with id : $id")
    }
}

class OrderPayCommand(private val id : Long) : Command {
    override fun execute() {
        println("Paying order with id : $id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>()

    fun addToQueue(command : Command) : CommandProcessor = apply { queue.add(command) }
    fun processCommand() : CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

class CommandTest {
    @Test
    fun testCommand() {
        CommandProcessor().addToQueue(OrderAddCommand(1L))
            .addToQueue(OrderAddCommand(2L))
            .addToQueue(OrderPayCommand(2L))
            .addToQueue(OrderPayCommand(1L))
            .processCommand()
    }
}

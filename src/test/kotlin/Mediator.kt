import org.junit.jupiter.api.Test

//A central object used for communicating between objects.
//objects don't talk to each other. Talks to mediator
//Eg: Chat application

class ChatUser(private val mediator: Mediator, private val name: String) {
    fun send(message: String) {
        println("$name: Sending message : $message")
        mediator.sendMessage(message, this)
    }

    fun receive(message: String) {
        println("$name: Received message : $message")
    }
}

class Mediator {
    private val users = arrayListOf<ChatUser>()

    fun sendMessage(msg : String, user : ChatUser) {
        users.filter {  it != user }.forEach { it.receive(msg) }
    }

    fun addUser(user : ChatUser) : Mediator = apply { users.add(user) }
}

class MediatorTest {
    @Test
    fun testMediator() {
        val mediator = Mediator()
        val alice = ChatUser(mediator, "Alice")
        val bob = ChatUser(mediator, "Bob")
        val carol = ChatUser(mediator, "Carol")

        mediator.addUser(alice).addUser(bob).addUser(carol)

        carol.send("Hi Bros")
    }
}
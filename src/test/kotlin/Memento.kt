import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//Way to save information
//Save and restore previous state without reveling information
//3 components Memento(store state), Originator(crates state), Caretaker(decide to save or not)

//Text editor... Have option to undo operation and store previous state

data class Memento(val state : String)

class Originator(var state : String) {
    fun createMemento() = Memento(state)
    fun restoreMemento(mMemento : Memento) {
        state = mMemento.state
    }
}

class CareTaker {
    private val mementoList = arrayListOf<Memento>()

    fun saveState(state: Memento) {
        mementoList.add(state)
    }

    fun restore(index : Int) : Memento = mementoList[index]
}

class MementoTest {
    @Test
    fun testMemento() {
        val originator = Originator("initial state")
        val careTaker = CareTaker()
        careTaker.saveState(originator.createMemento())

        originator.state = "State 1"
        careTaker.saveState(originator.createMemento())

        originator.state = "State 2"
        careTaker.saveState(originator.createMemento())

        println("Current State :${originator.state}")

        Assertions.assertThat(originator.state).isEqualTo("State 2")

        originator.restoreMemento(careTaker.restore(1))
        println("Current State :${originator.state}")
        Assertions.assertThat(originator.state).isEqualTo("State 1")

        originator.restoreMemento(careTaker.restore(0))
        println("Current State :${originator.state}")
        Assertions.assertThat(originator.state).isEqualTo("initial state")

        originator.restoreMemento(careTaker.restore(1))
        println("Current State :${originator.state}")
        Assertions.assertThat(originator.state).isEqualTo("State 1")

    }
}
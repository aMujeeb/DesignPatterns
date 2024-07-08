import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//Contains type of relationship. Bridge inheritance trees
//Having classes with multiple 'orthoganal traits' exponentially increases the size of the inheritance tree

interface IDevice {
    var volume : Int
    fun getName() : String
}


class Radio : IDevice {
    override var volume = 0
    override fun getName()= "Radio $this"

}

class Tv : IDevice {
    override var volume = 0
    override fun getName()= "Tv $this"
}

interface IRemote {
    fun volumeUp()
    fun volumeDown()
}

class BasicRemote(private val device : IDevice): IRemote {
    override fun volumeUp() {
        device.volume++
        println("${device.getName()} - Volume up: ${device.volume}")
    }

    override fun volumeDown() {
        device.volume--
        println("${device.getName()} - Volume down: ${device.volume}")
    }
}

class BridgeTest {
    @Test
    fun testBridge() {
        val tv = Tv()
        val radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()

        Assertions.assertThat(tv.volume).isEqualTo(1)
        Assertions.assertThat(radio.volume).isEqualTo(1)
    }
}
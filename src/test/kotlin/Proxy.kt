import org.junit.jupiter.api.Test

//Basically provide some functionality before and/or after calling an object, similar to Facade
//Proxy manages the lifecycle of the object. Not like in Decorator. Which is done by manager

interface IIMage {
    fun display()
}

class RealImage(private val fileName : String) : IIMage {
    override fun display() {
        println("Real Image : Displaying $fileName")
    }

    private fun loadFromDisk(fileName: String) {
        println("Real Image : Loading $fileName")
    }

    init {
        loadFromDisk(fileName)
    }
}

class ProxyImage(private val fileName: String) : IIMage {
    private var realImage : RealImage? = null

    override fun display() {
        println("Proxy Image : Displaying $fileName")
        if(realImage == null) {
            realImage = RealImage(fileName)
        }
        realImage!!.display()
    }
}

class ProxyTest {
    @Test
    fun testProxy() {
        val image = ProxyImage("tester.jpg")

        //Load image from disk
        image.display()
        println("--------------------")

        //Load image from cache
        image.display()
    }
}


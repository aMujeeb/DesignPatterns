import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AlertBox {
    var message: String? = null

    fun show() {
        println("Alert Box $this : $message")
    }
}

class Window {
    val box by lazy {
        AlertBox()
    }

    fun showMessage(messageToShow : String) {
        box.message = messageToShow
        box.show()
    }
}

class Window2 {
    lateinit var box: AlertBox
    fun showMessage(messageToShow : String) {
        box = AlertBox()
        box.message = messageToShow
        box.show()
    }

}

class WindowTest {

    @Test
    fun windowTest() {
        val window = Window()
        window.showMessage("Test Message")

        Assertions.assertThat(window.box).isNotNull

        val window2 = Window2()
        window2.showMessage("Test Second Message")

        Assertions.assertThat(window2.box).isNotNull
    }
}
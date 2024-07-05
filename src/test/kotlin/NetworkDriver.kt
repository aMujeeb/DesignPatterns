import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

object NetworkDriver {
    init {
        println("Initializing :$this")
    }

    fun log(): NetworkDriver =
        apply {
            println("Network Driver :$this")
        }
}

class SingletonTest {
    @Test
    fun testSingleton() {
        println("Start")
        val netWorkDriver1 = NetworkDriver.log()
        val netWorkDriver2 = NetworkDriver.log()

        Assertions.assertThat(netWorkDriver1).isSameAs(NetworkDriver)
        Assertions.assertThat(netWorkDriver2).isSameAs(NetworkDriver)
    }
}
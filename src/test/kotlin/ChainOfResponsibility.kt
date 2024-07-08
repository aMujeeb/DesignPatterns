import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//Define a chain of handlers to process a request
//Each handler contains a reference to the next handler
//Each handler decides to process and request AND/ OR pass it on

//Eg : Android UI xml layout

interface HandlerChain {
    fun addHeader(inputHeader : String): String
}

class AuthenticationHeader(private val token : String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) = "$inputHeader\nAuthorizaion: $token".let {
        next?.addHeader(it) ?: it
    }
}

class ContentTypeHeader(private val contentType : String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) = "$inputHeader\nContentType: $contentType".let {
        next?.addHeader(it) ?: it
    }
}

class BodyPayloadHeader(private val body : String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) = "$inputHeader\nBody: $body".let {
        next?.addHeader(it) ?: it
    }
}

class ChainResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        val authenticationHeader = AuthenticationHeader("123465")
        val contentTypeHeader = ContentTypeHeader("json")
        val bodyPayloadHeader = BodyPayloadHeader("Body:{\"username\"=\"hello\"}")

        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyPayloadHeader

        val messageAuthentication = authenticationHeader.addHeader("Headers with authentication")
        println(messageAuthentication)

        val messageWithoutAuthentication = contentTypeHeader.addHeader("Headers Without Authentication")
        println(messageWithoutAuthentication)

        Assertions.assertThat(messageAuthentication).isEqualTo("""Headers with authentication
Authorizaion: 123465
ContentType: json
Body: Body:{"username"="hello"}""".trimIndent())
    }
}
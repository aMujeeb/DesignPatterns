import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//An object changes its behavior based on an internal state
//Any point of time there is  finite number of stages
//State can be encapsulated in an object

//Eg: Logged user or Logged Out User

sealed class AuthorizedState
data object UnAuthorized : AuthorizedState()
class Authorized(val userName : String) : AuthorizedState()

class AuthorizationPresenter {
    private var state : AuthorizedState = UnAuthorized

    val isAuthorized : Boolean
        get() = when(state) {
            is Authorized -> true
            is UnAuthorized -> false
        }

    val userName : String
        get() {
            return when(val state = this.state){
                is Authorized -> state.userName
                is UnAuthorized -> "UnKnown"
            }
        }

    fun loginUser(userName : String) {
        state = Authorized(userName)
    }

    fun logOutUser() {
        state = UnAuthorized
    }

    override fun toString() = "User $userName is logged in is $isAuthorized"
}

class TestState {
    @Test
    fun testState() {
        val authorizationPresenter = AuthorizationPresenter()
        authorizationPresenter.loginUser("admin")
        println(authorizationPresenter)

        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(true)
        Assertions.assertThat(authorizationPresenter.userName).isEqualTo("admin")

        authorizationPresenter.logOutUser()
        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(false)
        Assertions.assertThat(authorizationPresenter.userName).isEqualTo("UnKnown")
    }
}




import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.final_assessment.models.login.LoginResponse
import com.example.final_assessment.repositories.LoginRepository
import com.example.final_assessment.viewmodels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var testDispatcher: TestDispatcher

    @Mock
    private lateinit var loginRepository: LoginRepository

    @Mock
    private lateinit var loginResultObserver: Observer<Result<LoginResponse>>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginRepository)
        viewModel.loginResult.observeForever(loginResultObserver)
        viewModel.isLoading.observeForever(loadingObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success`() = runTest {
        // Given
        val username = "sunil"
        val password = "s8113519"
        val loginResponse = LoginResponse(keypass = "fitness")
        `when`(
            loginRepository.login(
                username,
                password
            )
        ).thenReturn(Response.success(loginResponse))

        // When
        viewModel.login(username, password)
        advanceUntilIdle()

        // Then
        verify(loadingObserver).onChanged(true)
        verify(loginResultObserver).onChanged(argThat { it.isSuccess && it.getOrNull() == loginResponse })
        verify(loadingObserver).onChanged(false)
    }


    @Test
    fun `login failure - Exception`() = runTest {
        // Given
        val username = "testUser"
        val password = "testPassword"
        val exception = Exception("Network error")
        `when`(loginRepository.login(username, password)).thenThrow(exception)

        // When
        viewModel.login(username, password)
        advanceUntilIdle()

        // Then
        verify(loadingObserver).onChanged(true)
        verify(loginResultObserver).onChanged(argThat {
            it.isFailure && it.exceptionOrNull() == exception
        })
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun `login failure - Empty response`() = runTest {
        // Given
        val username = "testUser"
        val password = "testPassword"
        val emptyResponse = Response.success<LoginResponse>(null)
        `when`(loginRepository.login(username, password)).thenReturn(emptyResponse)

        // When
        viewModel.login(username, password)
        advanceUntilIdle()

        // Then
        verify(loadingObserver).onChanged(true)
        verify(loginResultObserver).onChanged(argThat {
            it.isFailure && it.exceptionOrNull()?.message == "Empty response"
        })
        verify(loadingObserver).onChanged(false)
    }
}
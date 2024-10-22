import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.final_assessment.models.dashboard.DashboardEntity
import com.example.final_assessment.models.dashboard.DashboardResponse
import com.example.final_assessment.repositories.DashboardRepository
import com.example.final_assessment.viewmodels.DashboardState
import com.example.final_assessment.viewmodels.DashboardViewModel
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
class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DashboardViewModel
    private lateinit var testDispatcher: TestDispatcher

    @Mock
    private lateinit var repository: DashboardRepository

    @Mock
    private lateinit var stateObserver: Observer<DashboardState>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository)
        viewModel.dashboardState.observeForever(stateObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchDashboard success`() = runTest {
        // Given
        val keypass = "fitness"
        val entities = listOf(
            DashboardEntity(
                "Squats",
                "Legs",
                "Bodyweight or Barbell",
                "Intermediate",
                500,
                "A compound exercise that primarily targets the quadriceps, hamstrings, and glutes."
            ),
            DashboardEntity(
                "Push-ups",
                "Chest",
                "Bodyweight",
                "Beginner",
                300,
                "A classic bodyweight exercise that targets the chest, shoulders, and triceps.",
            )
        )
        val response = DashboardResponse(entities, entityTotal = 2)
        `when`(repository.getDashboard(keypass)).thenReturn(Response.success(response))

        // When
        viewModel.fetchDashboard(keypass)
        advanceUntilIdle()

        // Then
        verify(stateObserver).onChanged(DashboardState.Loading)
        verify(stateObserver).onChanged(
            argThat { state ->
                state is DashboardState.Success && state.entities == entities
            }
        )
    }


    @Test
    fun `fetchDashboard failure - Exception`() = runTest {
        // Given
        val keypass = "test_keypass"
        val exception = Exception("Network error")
        `when`(repository.getDashboard(keypass)).thenThrow(exception)

        // When
        viewModel.fetchDashboard(keypass)
        advanceUntilIdle()

        // Then
        verify(stateObserver).onChanged(DashboardState.Loading)
        verify(stateObserver).onChanged(argThat { state ->
            state is DashboardState.Error && state.message == "Network error"
        })
    }

    @Test
    fun `fetchDashboard failure - Empty response`() = runTest {
        // Given
        val keypass = "test_keypass"
        val emptyResponse = Response.success<DashboardResponse>(null)
        `when`(repository.getDashboard(keypass)).thenReturn(emptyResponse)

        // When
        viewModel.fetchDashboard(keypass)
        advanceUntilIdle()

        // Then
        verify(stateObserver).onChanged(DashboardState.Loading)
        verify(stateObserver).onChanged(argThat { state ->
            state is DashboardState.Error && state.message == "Empty response"
        })
    }
}
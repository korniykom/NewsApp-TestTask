import androidx.room.Room
import com.korniykom.newsapp.BuildConfig
import com.korniykom.newsapp.data.local.NewsDatabase
import com.korniykom.newsapp.data.repository.NewsRepositoryImpl
import com.korniykom.newsapp.domain.repository.NewsRepository
import com.korniykom.newsapp.presentation.screens.categories.CategoriesViewModel
import com.korniykom.newsapp.presentation.screens.main_screen.MainScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.INFO
            }
            defaultRequest {
                header("Authorization", "Bearer ${BuildConfig.unnknown_gibberish}")
            }
        }
    }
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::CategoriesViewModel)
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            "news.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    single { get<NewsDatabase>().articleDao() }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
}
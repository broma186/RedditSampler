package dagger

import com.example.redditsampler.RedditSamplerApplication
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PostsModule::class,
        CommentsModule::class
    ]
)
interface AppComponent : AndroidInjector<RedditSamplerApplication>


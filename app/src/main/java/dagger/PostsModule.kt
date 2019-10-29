package dagger

import com.example.redditsampler.PostsActivity
import com.example.redditsampler.adapters.PostAdapter
import dagger.android.ContributesAndroidInjector


@Module
abstract class PostsModule {
    @ContributesAndroidInjector
    abstract fun contributeViewPostsActivity(): PostsActivity
}

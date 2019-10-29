package dagger

import com.example.redditsampler.CommentsActivity
import dagger.android.ContributesAndroidInjector


@Module
abstract class CommentsModule {
    @ContributesAndroidInjector
    abstract fun contributeViewCommentsActivity(): CommentsActivity
}


## RedditSampler Info

This app has two appcompat activities that display a recycler view each. One with the posts and one with the comments, each with adapters/view holders.
I decided to use Room for the database storage since it is the most efficient SQL wrapper to implement in android app stores using coroutines for multi threading. I also utilized view models with data binding to reduce boilerplate code and clean up the code around the recycler views. Retrofit is used in conjunction with coroutines for network requests. Only the coroutine launch method is used rather that async/await as I am doing one shot requests one at a time. **The app should run with any reddit login credentials, you don't need mine.
The app knows what the app id is for authentication, it is stored in the constants file.** 

_Make sure you accept the permissions request that is brought up in a webview at the start of the user flow. You will keep getting
prompted by this screen until you accept._

You can use the [editor on GitHub](https://github.com/broma186/RedditSampler/edit/master/README.md) to maintain and preview the content for your website in Markdown files.

Whenever you commit to this repository, GitHub Pages will run [Jekyll](https://jekyllrb.com/) to rebuild the pages in your site, from the content in your Markdown files.



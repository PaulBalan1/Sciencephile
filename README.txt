App name: Sciencephile 

Description: 
It is an app that works in tandem with the YouTube channel “Sciencephile the AI”, owned by me. 
Its main purpose would be to act as a blog version of the channel, holding the videos together 
with the text-script in the form of blog posts. Ideally, they should be automatically generated 
using the YouTube API. The blog posts will be sorted and categorized by topic (aka. Physics, Astronomy, Biology, etc…). 
Among the various sections, one could be a “Submit video idea”, where one could input their email and idea as text, 
which would be stored in a Google Docs Spreadsheet, that only I would have access to. 


YouTube link to channel: https://www.youtube.com/channel/UC7BhHN8NyMMru2RUygnDXSg
YouTube link to video demonstration: https://www.youtube.com/watch?v=dDs5N89xlD4



MoSCoW requirements:

Must have:
-	Video posts containing the relevant YouTube video and the respective script in the form of text. 
	That would require the use of layouts, activities, intents, fragments, RecyclerViews, local data storage and testing. 
	(Mostly completed)
-	A way for me to create blog posts that would be consumed by all the instances of the app. (Replaced with another requirement)
-	Statistics of the channel displayed and updated in real-time. (Completed)

Should have: 
-	Blog posts categorized by topic in different sections. (Completed)
-	“Submit video idea” section which would send the text to a Google Docs Spreadsheet. (Not implemented)

Could have: 
-	Ability of making videos "favorite", and then viewing the respective list (Completed)
-	Automatic blog generation by fetching the data through the YouTube API.	(Completed)
-	Quizzes on different topics to test the reader’s comprehension of the content. (Not implemented)

Won’t have:
-	A log-in system, as no user-specific action is needed. 





README FINAL UPDATE:

In the process, i have realized that instead of me making blog posts, I would instead automatically generate them through the YouTube API whenever a new video 
would get uploaded on the channel. Initially i wasn't sure if i'd be able to do it, but after researching it I found it feaseable so i chose to go with it.

Final implemented requirements (sorted by importance):

-	Automatic video-post (title, description, the video itself) generation by fetching the data through the YouTube API in real-time.
-	Statistics of the channel displayed and updated in real-time.
-	Blog posts categorized by topic in different sections.
-	Ability of making videos "favorite", and then viewing the respective list, using a local file storage.
-	Ability to share videos or open and watch them on YouTube instead.

Used: Layouts, activities, intents, fragments, tabs, nav-menus, RecyclerViews, local data storage, Retrofit, Material Design, YouTube API libraries, etc... 

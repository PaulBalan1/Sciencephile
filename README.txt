App name: Sciencephile 

Description: 
It is an app that works in tandem with the YouTube channel “Sciencephile the AI”, owned by me. 
Its main purpose would be to act as a blog version of the channel, holding the videos together 
with the text-script in the form of blog posts. Ideally, they should be automatically generated 
using the YouTube API. The blog posts will be sorted and categorized by topic (aka. Physics, Astronomy, Biology, etc…). 
Among the various sections, one could be a “Submit video idea”, where one could input their email and idea as text, 
which would be stored in a Google Docs Spreadsheet, that only I would have access to. 


YouTube link: https://www.youtube.com/channel/UC7BhHN8NyMMru2RUygnDXSg



MoSCoW requirements:

Must have:
-	Blog posts containing the relevant YouTube video and the respective script in the form of text. 
	That would require the use of layouts, activities, intents, fragments, RecyclerViews, local data storage and testing. 
-	A way for me to create blog posts that would be consumed by all the instances of the app.
-	Statistics of the channel displayed and updated in real-time.

Should have: 
-	Blog posts categorized by topic in different sections.
-	“Submit video idea” section which would send the text to a Google Docs Spreadsheet. 

Could have: 
-	Automatic blog generation by fetching the data through the YouTube API.
-	Quizzes on different topics to test the reader’s comprehension of the content.

Won’t have:
-	A log-in system, as no user-specific action is needed. 

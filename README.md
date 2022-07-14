# NUMAD22-A8StickItToThem-Team24
This app is made by Team 24 for the assignment **Assignment 8 (A8)**

# Running the App
- Clone this repository
- Checkout to the folder
- Open the folder in Android Studio
- Run the app on Api level 28 or above

# About the App
We made a chat application named **Puddle**. It is an individual sticker(gif) chat application. The user is able to send animated gif which are already predefined in the drawable and no other gif are allowed to send. There are a total of 10 different types of gif for the user to choose from.

The following feature are there in the application
1. Signup Activity 
   - User enters username and email to register.
   - We make the unique email by: username@puddle.com, the email entered doesn't matter.

2. Login Activity
   - User just needs to enter the username to proceed

3. Chat History Activity
   - Shows all the users list with whom the current user has chat
   - We display the most recent message and the time of the message on this activity.
   - It has a FAB to start a new chat.
  
4. New Chat Activity
   - Shows all the users which are there in the database.
   - User can click on the username to start chat.
  
5. Chat Room Activity
   - Shows the history of chats between those 2 users.
   - No chats are stored in local storage and are always fetched from api.

6. Sticker Activity
   - It contains list of 10 stickers(gif), which users can send.
   - It also shows the count for each sticker which the current user has shared so far.
  
7. Notification
   - The app receives notification when in foreground and background.
   - When the app is killed, no notifications are received.
  
# Different App Version
The scenario where the 2 users have different ap versions and different gif's in their drawable, in that case when the older version doesn't contain the gif which was sent by user using latest version, a **question mark gif** is shown as a placeholder.

# Api's used
1. Login Api
2. Register User Api
3. Get All Users api
4. Get All recent messages api
5. Sticker count api
6. Api to store chats in Database
7. Api to fetch all chat history between 2 users
8. Api to logout

# External Dependencies Used
- [Glide](https://github.com/bumptech/glide): This was used to display image from URL, which comes from api response
- [Firebase](https://firebase.google.com/): This was used as the realtime database to store all data
- [Material UI](https://material.io/develop/android): This was used to create elegant and neat UI

# Running the app
Following is the requirement for running the app:
- Android api level 28 or more
- Android version Pie 9.0 or above.

# Git and Branches
The following branches were used:
1. master - Only contains the final working code
2. rls/master - Used to merge all the changes from 2 or more branches
3. rls/nisarg - Contains commit made by Nisarg Sureshkumar Patel
4. rls/harshit-dev-branch - Contains commit made by Harshit Bhavesh Gajjar
5. Sean - Contains commit made by Zhenyu Wang
6. ChrisDevBranch - Contains commit made by Christopher Schelb

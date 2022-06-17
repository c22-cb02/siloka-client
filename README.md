# Siloka Chatbot
Siloka is an android application created for "Chatbot for optimizing customer operations" case given by Traveloka SG and created by Bangkit Academy 2022 Cohort.

Contributor to this repository:
- [Fahdii Ajmalal F.](https://github.com/fahdikrie)
- [Muhammad Naufal Nabiel](https://github.com/NaufalNabiel)
- [Ricky Alverdo](https://github.com/ClostridiumR)

## User Manual

Siloka is a very user friendly chatbot, a new user will only needs to follow the following steps to use this application.

<h3>Getting Started</h3>
  
  Before a user want to use the Siloka Chatbot, they need to install it on their own device. We'll be using Android Studio since its the Official IDE for Android. 
  Android Studio also provides the fastest tools for building apps on every type of Android device.
  
  1. Clone this repository.
  
  ```
  git clone https://github.com/c22-cb02/siloka-client.git
  ```
  
  3. Put it in your own Device and Open it on the Android Studio.
  4. Run it on an emulator or a real device.

-----

<h3>Starting the App</h3>

  After installing and made It run on your desired device, run the application and follow these steps:
  
  1. After seeing the splashscreen you will be shown the onboarding page.
  2. Insert your name or whatever you want to be called by the Chatbot.
  3. The user will be presented by the Chat room.  

  **Your screen will appear like this:**
  
  <kbd>
  <img src="https://user-images.githubusercontent.com/90308354/173238063-aa0cfe99-6a72-474f-a0e7-68bbc3be26f7.gif" width="200"/>
  </kbd>

-----

<h3>Using the chat feature</h3>

  When you enter the chat room, you will be greeted by the bot and can ask the questions right away!
  
  1. Write anything related to the user problem.
  2. The bot will read the message and send it to the backend.
  3. The backend will read the message and try to find the problems from traveloka help center.
  4. The bot will return the most similar problem on the help center.
  5. The bot will ask if the answer is satisfying.
  6. If the user answers with no, the user will be asked if they want to be directed to the Call Center.
  7. If the user answers with yes, they will be directed to the CS Menu.
  8. Note that this feature is out of our scope, so It was **not** implemented in this build.
  9. If the user answers with no, they can continue to chat with the bot.
 
**Your screen will appear like this:**
  
  <kbd>
  <img src="https://user-images.githubusercontent.com/47189456/174201040-6318a4c2-bcaf-48ec-b8c8-69cdc10e248a.gif" width="200"/>
  </kbd>    
  
-----

<h3>Using the shortcut menu on the chatroom</h3>

 When you enter the chat room, you can also choose the popular topics:
  
  1. Choose any of the choices from the Shortcut menu.
  2. The chat bubble that you click will automatically send as a message. 
  3. The bot will read the message and return the desored answer.
  4. The bot will ask if the answer is satisfying.
  5. If the user answers with yes, then the chatbot will thanks the user.
  6. Continue to ask if there's any other questions.


**Your screen will appear like this:**
  
  <kbd>
  <img src="https://user-images.githubusercontent.com/90308354/173238657-e9a5c32c-c28b-45e1-9b62-52f581348783.gif" width="200"/>
  </kbd> 

-----

<h3>Changing the user's name</h3>

 If the user want to change what they want to be called by the bot, they could use the settings feature.
  
  1. Click on the top right gear button.
  2. The user will be directed to the Settings Activity.
  3. Change your old name and click save.
  4. Return to the main chat room.
  5. Notice that the bot greets the user with the new name.

**Your screen will appear like this:**
  
  <kbd>
  <img src="https://user-images.githubusercontent.com/47189456/174200968-18a81025-adca-45cf-a62c-3af780582d0d.gif" width="200"/>
  </kbd> 

## Libraries used 

- Okhttp3
- Volley
- KTX
- Datastore
- Livedata
- ViewModel
- Firebase
- [DotsLoader](https://github.com/agrawalsuneet/DotLoadersPack-Android)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)

## References

- https://www.geeksforgeeks.org/how-to-create-a-chatbot-in-android-with-brainshop-api/
- https://github.com/VidyasagarMSC/WatBot-Kotlin

# cardinfofinder_
Android app to find details of credit or debit card

# Getting Started
This file will attempt to explain my approach in creating this app. You can download or clone this project to see the source code and run it.

# Development Approach
Language: Kotlin

Architecture: This app is built using the MVVM architecture with one activity (Main), view model as well as directory structures for networking, utils, models and views.

Libraries: Third party dependecies are used in this project: Retrofit library for network calls, espresso for UI test and SpinKit for progress loader

## Coding / Design
Service class defines the retrofit method

The models package contains the data models that defines the objects and DTOs in the application

The ViewModel is where has the main business logic of the application as I attempted to separate concerns and take away heavy lifting operations from my presentation layer.

FeedbackUtils defines utility methods for softKeyboard config, dialogs and promts, etc.

MainActivity initializes the views, viewmodel and makes the network call when button is clicked. 

The layout xml file is designed with constraint layout and card view and material design component.

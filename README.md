<img src="https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/jagawana_logo_final.jpeg" width="100" height="100" align="right" />

# Jagawana-AndroidApp
Capstone project Bangkit 2021

[![Mockup](https://img.shields.io/badge/mockup-PSDMockups-informational.svg)](https://www.psdmockups.com/mobile-android-samsung-smartphone-psd-mockup/)
![Build](https://img.shields.io/badge/build-passing-success.svg)
![Server](https://img.shields.io/badge/server_status-Active-critical.svg)
![Version](https://img.shields.io/badge/kotlin-1.5.0-informational.svg)


![Jagawana](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/AppScreenshot.jpg)

## About Our App

This is a client application of Jagawana, an illegal logging prevention app. This app displays where the IoT device is located and displays all the events captured by the connected IoT device. This application also in real time notifies the user if an emergency event occurs and shows the location of the event.

## How To Make Thing Like We Made

### Libraries We Use

| Library name                                                          | Usages                                            | Dependency                                                          |
| -------------                                                         | -------------                                     | -------------                                                       |
| [OkHttp](https://square.github.io/okhttp/)                            | Make a data request to the server                 | implementation "com.squareup.okhttp3:okhttp:4.9.1"                  |
| [Gson](https://github.com/google/gson)                                | Convert json obtained from okhttp into an object  | implementation 'com.google.code.gson:gson:2.8.6'                    |
| [Navigation component](https://developer.android.com/guide/navigation)| navigation between pages                          | implementation("androidx.navigation:navigation-fragment-ktx:2.3.5") |
|                                                                       |                                                   | implementation("androidx.navigation:navigation-ui-ktx:2.3.5")       |

### UI Libraries We Use

| Library name                                                      | Dependency                                                    | Demo                                                                                                                                |
| -------------                                                     | -------------                                                 | -------------                                                                                                                       |
| [Material Design](https://material.io/)                           | ***Auto added by android studio***                            | ![Jagawana Appbar](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/MaterialDesign.PNG)            |
| [SmoothBottomBar](https://github.com/ibrahimsn98/SmoothBottomBar) | implementation 'com.github.ibrahimsn98:SmoothBottomBar:1.7.6' | ![Jagawana SmoothBottomBar](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/BottomNavigation.PNG) |

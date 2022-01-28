import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/ui/chat_page.dart';
import 'package:jagawana_android_app/ui/device_detail_page.dart';
import 'package:jagawana_android_app/ui/device_list_page.dart';
import 'package:jagawana_android_app/ui/detail/event/event_detail.dart';
import 'package:jagawana_android_app/ui/laporan/laporan_page.dart';
import 'package:jagawana_android_app/ui/login/Mediator.dart';
import 'package:jagawana_android_app/ui/login/login_page.dart';
import 'package:jagawana_android_app/ui/map/main_map_page.dart';
import 'package:jagawana_android_app/ui/map/map_page.dart';
import 'package:jagawana_android_app/ui/home/navigation_pages/notification_page.dart';
import 'package:jagawana_android_app/ui/sound_list/SoundListPage.dart';
import 'package:jagawana_android_app/ui/test_page.dart';
import 'data/repository.dart';
import 'ui/home/home_page.dart';
import 'ui/other_page/error_page.dart';
import 'ui/other_page/splash_page.dart';

import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';

void main() {
  runApp(InitApp());
}

//init flutter app
class InitApp extends StatefulWidget {
  const InitApp({Key? key}) : super(key: key);

  @override
  _InitAppState createState() => _InitAppState();
}

class _InitAppState extends State<InitApp> {

  final Future<FirebaseApp> _initialization = Firebase.initializeApp();

  //================================================================
  Future<void> setupInteractedMessage() async {
    // Get any messages which caused the application to open from
    // a terminated state.
    RemoteMessage? initialMessage =
    await FirebaseMessaging.instance.getInitialMessage();

    // If the message also contains a data property with a "type" of "chat",
    // navigate to a chat screen
    if (initialMessage != null) {
      _handleMessage(initialMessage);
    }

    // Also handle any interaction when the app is in the background via a
    // Stream listener
    FirebaseMessaging.onMessageOpenedApp.listen(_handleMessage);
  }

  void _handleMessage(RemoteMessage message) {
    Navigator.pushNamed(context, '/map',);
  }
  //================================================================


  @override
  Widget build(BuildContext context) {

    //create notification channel for android
    const AndroidNotificationChannel channel = AndroidNotificationChannel(
      'warning_channel', // id
      'Warning Notifications', // title
      description: 'channel untuk menerima notifikasi tanda bahaya', // description
      importance: Importance.max,
    );
    final FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin =
    FlutterLocalNotificationsPlugin();
    flutterLocalNotificationsPlugin
        .resolvePlatformSpecificImplementation<AndroidFlutterLocalNotificationsPlugin>()
        ?.createNotificationChannel(channel);




    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: FutureBuilder(
        // Initialize FlutterFire:
        future: _initialization,
        builder: (context, snapshot) {
          // Check for errors
          if (snapshot.hasError) {
            return ErrorPage();
          }

          // Once complete, show your application
          if (snapshot.connectionState == ConnectionState.done) {
            setupInteractedMessage();

            FirebaseMessaging.onMessage.listen((RemoteMessage message) {
              RemoteNotification? notification = message.notification;
              AndroidNotification? android = message.notification?.android;

              // If `onMessage` is triggered with a notification, construct our own
              // local notification to show to users using the created channel.
              if (notification != null && android != null) {
                flutterLocalNotificationsPlugin.show(
                    notification.hashCode,
                    notification.title,
                    notification.body,
                    NotificationDetails(
                      android: AndroidNotificationDetails(
                        channel.id,
                        channel.name,
                        channelDescription: channel.description,
                        icon: android.smallIcon,
                        // other properties...
                      ),
                    ));
              }
            });


            //=======================
            //todo this my code
            //listen nitification from stream
            FirebaseFirestore.instance.collection('event').doc('mcW7xwshiBMStlgztYu2').snapshots().listen((event) {
              print(event["timestamp"]);
              MyRepository.lastEventTimestamp = event["timestamp"];
              //todo tambah notifikasi
            });
            //========================


            return MyNavigation();
          }

          // Otherwise, show something whilst waiting for initialization to complete
          return SplashPage();
        },
      ),
    );
  }
}



//route widget
class MyNavigation extends StatelessWidget {
  const MyNavigation({Key? key}) : super(key: key);



  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      routes: {
        '/test': (context) => TestPage(), //todo nanti kalau sudah jadi hapus ini
        '/home': (context) => HomePage(),
        '/map': (context) => MainMapPage(),
        '/sound': (context) => SoundListPage(),
        '/laporan': (context) => LaporanPage(),
        '/login': (context) => LoginPage(),
        '/chat': (context) => ChatPage(),
        '/device': (context) => DeviceListPage(),
        '/deviceDetail': (context) => DeviceDetail(),
        '/notification': (context) => NotificationPage(),
        '/notificationDetail': (context) => EventDetailPage(),
        '/': (context) => Mediator(),
      },

      theme: ThemeData(
        primarySwatch: My_Color_Scheme.MAIN_COLOR,
      ),
    );
  }
}

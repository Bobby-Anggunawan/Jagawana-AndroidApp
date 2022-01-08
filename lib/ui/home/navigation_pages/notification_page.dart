import 'package:flutter/material.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/items/notificarion_items.dart';
import 'package:jagawana_android_app/ui/other_page/error_page.dart';
import 'package:jagawana_android_app/ui/other_page/loading_page.dart';
import 'package:jagawana_android_app/constant.dart';

class NotificationPage extends StatefulWidget {
  const NotificationPage({Key? key}) : super(key: key);

  @override
  _NotificationPageState createState() => _NotificationPageState();
}

class _NotificationPageState extends State<NotificationPage> {

  final Future<List<EventData>> _listEvent = MyRepository.fetchNotificationList();

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<EventData>>(
        future: _listEvent,
        builder: (context, snapshot){
          if (snapshot.hasError && MyRepository.listEvent == null) {
            return ErrorPage();
          }
          if (snapshot.connectionState == ConnectionState.done || MyRepository.listEvent != null){
            return ListView.builder(

                itemCount: MyRepository.listEvent!.length,
                itemBuilder: (BuildContext context, int index){
                  return NotificationItem(data: MyRepository.listEvent![index]);
                }
            );
          }
          return LoadingPage();
        }
    );
  }
}


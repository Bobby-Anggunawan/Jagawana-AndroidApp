import 'package:flutter/material.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/items/notificarion_items.dart';
import 'package:jagawana_android_app/ui/items/sound_item.dart';
import 'package:jagawana_android_app/ui/other_page/error_page.dart';
import 'package:jagawana_android_app/ui/other_page/loading_page.dart';

class SoundListPage extends StatefulWidget {
  const SoundListPage({Key? key}) : super(key: key);

  @override
  _SoundListPageState createState() => _SoundListPageState();
}

class _SoundListPageState extends State<SoundListPage> {

  final Future<List<EventData>> _listEvent = MyRepository.fetchNotificationList();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("SoundList"),
      ),
      body: FutureBuilder<List<EventData>>(
          future: _listEvent,
          builder: (context, snapshot){
            if (snapshot.hasError && MyRepository.listEvent == null) {
              return ErrorPage();
            }
            if (snapshot.connectionState == ConnectionState.done || MyRepository.listEvent != null){
              return ListView.builder(

                  itemCount: MyRepository.listEvent!.length,
                  itemBuilder: (BuildContext context, int index){
                    return SoundItem(data: MyRepository.listEvent![index], includePlayButton: true, isFirstItem: (index == 0)? true:false);
                  }
              );
            }
            return LoadingPage();
          }
      )
    );
  }
}

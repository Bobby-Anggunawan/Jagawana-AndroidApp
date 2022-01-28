import 'package:flutter/material.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/items/notificarion_items.dart';
import 'package:jagawana_android_app/ui/other_page/error_page.dart';
import 'package:jagawana_android_app/ui/other_page/loading_page.dart';

import '../../../constant.dart';

class ForestHistory extends StatefulWidget {
  const ForestHistory({Key? key}) : super(key: key);

  @override
  _ForestHistoryState createState() => _ForestHistoryState();
}

class _ForestHistoryState extends State<ForestHistory> {

  final Future<List<EventData>> _listEvent = MyRepository.fetchRegionHistory();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Riwayat"),
        elevation: 0
      ),
      body: FutureBuilder<List<EventData>>(
          future: _listEvent,
          builder: (context, snapshot){
            if (snapshot.hasError && snapshot.data == null) {
              return ErrorPage();
            }
            if (snapshot.connectionState == ConnectionState.done || snapshot.data != null){
              return ListView(
                children:[
                  Container(
                    padding: EdgeInsets.all(Constant.PADDING_0_5),
                    child: Text(snapshot.data![0].region, textAlign: TextAlign.center, style: TextStyle(color: My_Color_Scheme.MAIN_FOREGROUND),),
                    decoration: BoxDecoration(
                      color: Colors.teal[700]
                    ),
                  ),
                  ListView.builder(
                      shrinkWrap: true,
                      itemCount: snapshot.data!.length,
                      itemBuilder: (BuildContext context, int index){
                        return NotificationItem(data: snapshot.data![index]);
                      }
                  )
                ]
              );
            }
            return LoadingPage();
          }
      )
    );
  }
}


import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/detail/event/audio_player.dart';
import 'package:jagawana_android_app/ui/items/detail_items.dart';

class EventDetailPage extends StatefulWidget {
  const EventDetailPage({Key? key}) : super(key: key);

  @override
  _EventDetailPageState createState() => _EventDetailPageState();
}

class _EventDetailPageState extends State<EventDetailPage> {

  @override
  Widget build(BuildContext context) {

    final args = ModalRoute.of(context)!.settings.arguments as EventData;

    return Scaffold(
      appBar: AppBar(
        title: Text(
          args.idClip,
        ),
      ),

      body: Container(
        padding: EdgeInsets.all(Constant.PADDING_2),
        child: ListView(
          physics: NeverScrollableScrollPhysics(),
          children: [
            DetailItem(header: "Region", content: args.region),
            DetailItem(header: "Result", content: args.classifyResult),
            DetailItem(header: "Device", content: args.idDevice),
            DetailItem(header: "Time", content: args.timestamp.substring(0, 19)),
            DetailItem(header: "Longitude", content: args.longitude.toString()),
            DetailItem(header: "Latitude", content: args.latitude.toString()),
            AudioPlayerWidget(audioUrl: args.link)
          ],
        )
      ),
    );
  }
}

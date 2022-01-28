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
          "Detail",
        ),
      ),

      body: Container(
        padding: EdgeInsets.all(Constant.PADDING_2),
        child: ListView(
          physics: NeverScrollableScrollPhysics(),
          children: [
            Padding(
              child: Text("Recording Information", style: TextStyle(color: My_Color_Scheme.SECOND_FOREGROUND, fontWeight: FontWeight.bold, fontSize: 20)),
              padding: EdgeInsets.only(bottom: Constant.PADDING_1)
            ),

            DetailItem(header: "Perangkat", content: args.idDevice),
            DetailItem(header: "Wilayah", content: args.region),
            DetailItem(header: "Hasil Prediksi", content: args.classifyResult),
            DetailItem(header: "ID Rekaman", content: args.idClip),
            DetailItem(header: "Waktu Terdeteksi", content: args.timestamp.substring(0, 19)),
            AudioPlayerWidget(audioUrl: args.link)
          ],
        )
      ),
    );
  }
}

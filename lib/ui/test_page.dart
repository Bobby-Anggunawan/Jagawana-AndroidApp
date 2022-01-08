import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:http/http.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/map/map_bottom_sheet.dart';

//todo cuma untuk test widget
class TestPage extends StatefulWidget {
  const TestPage({Key? key}) : super(key: key);

  @override
  _TestPageState createState() => _TestPageState();
}

class _TestPageState extends State<TestPage>{



  @override
  Widget build(BuildContext context) {


    MyRepository.listRegion!.forEach((element) {
      print(element.lgl);
    });

    return Stack(
        children:[
          Scaffold(
              resizeToAvoidBottomInset: false,
              appBar: AppBar(
                title: Text("ini test"),
              ),
              body: Center(child: Text("ini tengah"),)
          ),
          Align(
            alignment: Alignment.bottomCenter,
            child: Material(
              color: Colors.transparent,
              child: MyBottomSheet(),
            ),
          ),
        ]
    );
  }
}

import 'package:flutter/material.dart';
import 'package:expandable_bottom_sheet/expandable_bottom_sheet.dart';
import 'package:jagawana_android_app/ui/map/map_bottom_sheet.dart';
import 'package:jagawana_android_app/ui/map/map_page.dart';

class MainMapPage extends StatefulWidget {
  const MainMapPage({Key? key}) : super(key: key);

  @override
  _MainMapPageState createState() => _MainMapPageState();
}

class _MainMapPageState extends State<MainMapPage> {
  @override
  Widget build(BuildContext context) {

    return Stack(
        children:[
          MapPage(),
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

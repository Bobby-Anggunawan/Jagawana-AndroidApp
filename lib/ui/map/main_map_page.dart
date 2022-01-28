import 'package:flutter/material.dart';
import 'package:expandable_bottom_sheet/expandable_bottom_sheet.dart';
import 'package:jagawana_android_app/ui/map/map_bottom_sheet.dart';
import 'package:jagawana_android_app/ui/map/map_page.dart';

import '../../constant.dart';

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

          Container(
              height: 50,
              width: 50,
              margin: EdgeInsets.symmetric(vertical: 40, horizontal: Constant.PADDING_2),
              child: TextButton(
                child: Icon(Icons.arrow_back_rounded, color: My_Color_Scheme.SECOND_FOREGROUND, size: 35,),
                onPressed: (){
                   Constant.getMainNav(context).pop(context);
                },
              ),
              decoration: BoxDecoration(
                  color: My_Color_Scheme.MAIN_FOREGROUND,
                  borderRadius: BorderRadius.all(Radius.circular(25))
              )
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

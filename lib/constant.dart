import 'package:flutter/material.dart';

class Constant{
  static NavigatorState? MAIN_NAV = null;
  static NavigatorState getMainNav(BuildContext context){
    if(MAIN_NAV != null) return MAIN_NAV!;
    else return Navigator.of(context);
  }
  static const APP_NAME = "Jagawana";
  static const PADDING_2 = 20.0;
  static const PADDING_1 = 10.0;
  static const PADDING_0_5 = 5.0;

  static const TEXTSIZE_BIG = 30.0;
}

class My_Color_Scheme{
  static const MAIN_COLOR = Colors.teal;
  static const MAIN_FOREGROUND = Colors.white;
  static const SECOND_FOREGROUND = Colors.black45;
}
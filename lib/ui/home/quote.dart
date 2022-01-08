import 'package:flutter/material.dart';
import '../../constant.dart';

class Quote extends StatelessWidget {
  const Quote({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(Constant.PADDING_2),
      margin: EdgeInsets.all(Constant.PADDING_2),
      decoration: BoxDecoration(
          color: My_Color_Scheme.MAIN_COLOR,
          borderRadius: BorderRadius.all(Radius.circular(20))
      ),
      child: Center(child: Text(
        "Jagawana.",
        style: TextStyle(
          fontFamily: "Rancho",
          color: Colors.white,
          fontSize: 25,
        ),
      )),
    );
  }
}

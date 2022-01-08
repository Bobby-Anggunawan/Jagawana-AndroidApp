import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';

class DetailItem extends StatelessWidget {

  final String header;
  final String content;

  const DetailItem({Key? key, required this.header, required this.content}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: Constant.PADDING_0_5, bottom: Constant.PADDING_0_5),
      child: ListView(
          physics: NeverScrollableScrollPhysics(),
          shrinkWrap: true,
          children: [
            Text(header,
                style: TextStyle(
                  color: My_Color_Scheme.MAIN_COLOR,
                  fontWeight: FontWeight.bold,
                )
            ),
            Text(content,
                style: TextStyle(
                  color: My_Color_Scheme.SECOND_FOREGROUND,
                )
            ),
          ]
      )
    );
  }
}

import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';

class NotificationItem extends StatelessWidget {
  const NotificationItem({Key? key, required this.data}) : super(key: key);

  final EventData data;

  @override
  Widget build(BuildContext context) {
    return Padding(
      //todo ganti padding jadi yang ada di constant
        padding: EdgeInsets.fromLTRB(Constant.PADDING_2, Constant.PADDING_1, Constant.PADDING_2, Constant.PADDING_1),
        child: Container(
            decoration: BoxDecoration(
                color: Colors.white,
                //todo ganti padding jadi yang ada di constant
                borderRadius: BorderRadius.all(Radius.circular(Constant.PADDING_1)),
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 5,
                    blurRadius: 7,
                    offset: Offset(0, 3), // changes position of shadow
                  ),
                ]

            ),
            child: TextButton(
                style: TextButton.styleFrom(
                    alignment: Alignment.centerLeft
                ),
                onPressed: (){
                  Constant.getMainNav(context).pushNamed("/notificationDetail", arguments: data);
                },
                child: Padding(
                    padding: EdgeInsets.fromLTRB(Constant.PADDING_2, Constant.PADDING_1, Constant.PADDING_2, Constant.PADDING_1),
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Padding(
                            padding: EdgeInsets.only(bottom: Constant.PADDING_0_5),
                            child: Text(data.classifyResult, style: TextStyle(fontWeight: FontWeight.bold),),
                          ),
                          Padding(
                            padding: EdgeInsets.only(bottom: Constant.PADDING_0_5),
                            child: Text("Terdeteksi di area ${data.region}", style: TextStyle(color: My_Color_Scheme.SECOND_FOREGROUND)),
                          ),
                          Text(data.timestamp.substring(0, 19), style: TextStyle(color: My_Color_Scheme.SECOND_FOREGROUND)),
                        ]
                    )
                )
            )
        )
    );
  }
}
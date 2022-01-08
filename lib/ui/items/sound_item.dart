import 'package:flutter/material.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';

import '../../constant.dart';

class SoundItem extends StatefulWidget {
  const SoundItem({Key? key, required this.data, required this.includePlayButton, required this.isFirstItem}) : super(key: key);


  final bool includePlayButton;
  final bool isFirstItem;

  final EventData data;


  @override
  _SoundItemState createState() => _SoundItemState();
}

class _SoundItemState extends State<SoundItem> {
  @override
  Widget build(BuildContext context) {

    return Container(
        padding: EdgeInsets.only(top: Constant.PADDING_0_5, bottom: Constant.PADDING_0_5),
        margin: EdgeInsets.only(left: Constant.PADDING_1, right: Constant.PADDING_2),
        decoration: BoxDecoration(
          border: !widget.isFirstItem ? Border(
            top: BorderSide(
              color: My_Color_Scheme.SECOND_FOREGROUND,
              width: 1.0,
            ),
          ): null,
        ),
        child: Row(
            children: [
              widget.includePlayButton? Icon(Icons.play_circle, size: 60, color: My_Color_Scheme.MAIN_COLOR,): SizedBox.shrink(),
              Container(
                padding: EdgeInsets.only(left: Constant.PADDING_1),
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Text(widget.data.classifyResult, style: TextStyle(color: My_Color_Scheme.SECOND_FOREGROUND),),
                      Text(widget.data.idDevice, style: TextStyle(color: My_Color_Scheme.SECOND_FOREGROUND),),
                    ]
                ),
              ),
              Expanded(
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        Text(widget.data.timestamp.substring(0, 10), style: TextStyle(color: My_Color_Scheme.SECOND_FOREGROUND),),
                        Text(widget.data.timestamp.substring(11, 16), style: TextStyle(color: My_Color_Scheme.SECOND_FOREGROUND),),
                      ]
                  )
              ),
            ]
        )
    );
  }
}

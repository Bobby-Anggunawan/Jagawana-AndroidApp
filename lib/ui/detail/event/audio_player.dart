import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:audioplayers/audioplayers.dart';

class AudioPlayerWidget extends StatefulWidget {
  const AudioPlayerWidget({Key? key, required this.audioUrl}) : super(key: key);

  final String audioUrl;

  @override
  _AudioPlayerWidgetState createState() => _AudioPlayerWidgetState();
}

class _AudioPlayerWidgetState extends State<AudioPlayerWidget>{

  AudioPlayer audioPlayer = AudioPlayer();
  PlayerState playerState = PlayerState.COMPLETED;
  String maxDuration = "00:00";
  String thisDuration = "00:00";


  @override
  Widget build(BuildContext context) {

    var playButton = IconButton(
        icon: Icon(Icons.play_arrow, size: 40, color: My_Color_Scheme.MAIN_FOREGROUND),
        onPressed: () async{
          if(playerState == PlayerState.PAUSED){
            await audioPlayer.resume();
          }
          else{
            await audioPlayer.play(widget.audioUrl);
          }
        }
    );
    var pauseButton = IconButton(
        icon: Icon(Icons.pause, size: 40, color: My_Color_Scheme.MAIN_FOREGROUND),
        onPressed: () async{
          await audioPlayer.pause();
        }
    );

    var stopButton = IconButton(
        icon: Icon(Icons.stop, size: 40, color: My_Color_Scheme.MAIN_FOREGROUND),
        onPressed: () async{
          setState(() {
            thisDuration = "00:00";
          });
          await audioPlayer.stop();
        }
    );
/*
    audioPlayer.onPlayerStateChanged.listen((PlayerState s) => (){
      setState(() => playerState = s);
      print("player state berubah jadi $s");
    });*/
    audioPlayer.onPlayerStateChanged.listen((event) {
      setState(() {
        playerState = event;
      });
    });
    audioPlayer.onDurationChanged.listen((event) {
      setState(() {
        maxDuration = event.toString().substring(2, 7);
      });
    });
    audioPlayer.onAudioPositionChanged.listen((event) {
      setState(() {
        thisDuration = event.toString().substring(2, 7);
      });
    });
    audioPlayer.onPlayerCompletion.listen((event) {
      setState(() {
        thisDuration = "00:00";
      });
    });


    return Container(
        padding: EdgeInsets.only(top: Constant.PADDING_1, bottom: Constant.PADDING_1),
        margin: EdgeInsets.only(top: Constant.PADDING_1,),
        decoration: BoxDecoration(
            color: My_Color_Scheme.MAIN_COLOR,
            borderRadius: BorderRadius.all(Radius.circular(Constant.PADDING_1))
        ),
        child: Row(
            children: [
              Expanded(
                child: (playerState == PlayerState.PLAYING )? pauseButton : playButton,
              ),
              Expanded(child: stopButton),
              Expanded(
                  flex: 2,
                  child: Row(
                      children: [
                        Text(thisDuration, style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold,
                            color: My_Color_Scheme.MAIN_FOREGROUND
                        )),
                        Text(" | ", style: TextStyle(
                            fontSize: 20,
                            color: My_Color_Scheme.MAIN_FOREGROUND
                        )),
                        Text(maxDuration, style: TextStyle(
                            fontSize: 20,
                            color: My_Color_Scheme.MAIN_FOREGROUND
                        ))
                      ]
                  )
              )
            ]
        )
    );
  }
}


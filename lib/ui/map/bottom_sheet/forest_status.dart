import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/data/repository.dart';


class ForestStatus extends StatelessWidget {
  const ForestStatus({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              ListView(
                physics: NeverScrollableScrollPhysics(),
                padding: EdgeInsets.fromLTRB(Constant.PADDING_2, Constant.PADDING_1, Constant.PADDING_2, 0),
                shrinkWrap: true,
                children: [
                  Center(
                    child: Container(
                      width: 50,
                      height: 5,
                      margin: EdgeInsets.only(bottom: Constant.PADDING_1),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.all(Radius.circular(2.5)),
                          color: My_Color_Scheme.SECOND_FOREGROUND,
                      ),
                    )
                  ),
                  Text("${MyRepository.regionDeviceCount(MyRepository.listRegion[MyRepository.activeRegionIndex].regionName)} Perangkat Terhubung"),
                  Text(
                      MyRepository.listRegion[MyRepository.activeRegionIndex].regionName,
                      style: TextStyle(
                          color: My_Color_Scheme.MAIN_COLOR,
                          fontWeight: FontWeight.bold,
                          fontSize: 30
                      )
                  ),
                ]
              ),
              Container(
                padding: EdgeInsets.symmetric(vertical: Constant.PADDING_0_5),
                margin: EdgeInsets.only(top: Constant.PADDING_0_5),
                alignment: Alignment.center,
                decoration: BoxDecoration(
                    color: My_Color_Scheme.MAIN_COLOR
                ),
                child: Text("Seret untuk melihat riwayat lokasi", style: TextStyle(color: My_Color_Scheme.MAIN_FOREGROUND)),
              )
            ]
        )
    );
  }
}
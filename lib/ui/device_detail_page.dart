import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/data/model/agruments/device_detail_arg.dart';
import 'package:jagawana_android_app/data/model/device_list_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/items/detail_items.dart';
import 'package:jagawana_android_app/ui/other_page/error_page.dart';
import 'package:jagawana_android_app/ui/other_page/loading_page.dart';

class DeviceDetail extends StatefulWidget {
  const DeviceDetail({Key? key}) : super(key: key);

  @override
  _DeviceDetailState createState() => _DeviceDetailState();
}

class _DeviceDetailState extends State<DeviceDetail> {

  @override
  Widget build(BuildContext context) {
    final args = ModalRoute.of(context)!.settings.arguments as String;
    final Future<DeviceDetailAgr> _data = MyRepository.getDeviceDetail(args);

    return Scaffold(
      appBar: AppBar(
        title: Text(
          args,
          style: TextStyle(
            fontFamily: "Rancho",
          ),
        ),
      ),

      body: FutureBuilder<DeviceDetailAgr>(
          future: _data,
          builder: (context, snapshot){
            if (snapshot.hasError) {
              return ErrorPage();
            }
            if (snapshot.connectionState == ConnectionState.done){
              return ListView(
                shrinkWrap: true,
                physics: NeverScrollableScrollPhysics(),
                children: [

                  Container(
                    padding: EdgeInsets.all(Constant.PADDING_2),
                    child: ListView(
                        shrinkWrap: true,
                        physics: NeverScrollableScrollPhysics(),
                        children:[
                          DetailItem(header: "LastTransmission", content: snapshot.data!.lastTransmission.substring(0, 19)),
                          DetailItem(header: "Status", content: snapshot.data!.Status),
                          DetailItem(header: "Region", content: snapshot.data!.device.region),
                          DetailItem(header: "Latitude", content: snapshot.data!.device.latitude.toString()),
                          DetailItem(header: "Longitude", content: snapshot.data!.device.longitude.toString()),
                        ]
                    )
                  ),


                  ListView.builder(
                      shrinkWrap: true,
                      itemCount: snapshot.data!.events.length,
                      itemBuilder: (BuildContext context, int index){
                        return Container(
                          height: 50,
                          padding: EdgeInsets.fromLTRB(Constant.PADDING_2, 0, Constant.PADDING_2, 0),
                          margin: EdgeInsets.fromLTRB(Constant.PADDING_2/2, Constant.PADDING_2/4, Constant.PADDING_2/2, Constant.PADDING_2/4),
                          decoration: new BoxDecoration(
                              color: Colors.green,
                              borderRadius: new BorderRadius.all(Radius.circular(10.0))
                          ),
                          child: TextButton(
                              onPressed: () {
                                Navigator.pushNamed(context, "/notificationDetail", arguments: snapshot.data!.events[index]);
                              },
                              child: Row(
                                children: [
                                  Text(
                                      "${snapshot.data!.events[index].classifyResult} terjadi di ${snapshot.data!.events[index].region}",
                                      style: TextStyle(
                                        color: Colors.white,
                                      )
                                  ),
                                  Spacer(),
                                  Icon(
                                    Icons.arrow_forward_ios,
                                    color: Colors.white,
                                  )
                                ],
                              )
                          ),
                        );
                      }
                  )
                ]
              );
            }
            return LoadingPage();

          }
      ),
    );
  }
}

import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/data/model/device_list_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/other_page/error_page.dart';
import 'package:jagawana_android_app/ui/other_page/loading_page.dart';


class DeviceListPage extends StatefulWidget {
  const DeviceListPage({Key? key}) : super(key: key);

  @override
  _DeviceListPageState createState() => _DeviceListPageState();
}

class _DeviceListPageState extends State<DeviceListPage> {

  final Future<List<DeviceList>> _listDevice = MyRepository.fetchDeviceList();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(
            "Device",
            style: TextStyle(
              fontFamily: "Rancho",
            ),
          ),
        ),
        body: FutureBuilder<List<DeviceList>>(
            future: _listDevice,
            builder: (context, snapshot){
              if (snapshot.hasError && MyRepository.listOfDevice == null) {
                return ErrorPage();
              }
              if (snapshot.connectionState == ConnectionState.done || MyRepository.listOfDevice != null) {
                return ListView.builder(
                    itemCount: MyRepository.listOfDevice!.length,
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
                            Navigator.pushNamed(context, "/deviceDetail", arguments: MyRepository.listOfDevice![index].idDevice);
                          },
                          child: Row(
                            children: [
                              Text(
                                  MyRepository.listOfDevice![index].idDevice,
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
                );
              }
              return LoadingPage();
            }
        )
    );
  }
}

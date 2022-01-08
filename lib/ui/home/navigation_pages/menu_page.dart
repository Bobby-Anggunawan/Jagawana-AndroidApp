import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/data/model/region_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/map/map_page.dart';
import 'package:persistent_bottom_nav_bar/persistent-tab-view.dart';

class MenuPage extends StatefulWidget {
  const MenuPage({Key? key}) : super(key: key);

  @override
  _MenuPageState createState() => _MenuPageState();
}

class _MenuPageState extends State<MenuPage> {
  @override
  Widget build(BuildContext context) {
    return ListView(
        children: [
          Container(
            padding: const EdgeInsets.only(left: 10.0, right: 10.0),
            margin: EdgeInsets.fromLTRB(Constant.PADDING_2, Constant.PADDING_1, Constant.PADDING_2, Constant.PADDING_1),
            decoration: BoxDecoration(
                color: Colors.white,
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
            child: DropdownButtonHideUnderline(
              child: DropdownButton<RegionData>(
                  isExpanded: true,
                  value: MyRepository.listRegion[MyRepository.activeRegionIndex],
                  items: MyRepository.listRegion
                      .map<DropdownMenuItem<RegionData>>((RegionData value) {
                    return DropdownMenuItem<RegionData>(
                      value: value,
                      child: Center(
                        child: Text(value.regionName),
                      ),
                    );
                  }).toList(),
                  onChanged: (value) {
                    setState(() {
                      MyRepository.activeRegion = value!;
                      for(int x = 0; x<MyRepository.listRegion.length; x++){
                        if(MyRepository.listRegion[x].regionName == value.regionName) MyRepository.activeRegionIndex = x;
                      }
                    });
                  }
              ),
            ),
          ),

          Padding(
            padding: EdgeInsets.only(left: Constant.PADDING_1, right: Constant.PADDING_1),
            child: Column(
                children: [
                  Row(
                      children: [
                        Expanded(
                            child: Padding(
                                padding: EdgeInsets.all(Constant.PADDING_1),
                                child: Container(
                                    decoration: BoxDecoration(
                                        color: Colors.white,
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
                                      onPressed: () {
                                        Constant.getMainNav(context).pushNamed("/map");
                                      },
                                      child: Container(
                                        height: 150,
                                        margin: const EdgeInsets.only(bottom: 20.0),
                                        child: Column(
                                            children: [
                                              Expanded(
                                                child: Icon(Icons.map, size: 50),
                                              ),
                                              Text("Peta"),
                                            ]
                                        ),
                                      ),
                                    )
                                )
                            )
                        ),
                      ]
                  ),

                  Row(
                      children: [
                        Expanded(
                            child: Padding(
                                padding: EdgeInsets.all(Constant.PADDING_1),
                                child: Container(
                                    decoration: BoxDecoration(
                                        color: Colors.white,
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
                                      onPressed: () {
                                        Constant.getMainNav(context).pushNamed("/sound");
                                      },
                                      child: Container(
                                        height: 150,
                                        margin: const EdgeInsets.only(bottom: 20.0),
                                        child: Column(
                                            children: [
                                              Expanded(
                                                child: Icon(Icons.graphic_eq, size: 50),
                                              ),
                                              Text("Rekaman Suara"),
                                            ]
                                        ),
                                      ),
                                    )
                                )
                            )
                        ),
                        Expanded(
                            child: Padding(
                                padding: EdgeInsets.all(Constant.PADDING_1),
                                child: Container(
                                    decoration: BoxDecoration(
                                        color: Colors.white,
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
                                      onPressed: () {
                                        Constant.getMainNav(context).pushNamed("/laporan");
                                      },
                                      child: Container(
                                        height: 150,
                                        margin: const EdgeInsets.only(bottom: 20.0),
                                        child: Column(
                                            children: [
                                              Expanded(
                                                child: Icon(Icons.summarize, size: 50),
                                              ),
                                              Text("Laporan"),
                                            ]
                                        ),
                                      ),
                                    )
                                )
                            )
                        ),
                      ]
                  )
                ]
            )
          )
        ]
    );
  }
}

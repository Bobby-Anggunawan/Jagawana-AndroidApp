import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:jagawana_android_app/data/model/agruments/device_detail_arg.dart';
import 'package:jagawana_android_app/data/model/device_list_data.dart';
import 'package:http/http.dart' as http;
import 'package:jagawana_android_app/data/model/event_data.dart';
import 'dart:convert';

import 'package:jagawana_android_app/data/model/region_data.dart';

import 'model/overview_table_data.dart';

class MyRepository{

  static int lastEventTimestamp = 0;

  static List<DeviceList>? listOfDevice = null;
  static List<RegionData> listRegion = [
    RegionData(regionName: "Papua", lat: -2.567627625222472, lgl: 137.6261178137235),
    RegionData(regionName: "Kalimantan Barat", lat: -0.23214842286908874, lgl: 110.40159087442868),
    RegionData(regionName: "Kalimantan Selatan", lat: -0.7894445216350934, lgl: 112.5200062211388),
    RegionData(regionName: "Kalimantan Tengah", lat: -0.789275, lgl: 113.921327)
  ];

  static List<EventData>? listEvent = null;

  static List<OverviewTableData>? overviewTable = null;

  static RegionData activeRegion = RegionData(regionName: "Kalimantan Tengah", lat: -0.789275, lgl: 113.921327);
  static int activeRegionIndex = 3;

  static Future<List<DeviceList>> fetchDeviceList() async {
    if(listOfDevice == null){
      final response = await http.get(Uri.parse('https://xenon-anthem-312407.et.r.appspot.com/getalldevices'));

      if (response.statusCode == 200) {
        // If the server did return a 200 OK response,
        // then parse the JSON.
        Iterable l = json.decode(response.body);

        //==============================
        var ret = List<DeviceList>.from(l.map((model)=> DeviceList.fromJson(model)));
        Map<String, RegionData> regTemp = {};

        ret.forEach((element) {
          regTemp.putIfAbsent(element.region, ()=> RegionData(regionName: element.region, lat: element.latitude, lgl: element.longitude));
        });

        listRegion =
        regTemp.entries.map( (entry) => RegionData(regionName: entry.value.regionName, lat: entry.value.lat, lgl: entry.value.lgl)).toList();
        listOfDevice = ret;
        //==============================
        return ret;
      } else {
        // If the server did not return a 200 OK response,
        // then throw an exception.
        throw Exception('Failed to load Device List');
      }
    }
    else{
      //TODO Harusnya bisa langsung return, tapi malah error kalo langsung return
      var l = json.decode(json.encode(listOfDevice));
      return List<DeviceList>.from(l.map((model)=> DeviceList.fromJson(model)));
    }
  }


  static Future<List<EventData>> fetchNotificationList() async{
    if(listEvent == null){
      final response = await http.get(Uri.parse('https://xenon-anthem-312407.et.r.appspot.com/getallresult'));

      if (response.statusCode == 200) {
        // If the server did return a 200 OK response,
        // then parse the JSON.
        Iterable l = json.decode(response.body);

        var ret = List<EventData>.from(l.map((model)=> EventData.fromJson(model)));
        listEvent = ret;

        return ret;
      }else{
        throw Exception('Failed to load Notification');
      }
    }
    else{
      var l = json.decode(json.encode(listEvent));
      return List<EventData>.from(l.map((model)=> EventData.fromJson(model)));
    }
  }

  static Future<DeviceDetailAgr> getDeviceDetail(String deviceId) async{
    if(listOfDevice == null)  await fetchDeviceList();
    if(listEvent == null) await fetchNotificationList();

    List<EventData> events = [];
    String lastTransmission = "never";
    DeviceList? device = null;

    listEvent!.forEach((element) {
      if(element.idDevice == deviceId) events.add(element);
    });
    listOfDevice!.forEach((element) {
      if(element.idDevice == deviceId) device = element;
    });
    //dengan anggapan elemen terakhir adalah transmisi terakhir
    if(events.isNotEmpty) lastTransmission = events[events.length-1].timestamp;

    return DeviceDetailAgr(
        lastTransmission: lastTransmission,
        device: device!,
        events: events
    );
  }

  static int isContain(List<OverviewTableData> list, String search){
    int index = -1;
    for(int x =0; x< list.length; x++){
      if(list[x].perangkat == search) index = x;
    }
    return index;
  }

  static Future<List<OverviewTableData>> getOverviewTable(String dateTime, bool isAll, bool isMonth) async{
    if(listEvent == null) await fetchNotificationList();

    List<OverviewTableData> ret = [];

    String activeRegionOvT = listRegion[activeRegionIndex].regionName;

    if(isAll){
      listEvent!.forEach((element) {
        if(activeRegionOvT == element.region){
          int index = isContain(ret, element.idDevice);
          if(index == -1){
            ret.add(OverviewTableData(perangkat: element.idDevice, api: 0, chainsaw:0, pistol:0));
            index = ret.length-1;
          };
          //==========
          if(element.classifyResult == "Fire") ret[index].api++;
          if(element.classifyResult == "Chainsaw") ret[index].chainsaw++;
          if(element.classifyResult == "Gun Shot") ret[index].pistol++;
        }
      });
    }
    return ret;
  }

  static Future<List<EventData>> fetchRegionHistory() async{
    if(listEvent == null) await fetchNotificationList();

    String activeRegionOvT = listRegion[activeRegionIndex].regionName;

    List<EventData> ret = [];
    listEvent!.forEach((element) {
      if(element.region == activeRegionOvT) ret.add(element);
    });

    return ret;
  }

  //todo gak bagus codenya
  static int regionDeviceCount(String regionName){
    int ret = 0;
    if(listEvent == null) return ret;
    listOfDevice!.forEach((element) {
      if (element.region == regionName) ret++;
    });
    return ret;
  }

  static int selisih(int a, int b){
    if(a>b) return a-b;
    else return b-a;
  }

  //todo selisihnya satu jam
  static int jlhBahaya(){

    var region = listRegion[MyRepository.activeRegionIndex].regionName;
    int ret = 0;
    if(listEvent == null) return ret;
    else{
      listEvent!.forEach((element) {
        if (element.region == region && selisih(DateTime.parse(element.timestamp).millisecondsSinceEpoch, lastEventTimestamp) < 3600000) ret++;
      });
    }
    return ret;
  }

  static bool isDangerDevice(String deviceName){
    var region = listRegion[MyRepository.activeRegionIndex].regionName;
    bool ret = false;
    listEvent!.forEach((element){
      if(selisih(DateTime.parse(element.timestamp).millisecondsSinceEpoch, lastEventTimestamp) < 3600000 && deviceName == element.idDevice) ret = true;
    });
    return ret;
  }

}
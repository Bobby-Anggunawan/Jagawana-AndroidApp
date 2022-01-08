import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import 'package:jagawana_android_app/data/model/device_list_data.dart';
import 'package:jagawana_android_app/data/model/region_data.dart';
import 'package:jagawana_android_app/ui/other_page/error_page.dart';
import '../../data/repository.dart';
import 'package:jagawana_android_app/ui/other_page/loading_page.dart';

class MapPage extends StatelessWidget {
  const MapPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Map",
          style: TextStyle(
            fontFamily: "Rancho",
          ),
        ),
      ),

      body: MapSample(),
    );
  }
}



//=====================================
class MapSample extends StatefulWidget {
  @override
  State<MapSample> createState() => MapSampleState();
}

class MapSampleState extends State<MapSample> {
  Completer<GoogleMapController> _controller = Completer();

  final Future<List<DeviceList>> _listDevice = MyRepository.fetchDeviceList();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<List<DeviceList>>(
        future: _listDevice,
          builder: (context, snapshot){
            if (snapshot.hasError && MyRepository.listOfDevice == null) {
              return ErrorPage();
            }
            if (snapshot.connectionState == ConnectionState.done || MyRepository.listOfDevice != null) {

              return JustMap();
            }
            return LoadingPage();
          }
      ),
    );
  }
}



class JustMap extends StatefulWidget {
  const JustMap({Key? key}) : super(key: key);

  @override
  _JustMapState createState() => _JustMapState();
}

class _JustMapState extends State<JustMap> {

  Completer<GoogleMapController> _controller = Completer();

  CameraPosition _kGooglePlex = CameraPosition(
    target: LatLng(MyRepository.listRegion[MyRepository.activeRegionIndex].lat, MyRepository.listRegion[MyRepository.activeRegionIndex].lgl),
  );

  //===================
  Map<MarkerId, Marker> markers = <MarkerId, Marker>{};

  void initMarker(List<DeviceList> data) {
    for(int x=0; x<data.length; x++){
      final MarkerId markerId = MarkerId(data[x].idDevice);
      final Marker marker = Marker(
        markerId: markerId,
        position: LatLng(
          data[x].latitude, data[x].longitude,
        ),
        infoWindow: InfoWindow(title: data[x].idDevice, snippet: '*'),
      );
      markers[markerId] = marker;
    }
  }

  void updateMarker(List<DeviceList> data, RegionData region){

    markers.clear();

    for(int x=0; x<data.length; x++){
      if(data[x].region == region.regionName){
        final MarkerId markerId = MarkerId(data[x].idDevice);
        final Marker marker = Marker(
          markerId: markerId,
          position: LatLng(
            data[x].latitude, data[x].longitude,
          ),
          infoWindow: InfoWindow(title: data[x].idDevice, snippet: '*'),
        );
        markers[markerId] = marker;
      }
    }
  }

  int _selectedChip = 0;


  @override
  Widget build(BuildContext context) {

    updateMarker(MyRepository.listOfDevice!, MyRepository.listRegion[MyRepository.activeRegionIndex]);

    return Stack(
        children: [
          GoogleMap(
            mapType: MapType.hybrid,
            initialCameraPosition: _kGooglePlex,
            onMapCreated: (GoogleMapController controller) {
              _controller.complete(controller);

              moveMapCamera(MyRepository.listRegion![MyRepository.activeRegionIndex].lat, MyRepository.listRegion![MyRepository.activeRegionIndex].lgl);
            },
            myLocationEnabled: true,
            markers: Set<Marker>.of(markers.values),
          ),

          Align(
            alignment: Alignment.topCenter,
            child: Container(
                height: 35,
                child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    shrinkWrap: true,
                    itemCount: MyRepository.listRegion.length,
                    itemBuilder: (BuildContext context, int index){
                      return ChoiceChip(
                          label: Text(MyRepository.listRegion[index].regionName),
                          selected: _selectedChip == index,
                          onSelected: (bool selected) {
                            setState(() {
                              if (selected) {
                                updateMarker(MyRepository.listOfDevice!, MyRepository.listRegion![index]);
                                _selectedChip = index;

                                moveMapCamera(MyRepository.listRegion![index].lat, MyRepository.listRegion![index].lgl);
                              }
                            });
                          }
                      );
                    }
                )
            ),
          ),
        ]
    );
  }

  Future<void> moveMapCamera(double lat, double lng) async {

    CameraPosition nepPos = CameraPosition(
      target: LatLng(lat, lng),
      zoom: 5,
    );

    final GoogleMapController controller = await _controller.future;
    controller.animateCamera(CameraUpdate.newCameraPosition(nepPos));
  }
}

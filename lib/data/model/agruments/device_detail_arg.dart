import 'package:jagawana_android_app/data/model/device_list_data.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';

class DeviceDetailAgr{
  final String lastTransmission;
  final String Status = "Aktif"; //entah lah

  final DeviceList device;
  final List<EventData> events;

  DeviceDetailAgr({
    required this.lastTransmission,
    required this.device,
    required this.events
  });
}
class DeviceList {
  final String idDevice;
  final int idRegion;
  final double latitude;
  final double longitude;
  final String region;

  DeviceList({
    required this.idDevice,
    required this.idRegion,
    required this.latitude,
    required this.longitude,
    required this.region,
  });

  factory DeviceList.fromJson(Map<String, dynamic> json) {
    return DeviceList(
      idDevice: json['idDevice'],
      idRegion: json['idRegion'],
      latitude: json['latitude'],
      longitude: json['longitude'],
      region: json['region'],
    );
  }
}
class EventData{
  final String idClip;
  final String idAudioFile;
  final int startInterval;
  final int endInterval;
  final String idDevice;
  final String region;
  final double longitude;
  final double latitude;
  final String timestamp;
  final String classifyResult;
  final String link;

  EventData({
    required this.idClip,
    required this.idAudioFile,
    required this.startInterval,
    required this.endInterval,
    required this.idDevice,
    required this.region,
    required this.longitude,
    required this.latitude,
    required this.timestamp,
    required this.classifyResult,
    required this.link
  });

  factory EventData.fromJson(Map<String, dynamic> json) {
    return EventData(
      idClip: json['idClip'],
      idAudioFile: json['idAudioFile'],
      startInterval: json['startInterval'],
      endInterval: json['endInterval'],
      idDevice: json['idDevice'],
      region: json['region'],
      longitude: json['longitude'],
      latitude: json['latitude'],
      timestamp: json['timestamp'],
      classifyResult: json['classifyResult'],
      link: json['link'],
    );
  }
}
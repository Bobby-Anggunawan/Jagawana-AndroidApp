import 'package:flutter/material.dart';

class TileData {
  final IconData icon;
  final String title;
  final int lebar;
  final int tinggi;
  final String path; //untuk navigasi

  const TileData(this.icon, this.title, this.lebar, this.tinggi, this.path);
}
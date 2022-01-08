import 'package:flutter/material.dart';
import 'package:loading_indicator/loading_indicator.dart';

class LoadingPage extends StatelessWidget {
  const LoadingPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return LoadingIndicator(
        indicatorType: Indicator.pacman, /// Required, The loading type of the widget
    );
  }
}

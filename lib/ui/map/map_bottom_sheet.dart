import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/ui/map/bottom_sheet/forest_history.dart';
import 'dart:ui';

import 'package:jagawana_android_app/ui/map/bottom_sheet/forest_status.dart';

class MyBottomSheet extends StatefulWidget {
  const MyBottomSheet({Key? key}) : super(key: key);

  @override
  _MyBottomSheetState createState() => _MyBottomSheetState();
}

class _MyBottomSheetState extends State<MyBottomSheet> with SingleTickerProviderStateMixin {

  Duration animationDuration = Duration(milliseconds: 500);

  AnimationController? _animationController;

  double _capturedValue = 0.0;
  Offset? _capturedOffset;

  @override
  void initState() {
    super.initState();

    _animationController = AnimationController(
      vsync: this,
      value: 0.0,
      duration: animationDuration,
    );
  }

  @override
  Widget build(BuildContext context) {

    //================
    double collapsingHeight = 107;
    Size screenSize = MediaQuery.of(context).size;
    //================

    return AnimatedBuilder(
      animation: _animationController!,
      builder: (context, child) {
        return ClipRRect(
          borderRadius: BorderRadiusTween(
            begin: BorderRadius.only(
              topLeft: Radius.circular(30),
              topRight: Radius.circular(30),
            ),
            end: BorderRadius.zero,
          ).evaluate(_animationController!),
          child: GestureDetector(
            behavior: HitTestBehavior.opaque,
            onVerticalDragStart: _handleVerticalDragStart,
            onVerticalDragUpdate: _handleVerticalDragUpdate,
            onVerticalDragEnd: _handleVerticalDragEnd,
            child: Material(
              child: Container(
                  color: My_Color_Scheme.MAIN_FOREGROUND,
                  height: Tween<double>(
                    begin: collapsingHeight,
                    end: screenSize.height - 20,
                  ).animate(_animationController!).value,
                  child: Stack(
                      children: [
                        //todo HEADER
                        //butuh single child scrool view untuk mengabaikan child kalau overflow
                        SingleChildScrollView(
                          physics: NeverScrollableScrollPhysics(),
                          child: Opacity(
                              opacity: Tween<double>(
                                  begin: 1,
                                  end: 0
                              ).animate(_animationController!).value,
                              child: ForestStatus()
                          )
                        ),
                        //todo HIDDEN CONTENT
                        Opacity(
                            opacity: Tween<double>(
                                begin: 0,
                                end: 1
                            ).animate(_animationController!).value,
                            child: ForestHistory()
                        )
                      ]
                  )
              ),
            ),
          ),
        );
      },
    );
  }

  //============================================================================
  void _handleVerticalDragStart(DragStartDetails details) {
    _capturedOffset = details.globalPosition;
  }

  void _handleVerticalDragUpdate(DragUpdateDetails details) {
    if (_capturedOffset == null) return;

    final screenSize = MediaQuery.of(context).size;

    final diff = _capturedOffset! - details.globalPosition;

    _animationController!.value = _capturedValue + diff.dy / screenSize.height;
  }

  void _handleVerticalDragEnd(DragEndDetails details) {
    if (_capturedOffset == null) return;

    _capturedOffset = null;

    if (_animationController!.value > 0.5) {
      _handleOpen();
    } else {
      _handleClose();
    }
  }

  void _handleOpen() {
    _capturedValue = 1.0;
    _animationController!.animateTo(
      1.0,
      duration: animationDuration,
      curve: Curves.fastOutSlowIn,
    );
  }

  void _handleClose() {
    _capturedValue = 0.0;
    _animationController!.animateTo(
      0.0,
      duration: animationDuration,
      curve: Curves.fastOutSlowIn,
    );
  }

  @override
  void dispose() {
    _animationController!.dispose();

    super.dispose();
  }
}

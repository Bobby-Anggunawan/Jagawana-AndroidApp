import 'package:flutter/material.dart';

class SplashPage extends StatefulWidget {
  const SplashPage({Key? key}) : super(key: key);

  @override
  _SplashPageState createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> with TickerProviderStateMixin{
  @override
  Widget build(BuildContext context) {

    AnimationController _controller = AnimationController(
      duration: const Duration(seconds: 2),
      vsync: this,
    )..repeat(reverse: true);
    Animation<double> _animation = CurvedAnimation(
      parent: _controller,
      curve: Curves.bounceIn,
    );

    return Scaffold(
      body: Container(
          decoration: BoxDecoration(
              color: Color(0xFF0C9448)
          ),
          child: Center(
              child: ScaleTransition(
                  scale: _animation,
                  child: Image.asset("images/jagawana_logo.jpeg")
              )
          )
      ),
    );
  }
}

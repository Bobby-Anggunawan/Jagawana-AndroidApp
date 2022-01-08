import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/ui/home/home_page.dart';
import 'package:jagawana_android_app/ui/login/login_page.dart';

class Mediator extends StatelessWidget {
  const Mediator({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    var currentUser = FirebaseAuth.instance.currentUser;

    //isi main navigator(karena button nav pake navigator sendiri, navigtor parrent gabisa diakses)
    Constant.MAIN_NAV =  Navigator.of(context);

    if(currentUser == null){
      return LoginPage();
    }
    else{
      return HomePage();
    }
  }
}

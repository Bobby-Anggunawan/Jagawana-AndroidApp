import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';

//TODO: Belum masukin agrument
class Sidebar extends StatelessWidget {
  const Sidebar({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    var currentUser = FirebaseAuth.instance.currentUser;

    return Drawer(
      child: ListView(
        children: [
          //pakai SizedBox di dalam center emang harus karena kalo enggak width imagenya dipaksa jadi maksimal ikut parrent https://api.flutter.dev/flutter/widgets/SizedBox-class.html
          Center(
            child: SizedBox(
              width: 100,
              height: 100,
              child: ClipOval(
                child: Image.network(currentUser!.photoURL.toString(),
                  width: 100,
                  height: 100,
                  fit: BoxFit.fill,
              )
              ),
            ),
          ),
          Text(currentUser!.displayName.toString(), style: TextStyle(fontSize: 25.0), textAlign: TextAlign.center,),
          Divider(
            height: 1,
            thickness: 1,
          ),
          ListTile(
            leading: Icon(Icons.logout),
            title: Text('Logout'),
            onTap: (){
              FirebaseAuth.instance.signOut().then((value) => Navigator.pushNamed(context, "/login",));
            },
          ),
        ]
      )
    );
  }
}

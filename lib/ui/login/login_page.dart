import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';



class LoginPage extends StatelessWidget {
  const LoginPage({Key? key}) : super(key: key);

  //==============================================
  Future<UserCredential> signInWithGoogle() async {
    // Trigger the authentication flow
    final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();

    // Obtain the auth details from the request
    final GoogleSignInAuthentication? googleAuth = await googleUser?.authentication;

    // Create a new credential
    final credential = GoogleAuthProvider.credential(
      accessToken: googleAuth?.accessToken,
      idToken: googleAuth?.idToken,
    );

    // Once signed in, return the UserCredential
    return await FirebaseAuth.instance.signInWithCredential(credential);
  }
  //===============================================

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Login",
          style: TextStyle(
            fontFamily: "Rancho",
          ),
        ),
      ),

      body: Center(
        child: MaterialButton(
          child: Text("Login"),
          onPressed: (){
            signInWithGoogle().then((value) => Navigator.pushNamed(context, "/home",));
          },
        )
      ),
    );
  }
}
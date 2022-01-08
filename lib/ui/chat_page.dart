import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:jagawana_android_app/data/model/a_messages_data.dart';


class ChatPage extends StatefulWidget {

  const ChatPage({Key? key}) : super(key: key);

  @override
  _ChatPageState createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {

  FirebaseFirestore firestore = FirebaseFirestore.instance;

  @override
  Widget build(BuildContext context) {

    CollectionReference users = FirebaseFirestore.instance.collection('chat');

    final myTextField = TextEditingController();

    Future<void> writeMessage(String message, String senderUID, String receiverUID) {
      DateTime waktu = DateTime.now();
      return users
          .add({
            'sender': senderUID,
            'receiver': receiverUID,
            'message': message,
            'time': waktu.millisecondsSinceEpoch,
            'timeZone': waktu.timeZoneName
          })
          //Todo: buat pesan error jika data tidak berhasil dikirim
          .then((value) => print("User Added"))
          .catchError((error) => print("Failed to add user: $error"));
    }

    List<AMessagesData> messages = [
      AMessagesData("saya kirim 1Asli", MessageSender.Me, DateTime(2021, 10, 26, 12, 30, 1, 1, 1)),
      AMessagesData("saya kirim 2Asli", MessageSender.Me, DateTime(2021, 10, 26, 12, 31, 1, 1, 1)),
      AMessagesData("dia kirim 1Asli", MessageSender.Other, DateTime(2021, 10, 26, 15, 43, 1, 1, 1)),
      AMessagesData("saya kirim 3Asli", MessageSender.Me, DateTime(2021, 10, 27, 9, 12, 1, 1, 1)),
      AMessagesData("dia kirim 2Asli", MessageSender.Other, DateTime(2021, 10, 28, 20, 15, 1, 1, 1)),

      AMessagesData("saya kirim 1", MessageSender.Me, DateTime(2021, 10, 26, 12, 30, 1, 1, 1)),
      AMessagesData("saya kirim 2", MessageSender.Me, DateTime(2021, 10, 26, 12, 31, 1, 1, 1)),
      AMessagesData("dia kirim 1", MessageSender.Other, DateTime(2021, 10, 26, 15, 43, 1, 1, 1)),
      AMessagesData("saya kirim 3", MessageSender.Me, DateTime(2021, 10, 27, 9, 12, 1, 1, 1)),
      AMessagesData("dia kirim 2", MessageSender.Other, DateTime(2021, 10, 28, 20, 15, 1, 1, 1)),
      AMessagesData("saya kirim 1", MessageSender.Me, DateTime(2021, 10, 26, 12, 30, 1, 1, 1)),
      AMessagesData("saya kirim 2", MessageSender.Me, DateTime(2021, 10, 26, 12, 31, 1, 1, 1)),
      AMessagesData("dia kirim 1", MessageSender.Other, DateTime(2021, 10, 26, 15, 43, 1, 1, 1)),
      AMessagesData("saya kirim 3", MessageSender.Me, DateTime(2021, 10, 27, 9, 12, 1, 1, 1)),
      AMessagesData("dia kirim 2", MessageSender.Other, DateTime(2021, 10, 28, 20, 15, 1, 1, 1)),
      AMessagesData("saya kirim 1", MessageSender.Me, DateTime(2021, 10, 26, 12, 30, 1, 1, 1)),
      AMessagesData("saya kirim 2", MessageSender.Me, DateTime(2021, 10, 26, 12, 31, 1, 1, 1)),
      AMessagesData("dia kirim 1", MessageSender.Other, DateTime(2021, 10, 26, 15, 43, 1, 1, 1)),
      AMessagesData("saya kirim 3", MessageSender.Me, DateTime(2021, 10, 27, 9, 12, 1, 1, 1)),
      AMessagesData("dia kirim 2", MessageSender.Other, DateTime(2021, 10, 28, 20, 15, 1, 1, 1)),
      AMessagesData("saya kirim 1", MessageSender.Me, DateTime(2021, 10, 26, 12, 30, 1, 1, 1)),
      AMessagesData("saya kirim 2", MessageSender.Me, DateTime(2021, 10, 26, 12, 31, 1, 1, 1)),
      AMessagesData("dia kirim 1", MessageSender.Other, DateTime(2021, 10, 26, 15, 43, 1, 1, 1)),
      AMessagesData("saya kirim 3", MessageSender.Me, DateTime(2021, 10, 27, 9, 12, 1, 1, 1)),
      AMessagesData("dia kirim 2", MessageSender.Other, DateTime(2021, 10, 28, 20, 15, 1, 1, 1)),
      AMessagesData("saya kirim 1", MessageSender.Me, DateTime(2021, 10, 26, 12, 30, 1, 1, 1)),
      AMessagesData("saya kirim 2", MessageSender.Me, DateTime(2021, 10, 26, 12, 31, 1, 1, 1)),
      AMessagesData("dia kirim 1", MessageSender.Other, DateTime(2021, 10, 26, 15, 43, 1, 1, 1)),
      AMessagesData("saya kirim 3", MessageSender.Me, DateTime(2021, 10, 27, 9, 12, 1, 1, 1)),
      AMessagesData("dia kirim 2", MessageSender.Other, DateTime(2021, 10, 28, 20, 15, 1, 1, 1)),
      AMessagesData("saya kirim 1", MessageSender.Me, DateTime(2021, 10, 26, 12, 30, 1, 1, 1)),
      AMessagesData("saya kirim 2", MessageSender.Me, DateTime(2021, 10, 26, 12, 31, 1, 1, 1)),
      AMessagesData("dia kirim 1", MessageSender.Other, DateTime(2021, 10, 26, 15, 43, 1, 1, 1)),
      AMessagesData("saya kirim 3", MessageSender.Me, DateTime(2021, 10, 27, 9, 12, 1, 1, 1)),
      AMessagesData("dia kirim 2", MessageSender.Other, DateTime(2021, 10, 28, 20, 15, 1, 1, 1)),
    ];

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Chat",
          style: TextStyle(
            fontFamily: "Rancho",
          ),
        ),
      ),

      body: Stack(
        children: <Widget>[
          ListView.builder(
            itemCount: messages.length,
            shrinkWrap: true,
            padding: EdgeInsets.only(top: 10,bottom: 10),
            physics: BouncingScrollPhysics(),
            itemBuilder: (context, index){
              return Container(
                padding: EdgeInsets.only(left: 16,right: 16,top: 10,bottom: 10),
                child: Align(
                  alignment: (messages[index].sender == MessageSender.Other ?Alignment.topLeft:Alignment.topRight),
                  child: Container(
                  decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(20),
                  color: (messages[index].sender  == MessageSender.Other ? Colors.grey.shade200:Colors.blue[200]),
                  ),
                  padding: EdgeInsets.all(16),
                  child: Text(messages[index].messages, style: TextStyle(fontSize: 15),),
                ),
              ));
            },
          ),
          Align(
            alignment: Alignment.bottomLeft,
            child: Container(
              padding: EdgeInsets.only(left: 10,bottom: 10,top: 10),
              height: 60,
              width: double.infinity,
              color: Colors.white,
              child: Row(
                children: <Widget>[
                  GestureDetector(
                    onTap: (){
                    },
                    child: Container(
                      height: 30,
                      width: 30,
                      decoration: BoxDecoration(
                        color: Colors.lightBlue,
                        borderRadius: BorderRadius.circular(30),
                      ),
                      child: Icon(Icons.add, color: Colors.white, size: 20, ),
                    ),
                  ),
                  SizedBox(width: 15,),
                  Expanded(
                    child: TextField(
                      controller: myTextField,
                      decoration: InputDecoration(
                          hintText: "Write message...",
                          hintStyle: TextStyle(color: Colors.black54),
                          border: InputBorder.none
                      ),
                    ),
                  ),
                  SizedBox(width: 15,),
                  FloatingActionButton(
                    onPressed: (){
                      String pesan = myTextField.text;
                      myTextField.clear();
                      writeMessage(pesan, '8Yc2rjbdzLOs769vpjIAsdyaKF63', 'l8Fqi5nkqfcRj4NwDfLch3yCMW63');
                    },
                    child: Icon(Icons.send,color: Colors.white,size: 18,),
                    backgroundColor: Colors.blue,
                    elevation: 0,
                  ),
                ],

              ),
            ),
          ),
        ],
      ),
    );
  }
}

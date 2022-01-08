import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jagawana_android_app/constant.dart';
import 'package:jagawana_android_app/ui/home/navigation_pages/menu_page.dart';
import 'package:jagawana_android_app/ui/home/navigation_pages/notification_page.dart';
import 'package:jagawana_android_app/ui/home/quote.dart';
import 'sidebar.dart';
import 'staggered_menu.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:persistent_bottom_nav_bar/persistent-tab-view.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {

    var currentUser = FirebaseAuth.instance.currentUser;
    final GlobalKey<ScaffoldState> _key = GlobalKey();

    String tabTitle = "Jagawana";
    PersistentTabController _controller = PersistentTabController(initialIndex: 0);
    List<Widget> navigationPages = [
      MenuPage(),
      NotificationPage()
    ];

    return Scaffold(
        key: _key,
        appBar: AppBar(
            title: Text(tabTitle),
            actions: [
              Padding(
                padding: EdgeInsets.symmetric(horizontal: 16),
                child: IconButton(
                  icon: Icon(Icons.support_agent),
                  onPressed: (){
                    Constant.getMainNav(context).pushNamed("/test");
                  }
                ),
              ),
            ]
        ),
        body: PersistentTabView(
            context,
            controller: _controller,
            screens: navigationPages,
            navBarStyle: NavBarStyle.style9,
            backgroundColor: My_Color_Scheme.MAIN_COLOR,
            items: [
              PersistentBottomNavBarItem(
                icon: Icon(CupertinoIcons.home),
                title: ("Home"),
                activeColorPrimary: My_Color_Scheme.MAIN_FOREGROUND,
                inactiveColorPrimary: My_Color_Scheme.MAIN_FOREGROUND,
              ),
              PersistentBottomNavBarItem(
                icon: Icon(CupertinoIcons.settings),
                title: ("Settings"),
                activeColorPrimary: My_Color_Scheme.MAIN_FOREGROUND,
                inactiveColorPrimary: My_Color_Scheme.MAIN_FOREGROUND,
              ),
            ]
        )
    );
  }
}

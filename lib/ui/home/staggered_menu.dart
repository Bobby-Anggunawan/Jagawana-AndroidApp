import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import '../../data/model/tile_data.dart';
import '../../constant.dart';


class StaggredMenu extends StatelessWidget {
  const StaggredMenu({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    //========================================
    List<TileData> tileData =  [TileData(Icons.streetview, "Map", 1, 1, "/map"),
      TileData(Icons.settings_remote, "Devices", 1, 1, "/device"),
      TileData(Icons.notifications, "Notifications", 1, 1, "/notification"),
      TileData(Icons.contacts, "Contacts", 2, 1, "/login"),
      TileData(Icons.duo, "Video Call", 1, 2, "/login"),
      TileData(Icons.forum, "Chat", 2, 2, "/chat"),
      TileData(Icons.tune, "Setting", 1, 1, "/login")
    ];
    //========================================


    return Padding(
      padding: EdgeInsets.fromLTRB(Constant.PADDING_2, 0, Constant.PADDING_2, 0),
      child: StaggeredGridView.countBuilder(
        shrinkWrap: true,
        physics: ClampingScrollPhysics(),
        crossAxisCount: 3,
        itemCount:7,
        itemBuilder: (BuildContext context, int index) => TextButton(
            child: Center(
              child: Icon(tileData[index].icon),
            ),
            onPressed: (){
              Navigator.pushNamed(context, tileData[index].path,);
            },
            style: ButtonStyle(
              backgroundColor: MaterialStateProperty.all(Colors.green),
              foregroundColor: MaterialStateProperty.all(Colors.white),
                overlayColor: MaterialStateProperty.all(Colors.black12)
            )
        ),
        staggeredTileBuilder: (int index) => StaggeredTile.count(tileData[index].lebar, tileData[index].tinggi.toDouble()),
        mainAxisSpacing: 4.0,
        crossAxisSpacing: 4.0,
      ),
    );
  }
}

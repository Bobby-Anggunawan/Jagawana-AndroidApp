import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jagawana_android_app/data/model/event_data.dart';
import 'package:jagawana_android_app/data/model/overview_table_data.dart';
import 'package:jagawana_android_app/data/repository.dart';
import 'package:jagawana_android_app/ui/items/notificarion_items.dart';
import 'package:jagawana_android_app/ui/items/sound_item.dart';
import 'package:jagawana_android_app/ui/other_page/error_page.dart';
import 'package:jagawana_android_app/ui/other_page/loading_page.dart';

import '../../constant.dart';


class LaporanPage extends StatefulWidget {
  const LaporanPage({Key? key}) : super(key: key);

  @override
  _LaporanPageState createState() => _LaporanPageState();
}

class _LaporanPageState extends State<LaporanPage> {

  final Future<List<OverviewTableData>> _overviewTable = MyRepository.getOverviewTable("none", true, false);
  final Future<List<EventData>> _detailList = MyRepository.fetchRegionHistory();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Laporan")
      ),
      body: ListView(
        children: [
          Text("Overview", style: TextStyle(fontWeight: FontWeight.bold, fontSize: Constant.TEXTSIZE_BIG)),
          FutureBuilder<List<OverviewTableData>>(
            future: _overviewTable,
            builder: (context, snapshot){
              if (snapshot.hasError && MyRepository.listEvent == null) {
                return ErrorPage();
              }
              if (snapshot.connectionState == ConnectionState.done){
                return Container(
                    child: DataTable(
                      columns: const <DataColumn>[
                        DataColumn(
                          label: Text(
                            'Perangkat',
                            style: TextStyle(fontWeight: FontWeight.bold),
                          ),
                        ),
                        DataColumn(
                          label: Text(
                            'Chainsaw',
                            style: TextStyle(fontWeight: FontWeight.bold),
                          ),
                        ),
                        DataColumn(
                          label: Text(
                            'Api',
                            style: TextStyle(fontWeight: FontWeight.bold),
                          ),
                        ),
                        DataColumn(
                          //todo collumn ini overflow(gak kelihatan)
                          label: Text(
                            'Pistol',
                            style: TextStyle(fontWeight: FontWeight.bold),
                          ),
                        ),
                      ],
                      rows: snapshot.data!.map(
                            (sale) => DataRow(
                            cells: [
                              DataCell(
                                  Text(sale.perangkat)
                              ),
                              DataCell(
                                Text(sale.chainsaw.toString()),
                              ),
                              DataCell(
                                Text(sale.api.toString()),
                              ),
                              DataCell(
                                Text(sale.pistol.toString()),
                              ),
                            ]),
                      ).toList(),
                    )
                );
              }
              return Text("Loading..");
            }
          ),
          Text("Detail", style: TextStyle(fontWeight: FontWeight.bold, fontSize: Constant.TEXTSIZE_BIG)),

          FutureBuilder<List<EventData>>(
            future: _detailList,
            builder: (context, snapshot){
              if (snapshot.hasError && snapshot.data == null) {
                return ErrorPage();
              }
              if (snapshot.connectionState == ConnectionState.done || snapshot.data != null){
                return ListView.builder(
                    shrinkWrap: true,
                    physics: NeverScrollableScrollPhysics(),
                    itemCount: snapshot.data!.length,
                    itemBuilder: (BuildContext context, int index){
                      return SoundItem(data: snapshot.data![index], includePlayButton: false, isFirstItem: (index == 0)? true:false);
                    }
                );
              }
              return Text("Loading..");
            }
          ),

        ]
      )
    );
  }
}

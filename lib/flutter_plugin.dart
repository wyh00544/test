import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter/material.dart';



class MyPlugin {


  static const myPlugin = const MethodChannel('com.example.flutter_plugin/plugin');



  static Future<String> add(int x, int y) async {
    Map<String, Object> map = {"x": x, "y": y};
    final String sum = await myPlugin.invokeMethod('add',map);
    print("wangyahui---"+sum);
    return sum;
  }

}

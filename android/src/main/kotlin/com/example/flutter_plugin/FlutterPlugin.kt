
import io.flutter.plugin.common.PluginRegistry.Registrar
import android.app.Activity
import io.flutter.plugins.GeneratedPluginRegistrant
import android.os.Bundle

import android.app.Activity
import android.os.Bundle
import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.*

import io.flutter.plugin.common.PluginRegistry.Registrar
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
  protected fun onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)
    MyFlutterPlugin.registerWith(this)
    val eventPlugin = MyFlutterEventPlugin.registerWith(this)
    Thread(Runnable {
      var counter = 0
      while (true) {
        try {
          if (eventPlugin.eventSink != null) {
            eventPlugin.eventSink!!.success(counter++)
          }
          Thread.sleep(1000)
        } catch (e: InterruptedException) {
          e.printStackTrace()
          eventPlugin.eventSink!!.error("计时器异常", "计时器异常", e.message)
        }

      }
    }).start()
  }
}

internal class MyFlutterPlugin(private val activity: Activity) : MethodChannel.MethodCallHandler {
  override fun onMethodCall(methodCall: MethodCall, result: MethodChannel.Result) {
    if (methodCall.method == "getUser") {
      val userId = methodCall.argument<Int>("userId")
      val mockUser = String.format("{\"name\":\"Wiki\",\"id\":%s}", userId)
      result.success(mockUser)
    }
  }

  companion object {
    fun registerWith(registry: PluginRegistry) {
      val CHANNEL = "com.example.flutter_app/plugin"
      val registrar = registry.registrarFor(CHANNEL)
      val methodChannel = MethodChannel(registrar.messenger(), CHANNEL)
      val myFlutterPlugin = MyFlutterPlugin(registrar.activity())
      methodChannel.setMethodCallHandler(myFlutterPlugin)
    }
  }
}

internal class MyFlutterEventPlugin : EventChannel.StreamHandler {
  var eventSink: EventChannel.EventSink? = null
  override fun onListen(o: Any, eventSink: EventChannel.EventSink) {
    this.eventSink = eventSink
  }

  override fun onCancel(o: Any) {}

  companion object {

    fun registerWith(registry: PluginRegistry): MyFlutterEventPlugin {
      val CHANNEL = "com.example.flutter_app/event_plugin"
      val registrar = registry.registrarFor(CHANNEL)
      val eventChannel = EventChannel(registrar.messenger(), CHANNEL)
      val myFlutterEventPlugin = MyFlutterEventPlugin()
      eventChannel.setStreamHandler(myFlutterEventPlugin)
      return myFlutterEventPlugin
    }
  }
}

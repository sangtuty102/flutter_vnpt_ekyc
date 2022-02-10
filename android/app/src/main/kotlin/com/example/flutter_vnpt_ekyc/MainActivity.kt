package com.example.flutter_vnpt_ekyc

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.vnptit.idg.sdk.activity.VnptIdentityActivity
import com.vnptit.idg.sdk.utils.KeyIntentConstants.*
import com.vnptit.idg.sdk.utils.KeyResultConstants.*
import com.vnptit.idg.sdk.utils.SDKEnum
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterFragmentActivity() {
    private val _chanelEKYC: String = "com.flutter.ekyc_vnpt_sangth"
    private var flutterResult: MethodChannel.Result? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, _chanelEKYC).setMethodCallHandler { call, result ->
            flutterResult = result

            if (call.method == "getInformationCard") {
                openEKYC(call)
            } else {
                result.notImplemented()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null) {

            if (resultCode == Activity.RESULT_OK) {
                val strDataInfo = data!!.getStringExtra(INFO_RESULT)
                val imageFront = data!!.getStringExtra(FRONT_IMAGE)
                val imageRear = data!!.getStringExtra(REAR_IMAGE)
                val imagePortrait = data!!.getStringExtra(PORTRAIT_IMAGE)
//                val imageNearPortrait = data!!.getStringExtra(PORTRAIT_NEAR_IMAGE)
//                val imageFarPortrait = data!!.getStringExtra(PORTRAIT_FAR_IMAGE)
                val netWorkProblem = data!!.getStringExtra(NETWORK_PROBLEM)
                val jsonCompareFace = data!!.getStringExtra(COMPARE_RESULT)
                val jsonLivenessFace = data!!.getStringExtra(LIVENESS_RESULT)
                val jsonCheckMask = data!!.getStringExtra(MASKED_FACE_RESULT)
                val verifyFace = data!!.getStringExtra(ENCODE_RESULT)
                val jsonLivenessFontCard = data!!.getStringExtra(LIVENESS_CARD_FRONT_RESULT)
                val jsonLivenessRearCard = data!!.getStringExtra(LIVENESS_CARD_REAR_RESULT)

                val data: HashMap<String, Any?> = hashMapOf<String, Any?>()
                data["jsonInfo"] = strDataInfo
                data["imageFront"] = imageFront
                data["imageBack"] = imageRear
                data["imageFace"] = imagePortrait
//                data["imageNear"] = imageNearPortrait
//                data["imageFar"] = imageFarPortrait
                data["netWorkProblem"] = netWorkProblem
                data["jsonCompareFace"] = jsonCompareFace
                data["jsonLivenessFace"] = jsonLivenessFace
                data["jsonCheckMask"] = jsonCheckMask
                data["verifyFace"] = verifyFace
                data["jsonLivenessFontCard"] = jsonLivenessFontCard
                data["jsonLivenessRearCard"] = jsonLivenessRearCard

                flutterResult?.success(data)
            } else {
                flutterResult?.error("UNAVAILABLE", "Can't get value!", null)
            }
        } /*else {
            flutterResult?.error("UNAVAILABLE", "Can't get value!", null)
        }*/
    }

    private fun openEKYC(call: MethodCall) {
        val activity: Activity = this

        val intent = Intent(activity, VnptIdentityActivity::class.java)

//        val tokenKey: String? = call.argument("tokenKey")
//        val tokenId: String? = call.argument("tokenId")
//        val authorization: String? = call.argument("authorization")

//        if (tokenKey == null || tokenId == null || authorization == null) {
//            flutterResult?.error(
//                    "MISSING_PARAMETER", "Missing input parameters!", null
//            )
//            return
//        }

        // objCamera.unitCustomer = "test1"
        // objCamera.resourceCustomer = "VNPT"
        // objCamera.challengeCode = "INNOVATIONCENTER"

        intent.putExtra(ACCESS_TOKEN, "authorization")
        intent.putExtra(TOKEN_ID, "tokenId")
        intent.putExtra(TOKEN_KEY, "tokenKey")
//        intent.putExtra(VERSION_SDK, SDKEnum.VersionSDKEnum.STANDARD.getValue())
        intent.putExtra(SHOW_RESULT, false)
        intent.putExtra(SELECT_DOCUMENT, true)
        intent.putExtra(SHOW_DIALOG_SUPPORT, true)
        intent.putExtra(CAMERA_FOR_PORTRAIT, SDKEnum.CameraTypeEnum.FRONT.getValue())
        intent.putExtra(SHOW_SWITCH, false)
        intent.putExtra(CALL_ADD_FACE, false)
        intent.putExtra(LIVENESS_ADVANCED, true)
        intent.putExtra(CHECK_MASKED_FACE, true)
        intent.putExtra(CHECK_LIVENESS_CARD, true)
        intent.putExtra(VERSION_SDK, SDKEnum.VersionSDKEnum.ADVANCED.getValue())
//        intent.putExtra(SHOW_RESULT, true)

        startActivityForResult(intent, 1)
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(parent, name, context, attrs)
    }
}

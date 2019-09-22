package tech.takenoko.screenmirror.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import tech.takenoko.screenmirror.utils.MLog
import java.net.URI


class QRReaderModel : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentIntegrator(this).also {
            it.setPrompt("WebSocket URLを読み込みます")
            it.setOrientationLocked(false)
            it.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            MLog.info(TAG, result.contents)
            uri(URI(result.contents))
        }
        finish()
    }

    companion object {
        val TAG: String = QRReaderModel::class.java.simpleName

        private var uri: (URI) -> Unit = {}
        val run: (context: Context, (URI) -> Unit) -> Unit = { context, callback ->
            uri = callback
            context.startActivity(Intent(context, QRReaderModel::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
}

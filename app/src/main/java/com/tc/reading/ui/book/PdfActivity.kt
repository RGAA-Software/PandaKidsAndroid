package com.tc.reading.ui.book

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
//import com.rajat.pdfviewer.PdfRendererView
import com.tc.reading.R
import java.net.URL
import java.util.concurrent.Executors

//import com.zjy.pdfview.PdfView

class PdfActivity : AppCompatActivity() {
    private val TAG = "Pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        val url = "http://192.168.31.5:9988/Resources/Preset/01【绘本】牛津树 全1-9级 PDF/级别 (02)/2-02 New Trainers.pdf"
//        val url = "http://192.168.31.5:9988/Resources/Preset/test.pdf"
        val pdfView = findViewById<PDFView>(R.id.pdfView)
//        val encodeUrl = Uri.encode(url, "utf-8")
//        Log.i(TAG, "encode url: " + encodeUrl)
//        pdfView.fromUri(Uri.parse(url))
////        pdfView.fromAsset("01.Taking Care of Chase.pdf")
//            .defaultPage(0)
//            .onLoad(object : OnLoadCompleteListener {
//                override fun loadComplete(nbPages: Int) {
//                    Log.i(TAG, "nbPages: $nbPages")
//                }
//
//            })
//            .load()

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            val input = URL(url).openStream()
            handler.post {
                pdfView.fromStream(input).load()
            }
        }

//        val pdfView = findViewById<PdfRendererView>(R.id.pdfView)
//        pdfView.statusListener = object : PdfRendererView.StatusCallBack {
//            override fun onPdfLoadStart() {
//                Log.i(TAG, "OnPdfLoadStart")
//            }
//            override fun onPdfLoadProgress(progress: Int, downloadedBytes: Long, totalBytes: Long?) {
//                Log.i(TAG, "OnPdfLoadProgress: $progress, bytes: $downloadedBytes, total bytes: $totalBytes")
//            }
//            override fun onPdfLoadSuccess(absolutePath: String) {
//                Log.i(TAG, "OnPdfLoadSuccess")
//            }
//
//            override fun onError(error: Throwable) {}
//            override fun onPageChanged(currentPage: Int, totalPage: Int) {}
//        }
//        pdfView.initWithUrl(
//            url = "http://192.168.31.5:9988/Resources/Preset/01【绘本】牛津树 全1-9级 PDF/级别 (02)/2-02 New Trainers.pdf",
//            lifecycleCoroutineScope = lifecycleScope,
//            lifecycle = lifecycle
//        )

    }
}
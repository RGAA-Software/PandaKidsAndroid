package com.tc.reading.ui.book

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.tc.reading.App
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkBook
import java.io.InputStream
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PdfActivity : AppCompatActivity() {
    private val TAG = "Pdf"
    private lateinit var appCtx: AppContext
    private var book: PkBook? = null
    private lateinit var pdfView: PDFView
    private lateinit var execService: ExecutorService
    private var inputStream: InputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        appCtx = (application as App).getAppContext()
        book = intent.getSerializableExtra("book") as PkBook
        pdfView = findViewById<PDFView>(R.id.pdfView)

        execService = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        if (book != null) {
            execService.execute {
                inputStream = URL(appCtx.getBaseServerUrl() + "/" + book!!.file).openStream()
                handler.post {
                    pdfView.fromStream(inputStream).load()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (inputStream != null) {
                inputStream!!.close()
            }
            execService.shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
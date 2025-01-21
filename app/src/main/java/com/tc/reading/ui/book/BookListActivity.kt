package com.tc.reading.ui.book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tc.reading.App
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkBook
import com.tc.reading.entity.PkBookSuit
import com.tc.reading.res.BookResManager
import com.tc.reading.ui.videolist.VideoListItemDecoration
import com.tc.reading.util.ScreenUtil

class BookListActivity : AppCompatActivity() {
    private val TAG = "BookList"

    private lateinit var appCtx: AppContext
    private lateinit var bookResManager: BookResManager
    private var mainBooks: MutableList<PkBook> = mutableListOf()
    private lateinit var bookListAdapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_book_list)
        appCtx = (application as App).getAppContext()
        bookResManager = appCtx.getBookResManager()

        val videoList = findViewById<RecyclerView>(R.id.id_book_list)
        val layoutManager = GridLayoutManager(this, 4)
        videoList.layoutManager = layoutManager
        videoList.addItemDecoration(VideoListItemDecoration())
        bookListAdapter = BookListAdapter(appCtx, mainBooks)
        videoList.adapter = bookListAdapter
        bookListAdapter.onBookClickListener = object: BookListAdapter.OnBookClickListener {
            override fun onBookClicked(book: PkBook) {
                val intent = Intent(this@BookListActivity, PdfActivity::class.java)
                intent.putExtra("book", book)
                startActivity(intent)
            }
        }

        val bookSuit = intent.getSerializableExtra("bookSuit") as PkBookSuit
        bookResManager.queryBooks(bookSuit.id, 1, 20) { books ->
            if (books == null) {
                return@queryBooks
            }
            this.mainBooks.removeAll(books)
            this.mainBooks.addAll(books)
            appCtx.postUITask {
                bookListAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

}
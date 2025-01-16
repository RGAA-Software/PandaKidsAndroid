package com.tc.reading.ui.book

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simform.refresh.SSPullToRefreshLayout
import com.tc.reading.databinding.FragmentBookBinding
import com.tc.reading.entity.PkBookSuit
import com.tc.reading.ui.BaseFragment

class BookFragment() : BaseFragment() {
    private val TAG = "BookFragment"

    private var binding: FragmentBookBinding? = null
    private var handler: Handler? = null
    private var bookAdapter: BookAdapter? = null
    private var books = mutableListOf<PkBookSuit>();

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        this.binding = FragmentBookBinding.inflate(inflater, container, false)
        this.handler = Handler(Looper.getMainLooper());
        val root: View = this.binding!!.root

        homeViewModel.text.observe(viewLifecycleOwner) {

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding == null) {
            return
        }
        this.binding!!.refreshLayout.apply {
            setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT);
            setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE);
            setRefreshStyle(SSPullToRefreshLayout.RefreshStyle.NORMAL);
            setLottieAnimation("lottie_clock.json");
            setOnRefreshListener {
                handler.postDelayed({
                    setRefreshing(false);
                }, 2000)
            }
        }

        bookAdapter = BookAdapter(appContext, requireActivity(), books)
        bookAdapter!!.onItemClickListener = object: BookAdapter.OnItemClickListener {
            override fun onItemClicked(bookSuit: PkBookSuit) {
                val intent = Intent(activity, BookListActivity::class.java)
                intent.putExtra("bookSuit", bookSuit)
                requireActivity().startActivity(intent)
            }
        }

        this.binding!!.bookList.apply {
            layoutManager = GridLayoutManager(activity, 4)
            (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0) 4 else 1
                }
            }
            adapter = bookAdapter
            addItemDecoration(BookSuitItemDecoration());
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val manager = recyclerView.layoutManager as GridLayoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        if (lastVisibleItem == (books.size - 1)) {
                            Toast.makeText(activity, "Last...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        appContext.getBookResManager().queryBookSuits(1, 20) { bookSuits ->
            if (bookSuits == null) {
                return@queryBookSuits
            }
            bookSuits.forEach {
                Log.i(TAG, "bookSuit: $it")
            }

            books.removeAll(bookSuits)
            books.addAll(bookSuits)
            appContext.postUITask {
                bookAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
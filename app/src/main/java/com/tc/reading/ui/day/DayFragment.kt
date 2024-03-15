package com.tc.reading.ui.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.databinding.FragmentDayBinding
import com.tc.reading.ui.BaseFragment
import com.tc.reading.util.DateUtil

class DayFragment(appContext: AppContext) : BaseFragment(appContext) {

    private var _binding: FragmentDayBinding? = null
    private val binding get() = _binding!!
    private val daySentence = DaySentenceManager(appContext);

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentDayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // date
        val date = DateUtil.getSeparatedDate();
        binding.dateYear.text = date.year;
        binding.dateMonth.text = date.month;
        binding.dateDay.text = date.day;
        binding.refreshLayout.setColorSchemeResources(com.rajat.pdfviewer.R.color.colorPrimary,
            com.rajat.pdfviewer.R.color.colorPrimaryDark);
        binding.refreshLayout.setOnRefreshListener {
            refresh();
            appContext.postDelayUITask({
                binding.refreshLayout.isRefreshing = false;
            }, 2000);
        }

        refresh();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refresh() {
        daySentence.requestTodaySentence{ sentence ->
            appContext.postUITask{
                if (sentence.type == "shanbay") {
                    binding.shanbayAuthor.text = "--" + sentence.author;
                    binding.shanbayContent.text = sentence.content;
                    binding.shanbayTranslation.text = sentence.translation;
                    Glide.with(this)
                        .load(sentence.imageUrl)
                        .centerCrop()
                        //.placeholder(R.drawable.test_cover)
                        .into(binding.shanbayCover);
                } else if (sentence.type == "youdao") {
                    binding.youdaoAuthor.text = sentence.from;
                    binding.youdaoContent.text = sentence.content;
                    binding.youdaoTranslation.text = sentence.translation;
                    Glide.with(this)
                        .load(sentence.imageUrl)
                        .centerCrop()
                        //.placeholder(R.drawable.test_cover)
                        .into(binding.youdaoCover);
                }
            };
        };
    }
}
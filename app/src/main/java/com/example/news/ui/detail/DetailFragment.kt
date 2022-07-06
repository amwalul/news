package com.example.news.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.news.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = binding.apply {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        with(args.article) {
            Glide.with(requireContext())
                .load(urlToImage)
                .into(ivImage)

            tvTitle.text = title

            val content = buildSpannedString {
                val fixedContent = content?.run {
                    indexOfLast { it == '[' }.takeIf { it != -1 }?.let { firstIndex ->
                        removeRange(
                            firstIndex,
                            length
                        )
                    }
                }
                append(fixedContent.orEmpty())

                inSpans(object : ClickableSpan() {
                    override fun onClick(p0: View) {
                        openUrl()
                    }
                }) { append("[Selengkapnya]") }
            }
            tvContent.text = content
            tvContent.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun openUrl() {
        val uri = Uri.parse(args.article.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
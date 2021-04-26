package com.example.shoujiedemo.kotlinbook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoujiedemo.R
import com.example.shoujiedemo.kotlinbook.view.MyBannerView

class BookStoreFragment: Fragment(R.layout.book_store_fragment) {

    lateinit var bannerView: MyBannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.book_store_fragment,container,false);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bannerView = view.findViewById(R.id.my_bannerView)
        val drawableArray = intArrayOf(
                R.drawable.girl1,
                R.drawable.girl2,
                R.drawable.girl3
        )
        bannerView.addDrawable(drawableArray)
    }
}
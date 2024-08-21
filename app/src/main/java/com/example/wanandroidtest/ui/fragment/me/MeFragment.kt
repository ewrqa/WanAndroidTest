package com.example.wanandroidtest.ui.fragment.me
import android.os.Bundle
import com.example.jetpackmvvm.util.nav
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.FragmentMeBinding
import com.example.wanandroidtest.ext.jumpByLogin
import com.example.wanandroidtest.viewmodel.state.me.MeViewModel
class MeFragment :BaseFragment<MeViewModel, FragmentMeBinding>(){
    override fun initView(savedInstanceState: Bundle?) {
        myDataBinding.viewmodel=viewModel
        myDataBinding.click=ProxyCClick()
    }
    override fun layoutId(): Int {
        return R.layout.fragment_me
    }
    //设置该页面的点击事件
    inner class ProxyCClick(){
        fun  goLogin(){
            nav().jumpByLogin {  }
        }

    }
}
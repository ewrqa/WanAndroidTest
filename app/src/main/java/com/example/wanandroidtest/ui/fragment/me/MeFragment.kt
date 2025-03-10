package com.example.wanandroidtest.ui.fragment.me
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.example.jetpackmvvm.ext.parseState
import com.example.jetpackmvvm.util.nav
import com.example.wanandroidtest.R
import com.example.wanandroidtest.appViewModel
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.data.bean.IntegralBean
import com.example.wanandroidtest.databinding.FragmentMeBinding
import com.example.wanandroidtest.ext.init
import com.example.wanandroidtest.ext.jumpByLogin
import com.example.wanandroidtest.ext.showToast
import com.example.wanandroidtest.viewmodel.state.me.MeViewModel
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.util.notNull
class MeFragment :BaseFragment<MeViewModel, FragmentMeBinding>(){
    //初始化viewmodel
    private  val  meViewModel:MeViewModel by viewModels()
    private  var  rank: IntegralBean? = null
    override fun layoutId(): Int {
        return R.layout.fragment_me
    }
    override fun initView(savedInstanceState: Bundle?) {
        //使用该fragment的viewmodel与点击事件
        myDataBinding.viewmodel=viewModel
        myDataBinding.click=ProxyCClick()
        appViewModel.userInfo.value?.let { meViewModel.name.set(if(it.data.userInfo.nickname.isEmpty()) it.data.userInfo.username else it.data.userInfo.nickname) }
        myDataBinding.meSwipe.init {
            viewModel.getIntegral()
        }
    }
    override fun lazyData() {
       appViewModel.userInfo.value?.run {
           myDataBinding.meSwipe.isRefreshing=true
           viewModel.getIntegral()
       }
    }
    override fun createObserver() {
        /**
         * viewLifecyccleOwner当页面处于活跃状态的时候更新数据  started resumed
         */
        meViewModel.mutableLiveData.observe(viewLifecycleOwner, Observer{ resultState->
            myDataBinding.meSwipe.isRefreshing=false
            parseState(resultState,{
                rank=it
                meViewModel.info.set("id：${it.userId}　排名：${it.rank}")
                meViewModel.name.set(it.username)
                meViewModel.integral.set(it.coinCount)
            },{
               it.errorMsg.showToast(this@MeFragment.requireContext())
            })
        })

        appViewModel.run {
            userInfo.observeInFragment(this@MeFragment, Observer {
                it.notNull({
                    myDataBinding.meSwipe.isRefreshing = true
                    meViewModel.name.set(if (it.data.userInfo.nickname.isEmpty()) it.data.userInfo.username else it.data.userInfo.nickname)
                    meViewModel.getIntegral()
                }, {
                    meViewModel.name.set("请先登录~")
                    meViewModel.info.set("id：--　排名：--")
                    meViewModel.integral.set(0)
                })
            })
        }
    }
    //设置该页面的点击事件
    inner class ProxyCClick{
        //去登录
        fun  goLogin(){
            nav().jumpByLogin {  }
        }
        /**收藏 需先登录*/
        fun  collect(){
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_collectFragment)
//               "收藏界面".showToast(this@MeFragment.requireContext())
            }
        }
        /** 积分*/
        fun integral(){
            nav().jumpByLogin {
                "积分界面".showToast(this@MeFragment.requireContext())
            }
        }
        /** 文章*/
        fun ariticle(){
            nav().jumpByLogin {
                "文章界面".showToast(this@MeFragment.requireContext())
            }
        }
        /** 任务*/
        fun todo() {
           nav().jumpByLogin {
               "任务界面".showToast(this@MeFragment.requireContext())
           }
        }
    }
}
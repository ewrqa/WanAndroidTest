package com.example.wanandroidtest.ui.activity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseActivity
import com.example.wanandroidtest.databinding.ActivityWelcomeBinding
import com.example.wanandroidtest.ext.myStartActivity
import com.example.wanandroidtest.ui.adapter.WelcomeBannerAdapter
import com.example.wanandroidtest.util.CacheUtil
import com.example.wanandroidtest.weight.banner.WelacomeBannerViewHolder
import com.zhpan.bannerview.BannerViewPager
import me.hgj.jetpackmvvm.ext.view.gone
import me.hgj.jetpackmvvm.ext.view.visible
class WelcomActivity :BaseActivity<BaseViewModel, ActivityWelcomeBinding>() {
    //设置viewpager显示的文字
    private lateinit var mViewPager: BannerViewPager<String,WelacomeBannerViewHolder>
    private  val  viewpagerList= listOf("唱","跳","r a p")
    override fun layoutId(): Int {
        return R.layout.activity_welcome
    }
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.click= ProxyCClick()
        mViewPager = findViewById(R.id.banner_view)
        if(CacheUtil.getJoinAppFirst()){
            mDataBinding.welcomeImage.gone()
            mViewPager.apply {
                //设置 viewpager的适配器 重写 BaseBannerAdapater
                adapter= WelcomeBannerAdapter()
                //生命周期的监听
                setLifecycleRegistry(lifecycle)
                registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if(position==viewpagerList.size-1){
                            mDataBinding.welcomeJoin.visible()
                        }else{
                            mDataBinding.welcomeJoin.gone()
                        }
                    }
                })
                //显示文本
                create(viewpagerList.toList())
            }
        }else{
            //直接进入到主页
            mDataBinding.welcomeImage.visible()
            mViewPager.postDelayed({
                myStartActivity<MainActivity>(this){}
                finish()
            },300)
        }
    }
    //设置进入的点击事件
    inner  class ProxyCClick {
        fun joinMain() {
            //点击的时候认为已经进入过的app
            CacheUtil.setJoinAppFirst(false)
            //进入主页
            myStartActivity<MainActivity>(this@WelcomActivity) {}
            finish()
        }
    }

}
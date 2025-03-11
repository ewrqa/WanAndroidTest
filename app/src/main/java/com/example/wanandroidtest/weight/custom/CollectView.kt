package com.example.wanandroidtest.weight.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.VibrateUtils
import com.example.wanandroidtest.R
import com.example.wanandroidtest.util.CacheUtil
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import per.goweii.reveallayout.RevealLayout
import java.io.FileDescriptor
import java.time.format.DecimalStyle

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.custom</p>
 * <p>简述:自定义收藏状态展示</p>
 *  自定义视图继承 revealLayout动画  必须需要参数为：context,attrs属性结合，defstyle属性样式
 * @author 张凯涛
 * @date 2025/3/11
 */
class CollectView @JvmOverloads constructor(context:Context,attrs:AttributeSet?=null,defStyle: Int=0)
    :RevealLayout(context,attrs,defStyle),View.OnTouchListener
{
    private  var  onClickListener:OnCollectViewClickListener?=null
        /**
         * 初始化属性的设置
          */
    override fun initAttr(attrs: AttributeSet?) {
        super.initAttr(attrs)
            //选中时展开的状态
        setCheckWithExpand(true)
            //未选中不展开
        setUncheckWithExpand(false)
        //设置选中的样式与未选中工的样式、
        setCheckedLayoutId(R.layout.collect_view_checked)
        setUncheckedLayoutId(R.layout.collect_view_unchecked)

        setAnimDuration(300)
        setAllowRevert(true)
        setOnTouchListener(this)
    }

        /**
         * 重写按下事件
         */
    override fun onTouch(view: View, event: MotionEvent?): Boolean {
        //判断是抬起还是按下
        when(event?.action){
            MotionEvent.ACTION_UP->{
                //震动40毫米
               VibrateUtils.vibrate(40)
                if(CacheUtil.isLogin()){
                    onClickListener?.onClick(this)
                }else{
                        isChecked=true
                    nav(view).navigateAction(R.id.action_to_loginActivity)
                }
            }
        }
            return  false
    }
    /**
     * 接口回调 对外开放一个点击事件
     */
    fun  setOnCollectViewClickListener(onCollectViewClickListener: OnCollectViewClickListener){
        this.onClickListener=onCollectViewClickListener
    }
    interface  OnCollectViewClickListener{
        fun  onClick(v:CollectView)
    }

}
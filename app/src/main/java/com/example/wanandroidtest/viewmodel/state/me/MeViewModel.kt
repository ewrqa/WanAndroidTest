package com.example.wanandroidtest.viewmodel.state.me
import androidx.lifecycle.MutableLiveData
import com.example.jetpackmvvm.ResultState
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.callback.databing.StringObservable
import com.example.jetpackmvvm.ext.request
import com.example.wanandroidtest.data.bean.IntegralBean
import com.example.wanandroidtest.network.apiservice
import com.example.wanandroidtest.util.ColorUtil
import me.hgj.jetpackmvvm.callback.databind.IntObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.viewmodel.state</p>
 * <p>简述:个人中心的viewmodel</p>
 *
 * @author 张凯涛
 * @date 2024/8/3
 */
class MeViewModel : BaseViewModel(){
    var mutableLiveData =MutableLiveData<ResultState<IntegralBean>>()
    //名称
    var name=StringObservable("请先登录~")

    var integral = IntObservableField(0)
    var info = StringObservableField("id：--　排名：-")
    //随机产生图片
    var imageUrl = StringObservableField(ColorUtil.randomImage())

    //获取个人的积分以及排名
    fun getIntegral(){
        request({ apiservice.getIntegral()},mutableLiveData)
    }
}
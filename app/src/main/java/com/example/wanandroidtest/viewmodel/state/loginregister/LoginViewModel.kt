package com.example.wanandroidtest.viewmodel.state.loginregister
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.example.jetpackmvvm.ResultState
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.callback.databing.BooleanObservable
import com.example.jetpackmvvm.callback.databing.StringObservable
import com.example.jetpackmvvm.ext.request
import com.example.wanandroidtest.data.bean.LoginBean
import com.example.wanandroidtest.data.bean.UserInfoBean
import com.example.wanandroidtest.data.request.HttpRequestCoroutine
import com.example.wanandroidtest.data.request.HttpRequestManager
import com.example.wanandroidtest.network.apiservice
/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.viewmodel.state</p>
 * <p>简述:登录的viewModel</p>
 *
 * @author 张凯涛
 * @date 2024/8/4
 */
class LoginViewModel : BaseViewModel() {
    //databind 监听变化
    var username = StringObservable()
    var password = StringObservable()
    var confirmpassword = StringObservable()
    //输入框,显示的逻辑状态
    var showPwd = BooleanObservable()
    var isepassword = BooleanObservable()
    //可观察的livedata
    val mutableLiveData = MutableLiveData<ResultState<UserInfoBean>>()
    fun login(username: String, password: String) {
        request({ apiservice.login(username, password) }, mutableLiveData,
            true, "正在登陆---")
    }
    //注册
    fun registerAndLogin(username: String, password: String, repassword: String) {
        request(
            { HttpRequestCoroutine.registerAndLogin(username,password,repassword) }, mutableLiveData,
            true, "正在注册")
    }
    /**
     *  设置 登录与注册文本框逻辑
     *  ObservableInt   当输出发生改变的时候凡事绑定的都会发生改变
     */
    var clearVisible = object : ObservableInt(username) {
        override fun get(): Int {
            return if (username.get().isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
     var passwordVisible=object  :ObservableInt(password){
         override fun get(): Int {
             return if(password.get().isEmpty()){
                 View.GONE
             }else{
                 View.VISIBLE
             }
         }
     }
    var repasswordVisible=object  :ObservableInt(confirmpassword){
        override fun get(): Int {
            return if(confirmpassword.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }


    //    var observableInt = ObservableInt(username)
//    fun  sdfs():Int{
//        if(username.get().isEmpty()){
//            observableInt.set(View.GONE)
//        }else{
//            observableInt.set(View.VISIBLE)
//        }
//        Log.e("JetpackMvvm","$observableInt.get()")
//        return   observableInt.get()
//    }
}
package com.example.wanandroidtest.ui.activity.loginregister
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.jetpackmvvm.ext.parseState
import com.example.wanandroidtest.R
import com.example.wanandroidtest.appViewModel
import com.example.wanandroidtest.base.BaseActivity
import com.example.wanandroidtest.databinding.ActivityLoginBinding
import com.example.wanandroidtest.ext.myStartActivity
import com.example.wanandroidtest.ext.showMessage
import com.example.wanandroidtest.ext.showToast
import com.example.wanandroidtest.util.CacheUtil
import com.example.wanandroidtest.viewmodel.state.loginregister.LoginViewModel
/**
 * 用户登录页面与注册页面
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun layoutId(): Int {
        return R.layout.activity_login
    }
    override fun initView(savedInstanceState: Bundle?) {
        //进行关联 初始化 viewmodel databing
        addLoadingObserve(loginViewModel)
        mDataBinding.viewmodel=viewModel
        mDataBinding.click=ProxyCClick()

        mDataBinding.loginToolbar.toolbar.apply {
            title="登录"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener{
                finish()
            }
        }
    }
    override fun createObserver() {
        loginViewModel.mutableLiveData.observe(this, Observer { resultState ->
            parseState(resultState, {
           //记录登录过后的状态=
                CacheUtil.setLogin(true)
                CacheUtil.setUser(it)
                appViewModel.userInfo.value=it
                finish()
            }, {
                Log.e("re","失败")
                showMessage(it.errorMsg)
            })
        })
    }
    inner  class  ProxyCClick(){
        /**
         * 登录
         */
        fun  login(){
            when{
                viewModel.username.get().isEmpty()->"请输入账号".showToast(this@LoginActivity)
                viewModel.password.get().isEmpty()->"请输入密码".showToast(this@LoginActivity)
                else -> viewModel.login(
                    viewModel.username.get(),
                    viewModel.password.get()
                )
            }
        }
        /**
         * 注册
         */
        fun  goRegister(){
            myStartActivity<RegisterActivity>(this@LoginActivity){}
            finish()
        }
        //清空
        fun clear(){
            viewModel.username.set("")
        }
        val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
                viewModel.showPwd.set(isChecked)
        }
    }
}
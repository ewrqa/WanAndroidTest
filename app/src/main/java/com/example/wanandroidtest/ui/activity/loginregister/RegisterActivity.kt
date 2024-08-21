package com.example.wanandroidtest.ui.activity.loginregister

import android.os.Bundle
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import com.example.jetpackmvvm.ext.parseState
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseActivity
import com.example.wanandroidtest.databinding.ActivityRegisterBinding
import com.example.wanandroidtest.ext.showMessage
import com.example.wanandroidtest.ext.showToast
import com.example.wanandroidtest.viewmodel.state.loginregister.LoginViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.activity.loginregister</p>
 * <p>简述:注册的activit</p>
 * @author 张凯涛
 * @date 2024/8/4
 */
class RegisterActivity :BaseActivity<LoginViewModel,ActivityRegisterBinding>(){
    override fun layoutId(): Int {
        return  R.layout.activity_register
    }
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.click=ProxyCClick()
        mDataBinding.viewmodel=viewModel

        mDataBinding.regisToolbar.toolbar.apply {
            title="注册"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener{
                finish()
            }
        }
    }
    override fun createObserver() {
            viewModel.mutableLiveData.observe(this, Observer {
                resultState->
                parseState(resultState,{
                        finish()
                },{
                    showMessage(it.errorMsg)
                })
            })
    }
    inner class  ProxyCClick(){
        /**
         * 登录
         */
        fun  register(){
            when{
                viewModel.username.get().isEmpty()->"请输入账号".showToast(this@RegisterActivity)
                viewModel.password.get().isEmpty()->"请输入密码".showToast(this@RegisterActivity)
                viewModel.confirmpassword.get().isEmpty()->"请输入确认密码".showToast(this@RegisterActivity)
                viewModel.password.get().length<6->"密码最少6位".showToast(this@RegisterActivity)
                viewModel.password.get()!=viewModel.confirmpassword.get()->"密码不一致".showToast(this@RegisterActivity)
                else ->viewModel.register(
                    viewModel.username.get(),
                    viewModel.password.get(),
                    viewModel.confirmpassword.get()
                )
            }
        }

        /**
         * 清空
          */
        fun clear(){
            viewModel.username.set("")
        }
        /**
         * 显示状态
         */
        val onCheckedChangeListener1 = CompoundButton.OnCheckedChangeListener { _, isChack ->
            viewModel.showPwd.set(isChack)
        }

        val onCheckedChangeListener2 =CompoundButton.OnCheckedChangeListener { _, isChack ->
            viewModel.isepassword.set(isChack)
        }
    }
}
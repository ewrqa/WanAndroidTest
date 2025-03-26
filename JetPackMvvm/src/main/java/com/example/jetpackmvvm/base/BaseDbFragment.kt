package com.example.jetpackmvvm.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackmvvm.network.NetState
import com.example.jetpackmvvm.network.manager.NetworkStateManager
import java.lang.reflect.ParameterizedType

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.base</p>
 * <p>简述:fragment的基类</p>
 *
 * @author 张凯涛
 * @date 2024/8/4
 */
abstract  class BaseDbFragment <VM : BaseViewModel,DB:ViewDataBinding>:Fragment(){
    //弹框的文本显示
    abstract fun showLoading(message: String = "请求网络中...")
    abstract fun dismissLoading()
    lateinit var viewModel:VM
    lateinit var myDataBinding: DB
    //连接布局的id
    abstract fun layoutId():Int
    lateinit var  mActivity:AppCompatActivity
    //延时请求数据
    private  val  handler=Handler()
    /**
     * 创建的时候调佣
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //使用dataBing进行绑定
         myDataBinding =
            DataBindingUtil.inflate<DB>(LayoutInflater.from(activity), layoutId(), container, false)

        return  myDataBinding.root
    }
    /**
     * 绑定activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
         mActivity = context as AppCompatActivity
        }
    /**
     * 创建完毕之后进行一些初始化操作
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        registorDefUIChange()
        initData()
    }
    /**
     * 用户可见的时候进行懒加载处理
     */
    override fun onResume() {
        super.onResume()
        onVisible()
    }
    abstract  fun  initView(savedInstanceState: Bundle?)
    //初始化viewmodel
    private  fun createViewModel():VM{
        //获取当前类的父类类型  强转成ParameterizedType类型
        val parameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        //该类型的第一个就是 我们要的实例 泛型是viewmodel类型
        val clzz =parameterizedType.actualTypeArguments[0] as Class<VM>

        return  ViewModelProvider(this)[clzz]
    }
    /**
     * 网络监听
     */
    open fun onNetworkStateChange(netState: NetState){}
    /**
     * 创建观察者
     */
    abstract  fun  createObserver()
    /**
     * 数据的加载
     */
    abstract fun lazyData()
    /**
     * 用户可见进行懒加载处理
     */
    private  fun onVisible(){
       //判断是否已页面加载完毕
        if(lifecycle.currentState==Lifecycle.State.STARTED){
            //页面显示的时候加载数据
             handler.postDelayed({
                 NetworkStateManager.instance.mNetworkStateCallback.observeInFragment(this) {
                     onNetworkStateChange(it)
                 }
                 lazyData()
             }, 500)
        }
    }
    open  fun  initData(){}

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        viewModel .loadingChange.showDialog.observeInFragment(this, Observer {
            showLoading(it)
        })
        viewModel.loadingChange.dismissDialog.observeInFragment(this, Observer {
            dismissLoading()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放资源
        handler.removeCallbacksAndMessages(null)
    }
}
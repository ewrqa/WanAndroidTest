package com.example.jetpackmvvm.base
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackmvvm.network.NetState
import com.example.jetpackmvvm.network.manager.NetworkStateManager
import java.lang.reflect.ParameterizedType

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.base</p>
 * <p>简述:activity的基类  将viewmodel引用进来</p>
 *
 * @author 张凯涛
 * @date 2024/8/1
 */
abstract  class BaseDbActvivity<VM :BaseViewModel,DB :ViewDataBinding>:AppCompatActivity(){

    lateinit var mDataBinding: DB

    lateinit var  viewModel: VM

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()
    //布局
    abstract fun layoutId(): Int
    //初始化布局
    abstract  fun  initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //使用databing双向绑定
        mDataBinding= DataBindingUtil.setContentView<DB>(this,layoutId())
        //是viewmodel和activity的绑定
        mDataBinding.setLifecycleOwner(this)
        init(savedInstanceState)
    }
    private fun init(savedInstanceState: Bundle?){
        //调用viewmodel初始化
         viewModel = createViewModel()
        //请求加载框的显示与隐藏
        registerUiChange()
        //创建观察者
        createObserver()
        //连接view
        initView(savedInstanceState)
        //网络状态
        NetworkStateManager.instance.mNetworkStateCallback.observeInActivity(this){
          onNetWorkStateChange(it)
        }
    }
    //网络状态的监听  对应不同的处理
    open fun onNetWorkStateChange(nesState: NetState){}
    //创建viewmodel
    private  fun  createViewModel():VM{
        return  ViewModelProvider(this).get(getVmClazz(this))
    }
    //用于获取泛型的实际类型
    @Suppress("UNCHECKED_CAST")
    fun <VM> getVmClazz(obj: Any): VM {
        //obj.javaClass.genericSuperclass 获取该类的父类
        //ParameterizedType 这是一个表示带有泛型参数的类型的特殊类型
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }
    //创建livedata观察者
    abstract  fun createObserver()

    //注册 加载框的显示
    private  fun registerUiChange(){
        viewModel.loadingChange.showDialog.observeInActivity(this){
            showLoading(it)
        }
        viewModel.loadingChange.dismissDialog.observeInActivity(this){
            dismissLoading()
        }
    }
    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observeInActivity(this, Observer {
                showLoading(it)
            })
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observeInActivity(this, Observer {
                dismissLoading()
            })
        }
    }
}


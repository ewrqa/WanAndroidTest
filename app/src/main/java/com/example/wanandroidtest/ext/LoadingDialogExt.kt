package com.example.wanandroidtest.ext
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.wanandroidtest.R

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ext</p>
 * <p>简述:加载框的显示</p>
 *
 * @author 张凯涛
 * @date 2024/8/1
 */
    private  var loadingDialog: MaterialDialog?=null

    //为了兼容 旧版本的android
    fun AppCompatActivity.showLoadingEXT(message:String="请求网络中"){
        //判断当前是否在结束 在不结束的时显示
        if(!this.isFinishing){
            if(loadingDialog==null){
                loadingDialog=MaterialDialog(this)
                        //是否可以取消
                    .cancelable(true)
                        //不可触摸外部取消
                    .cancelOnTouchOutside(false)
                        //设置圆角
                    .cornerRadius(12f)
                //设置加载框的布局
                    .customView(R.layout.layout_custom_progress_dialog_view)
                        //防止内存泄漏  将他的生命周期指定为使用的那个activity或者fragment
                    .lifecycleOwner(this)
            }
            loadingDialog?.show()
        }
    }

    fun Fragment.showLoadingExt(message: String = "请求网络中") {
        activity?.let {
            if (!it.isFinishing) {
                if (loadingDialog == null) {
                    loadingDialog = MaterialDialog(it)
                        .cancelable(true)
                        .cancelOnTouchOutside(false)
                        .cornerRadius(12f)
                        .customView(R.layout.layout_custom_progress_dialog_view)
                        .lifecycleOwner(this)
                }
                loadingDialog?.show()
            }
        }
    }



    //关闭
    fun Activity.dismissLoadingExt(){
        loadingDialog?.dismiss()
        loadingDialog=null
    }
    fun Fragment.dismissLoadingExt(){
        loadingDialog?.dismiss()
        loadingDialog=null
    }





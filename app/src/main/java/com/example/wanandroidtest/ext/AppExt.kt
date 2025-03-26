package com.example.wanandroidtest.ext

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.jetpackmvvm.util.navigateActio
import com.example.wanandroidtest.R
import com.example.wanandroidtest.util.CacheUtil

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ext</p>
 * <p>简述:常用的拓展类</p>
 *
 * @author 张凯涛
 * @date 2024/8/2
 */

/**
 *  泛型实例化 简化activity跳转
 */
inline  fun <reified  T> myStartActivity(context: Context,block:Intent.()->Unit){
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}
/**
 * Toast 调用
 */
 fun  String.showToast(context: Context){
        Toast.makeText(context,this,Toast.LENGTH_LONG).show()
 }
/**
 * 一般  结果请求失败的弹窗
 */
fun AppCompatActivity.showMessage(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "",
    negativeAction: () -> Unit = {}
) {
    MaterialDialog(this)
        .cancelable(true)
        .lifecycleOwner(this)
        .show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveButtonText) {
                positiveAction.invoke()
            }
            if (negativeButtonText.isNotEmpty()) {
                negativeButton(text = negativeButtonText) {
                    negativeAction.invoke()
                }
            }
//            getActionButton(WhichButton.POSITIVE).updateTextColor(SettingUtil.getColor(this@showMessage))
//            getActionButton(WhichButton.NEGATIVE).updateTextColor(SettingUtil.getColor(this@showMessage))
        }
}

/**
 *    fragment的拓展提示框
 *    MaterialDialog风格的对话框， cancelable是否可以通过点击外部进行取消
 *    lifecycle 与绑定 防止内存泄漏
 */
fun Fragment.showMessage(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    negativeButtonText: String = "取消",
    positiveButton: () -> Unit = {},
    negativeAction: () -> Unit = {},
) {
    activity?.let {
        MaterialDialog(it)
            .cancelable(true)
            .lifecycleOwner(it)
            .show {
                title(text = title)
                message(text = message)
                positiveButton(text = positiveButtonText).positiveButton {
                    positiveButton.invoke()
                }
                negativeButton(text = negativeButtonText).negativeButton {
                    negativeAction.invoke()
                }
            }
    }
}

/**
登录状态的拦截
 */
fun NavController.jumpByLogin(block: (NavController) -> Unit) {
    if (CacheUtil.isLogin()) {
        block(this)
    } else {
        this.navigateActio(R.id.login_activity)
    }
}




package com.example.wanandroidtest.data.bindadapater

import android.text.InputType
import android.view.DragAndDropPermissions
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.bindadapater</p>
 * <p>简述:</p>
 *databingadapater 的控件业务的拓展
 * @author 张凯涛
 * @date 2024/8/6
 */
object CustomBindAdapter {
    /**
     * listener: CompoundButton.OnCheckedChangeListener 监听对象设置给chekbox
     */
    @BindingAdapter(value = ["checkChange"])
    @JvmStatic
    fun checkChange(checkbox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkbox.setOnCheckedChangeListener(listener)
    }
    @BindingAdapter(value =["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }
    /**
     * 图片的路径 设置
     */
    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun   circleImageUrl(view:ImageView,url:String){
        Glide.with(view.context.applicationContext)
            .load(url)
                //将加载的图片裁剪成圆形
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                //设置图片加载动画 淡入淡出500毫秒
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)

    }



}
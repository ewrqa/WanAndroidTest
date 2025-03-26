package com.example.wanandroidtest.weight.preference

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.example.wanandroidtest.R
import com.example.wanandroidtest.util.SettingUtil

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.preference</p>
 * <p>简述:自定义的preference</p>
 *
 * @author 张凯涛
 * @date 2025/3/25
 */
class IconPreference(context: Context, attributes: AttributeSet) : Preference(context, attributes) {
    private var myColorView: MyColorView? = null

    init {
        widgetLayoutResource = R.layout.my_colorview
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        //展示选中的color
        val color = SettingUtil.getColor(context)

        myColorView = holder.itemView.findViewById<MyColorView>(R.id.myColorView)
        myColorView?.color = color
        myColorView?.border = color

    }

    fun setView() {
        val color = SettingUtil.getColor(context)
        myColorView?.color = color
        myColorView?.border = color
    }
}

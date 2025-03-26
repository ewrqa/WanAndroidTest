package com.example.wanandroidtest.ui.fragment.setup

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.example.jetpackmvvm.util.nav
import com.example.wanandroidtest.R
import com.example.wanandroidtest.appViewModel
import com.example.wanandroidtest.ext.dismissLoadingExt
import com.example.wanandroidtest.ext.setUiTheme
import com.example.wanandroidtest.ext.showMessage
import com.example.wanandroidtest.network.NetWorkApi
import com.example.wanandroidtest.util.CacheDataManager
import com.example.wanandroidtest.util.CacheUtil
import com.example.wanandroidtest.util.ColorUtil
import com.example.wanandroidtest.util.SettingUtil
import com.example.wanandroidtest.weight.preference.IconPreference

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        private var containerView: FrameLayout? = null
        private var linearLayout: LinearLayout? = null
        private var toolbarView: View? = null
        private var iconPreference: IconPreference? = null
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.root_preferences)
            setText()
            iconPreference = findPreference<IconPreference>("color")
            //退出登录的点击事时间
            findPreference<Preference>("out")?.setOnPreferenceClickListener { it ->
                showMessage(message = "确定要退出吗",
                    negativeButtonText = "取消", positiveButtonText = "确定",
                    positiveButton = {
                        //退出登录 清除存储的cookie
                        NetWorkApi.INSTANCE.cookieJar.clear()
                        //将存储的信息清空
                        CacheUtil.setUser(null)
                        appViewModel.userInfo.value = null
                        nav().navigateUp()
                    }, negativeAction = {
                        dismissLoadingExt()
                    })
                false
            }
            //设置列表动画
            findPreference<Preference>("animation")?.setOnPreferenceClickListener { oreference ->
                activity?.let {
                    MaterialDialog(it).show {
                        lifecycleOwner()
                            .negativeButton(text = "取消")
                            .positiveButton(text = "确定")
                            .title(text = "动画列表")
                            .listItemsSingleChoice(R.array.reply_entries,
                                initialSelection = SettingUtil.getModel())
                            { dialog, index, text ->
                                SettingUtil.setModel(index)
                                oreference.summary = text
                                //通知全局的viewmodel更新样式
                                appViewModel.appAnimation.value = index
                            }
                        getActionButton(WhichButton.POSITIVE).updateTextColor(
                            SettingUtil.getColor(
                                requireActivity()
                            )
                        )
                        getActionButton(WhichButton.NEGATIVE).updateTextColor(
                            SettingUtil.getColor(
                                requireActivity()
                            )
                        )
                    }
                }
                false
            }
            //设置主题颜色
            iconPreference?.setOnPreferenceClickListener { preference ->
                activity?.let {
                    MaterialDialog(it).show {
                        title(text = "主题颜色")
                        colorChooser(
                            ColorUtil.ACCENT_COLORS,
                            initialSelection = SettingUtil.getColor(it),
                            subColors = ColorUtil.PRIMARY_COLORS_SUB) { dialog, color ->
                            //存储设置的颜色
                            SettingUtil.setColor(it, color)
                            findPreference<com.example.wanandroidtest.weight.preference.PreferenceCategory>(
                                "base")?.setTitleViewColor(color)
                            findPreference<com.example.wanandroidtest.weight.preference.PreferenceCategory>(
                                "other")?.setTitleViewColor(color)
                            findPreference<com.example.wanandroidtest.weight.preference.PreferenceCategory>(
                                "about")?.setTitleViewColor(color)
                            findPreference<com.example.wanandroidtest.weight.preference.CheckBoxPreference>(
                                "top")?.setCheckboxViewColor(color)
                            toolbarView?.setBackgroundColor(color)
                            //通知其他界面立马修改配置
                            appViewModel.appColor.value = color
                        }
                        getActionButton(WhichButton.POSITIVE).updateTextColor(
                            SettingUtil.getColor(
                                requireActivity()
                            )
                        )
                        getActionButton(WhichButton.NEGATIVE).updateTextColor(
                            SettingUtil.getColor(
                                requireActivity()
                            )
                        )
                        positiveButton(R.string.done)
                        negativeButton(R.string.cancel)
                    }
                }
                false
            }
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            //获取根容器
            containerView = view.findViewById<FrameLayout>(android.R.id.list_container)
            containerView?.let { it ->
                //转换成线性布局
                linearLayout = it.parent as? LinearLayout
                linearLayout?.run {
                    toolbarView =
                        LayoutInflater.from(context).inflate(R.layout.include_toolbar, null)
                    toolbarView?.let { view ->
                        view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
                            ?.let { toolbarView2 ->
                                toolbarView2.title = "设置"
                                toolbarView2.setNavigationIcon(R.drawable.ic_back)
                                toolbarView2.setNavigationOnClickListener {
                                    nav().navigateUp()
                                }
                                //添加到第一个
                                addView(toolbarView, 0)
                            }
                        //修改主题颜色
                        appViewModel.appColor.value?.let {
                            setUiTheme(it, toolbarView)
                        }
                    }
                }
            }
        }

        //需要实现sharedpreferences才可以进行存储
        override fun onSharedPreferenceChanged(p0: SharedPreferences, p1: String?) {
            if (p1 == "color") {
                Log.e("icon", "1")
                iconPreference?.setView()
            }
            if (p1 == "top") {
                Log.e("top", "1")
                CacheUtil.setNeedTop(p0.getBoolean("top", true))
            }
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onDestroy() {
            super.onDestroy()
            containerView?.removeAllViews()
            toolbarView = null
        }

        /**
         * 设置界面初始值
         */
        private fun setText() {
            activity?.let {
                val login = CacheUtil.isLogin()
                //登录隐藏
                findPreference<Preference>("out")?.isVisible = login
                //缓存大小
                findPreference<Preference>("clear")?.summary =
                    CacheDataManager.getTotalCacheSize(it)
                //置顶
                if (login) {
                    findPreference<com.example.wanandroidtest.weight.preference.CheckBoxPreference>(
                        "top")?.isChecked = CacheUtil.getTopState()
                } else {
                    findPreference<com.example.wanandroidtest.weight.preference.CheckBoxPreference>(
                        "top")?.isChecked = false
                }
                //列表动画
                val modes = it.resources.getStringArray(R.array.reply_entries)
                findPreference<Preference>("animation")?.summary =
                    modes[SettingUtil.getModel()]
            }
        }
    }
}
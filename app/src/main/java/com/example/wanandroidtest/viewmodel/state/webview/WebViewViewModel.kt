package com.example.wanandroidtest.viewmodel.state.webview

import com.example.jetpackmvvm.base.BaseViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.viewmodel.state.webview</p>
 * <p>简述:webview的 viewmodel</p>
 *
 * @author 张凯涛
 * @date 2025/3/18
 */
class WebViewViewModel : BaseViewModel() {

    //文章标题
    var articleTitle: String = "特大新闻"

    //是否已经收藏
    var isChecked: Boolean = false

    //路径
    var url: String = ""

    //收藏的id
    var id: Int = 0

    //判断是网址还是文章
    var type: Int = 0
}
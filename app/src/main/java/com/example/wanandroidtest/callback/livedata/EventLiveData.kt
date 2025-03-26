package com.example.wanandroidtest.callback.livedata

import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.callback.livedata</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2025/3/14
 */
/**
 * 最新版的发送消息LiveData使用 https://github.com/KunMinX/UnPeek-LiveData 的最新版，因为跟其他类名（UnPeekLiveData）一致
 * 所以继承换了一个名字
 * 在Activity中observe 调用observeInActivity 在Fragment中使用调用 observeInFragment
 * 具体写法请参考 https://github.com/KunMinX/UnPeek-LiveData的示例
 */
class EventLiveData<T> : UnPeekLiveData<T>()

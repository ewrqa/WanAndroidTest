package com.example.jetpackmvvm.ext

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackmvvm.ResultState
import com.example.jetpackmvvm.base.BaseDbActvivity
import com.example.jetpackmvvm.base.BaseDbFragment
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.network.AppException
import com.example.jetpackmvvm.network.BaseResponse
import com.example.jetpackmvvm.paresException
import com.example.jetpackmvvm.paresResult
import com.example.jetpackmvvm.util.loge
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.ext</p>
 * <p>简述:viewmodel请求结果之后的处理</p>
 *
 * @author 张凯涛
 * @date 2024/8/5
 */
/**
 * net request 不校验请求结果数据是否是成功
 * @param block 请求体方法
 * @param resultState 请求回调的ResultState数据  livedatashuj
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block:suspend()->BaseResponse<T>,
    //livedata数据
    requestState: MutableLiveData<ResultState<T>>,
    //是否显示加载框
    isShowDialog:Boolean=false,
    showDialogMessage:String="请求网络中，请稍等"
):Job{
    //协成的作用域
    return  viewModelScope.launch(){
    //异常的捕获
        runCatching {
            if(isShowDialog)
                requestState.value= ResultState.onAppLoading<String>(showDialogMessage)
                block()
        }.onSuccess {
          requestState.paresResult(it)
        }.onFailure {
            //请求失败
            it.message?.loge()
            //打印错误的栈信息
            it.printStackTrace()
            requestState.paresException(it)
        }
    }

}
/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 */
fun <T> BaseDbActvivity<*,*>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            Log.e("JetpackMvvm","请求中")
            onLoading?.run { this }
        }
        is ResultState.Success -> {
            dismissLoading()
            Log.e("JetpackMvvm","请求成功")
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            Log.e("JetpackMvvm","失败")
            onError?.run { this(resultState.error) }
        }
    }
}
/**
 *  在fragment状况下使用
 *   @param isShowLoading 是否显示加载框
 *   @param loadingMessage 加载框的文本
 */
fun <T>BaseDbFragment<*,*>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)?=null,
    onLoading: (() -> Unit)?=null
){
    //判断 请求的状态
    when(resultState){
        is  ResultState.Loading->{
         if(onLoading==null){
             showLoading(resultState.loadingMessage)
         }else{
             onLoading.invoke()
         }
        }
        is ResultState.Success->{
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error->{
             dismissLoading()
            onError?.run { this(resultState.error) }
        }
    }
}



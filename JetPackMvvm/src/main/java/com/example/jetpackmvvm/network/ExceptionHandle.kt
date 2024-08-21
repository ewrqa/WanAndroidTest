package com.example.jetpackmvvm.network

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.network</p>
 * <p>简述:</p>
 * 异常信息的处理  判断 展示出对应地异常信息
 * @author 张凯涛
 * @date 2024/8/5
 */
object   ExceptionHandle {

    fun  handlerException(error:Throwable?):AppException{
        val  e : AppException
        //当不为空的时候  判断是哪冲错误 显示文本
        error?.let {
            when(it){
                //网络状态错误
                is HttpException->{
                    val e = AppException(Error.NETWORK_ERROR, error)
                    e
                }
                // 解析异常
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException ->{
                    e = AppException(Error.PARSE_ERROR, error)
                     e
                }
                //连接超时异常
                is ConnectTimeoutException->{
                     e = AppException(Error.TIMEOUT_ERROR, error)
                     e
                }
                is SocketTimeoutException->{
                    e= AppException(Error.TIMEOUT_ERROR,error)
                    e
                }
                //未知异常
                is UnknownHostException->{
                     e = AppException(Error.UNKNOWN, error)
                     e
                }
                is AppException-> it
                else->{
                        e=AppException(Error.UNKNOWN,error)
                           e
                }
            }
        }
       val ex = AppException(Error.UNKNOWN,error)
        return ex
    }
}
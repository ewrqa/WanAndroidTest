package com.example.jetpackmvvm.network

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.network</p>
 * <p>简述:</p>
 *错误信息的枚举类
 * 用来定义  请求遇到错误的信息
 * @author 张凯涛
 * @date 2024/8/5
 */
enum  class Error(private  val code:Int,private val message:String){
    /**
     * 未知错误
     */
    UNKNOWN(1000,"未知错误，请稍后再试"),

    /**
     * 网络异常
     */
    NETWORK_ERROR(1002,"网络连接错误，请稍后再试"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006,"网络连接超时，请稍后再试"),

    /**
     * 连接超时
     */
     PARSE_ERROR(1001,"解析异常，请稍后再试");

    fun getMessage():String{
        return  message
    }

    fun  getKey():Int{
        return  code
    }
}
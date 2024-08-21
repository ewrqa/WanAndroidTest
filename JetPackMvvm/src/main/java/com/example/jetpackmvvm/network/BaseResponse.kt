package com.example.jetpackmvvm.network

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.network</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/5
 */
abstract class BaseResponse <T>{

     abstract  fun isSuccess():Boolean

     abstract  fun getResponseData():T

     abstract  fun getResponseMessage():String

     abstract  fun getResponseCode():Int
}
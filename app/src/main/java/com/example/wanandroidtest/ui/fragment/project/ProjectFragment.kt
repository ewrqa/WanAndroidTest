package com.example.wanandroidtest.ui.fragment.project

import android.os.Bundle
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.ProjectFragmentBinding
import com.example.wanandroidtest.viewmodel.state.project.ProjectViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.project</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/7
 */
class ProjectFragment :BaseFragment<ProjectViewModel,ProjectFragmentBinding>(){
    override fun layoutId(): Int {
      return  R.layout.project_fragment
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}
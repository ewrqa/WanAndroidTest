package com.example.wanandroidtest.weight.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceViewHolder;

import com.example.wanandroidtest.R;
import com.example.wanandroidtest.util.SettingUtil;

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.preference</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2025/3/26
 */
public class PreferenceCategory extends PreferenceGroup {

    TextView titleView;

    public PreferenceCategory(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("RestrictedApi")
    public PreferenceCategory(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context, R.attr.preferenceCategoryStyle,
                android.R.attr.preferenceCategoryStyle));
    }

    public PreferenceCategory(Context context) {
        this(context, null);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean shouldDisableDependents() {
        return !super.isEnabled();
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        //根据android判断来开启辅助阅读
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            holder.itemView.setAccessibilityHeading(true);
            titleView = holder.itemView.findViewById(android.R.id.title);
            if (titleView == null) {
                return;
            }
            //设置之前选好的颜色
            titleView.setTextColor(SettingUtil.INSTANCE.getColor(getContext()));
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // We can't safely look for colorAccent in the category layout XML below Lollipop, as it
            // only exists within AppCompat, and will crash if using a platform theme. We should
            // still try and parse the attribute here in case we are running inside
            // PreferenceFragmentCompat with an AppCompat theme, and to set the category title
            // accordingly.
            //创建系统资源的存储
            final TypedValue value = new TypedValue();
            Log.d("创建标题", "1");
            //从上下文来解析colorAccent的值
            if (!getContext().getTheme().resolveAttribute(R.attr.colorAccent, value, true)) {
                // Return if the attribute could not be resolved
                return;
            }
            Log.d("创建标题", "2");
            titleView = (TextView) holder.findViewById(android.R.id.title);
            if (titleView == null) {
                Log.d("创建标题", "3");
                return;
            }
            final int fallbackColor = ContextCompat.getColor(getContext(),
                    R.color.preference_fallback_accent_color);
            // If the current color is not the fallback color we hardcode in the layout XML,
            // then this has already been handled by developers and we shouldn't override the
            // color.
            if (titleView.getCurrentTextColor() != fallbackColor) {
                return;
            }
            titleView.setTextColor(SettingUtil.INSTANCE.getColor(getContext()));
        }
    }

    public void setTitleViewColor(int color) {
        if (titleView != null) {
            titleView.setTextColor(color);
        }
    }

    /**
     * @deprecated Super class Preference deprecated this API.
     */
    @Deprecated
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            AccessibilityNodeInfoCompat.CollectionItemInfoCompat existingItemInfo = info.getCollectionItemInfo();
            if (existingItemInfo == null) {
                return;
            }

            final AccessibilityNodeInfoCompat.CollectionItemInfoCompat newItemInfo = AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
                    existingItemInfo.getRowIndex(),
                    existingItemInfo.getRowSpan(),
                    existingItemInfo.getColumnIndex(),
                    existingItemInfo.getColumnSpan(),
                    true /* heading */,
                    existingItemInfo.isSelected());
            info.setCollectionItemInfo(newItemInfo);
        }
    }
}
package com.starboy.karav.SA.UI.Database;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Karav on 6/2/2015.
 */
public class CustomLayoutManager extends LinearLayoutManager {
	private int mParentWidth;
	private int mItemWidth;

	public CustomLayoutManager(Context context, int parentWidth, int itemWidth) {
		super(context);
		mParentWidth = parentWidth;
		mItemWidth = itemWidth;
	}

	@Override
	public int getPaddingLeft() {
		if (mParentWidth > mItemWidth) return Math.round(mParentWidth / 2f - mItemWidth / 2f);
		else return 0;
	}

	@Override
	public int getPaddingRight() {
		return getPaddingLeft();
	}
}
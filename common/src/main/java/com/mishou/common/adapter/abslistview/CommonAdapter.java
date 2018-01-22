package com.mishou.common.adapter.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter
{
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	private int layoutId;

	public CommonAdapter(Context context, int layoutId, List<T> datas )
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		this.layoutId = layoutId;
	}



	@Override
	public int getCount()
	{
		return mDatas == null ? 0:mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
				layoutId, position);
		convert(holder, getItem(position));

		return holder.getConvertView();
	}

	public abstract void convert(ViewHolder holder, T t);
	/**
	 * 刷新界面数据
	 */
	public void notifyDataSetChanged(List<T> lists) {
		mDatas = lists;
		notifyDataSetChanged();
	}

}

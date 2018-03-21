package com.library.widgets.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.mishou.common.utils.LogUtils;
import com.mishou.common.utils.StringUtils;


/**
 * Created by ${shishoufeng} on 2016/9/1 0001.
 * email:shishoufeng1227@126.com
 * <p/>
 * design 风格dialog
 */
public class DesignDialog {

    private static final String OK = "确定";
    private static final String CANCEL = "取消";


    /**
     * charSequences[0] title
     * charSequences[1] message
     * charSequences[2] 确定
     * charSequences[3] 取消
     * <p/>
     * onPositiveNegativeListener 回调接口
     *
     * @param context
     * @param charSequences
     * @param onPositiveNegativeListener
     */
    public static void showMessageDialog(Context context, CharSequence[] charSequences, final onPositiveNegativeListener onPositiveNegativeListener) {

        if (charSequences != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            if (!StringUtils.isequals(charSequences[0], "")) {
                builder.setTitle(charSequences[0]);
            }
            if (!StringUtils.isequals(charSequences[1], "")) {
                builder.setMessage(charSequences[1]);
            }

            builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (onPositiveNegativeListener != null)
                        onPositiveNegativeListener.onPositive();

                }
            });

            builder.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (onPositiveNegativeListener != null)
                        onPositiveNegativeListener.onNegative();
                }
            });

            builder.show();

        } else {
            LogUtils.d("dialog  openConnect failure ");
        }

    }

    /**
     * charSequences[0] title
     * charSequences[1] message
     * charSequences[2] 确定
     * charSequences[3] 取消
     *
     * @param context
     * @param charSequences
     */
    public static void showMessageDialog(Context context, CharSequence[] charSequences) {

        if (charSequences != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            if (!StringUtils.isequals(charSequences[0], "")) {
                builder.setTitle(charSequences[0]);
            }
            if (!StringUtils.isequals(charSequences[1], "")) {
                builder.setMessage(charSequences[1]);
            }

            builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });


            builder.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });


            builder.show();

        } else {
            LogUtils.d("dialog  openConnect failure ");
        }

    }

    /***
     * charSequences[0] title
     * charSequences[1] message
     * charSequences[2] 确定
     * charSequences[3] 取消
     * <p/>
     * CharSequence[] items 子选项
     * <p/>
     * onItemPositiveNegativeListener 回调接口
     *
     * @param context
     * @param charSequences
     * @param items
     * @param listener
     */
    public static void showItemDialog(Context context, CharSequence[] charSequences, CharSequence[] items, final onItemPositiveNegativeListener listener) {

        if (charSequences != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            if (!StringUtils.isequals(charSequences[0], "")) {
                builder.setTitle(charSequences[0]);
            }
            if (!StringUtils.isequals(charSequences[1], "")) {
                builder.setMessage(charSequences[1]);
            }
            if (items != null && items.length != 0) {
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (listener != null)
                            listener.onItemClick(which);
                    }
                });
            }

            builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (listener != null)
                        listener.onPositive();

                }
            });


            builder.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (listener != null)
                        listener.onNegative();
                }
            });

            builder.show();

        } else {
            LogUtils.d("dialog  openConnect failure ");
        }

    }

    private onPositiveNegativeListener onPositiveNegativeListener;

    public void setOnPositiveNegativeListener(DesignDialog.onPositiveNegativeListener onPositiveNegativeListener) {
        this.onPositiveNegativeListener = onPositiveNegativeListener;
    }

    /**
     * 回调接口
     */
    public interface onPositiveNegativeListener {

        void onPositive();

        void onNegative();

    }

    /**
     * 回调接口
     */
    public interface onItemPositiveNegativeListener {

        void onPositive();

        void onNegative();

        void onItemClick(int position);

    }
}

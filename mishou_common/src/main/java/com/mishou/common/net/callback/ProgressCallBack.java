package com.mishou.common.net.callback;

import android.app.Dialog;
import android.content.DialogInterface;

import com.mishou.common.net.exception.ApiException;

import io.reactivex.disposables.Disposable;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 带进度监听回调
 */

public abstract class ProgressCallBack<T> extends CallBack<T> implements ProgressCancelListener {

    private OnProgressDialogListener onProgressDialogListener;
    private Dialog mDialog;
    private boolean isShowProgress = true;

    public ProgressCallBack(OnProgressDialogListener onProgressDialogListener) {
        this.onProgressDialogListener = onProgressDialogListener;
        init(false);
    }

    /**
     * 自定义加载进度框,可以设置是否显示弹出框，是否可以取消
     *
     * @param onProgressDialogListener dialog
     * @param isShowProgress 是否显示进度
     * @param isCancel       对话框是否可以取消
     */
    public ProgressCallBack(OnProgressDialogListener onProgressDialogListener, boolean isShowProgress, boolean isCancel) {
        this.onProgressDialogListener = onProgressDialogListener;
        this.isShowProgress = isShowProgress;
        init(isCancel);
    }

    /**
     * 初始化
     *
     * @param isCancel
     */
    private void init(boolean isCancel) {
        if (onProgressDialogListener == null) return;

        mDialog = onProgressDialogListener.createDialog();
        if (mDialog == null) return;

        mDialog.setCancelable(isCancel);
        if (isCancel) {
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    onCancelProgress();
                }
            });
        }
    }

    /**
     * 展示进度框
     */
    private void showProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * 取消进度框
     */
    private void dismissProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void onStart() {
        showProgress();
    }

    @Override
    public void onCompleted() {
        dismissProgress();
    }

    @Override
    public void onError(ApiException e) {
        dismissProgress();
    }

    @Override
    public void onCancelProgress() {
        if (disposed != null && !disposed.isDisposed()) {
            disposed.dispose();
        }
    }

    private Disposable disposed;

    public void subscription(Disposable disposed) {
        this.disposed = disposed;
    }
}

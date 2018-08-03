package factory.presenter;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    factory.presenter
 * 创建者：   wdl
 * 创建时间： 2018/8/3 14:35
 * 描述：    抽离公共的,基础的presenter
 * 设置对view的引用以及实现开始和销毁触发
 */
@SuppressWarnings({"unused", "unchecked"})
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    private T view;

    public BasePresenter(T view) {
        setView(view);
    }

    /**
     * 子类获取view，final不可复写
     *
     * @return T view
     */
    protected final T getView() {
        return view;
    }

    /**
     * 设置view对presenter的引用,可复写
     *
     * @param view BaseContract.View的导出类
     */
    protected void setView(T view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    /**
     * 开始触发
     */
    @Override
    public void start() {
        T mView = this.view;
        //显示进度
        if (mView != null) {
            mView.showLoading();
        }

    }

    /**
     * 销毁的触发
     */
    @Override
    public void destroy() {
        T mView = this.view;
        this.view = null;
        //销毁view对presenter的引用
        if (mView != null)
            mView.setPresenter(null);
    }
}

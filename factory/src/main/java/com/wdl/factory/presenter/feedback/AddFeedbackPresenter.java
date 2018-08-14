package com.wdl.factory.presenter.feedback;

import android.text.TextUtils;

import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.FeedbackHelper;
import com.wdl.factory.model.card.Feedback;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.feedback
 * 创建者：   wdl
 * 创建时间： 2018/8/14 11:31
 * 描述：    p层
 */
@SuppressWarnings("unused")
public class AddFeedbackPresenter extends BasePresenter<AddFeedbackContract.View>
        implements AddFeedbackContract.Presenter, DataSource.SucceedCallback<Feedback> {
    public AddFeedbackPresenter(AddFeedbackContract.View view) {
        super(view);
    }

    /**
     * 添加反馈
     *
     * @param subject 标题
     * @param content 内容
     */
    @Override
    public void insertFeedback(String subject, String content) {
        start();
        final AddFeedbackContract.View view = getView();
        if (!TextUtils.isEmpty(subject) && !TextUtils.isEmpty(content)) {
            Integer uId = Account.getUserId();
            if (uId == -1) {
                view.showError(R.string.data_account_error_un_login);
                return;
            } else {
                Feedback feedback = new Feedback();
                feedback.setuId(uId);
                feedback.setfSubject(subject);
                feedback.setfContent(content);
                FeedbackHelper.insert(feedback, this);
            }
        } else {
            if (view != null) {
                view.showError(R.string.data_login_account_invalid_parameter);
            }
        }
    }

    @Override
    public void onLoaded(final Feedback data) {
        final AddFeedbackContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (data != null && view != null) {
                    view.insertSucceed();
                }
            }
        });

    }

}

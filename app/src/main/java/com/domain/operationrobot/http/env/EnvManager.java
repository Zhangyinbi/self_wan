package com.domain.operationrobot.http.env;

import android.text.TextUtils;

import static com.domain.operationrobot.http.Constant.BASE_API_QA;

public class EnvManager {
    private static EnvManager.ENV env;

    static {
        env = EnvManager.ENV.QA;
    }

    public EnvManager() {
    }

    public static void updateENV(String tempenv) {
        if (TextUtils.isEmpty(tempenv)) {
            return;
        }
        switch (tempenv) {
            case BASE_API_QA:
                env = ENV.QA;
                break;
            default:
                env = ENV.QA;
                break;
        }
    }

    public static EnvManager.ENV getENV() {
        return env;
    }

    public static enum ENV {
        QA;

        private ENV() {
        }
    }
}
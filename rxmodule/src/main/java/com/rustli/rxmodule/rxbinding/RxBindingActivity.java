package com.rustli.rxmodule.rxbinding;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rustli.rxmodule.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class RxBindingActivity extends AppCompatActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private static final String TAG = "RxBindingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbinding);
        ButterKnife.bind(this);
        loginVerify();
    }

    private void loginVerify() {
        Observable<CharSequence> observableCount = RxTextView.textChanges(etAccount);
        Observable<CharSequence> observablePassword = RxTextView.textChanges(etPassword);
        Observable.combineLatest(observableCount, observablePassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCount, CharSequence mPassword) throws Exception {
                return isNameValid(mCount.toString()) && isPwdValid(mPassword.toString());
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    btnLogin.setEnabled(true);
                    RxView.clicks(btnLogin).subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Toast.makeText(RxBindingActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean isNameValid(String name) {
        return "RxBind".equals(name);
    }

    private boolean isPwdValid(String pwd) {
        return "123".equals(pwd);
    }

}

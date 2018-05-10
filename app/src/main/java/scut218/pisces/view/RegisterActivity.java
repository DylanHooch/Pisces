package scut218.pisces.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import scut218.pisces.Constants;
import scut218.pisces.R;
import scut218.pisces.beans.User;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.utils.UserUtil;

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView mIdView;
    private EditText mPasswordView;
    private EditText mDoubleCheckView;
    private View mProgressView;
    private View mRegisterFormView;
    private Button mRegisterButton;
    private UserRegisterTask mRegisterTask=null;

    private void findViews()
    {
        mRegisterFormView=findViewById(R.id.register_form);
        mDoubleCheckView=(EditText)findViewById(R.id.register_double_check);
        mPasswordView=(EditText)findViewById(R.id.register_password);
        mProgressView=findViewById(R.id.register_progress);
        mIdView=(AutoCompleteTextView)findViewById(R.id.register_id);
        mRegisterButton=(Button)findViewById(R.id.register_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }
    void attemptRegister(){
        if(mRegisterTask!=null){
            return;
        }
        mIdView.setError(null);
        mPasswordView.setError(null);
        mDoubleCheckView.setError(null);

        String id=mIdView.getText().toString();
        String password=mPasswordView.getText().toString();
        boolean cancel=false;
        View focusView=null;
        if(!TextUtils.isEmpty(password)&&!isPasswordValid(password))
        {
            cancel=true;
            focusView=mPasswordView;
            mPasswordView.setError("Password is too short");
        }
        if(!password.equals(mDoubleCheckView.getText().toString()))
        {
            cancel=true;
            focusView=mDoubleCheckView;
            mDoubleCheckView.setError("inconsistent password");
        }
        if(TextUtils.isEmpty(id))
        {
            mIdView.setError("This field is required");
            focusView=mIdView;
            cancel=true;
        }else if(!isIdValid(id))
        {
            mIdView.setError("invalid id");
            focusView=mIdView;
            cancel=true;
        }
        if(cancel)
        {
            focusView.requestFocus();
        }
        else{
            showProgress(true);
            mRegisterTask=new UserRegisterTask(id,password);
            mRegisterTask.execute((Void)null);
        }

    }
    private boolean isIdValid(String id) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mId;
        private final String mPassword;

        UserRegisterTask(String id, String password) {
            mId = id;
            mPassword = password;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // Simulate network access.
            UserUtil userUtil= UtilFactory.getUserUtil();
            User user=new User();
            user.setId(mId);
            user.setPassword(mPassword);

            if(userUtil.register(user)== Constants.SUCCESS)
                return true;
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegisterTask = null;
            showProgress(false);

            if (success) {
                Log.d("register","success");
                Intent intent=new Intent();
                intent.putExtra("id",mId);
                setResult(1,intent);
                finish();

            } else {
                mIdView.setError("existing id!");
                mIdView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            showProgress(false);
        }
    }
}

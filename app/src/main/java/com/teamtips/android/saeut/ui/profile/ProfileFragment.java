package com.teamtips.android.saeut.ui.profile;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.teamtips.android.saeut.R;
import com.teamtips.android.saeut.RequestHttpURLConnection;
import com.teamtips.android.saeut.RequestHttpURLConnection_POST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    TextView tv1;
    TextView tv2;
    TextView tv3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        tv1 = root.findViewById(R.id.textView1);
        tv2 = root.findViewById(R.id.textView2);
        tv3 = root.findViewById(R.id.textView3);

        // GET방식 예시 -> NetworkTask(요청주소, null)
        NetworkTask networkTask = new NetworkTask("http://49.50.173.180:8080/saeut/account/test",null);
        networkTask.execute();

        // POST 방식 예시 -> NetworkTask_POST(요청주소, 데이터)
        JSONObject parameters = new JSONObject();
        //전송 데이터 설정(json)
        try {
            parameters.put("id", "test2");
            parameters.put("password","1234");
            parameters.put("name","post_test");
            parameters.put("nickname","test_edited");
            parameters.put("phone",1012345678);
            parameters.put("pic","test");
            parameters.put("type",0);
        } catch(JSONException e){
            e.printStackTrace();
        }
        NetworkTask_POST networkTask_post = new NetworkTask_POST("http://49.50.173.180:8080/saeut/account/mod",parameters.toString());
        networkTask_post.execute();



        return root;
    }

    // GET 방식 네트워크 클래스
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;


        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        // 요청결과 처리
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                    // HTTP 통신 결과로 전달받은 값 s를 JSON으로 파싱하여 사용
                    JSONObject object = new JSONObject(s);
                    String id = object.getString("id");
                    String pw = object.getString("password");
                    tv1.setText(id);
                    tv2.setText(pw);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    } //NetworkTask

    //POST 방식 네트워크 클래스
    public class NetworkTask_POST extends AsyncTask<Void, Void, String> {

        private String url;
        private String json;

        public NetworkTask_POST(String url, String json) {
            this.url = url;
            this.json = json;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection_POST requestHttpURLConnection = new RequestHttpURLConnection_POST();
            result = requestHttpURLConnection.request(url, json); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        // 요청결과 처리
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // 결과값 s 사용
            tv3.setText(s);
        }

    } //NetworkTask_POST

}

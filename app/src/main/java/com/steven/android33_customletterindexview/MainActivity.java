package com.steven.android33_customletterindexview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.steven.android33_customletterindexview.adapter.MyIndexerAdapter;
import com.steven.android33_customletterindexview.helper.ChineseToPinyinHelper;
import com.steven.android33_customletterindexview.model.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext = this;
    private ListView listView_main;
    private TextView textView_empty;
    private TextView textView_dialog;
    private LetterIndexView letterIndexView_main;

    private MyIndexerAdapter adapter = null;
    private List<UserModel> totalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        totalList = getUserList();
        // 要对集合重新进行排序
        Collections.sort(totalList, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel lhs, UserModel rhs) {
                return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
            }
        });
    }

    private void initView() {
        listView_main = (ListView) findViewById(R.id.listView_main);
        textView_empty = (TextView) findViewById(R.id.textView_empty);
        textView_dialog = (TextView) findViewById(R.id.textView_dialog);
        listView_main.setEmptyView(textView_empty);
        letterIndexView_main = (LetterIndexView) findViewById(R.id.letterIndexView_main);
        letterIndexView_main.initTextView(textView_dialog);

        //给自定义View设置监听器
        letterIndexView_main.setOnLetterClickedListener(new LetterIndexView
                .OnLetterClickedListener() {
            @Override
            public void onLetterClicked(String letter) {
                int position = adapter.getPositionForSection(letter.charAt(0));
                listView_main.setSelection(position);
            }
        });

        adapter = new MyIndexerAdapter(mContext, totalList);
        listView_main.setAdapter(adapter);
    }


    private List<UserModel> getUserList() {
        List<UserModel> list = new ArrayList<UserModel>();
        String[] arrUserNames = getResources().getStringArray(R.array.arrUsernames);
        for (int i = 0; i < arrUserNames.length; i++) {
            UserModel userModel = new UserModel();
            String username = arrUserNames[i];
            String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(
                    username);
            String firstLetter = pinyin.substring(0, 1).toUpperCase();

            userModel.setUsername(username);
            userModel.setPinyin(pinyin);

            if (firstLetter.matches("[A-Z]")) {
                userModel.setFirstLetter(firstLetter);
            } else {
                userModel.setFirstLetter("#");
            }
            list.add(userModel);
        }
        return list;
    }
}

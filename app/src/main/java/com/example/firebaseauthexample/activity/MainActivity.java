package com.example.firebaseauthexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebaseauthexample.R;
import com.example.firebaseauthexample.model.AuthOptionModel;
import com.example.firebaseauthexample.utils.MyUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    RecyclerView rv_auth_option;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<AuthOptionModel> authOptionModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        addDataToList();

        rv_auth_option = findViewById(R.id.rv_auth_option);
        recyclerViewAdapter = new RecyclerViewAdapter(authOptionModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_auth_option.setLayoutManager(linearLayoutManager);
        rv_auth_option.setAdapter(recyclerViewAdapter);


    }


    private void addDataToList() {
        AuthOptionModel authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_phone));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_phone);
        authOptionModel.setAuthTextColor(R.color.white);
        authOptionModel.setAuthBackGroundColor(R.color.blueberry);
        authOptionModel.setAuthClass(MobileAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_email));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_email);
        authOptionModel.setAuthTextColor(R.color.white);
        authOptionModel.setAuthBackGroundColor(R.color.red_salsa);
        authOptionModel.setAuthClass(EmailAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_google));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_google);
        authOptionModel.setAuthTextColor(R.color.black_low_emphasis);
        authOptionModel.setAuthBackGroundColor(R.color.white);
        authOptionModel.setAuthClass(GoogleAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_facebook));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_facebook);
        authOptionModel.setAuthTextColor(R.color.white);
        authOptionModel.setAuthBackGroundColor(R.color.fb_blue);
        authOptionModel.setAuthClass(FacebookAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_twitter));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_twitter);
        authOptionModel.setAuthTextColor(R.color.white);
        authOptionModel.setAuthBackGroundColor(R.color.blue_jeans);
        authOptionModel.setAuthClass(TwitterAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_github));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_github);
        authOptionModel.setAuthTextColor(R.color.white);
        authOptionModel.setAuthBackGroundColor(R.color.git_black);
        authOptionModel.setAuthClass(GithubAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_mslive));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_mslive);
        authOptionModel.setAuthTextColor(R.color.white);
        authOptionModel.setAuthBackGroundColor(R.color.blue_younder);
        authOptionModel.setAuthClass(MSLiveAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_yahoo));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_yahoo);
        authOptionModel.setAuthTextColor(R.color.white);
        authOptionModel.setAuthBackGroundColor(R.color.yahoo_purple);
        authOptionModel.setAuthClass(YahooAuthActivity.class);
        authOptionModels.add(authOptionModel);

        authOptionModel = new AuthOptionModel();
        authOptionModel.setAuthText(getString(R.string.signin_with_play_games));
        authOptionModel.setAuthIcon(R.drawable.icon_auth_play_games);
        authOptionModel.setAuthTextColor(R.color.black_low_emphasis);
        authOptionModel.setAuthBackGroundColor(R.color.white);
        authOptionModel.setAuthClass(PlayGamesAuthActivity.class);
        authOptionModels.add(authOptionModel);

    }



    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
        ArrayList<AuthOptionModel> mList;

        public RecyclerViewAdapter(ArrayList<AuthOptionModel> mList) {
            this.mList = mList;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder vh, int position) {
            AuthOptionModel authOptionModel = mList.get(position);

            CardView.LayoutParams layoutParams = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    MyUtils.dpToPx(50)
            );
            layoutParams.setMargins(MyUtils.dpToPx(15), MyUtils.dpToPx(10), MyUtils.dpToPx(15), MyUtils.dpToPx(10));
            vh.cv_auth_option.setLayoutParams(layoutParams);

            vh.cv_auth_option.setCardBackgroundColor(getResources().getColor(authOptionModel.getAuthBackGroundColor()));
            vh.iv_auth_image.setImageResource(authOptionModel.getAuthIcon());
            vh.tv_auth_text.setText(authOptionModel.getAuthText());
            vh.tv_auth_text.setTextColor(getResources().getColor(authOptionModel.getAuthTextColor()));

            vh.itemView.setOnClickListener(v->{
                Intent intent = new Intent(context,authOptionModel.getAuthClass());
                startActivity(intent);
            });

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View convertView = inflater.inflate(R.layout.item_auth_option, null, true);

            return new MyViewHolder(convertView);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            CardView cv_auth_option;
            ImageView iv_auth_image;
            TextView tv_auth_text;

            public MyViewHolder(View view) {
                super(view);
                cv_auth_option = view.findViewById(R.id.cv_auth_option);
                iv_auth_image = view.findViewById(R.id.iv_auth_image);
                tv_auth_text = view.findViewById(R.id.tv_auth_text);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }


    }
}

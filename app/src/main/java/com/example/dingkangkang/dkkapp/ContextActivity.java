package com.example.dingkangkang.dkkapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dingkangkang.dkkapp.util.GsonUtil;
import com.example.dingkangkang.dkkapp.util.WeiXinBean;
import com.example.dingkangkang.dkkapp.util.WeiXinTitalBean;
import com.solidfire.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ContextActivity extends AppCompatActivity {

    private Button btn_back;
    private ListView list_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);
        btn_back= (Button) findViewById(R.id.btn_back);
        list_title = (ListView)findViewById(R.id.list_title);
        list_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(wenxinTital==null){
                    inintTital(wexinCntext.getResult().get(position).getCid());

                }else {
                    Intent intent = new Intent();
                    intent.putExtra("url",wenxinTital.getResult().getList().get(position).getSourceUrl());
                    intent.setClass(ContextActivity.this,Context2Activity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ContextActivity.this,MainActivity.class);
                startActivity(intent);
                //http://apicloud.mob.com/wx/article/category/query?key=13e3d87147959
            }
        });
        inintData();

    }

    private void inintTital(String cid) {
        System.out.print("cid:::::"+cid);
        String strUrl="http://apicloud.mob.com/wx/article/search?key=13e3d87147959&cid="+cid;
        OkHttpClient okhttpClick=new OkHttpClient();
        Request request=new  Request.Builder().url(strUrl).get().build();
        Call call=okhttpClick.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                wenxinTital = GsonUtil.parseJsonWithGson(str,WeiXinTitalBean.class);
                handler.sendEmptyMessage(2);
            }
        });
    }

    WeiXinBean wexinCntext=null;
    WeiXinTitalBean wenxinTital=null;
    private void inintData() {
        OkHttpClient okHttpClick=new OkHttpClient();

        final Request request=new  Request.Builder().url("http://apicloud.mob.com/wx/article/category/query?key=13e3d87147959").get().build();
        Call call= okHttpClick.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                wenxinTital=null;
                final String str=response.body().string();
                 wexinCntext= GsonUtil.parseJsonWithGson(str,WeiXinBean.class);
                handler.sendEmptyMessage(1);


            }
        });
    }
    myAdapter adapter=null;
    myAdapter2 adapter2=null;
    public  void getWeixinlist(){
        adapter=new myAdapter(ContextActivity.this);
        list_title.setAdapter(adapter);
    }
    public  void getWeixinlist2(){
        adapter2=new myAdapter2(ContextActivity.this);
        list_title.setAdapter(adapter2);
    }
    class myAdapter extends BaseAdapter{
        private  LayoutInflater mflater=null;

        public  myAdapter(Context context){
            this.mflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return wexinCntext.getResult().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if (convertView==null){
                convertView=mflater.inflate(R.layout.adapter_item,null);
                viewHolder=new ViewHolder();
                viewHolder.tv_tita_item= (TextView) convertView.findViewById(R.id.tv_tita_item);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_tita_item.setText(wexinCntext.getResult().get(position).getName()+"");
            return convertView;
        }
    }

    class  ViewHolder{
        TextView tv_tita_item;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //获得通过handler.sendEmptyMessage发送的消息编码
            int what = msg.what;
                   /* 处理代码 */
            if (what==1){
                getWeixinlist();
            }else  if(what==2){
                getWeixinlist2();
            }
        }
    };

    class myAdapter2 extends BaseAdapter{
        private  LayoutInflater mflater2=null;

        public  myAdapter2(Context context){
            this.mflater2=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return wenxinTital.getResult().getList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder2 viewHolder=null;
            if (convertView==null){
                convertView=mflater2.inflate(R.layout.adapter_item,null);
                viewHolder=new ViewHolder2();
                viewHolder.tv_tita_item= (TextView) convertView.findViewById(R.id.tv_tita_item);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder2) convertView.getTag();
            }
            viewHolder.tv_tita_item.setText(wenxinTital.getResult().getList().get(position).getTitle()+"");
            return convertView;
        }
    }

    class  ViewHolder2{
        TextView tv_tita_item;
    }

    long waitTime = 2000;
    long touchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if((currentTime-touchTime)>=waitTime) {
                //让Toast的显示时间和等待时间相同
                Toast.makeText(this, "再按一次退出",Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            }else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

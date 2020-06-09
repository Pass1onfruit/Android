package com.example.XianChengCheng;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler myHandler = new Handler();

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar seekBar;
    private TextView timeTextView;
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private int i = 0;

    File pFile = Environment.getExternalStorageDirectory();
    private String[] musicPath = new String[]{
            pFile + "/sdcard/music/Ring.mp3",
            pFile + "/sdcard/music/Theme.mp3",
            pFile + "/sdcard/music/music/Waterfall.mp3",
            pFile + "/sdcard/music/Memory.mp3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.play);
        Button stop = findViewById(R.id.stop);
        Button pause = findViewById(R.id.pause);
        Button nextMusic = findViewById(R.id.next);
        Button preMusic = findViewById(R.id.previous);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);
        nextMusic.setOnClickListener(this);
        preMusic.setOnClickListener(this);

        seekBar = findViewById(R.id.seekbar);
        timeTextView = findViewById(R.id.text1);
        //权限检查，动态申请权限
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initMediaPlayer(0);
        }

        myHandler.post(updateUI);
    }

    private void initMediaPlayer(int musicIndex){
        try {
            mediaPlayer.setDataSource(musicPath[musicIndex]);//指定音乐文件路径
            mediaPlayer.prepare();
        }catch(Exception e){
            e.printStackTrace();
        }
        //每换一首歌seekBar的长度都要发生变化
        seekBar.setMax(mediaPlayer.getDuration());
        //拖动进度条
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer(i);
                }
                break;
            case R.id.next:
                playNextMusic();
                break;
            case R.id.previous:
                playPreMusic();
                break;
            default:
                break;
        }
    }
    //上一首
    private void playPreMusic(){
        if(mediaPlayer != null && i < 4 && i >=0){
            mediaPlayer.reset();
            switch (i){
                case 1: case 2: case 3:
                    initMediaPlayer(i-1);
                    i = i - 1;
                case 0:
                    initMediaPlayer(3);
            }
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }
    }
    //下一首
    private void playNextMusic(){
        if(mediaPlayer != null && i < 4 && i >=0){
            mediaPlayer.reset();
            switch (i){
                case 0: case 1: case 2:
                    initMediaPlayer(i+1);
                    i = i + 1;
                case 3:
                    initMediaPlayer(0);
            }
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }
    }

    //更新UI
    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            //获取播放位置
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            timeTextView.setText(time.format(mediaPlayer.getCurrentPosition()) + "s");
            myHandler.postDelayed(updateUI,1000);
        }

    };
    //释放资源
    protected void onDestroy(){
        super.onDestroy();
        myHandler.removeCallbacks(updateUI);
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}

/////////////////////////
setDataSource() //指定音乐路径
getDuration()  //获取歌曲长度
getCurrentPosition()   //获取播放到的位置
seekTo()   //播放到的位置
onClick()   //点击事件
start()    //开始播放
pause()    //暂停播放
release()  //释放资源
isPlaying()   //是否正在播放

2、详细代码：
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler myHandler = new Handler();

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar seekBar;
    private TextView timeTextView;
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private int i = 0;

    File pFile = Environment.getExternalStorageDirectory();
    private String[] musicPath = new String[]{
            pFile + "/sdcard/Bad.mp3",
            pFile + "/sdcard/GOAT.mp3",
            pFile + "/sdcard/DeathNote.mp3",
            pFile + "/sdcard/Scancy.mp3"
    };

    @Override
//activity初始化，调用UI
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //…
        //权限检查，动态申请权限
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initMediaPlayer(0);
        }

        myHandler.post(updateUI);
    }

    private void initMediaPlayer(int musicIndex){
        try {
            mediaPlayer.setDataSource(musicPath[musicIndex]);//指定音乐文件路径
            mediaPlayer.prepare();
        }catch(Exception e){
            e.printStackTrace();
        }
        //每换一首歌seekBar的长度都要发生变化
        seekBar.setMax(mediaPlayer.getDuration());
        //拖动进度条
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer(i);
                }
                break;
            case R.id.next:
                playNextMusic();
                break;
            case R.id.previous:
                playPreMusic();
                break;
            default:
                break;
        }
    }
    //上一首
    private void playPreMusic(){
        if(mediaPlayer != null && i < 4 && i >=0){
            mediaPlayer.reset();
            switch (i){
                case 1: case 2: case 3:
                    initMediaPlayer(i-1);
                    i = i - 1;
                case 0:
                    initMediaPlayer(3);
            }
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }
    }
    //下一首
    private void playNextMusic(){
        if(mediaPlayer != null && i < 4 && i >=0){
            mediaPlayer.reset();
            switch (i){
                case 0: case 1: case 2:
                    initMediaPlayer(i+1);
                    i = i + 1;
                case 3:
                    initMediaPlayer(0);
            }
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }
    }

    //更新UI
    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            //获取播放位置
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            timeTextView.setText(time.format(mediaPlayer.getCurrentPosition()) + "s");
            myHandler.postDelayed(updateUI,1000);
        }

    };
    //最后释放资源
    //protected void onDestroy(){…}

}



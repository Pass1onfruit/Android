package com.ex4_7;
 import java.io.IOException;
 import android.app.Activity;
 import android.media.MediaPlayer;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.widget.CheckBox;
 import android.widget.ImageButton;
 import android.widget.TextView;
 public class MainActivity extends Activity 
 {
	  CheckBox ch1,ch2;
	  TextView txt;     
	  ImageButton	mStopButton, mStartButton, mPauseButton;
	  MediaPlayer	mMediaPlayer; 
	  String sdcard_file;
	  sdcard_file = new String("/sdcard/music/mtest2.mp3");
    int res_file = R.raw.mtest1;
  	@Override 
    public void onCreate(Bundle savedInstanceState) 
    {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main); 
       /* 构建MediaPlayer对象 */ 
       mMediaPlayer = new MediaPlayer();
       ch1=(CheckBox)findViewById(R.id.check1);        
		 ch2=(CheckBox)findViewById(R.id.check2);
		 txt = (TextView)findViewById(R.id.text1);
    	 mStopButton  = (ImageButton) findViewById(R.id.Stop);
		 mStartButton = (ImageButton) findViewById(R.id.Start); 
		 mPauseButton = (ImageButton) findViewById(R.id.Pause);	
		 mStopButton.setOnClickListener(new mStopClick());
		 mStartButton.setOnClickListener(new mStartClick());
		 mPauseButton.setOnClickListener(new mPauseClick());
     } 
	 //播放SD卡或其他路径的音乐文件
	 private void playMusic(String path)
	 {
	   try
	    {
		  /* 重置MediaPlayer对象，使之处于空闲状态 */
		  mMediaPlayer.reset();
		  /* 设置要播放的文件的路径 */
		  mMediaPlayer.setDataSource(path);
		  /* 准备播放 */
		  mMediaPlayer.prepare();
		  /* 开始播放 */
		  mMediaPlayer.start();
	    }catch (IOException e){    }
	 }   	
	 /* 停止按钮事件  */
	 class mStopClick implements OnClickListener
	 {
	   @Override
	   public void onClick(View v)
	   {
		  /* 是否正在播放 */
		 if (mMediaPlayer.isPlaying())
		  {
			//重置MediaPlayer到初始状态
			 mMediaPlayer.reset();
			 mMediaPlayer.release();
		  }
	   }
   }
	/* 播放按钮事件  */
  class mStartClick implements OnClickListener
  {
   @Override
 	  public void onClick(View v)
	  {
  	String str="";
	    if(ch1.isChecked())
	    {
	 	  str = str + "\n" + ch1.getText(); 
		  try {
		    mMediaPlayer = MediaPlayer.create(MainActivity.this, res_file);
			mMediaPlayer.start();
		    } catch (Exception e) {Log.i("ch1", "res err ....");	}  
		}
	    if(ch2.isChecked())
	    { 
		  str=str + "\n" + ch2.getText();
		  try{
	 	     mMediaPlayer = new MediaPlayer();
		     mMediaPlayer.setDataSource(sdcard_file);
		     playMusic(sdcard_file);
	    	 } catch (Exception e){ Log.i("ch2", "sdcard err .... "); }	 
		   }
 	      txt.setText(str);
		}
	 }
	 /* 暂停按钮事件  */
	 class mPauseClick implements OnClickListener
	 {
		@Override
		public void onClick(View v)
		{
          if (mMediaPlayer.isPlaying())
		    {
			  /* 暂停 */
			   mMediaPlayer.pause();
		    }
		    else 
		    {
			  /* 开始播放 */
			   mMediaPlayer.start();
		     }	
 	      }
    }
 }

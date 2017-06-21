package com.example.soundstest;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static MediaPlayer mediaPlayer;
    float betterSeekBarValue = 0.0f;
    float leftVolume = 1.0f;
    float rightVolume = 1.0f;

    MediaPlayer mp;
    AudioManager audioManager;


    @BindView(R.id.sb_main)
    SeekBar sbMain;

    @BindView(R.id.tv_progress)
    TextView tvProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);



        ButterKnife.bind(this);

        Log.d(TAG,"------------");
        Log.d(TAG,String.format("Left volume : %f",leftVolume * 100));
        Log.d(TAG,String.format("right volume : %f",rightVolume * 100));

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        sbMain.setProgress(50);

        sbMain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvProgress.setText(String.valueOf(progress));

                betterSeekBarValue = progress / 100f;


                // Level up left side
                if(progress <= 50) {
                    rightVolume = 1.0f - (1.0f - betterSeekBarValue);

                } else { // level up right side
                    leftVolume = 1.0f - betterSeekBarValue;
                }

                Log.d(TAG,"------------");
                Log.d(TAG,String.format("Left volume : %f",leftVolume * 100));
                Log.d(TAG,String.format("right volume : %f",rightVolume * 100));

                mediaPlayer.setVolume(leftVolume,rightVolume);


//                int mainVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                int mainVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);




            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static void play (Context context, String mp3FileName, Boolean loop) {


        // Play audio from res/raw folder
        try {
            int fileResId = context.getResources().getIdentifier(mp3FileName, "raw", context.getPackageName());
            mediaPlayer = MediaPlayer.create(context,fileResId);
            mediaPlayer.setLooping(loop);
            //mediaPlayer.setVolume( 0.59f , 0.09f);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Play sound
     */
    @OnClick(R.id.btn_play)
    public void playSound() {
       play(getApplicationContext(),"s102",true);
    }

    /**
     *
     */
    @OnClick(R.id.btn_stop)
    public void stopSound(){
        mediaPlayer.setLooping(false);
        mediaPlayer.release();
    }
}

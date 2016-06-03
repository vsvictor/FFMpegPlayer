package media.ffmpeg.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;

import media.ffmpeg.FFmpegMediaPlayer;
import media.ffmpeg.starter.R;

public class VideoPlayerActivity extends FragmentActivity {

    private SurfaceView mmSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Surface mFinalSurface;
    private LinearLayout llPanel;
    private ToggleButton ibPlay;
    private ToggleButton ibChannels;
    private ToggleButton ibTitres;
    private TextView tvName;

    private FFmpegMediaPlayer mMediaPlayer;

    private String fixedUri;

    private boolean panelView = false;
    private boolean played = true;

    public void onCreate(Bundle icicle) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(icicle);
        setContentView(R.layout.activity_video_player);
        fixedUri = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4";
        setIntent(null);
        llPanel = (LinearLayout) findViewById(R.id.llPanel);
        tvName = (TextView) findViewById(R.id.tvName);
        ibPlay = (ToggleButton) findViewById(R.id.ibPlay);
        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ibChannels = (ToggleButton) findViewById(R.id.ibChannels);
        ibTitres = (ToggleButton) findViewById(R.id.ibTitre);
        mmSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mmSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelView = !panelView;
                ViewGroup.LayoutParams params = llPanel.getLayoutParams();
                int hh = panelView?64:0;
                params.height = hh;
                llPanel.setLayoutParams(params);
                tvName.setVisibility(panelView?View.INVISIBLE:View.VISIBLE);
            }
        });
        mSurfaceHolder = mmSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.v("TAG", "surfaceChanged format=" + format + ", width=" + width + ", height=" + height);
            }
            public void surfaceCreated(SurfaceHolder holder) {
                mFinalSurface = holder.getSurface();
                try {
                    mMediaPlayer.setDataSource(fixedUri);
                    if (mFinalSurface != null) {
                        mMediaPlayer.setSurface(mFinalSurface);
                    }
                    mMediaPlayer.prepareAsync();
                } catch (IOException ex) {
                }
            }
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

        });
        mMediaPlayer = new FFmpegMediaPlayer();
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
    }
    private FFmpegMediaPlayer.OnPreparedListener mOnPreparedListener = new FFmpegMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(FFmpegMediaPlayer mp) {
            mp.start();
            tvName.setText(fixedUri);
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}

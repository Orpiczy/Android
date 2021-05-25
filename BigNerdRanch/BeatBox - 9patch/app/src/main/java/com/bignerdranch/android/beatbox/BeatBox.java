package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {

    private static final String TAG = "BeatBoxAllert";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private AssetManager mAssetManager;

    private List<Sound> mSounds = new ArrayList<>();
    private static final int MAX_SOUNDS = 5;
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC,0);
        loadSounds();
    }

    private void loadSounds(){
        String[] soundNames;
        try{
            soundNames = mAssetManager.list(SOUNDS_FOLDER);
            Log.i(TAG,"Znaleziono " +soundNames.length+" dźwięków"+":\n");
        }catch (IOException ioe){
            Log.e(TAG, "Nie można wyświetlić listy dźwięki",ioe);
            return;
        }


        for (String filename: soundNames){
            try {
                String assetPath = SOUNDS_FOLDER+"/"+filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            }catch (IOException exception){
                Log.e(TAG,"Błąd ładowania pliku "+filename,exception);
            }

        }
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getAssetPath());
        int soundID = mSoundPool.load(afd, 1);
        sound.setSoundID(soundID);

    }

    public void play(Sound sound){
        Integer soundID= sound.getSoundID();
        if(soundID!=null){
            mSoundPool.play(soundID,1.0f,1.0f,1,0,1.0f);
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void release(){
        mSoundPool.release();
    }
}

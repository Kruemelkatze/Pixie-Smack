package at.aau.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager {
    private PixieSmack parentGame;
    private HashMap<String, String> event2sound;

    public SoundManager(PixieSmack parentGame) {
        this.parentGame = parentGame;

        // register the available events.
        event2sound = new HashMap<String, String>(20);
        event2sound.put("blip", "sfx/blip.wav");
        event2sound.put("explode", "sfx/explosion.wav");
        event2sound.put("hit", "sfx/hit.wav");
        event2sound.put("jump", "sfx/jump.wav");
        event2sound.put("laser", "sfx/laser.wav");
        event2sound.put("pickup", "sfx/pickup.wav");
        event2sound.put("powerup", "sfx/powerup.wav");
        
        event2sound.put("gameMusic", "sfx/gameMusic.wav");
        
        for (int i = 1; i <= PixieSmack.SmackSoundsCount; i++) {
            event2sound.put("smack" + i, "sfx/smack" + i + ".wav");
        }

        for (int i = 1; i <= PixieSmack.CollectSoundsCount; i++) {
            event2sound.put("collect" + i, "sfx/collect" + i + ".wav");
        }
        
        
    }

    /**
     * Plays an event registered in the constructor. Make sure that (i) the event is known and (ii) the
     * asset is loaded in the constructor of PixieSmack.
     *
     * @param event
     */
    public void playEvent(String event) {
        if (event2sound.get(event) != null) {
        	if(event2sound.get(event).toString().equals("sfx/gameMusic.wav")){
        		  parentGame.getAssetManager().get(event2sound.get(event), Music.class).setLooping(true);
        		  parentGame.getAssetManager().get(event2sound.get(event), Music.class).play();
        	} else {
        		parentGame.getAssetManager().get(event2sound.get(event), Sound.class).play();
        	}
        } else {
            System.err.println("Event unknown.");
        }
    }
    
    public void pauseEvent(String event){
    	if (event2sound.get(event) != null) {
    		if(event2sound.get(event).toString().equals("sfx/gameMusic.wav")){
    			parentGame.getAssetManager().get(event2sound.get(event), Music.class).pause();
    		}
    	} else {
            System.err.println("Event unknown.");
        }
    }
    
    public void resumeEvent(String event){
    	if (event2sound.get(event) != null) {
    		if(event2sound.get(event).toString().equals("sfx/gameMusic.wav")){
    			if(parentGame.getAssetManager().get(event2sound.get(event), Music.class).isPlaying()){
        			parentGame.getAssetManager().get(event2sound.get(event), Music.class).play();
    			}
    		}
    	} else {
            System.err.println("Event unknown.");
        }
    }
}

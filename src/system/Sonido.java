/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 *
 * @author GeovannyRojas
 */
public class Sonido{
    private static Sonido instance;
    private  Media audio;
    private MediaPlayer reproductor;
    
    private Sonido(){        
    }
    
    public static Sonido getInstance(){
        if(instance == null)
            instance = new Sonido();
        return instance;
    }

    public Media getAudio() {
        return audio;
    }
    
    public void play(String valor){
        File archivo = new File("src/"+valor+".mp3");
        audio = new Media(archivo.toURI().toString());
        reproductor = new MediaPlayer(audio);
        reproductor.play();
    }   
}

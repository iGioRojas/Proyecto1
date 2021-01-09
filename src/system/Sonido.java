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
public class Sonido extends Thread{
    private  Media audio;
    private MediaPlayer reproductor;
    private boolean detener = false;
    private String code;
    
    public Media getAudio() {
        return audio;
    }
    
    public void play(String valor){
        File archivo = new File("src/"+valor+".mp3");
        audio = new Media(archivo.toURI().toString());
        reproductor = new MediaPlayer(audio);
        reproductor.play();
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public String getCode(){
        return code;
    }
    
    public void sonar(){
        String morse = getCode();
            if(morse != null){
                for (int i = 0; i < morse.length(); i++) {
                    try {
                        char c = code.charAt(i);
                        switch(c){
                            case '.':
                                play("punto");
                                Thread.sleep(2000);
                                break;
                            case '-':
                                play("raya");
                                Thread.sleep(2000);
                                break;
                        }   } catch (InterruptedException ex) {
                        Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
                    }
             }
                setCode(null);
        }
    }
    
    @Override
    public void run(){
            sonar();
    }

    public MediaPlayer getReproductor() {
        return reproductor;
    }

    public void setReproductor(MediaPlayer reproductor) {
        this.reproductor = reproductor;
    }

    public boolean isDetener() {
        return detener;
    }

    public void setDetener(boolean detener) {
        this.detener = detener;
    }
    
}

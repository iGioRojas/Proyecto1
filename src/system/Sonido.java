/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.io.File;
import java.util.Queue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author GeovannyRojas
 */
public class Sonido {

    private Media audio;
    private MediaPlayer reproductor;
    private boolean detener = false;
    private Queue<String> code;
    
    public Media getAudio() {
        return audio;
    }

    public void play(String valor) {
        File archivo = new File("src/" + valor + ".mp3");
        audio = new Media(archivo.toURI().toString());
        reproductor = new MediaPlayer(audio);
        reproductor.play();
    }

    public Queue<String> getCode() {
        return code;
    }

    public void setCode(Queue<String> code) {
        this.code = code;
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

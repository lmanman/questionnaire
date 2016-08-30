package com.visionet.letsdesk.app.common.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by xuetao on 15/6/1.
 */
public class ChangeAudioFormat {
    private static Logger log = LoggerFactory.getLogger(ChangeAudioFormat.class);

    public static void main(String[] args) throws Exception {
        String path1 = "/Users/xuetao/Documents/temp/wsk/s1.amr";
//        String path2 = "/Users/xuetao/Documents/temp/wsk/s1.mp3";
        String path2 = path1.substring(0, path1.length() - 4) + ".mp3";
        changeToMp3(path1, path2);

    }



    public static void changeToMp3test(File source, File target) {
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException e) {
            log.error("changeToMp3 IllegalArgumentException:"+e.toString());
        } catch (InputFormatException e) {
            log.error("changeToMp3 InputFormatExceptionn:"+e.toString());
        } catch (EncoderException e) {
            log.error("changeToMp3 EncoderExceptionn:"+e.toString());
        }
    }

    public static void changeToMp3(File source, File target) {
        source.renameTo(target);
    }

    public static boolean changeToMp3(String sourcePath, String targetPath) {

        try {
            final String[] shellcmd = new String[]{"ffmpeg","-i",sourcePath,targetPath};
            Runtime.getRuntime().exec(shellcmd);
            return true;
        } catch (IOException e) {
            log.error("changeToMp error:",e);
            return false;
        }
    }
}

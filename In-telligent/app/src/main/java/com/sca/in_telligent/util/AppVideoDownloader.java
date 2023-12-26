package com.sca.in_telligent.util;

import android.content.Context;
import android.util.Log;
import com.sca.in_telligent.di.ApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

@Singleton
public class AppVideoDownloader implements VideoDownloader {

  private Context mContext;

  @Inject
  public AppVideoDownloader(@ApplicationContext Context context) {
    mContext = context;
  }

  @Override
  public Observable<File> download(final String url, final String filename) {

    boolean noCache = false;
    return Observable.create((ObservableOnSubscribe<File>) subscriber -> {
      try {

        File mFolder = new File("/data/data/com.sca.in_telligent/");

        File cache = new File(mFolder + "/" + filename);

        if (!mFolder.exists()) {
          mFolder.mkdir();
        }

        if (!cache.exists()) {
          cache.createNewFile();
          InputStream in = null;
          FileOutputStream out = null;
          try {
            in = new URL(url).openStream();
            out = new FileOutputStream(cache);

            byte[] buffer = new byte[10 * 1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
              out.write(buffer, 0, len);
            }
          } catch (Exception e) {
            Log.e("Error", "load image from url failed", e);
            e.printStackTrace();
          } finally {
            try {
              if (in != null) {
                in.close();
              }

              if (out != null) {
                out.close();
              }
            } catch (Exception ignored) {
            }
          }
        } else {

        }
        subscriber.onNext(cache);
        subscriber.onComplete();
      } catch (Exception e) {
        e.printStackTrace();
        subscriber.onError(e);
      }
    });

  }
}

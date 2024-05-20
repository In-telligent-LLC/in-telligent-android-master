package com.sca.in_telligent.util;

import java.io.File;

import io.reactivex.rxjava3.core.Observable;

public interface VideoDownloader {

  Observable<File> download(String url, String filename);
}

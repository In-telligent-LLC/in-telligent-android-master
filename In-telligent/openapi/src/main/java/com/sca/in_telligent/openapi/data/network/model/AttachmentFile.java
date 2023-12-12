package com.sca.in_telligent.openapi.data.network.model;

import java.io.File;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AttachmentFile {
    private final File file;
    private final FileType fileType;
    private final String path;

    public AttachmentFile(FileType fileType, File file, String str) {
        this.fileType = fileType;
        this.file = file;
        this.path = str;
    }

    public File getFile() {
        return this.file;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public String getPath() {
        return this.path;
    }
}

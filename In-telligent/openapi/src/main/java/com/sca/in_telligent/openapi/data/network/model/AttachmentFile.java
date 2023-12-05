package com.sca.in_telligent.openapi.data.network.model;

import java.io.File;

public class AttachmentFile {

    private FileType fileType;
    private File file;

    public AttachmentFile(final FileType fileType, final File file) {
        this.fileType = fileType;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public FileType getFileType() {
        return fileType;
    }

}

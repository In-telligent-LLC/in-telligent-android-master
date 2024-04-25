//package com.sca.in_telligent.util;
//
//import static androidx.core.app.ActivityCompat.startActivityForResult;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//
//import com.sca.in_telligent.R;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class AttachmentCompressUtil {
//
//    private static final int FILE_PICKER_CODE = 123; // You can change this code as needed
//
//    public void launchAddAttachments(Context context) {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//        intent.putExtra(Intent.CATEGORY_OPENABLE, true);
//
//        startActivityForResult(Intent.createChooser(intent, context.getString(R.string.select_files)),
//                FILE_PICKER_CODE);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null) {
//            Log.d("AttachmentsUtil", "Cancelled picker");
//            return;
//        }
//
//        if (requestCode == FILE_PICKER_CODE) {
//            ArrayList<AttachmentFile> attachmentFiles = new ArrayList<>();
//
//            if (data.getData() != null) {
//                Uri selectedUri = data.getData();
//                handleUri(selectedUri, attachmentFiles);
//            } else if (data.getClipData() != null) {
//                ClipData mClipData = data.getClipData();
//                for (int i = 0; i < mClipData.getItemCount(); i++) {
//                    ClipData.Item item = mClipData.getItemAt(i);
//                    Uri selectedUri = item.getUri();
//                    handleUri(selectedUri, attachmentFiles);
//                }
//            }
//            compressFiles(attachmentFiles);
//        }
//    }
//
//    private void handleUri(Uri selectedUri, ArrayList<AttachmentFile> attachmentFiles) {
//        String attachmentPath = selectedUri.getPath();
//        File file = new File(attachmentPath);
//        if (selectedUri.toString().contains("video")) {
//            attachmentFiles.add(new AttachmentFile(FileType.VIDEO, file, selectedUri.toString()));
//        } else {
//            attachmentFiles.add(new AttachmentFile(FileType.IMAGE, file, selectedUri.toString()));
//        }
//    }
//
//    private void compressFiles(ArrayList<AttachmentFile> attachmentFiles) {
//        // Implement file compression logic here if needed
//    }
//
//    // Assuming you have access to these classes
//    private static class AttachmentFile {
//        FileType fileType;
//        File file;
//        String uriString;
//
//        public AttachmentFile(FileType fileType, File file, String uriString) {
//            this.fileType = fileType;
//            this.file = file;
//            this.uriString = uriString;
//        }
//    }
//
//    private enum FileType {
//        IMAGE, VIDEO
//    }
//}

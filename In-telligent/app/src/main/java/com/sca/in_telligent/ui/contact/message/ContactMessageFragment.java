package com.sca.in_telligent.ui.contact.message;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.exifinterface.media.ExifInterface;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.AttachmentFile;
import com.sca.in_telligent.openapi.data.network.model.BuildingMember;
import com.sca.in_telligent.openapi.data.network.model.CreateNotificationRequest;
import com.sca.in_telligent.openapi.data.network.model.FileType;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.openapi.data.network.model.SuggestNotificationRequest;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverDialog;
import com.sca.in_telligent.util.MySpinner;
import com.sca.in_telligent.util.video.ProgressReporter;
import com.sca.in_telligent.util.video.VideoResolutionChanger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;


public class ContactMessageFragment extends BaseFragment implements ContactMessageMvpView,
        ContactDeliverDialog.ContactDeliverSelector {

    public static final String TAG = "ContactMessageFragment";

    private static final int FILE_PICKER_CODE = 1;
    private static final long MAX_TOTAL_ATTACHMENTS_SIZE_KB = 10000;
    private ArrayList<String> attachmentPaths = new ArrayList<>();
    private ArrayList<String> subscriberIds = new ArrayList<>();
    private long totalAttachmentsSize;

    @Inject
    ContactMessageMvpPresenter<ContactMessageMvpView> mPresenter;

    @Inject
    ContactMessageSpinnerAdapter contactAlertSpinnerAdapter;

    @Inject
    ContactMessageSpinnerAdapter contactSendSpinnerAdapter;

    @BindView(R.id.contact_alert_spinner)
    MySpinner alertSpinner;

    @BindView(R.id.contact_send_to_spinner)
    MySpinner sendSpinner;

    @BindView(R.id.contact_send_spinner_layout)
    RelativeLayout sendSpinnerLayout;

    @BindView(R.id.contact_alert_spinner_layout)
    RelativeLayout alertSpinnerLayout;

    @BindView(R.id.contact_send_attachment_layout)
    ViewGroup sendAttachmentLayout;

    @BindView(R.id.contact_send_attachment_text)
    TextView attachmentText;

    @BindView(R.id.contact_send_attachment_progress_bar)
    ProgressBar attachmentProgressBar;

    @BindView(R.id.required_alert_type)
    RelativeLayout requiredAlertType;

    @BindView(R.id.required_send_to)
    RelativeLayout requiredSendTo;

    @BindView(R.id.contact_send_message_button)
    TextView sendMessageButton;

    @BindView(R.id.contact_send_title_edittext)
    EditText sendTitleEdittext;

    @BindView(R.id.contact_send_message_edittext)
    EditText messageEditText;

    TextView goBackText;

    private boolean managed;
    private boolean isPersonalCommunity;
    private boolean canSendLSA;
    private String buildingId;


    private String alertTitle = "";
    private String alertBody = "";
    private String alertType = "";
    private String sendTo = "";
    private ArrayList<Invitee> deliverInvitees = new ArrayList<>();
    private ArrayList<BuildingMember> buildingMembers = new ArrayList<>();

    public static ContactMessageFragment newInstance(boolean managed, boolean isPersonalCommunity,
                                                     boolean canSendLSA, String buildingId) {
        Bundle args = new Bundle();
        args.putBoolean("managed", managed);
        args.putBoolean("isPersonalCommunity", isPersonalCommunity);
        args.putString("buildingId", buildingId);
        args.putBoolean("canSendLSA", canSendLSA);
        ContactMessageFragment fragment = new ContactMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        managed = getArguments().getBoolean("managed");
        isPersonalCommunity = getArguments().getBoolean("isPersonalCommunity");
        buildingId = getArguments().getString("buildingId");
        canSendLSA = getArguments().getBoolean("canSendLSA");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_message, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            alertSpinner = view.findViewById(R.id.contact_alert_spinner);
            sendSpinner = view.findViewById(R.id.contact_send_to_spinner);
            sendSpinnerLayout = view.findViewById(R.id.contact_send_spinner_layout);
            alertSpinnerLayout = view.findViewById(R.id.contact_alert_spinner_layout);
            sendAttachmentLayout = view.findViewById(R.id.contact_send_attachment_layout);
            attachmentText = view.findViewById(R.id.contact_send_attachment_text);
            attachmentProgressBar = view.findViewById(R.id.contact_send_attachment_progress_bar);
            requiredAlertType = view.findViewById(R.id.required_alert_type);
            requiredSendTo = view.findViewById(R.id.required_send_to);
            sendMessageButton = view.findViewById(R.id.contact_send_message_button);
            sendTitleEdittext = view.findViewById(R.id.contact_send_title_edittext);
            messageEditText = view.findViewById(R.id.contact_send_message_edittext);
            goBackText = view.findViewById(R.id.contact_message_back_text);


            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

            goBackText.setOnClickListener(v -> backClick());
            sendMessageButton.setOnClickListener(v -> sendMessage(v));
            attachmentText.setOnClickListener(v -> attachmentButtonClick(v));

//            contactAlertSpinnerAdapter = new ContactMessageSpinnerAdapter(requireContext(),
//                    new ArrayList<>());
//            contactAlertSpinnerAdapter.setDropDownViewResource(R.layout.spinner_list_item);


        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        initManaged();
        if (managed) {
            prepareAlertSpinner();
            if (isPersonalCommunity) {
                prepareSendToSpinner();
            }
        }
    }

    private void initManaged() {
        if (managed) {
            alertSpinnerLayout.setVisibility(View.VISIBLE);
            requiredAlertType.setVisibility(View.VISIBLE);
        } else {
            alertSpinnerLayout.setVisibility(View.GONE);
            requiredAlertType.setVisibility(View.GONE);
        }
        initPersonal();
    }

    private void initPersonal() {
        if (isPersonalCommunity) {
            sendSpinnerLayout.setVisibility(View.VISIBLE);
            requiredSendTo.setVisibility(View.VISIBLE);
            sendAttachmentLayout.setVisibility(View.GONE);
        } else {
            sendAttachmentLayout.setVisibility(View.VISIBLE);
            sendSpinnerLayout.setVisibility(View.GONE);
            requiredSendTo.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.contact_message_back_button, R.id.contact_message_back_text})
    void backClick() {
        getActivity().onBackPressed();
    }

    void prepareAlertSpinner() {
        ArrayList<String> spinnerItems = new ArrayList<>();

        if (isPersonalCommunity) {
            spinnerItems.add("Emergency");
            spinnerItems.add("Urgent");
        } else {
            if (canSendLSA) {
                spinnerItems.add(getString(R.string.normal));
                spinnerItems.add(getString(R.string.personal_alert));
                spinnerItems.add(getString(R.string.ping_alert));
                spinnerItems.add(getString(R.string.critical_alert));
                spinnerItems.add(getString(R.string.life_safety));
            } else {
                spinnerItems.add(getString(R.string.normal));
                spinnerItems.add(getString(R.string.personal_alert));
            }
        }

        spinnerItems.add(getString(R.string.select_alert_type));

        contactAlertSpinnerAdapter.addItems(spinnerItems);


        alertSpinner.setAdapter(contactAlertSpinnerAdapter);
        alertSpinner.setSelection(contactAlertSpinnerAdapter.getCount());
    }

    void prepareSendToSpinner() {
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Send to Specific Subscriber or Groups");
        spinnerItems.add("Send to All Subscribers of This Community");

        spinnerItems.add("Send To");

        contactSendSpinnerAdapter.addItems(spinnerItems);

        sendSpinner.setAdapter(contactSendSpinnerAdapter);
        sendSpinner.setSelection(contactSendSpinnerAdapter.getCount());
    }

    @OnItemSelected(R.id.contact_alert_spinner)
    void alertItemSelected(Spinner spinner, int position) {
        if (isPersonalCommunity) {
            switch (position) {
                case 0:
                    alertType = "pc-emergency";
                    break;
                case 1:
                    alertType = "pc-urgent";
                    break;
            }
        } else {
            if (canSendLSA) {
                switch (position) {
                    case 0:
                        alertType = "normal";
                        break;
                    case 1:
                        alertType = "personal-safety";
                        break;
                    case 2:
                        alertType = "ping";
                        break;
                    case 3:
                        alertType = "critical";
                        break;
                    case 4:
                        alertType = "life-safety";
                }
            } else {
                switch (position) {
                    case 0:
                        alertType = "normal";
                        break;
                    case 1:
                        alertType = "personal-safety";
                        break;
                }
            }
        }
    }

    @OnItemSelected(R.id.contact_send_to_spinner)
    void sendToItemSelected(Spinner spinner, int position) {
        switch (position) {
            case 0:
                sendTo = "s"; //"SPECIFIC";
                break;
            case 1:
                sendTo = "a"; //"ALL";
                break;
            case 2:
                sendTo = "i"; //"NEAR";
                break;
        }

        if (sendTo.equals("s")) {
            ContactDeliverDialog contactDeliverDialog = ContactDeliverDialog
                    .newInstance(buildingId, isPersonalCommunity);
            contactDeliverDialog.setContactDeliverSelector(this);
            contactDeliverDialog.show(requireActivity().getSupportFragmentManager(), ContactDeliverDialog.class.getSimpleName());
        }
    }

    @OnClick(R.id.contact_send_attachment_layout)
    void attachmentButtonClick(View v) {
        mPresenter.getStoragePermission();
    }

    @OnClick(R.id.contact_send_message_button)
    void sendMessage(View v) {
        if (checkFields(managed)) {
            if (managed) {
                if (attachmentPaths.size() > 0) {
                    createNotification();
                } else {
                    createNotificationNoThumbnail();
                }

            } else {
                if (attachmentPaths.size() > 0) {
                    suggestNotification();
                } else {
                    suggestNotifiationNoThumbnail();
                }
            }
        } else {
            showMessage(R.string.required_field);
        }

    }

    private void createNotification() {
        mPresenter.createNotification(buildingId, sendTitleEdittext.getText().toString(),
                messageEditText.getText().toString(), alertType, attachmentPaths, sendTo,
                subscriberIds);
    }

    private void createNotificationNoThumbnail() {
        CreateNotificationRequest createNotificationRequest = new CreateNotificationRequest();
        createNotificationRequest.setBuildingId(buildingId);
        createNotificationRequest.setTitle(sendTitleEdittext.getText().toString());
        createNotificationRequest.setBody(messageEditText.getText().toString());
        createNotificationRequest.setType(alertType);
        createNotificationRequest.setSubscribers(subscriberIds);
        createNotificationRequest.setSend_to_email(sendTo);
        mPresenter.createNotificationNoAttachment(createNotificationRequest);
    }

    private void suggestNotification() {
        SuggestNotificationRequest suggestNotificationRequest = new SuggestNotificationRequest();
        suggestNotificationRequest.setBuildingId(buildingId);
        suggestNotificationRequest.setDescription(messageEditText.getText().toString());
        suggestNotificationRequest.setTitle(sendTitleEdittext.getText().toString());
        suggestNotificationRequest.setAttachments(attachmentPaths);
        mPresenter.suggestNotification(suggestNotificationRequest);
    }

    private void suggestNotifiationNoThumbnail() {
        SuggestNotificationRequest suggestNotificationRequest = new SuggestNotificationRequest();
        suggestNotificationRequest.setBuildingId(buildingId);
        suggestNotificationRequest.setDescription(messageEditText.getText().toString());
        suggestNotificationRequest.setTitle(sendTitleEdittext.getText().toString());
        mPresenter.suggestNotificationNoAttachment(suggestNotificationRequest);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void sendDeliverList(ArrayList<Invitee> invitees) {
        Observable.fromIterable(invitees).filter(invitee -> !invitee.getStatus().equals("pending"))
                .toList().subscribe(filteredInvitees ->
                deliverInvitees = (ArrayList<Invitee>) filteredInvitees);

        Observable.fromIterable(deliverInvitees).map(invitee -> Integer.toString(invitee.getSubscriberId()))
                .toList().subscribe(strings -> subscriberIds = (ArrayList<String>) strings);

    }

    @Override
    public void sendDeliverListMember(ArrayList<BuildingMember> ids) {
        buildingMembers = ids;
        Observable.fromIterable(buildingMembers).map(buildingMember -> buildingMember.getSubscriberId())
                .toList().subscribe(strings -> subscriberIds = (ArrayList<String>) strings);
    }

    @Override
    public void storagePermissionResult(boolean granted) {
        if (granted) {
            launchAddAttachments();
        }
    }

    @Override
    public void messageSendResult(boolean sent) {
        if (sent) {
            getActivity().onBackPressed();
        }
    }

    private boolean checkFields(boolean isManaged) {
        boolean isFieldsFilled = false;
        alertTitle = sendTitleEdittext.getText().toString();
        alertBody = messageEditText.getText().toString();
        if (isManaged) {
            if (isPersonalCommunity) {
                isFieldsFilled =
                        !alertTitle.isEmpty() && !alertBody.isEmpty() && !alertType.isEmpty()
                                && (!sendTo.isEmpty() && !sendTo.equals("s")) || (!sendTo.isEmpty() && sendTo
                                .equals("s") && subscriberIds.size() > 0);
            } else {
                isFieldsFilled =
                        !alertTitle.isEmpty() && !alertBody.isEmpty() && !alertType.isEmpty();
            }
        } else {
            isFieldsFilled = !alertTitle.isEmpty() && !alertBody.isEmpty();
        }

        return isFieldsFilled;
    }

    private void launchAddAttachments() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(Intent.CATEGORY_OPENABLE, true);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_files)),
                FILE_PICKER_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            Log.d("CreateCommunityAlert", "Cancelled picker");
            return;
        }

        if (requestCode == FILE_PICKER_CODE) {

            attachmentPaths = new ArrayList<>();
            final ArrayList<AttachmentFile> attachmentFiles = new ArrayList<>();

            if (data.getData() != null) {



                Uri selectedUri = data.getData();

                final String attachmentPath = selectedUri.getPath();


                File file = new File(attachmentPath);
                attachmentText.setText("\t" + file.getName());

                if (selectedUri.toString().contains("video")) {
                    attachmentFiles.add(new AttachmentFile(FileType.VIDEO, file, selectedUri.toString()));
                } else {
                    attachmentFiles.add(new AttachmentFile(FileType.IMAGE, file, selectedUri.toString()));
                    attachmentPaths.add(attachmentPath);
                }

                compressFiles(attachmentFiles);

            } else if (data.getClipData() != null) {

                ClipData mClipData = data.getClipData();


                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri selectedUri = item.getUri();

                    final String attachmentPath = selectedUri.getPath();

                    final File file = new File(attachmentPath);

                    if (selectedUri.toString().contains("video")) {
                        attachmentFiles.add(new AttachmentFile(FileType.VIDEO, file, selectedUri.toString()));
                    } else {
                        attachmentFiles.add(new AttachmentFile(FileType.IMAGE, file, selectedUri.toString()));
                    }
                }
                compressFiles(attachmentFiles);
            }
        }
    }

    private enum LoadAttachmentViewState {
        COMPRESSING,
        LOADED
    }

    private void setLoadAttachmentViewState(LoadAttachmentViewState viewState) {
        switch (viewState) {
            case COMPRESSING:
                sendAttachmentLayout.setClickable(false);
                attachmentProgressBar.setProgress(0);
                attachmentProgressBar.setVisibility(View.VISIBLE);
                attachmentText.setText(R.string.compressing_file_message);
                break;
            case LOADED:
                sendAttachmentLayout.setClickable(true);
                attachmentProgressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void compressFiles(final ArrayList<AttachmentFile> inputFiles) {
        if (inputFiles == null || inputFiles.isEmpty()) {
            return;
        }

        setLoadAttachmentViewState(LoadAttachmentViewState.COMPRESSING);

        @SuppressLint("StaticFieldLeak") AsyncTask<ArrayList<AttachmentFile>, Integer, ArrayList<String>> asyncTask = new AsyncTask<ArrayList<AttachmentFile>, Integer, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(ArrayList<AttachmentFile>... files) {

                ArrayList<AttachmentFile> inputFiles = files[0];

                ArrayList<String> newFilePaths = new ArrayList<>();

                for (AttachmentFile attachmentFile : inputFiles) {
                    final FileType fileType = attachmentFile.getFileType();
                    final File file = attachmentFile.getFile();

                    switch (fileType) {
                        case IMAGE: {

                            publishProgress(33);
                            Bitmap bitmap = getScaledBitmapFromFile(file, 600);

                            bitmap = rotateBitmap(bitmap, file.getPath());

                            final File newTempFile = createTempFile();
                            publishProgress(66);
                            newFilePaths.add(persistImage(bitmap, newTempFile));
                            publishProgress(100);

                            try {
                                ExifInterface exifForNewImage = new ExifInterface(newTempFile.getPath());
                                exifForNewImage.setAttribute(ExifInterface.TAG_ORIENTATION, "1");
                                exifForNewImage.saveAttributes();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;
                        }
                        case VIDEO: {

                            try {

                                VideoResolutionChanger compressor = new VideoResolutionChanger(
                                        new ProgressReporter() {
                                            @Override
                                            public void onProgress(int progress) {
                                                publishProgress(progress);
                                            }
                                        });
                                final String newFilePath = compressor.changeResolution(file);
                                Log.d("CreateCommunityAlert", "Compress success: " + newFilePath);
                                newFilePaths.add(newFilePath);

                            } catch (Throwable throwable) {

                                Log.e("CreateCommunityAlert", "Error compressing video", throwable);
                                return null;

                            }

                            break;
                        }
                    }

                }

                return newFilePaths;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
//                Log.d("CreateCommunityAlert", "Progress: " + values[0]);
                attachmentProgressBar.setProgress(values[0]);
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(ArrayList<String> newFilePaths) {

                for (String newFilePath : newFilePaths) {
                    final long filesize = (new File(newFilePath).length() / 1024);

                    totalAttachmentsSize += filesize;
                }


                setLoadAttachmentViewState(LoadAttachmentViewState.LOADED);

                attachmentPaths.addAll(newFilePaths);


                if (totalAttachmentsSize < MAX_TOTAL_ATTACHMENTS_SIZE_KB) {
                    attachmentText.setText("\t" + attachmentPaths.size() + " " + getString(
                            R.string.attachments));
                } else {
                    attachmentPaths.clear();
                    totalAttachmentsSize = 0;

                    attachmentText.setText(" " + getString(R.string.attachments_too_large));
                    launchAttachmentsTooLargeDialog();
                }

            }
        };
        asyncTask.execute(inputFiles);

    }

    private File createTempFile() {
        File storageDir = getActivity().getCacheDir();
        if (!storageDir.exists() && !storageDir.isDirectory()) {
            storageDir.mkdirs();
        }

        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void launchAttachmentsTooLargeDialog() {
        final Activity activity = getActivity();

        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            if (!activity.isDestroyed() && !activity.isFinishing()) {
                builder
                        .setTitle(R.string.attachments_too_large_try_again)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
                        .show();
            }
        }
    }

    private Bitmap rotateBitmap(final Bitmap bitmap, final String filePath) {
        try {

            ExifInterface exif = new ExifInterface(filePath);
            int rotation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


            int rotationInDegrees = exifToDegrees(rotation);



            if (rotationInDegrees == 0) {

                return bitmap;

            } else {

                Matrix matrix = new Matrix();
                if (rotation != 0f) {
                    matrix.preRotate(rotationInDegrees);
                }

                return Bitmap
                        .createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private int scaleImage(int width, int height, final int requiredSize) {
        int scale = 1;
        while (true) {
            if (width / 2 < requiredSize || height / 2 < requiredSize) {
                break;
            }
            width /= 2;
            height /= 2;
            scale *= 2;
        }
        return scale;
    }

    private String persistImage(final Bitmap bitmap, final File imageFile) {
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile.getPath();
    }

    private Bitmap getScaledBitmapFromFile(final File file, final int requiredSize) {
        Bitmap b = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(file);
            BitmapFactory.decodeStream(fis, null, options);
            fis.close();

            final int scale = scaleImage(options.outWidth, options.outHeight, requiredSize);

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(file);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}

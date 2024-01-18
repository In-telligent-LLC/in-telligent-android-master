package com.sca.in_telligent.ui.group.generate;


import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getCacheDir;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.CreateEditGroupRequest;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class GenerateGroupFragment extends BaseFragment implements GenerateGroupMvpView {

    @Inject
    GenerateGroupMvpPresenter<GenerateGroupMvpView> mPresenter;

    public static final String TAG = "GenerateGroupFragment";

    private int EMERGENCY_AUDIO_FILE = R.raw.personal_community_emergency;
    private int URGENT_AUDIO_FILE = R.raw.personal_community_urgent;

    private MediaPlayer playSoundUrgent;
    private MediaPlayer playSoundEmergency;

    @BindView(R.id.generate_group_title_text)
    TextView titleText;

    @BindView(R.id.emergency_sound_alert_image)
    ImageView emergencyImage;

    @BindView(R.id.urgent_sound_alert_image)
    ImageView urgentImage;

    @BindView(R.id.create_thumbnail_image)
    ImageView createThumbnailImage;

    @BindView(R.id.default_thumbnail_image)
    ImageView defaultThumbnailImage;

    @BindView(R.id.default_thumbnail_text)
    TextView defaultThumbnailText;

    @BindView(R.id.generate_group_name_edittext)
    EditText nameEdittext;

    @BindView(R.id.generate_group_description_edittext)
    EditText descEdittext;

    @BindView(R.id.generate_group_create_button)
    TextView createButton;

    private Uri croppedUri = null;
    private Building building;
    private String buildingId = "";

    GenerateGroupSelector generateGroupSelector;



    public interface GenerateGroupSelector {

        void groupCreated();
    }

    public static GenerateGroupFragment newInstance(Building building) {
        Bundle args = new Bundle();
        args.putSerializable("building", building);
        GenerateGroupFragment fragment = new GenerateGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@Nullable Context context) {
        generateGroupSelector = (GenerateGroupSelector) context;
        super.onAttach(context);
        
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate_group, container, false);
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            titleText = view.findViewById(R.id.generate_group_title_text);
            emergencyImage = view.findViewById(R.id.emergency_sound_alert_image);
            urgentImage = view.findViewById(R.id.urgent_sound_alert_image);
            createThumbnailImage = view.findViewById(R.id.create_thumbnail_image);
            defaultThumbnailImage = view.findViewById(R.id.default_thumbnail_image);
            defaultThumbnailText = view.findViewById(R.id.default_thumbnail_text);
            nameEdittext = view.findViewById(R.id.generate_group_name_edittext);
            descEdittext = view.findViewById(R.id.generate_group_description_edittext);
            createButton = view.findViewById(R.id.generate_group_create_button);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @OnClick(R.id.create_thumbnail_image)
    void createThumbnailClick(View v) {
        pickFromGallery();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        cancelSounds();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        getAudioManager();
        initSounds();
        if (building != null) {
            buildingId = Integer.toString(building.getId());
            titleText.setText(getResources().getString(R.string.edit_group));
            createButton.setText(getResources().getString(R.string.save_changes));
            nameEdittext.setText(building.getName());
            descEdittext.setText(building.getDescription());
            if (building.getImageUrl() != null) {
                Picasso.get().load(building.getImageUrl())
                        .into(createThumbnailImage);
            }
        }
    }

    private void cropImage(Uri sourceUri, Uri destinationUri) {
        UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(3, 2)
                .start(getContext(), this);
    }

    @OnClick(R.id.urgent_sound_alert_image)
    void urgentClick(View v) {
        urgentImage.setSelected(!urgentImage.isSelected());
        emergencyImage.setSelected(false);
        playUrgentAudio(urgentImage.isSelected());
    }

    @OnClick(R.id.emergency_sound_alert_image)
    void emergencyClick(View v) {
        emergencyImage.setSelected(!emergencyImage.isSelected());
        urgentImage.setSelected(false);
        playEmergencyAudio(emergencyImage.isSelected());
    }

    private void initSounds() {
        if (playSoundUrgent == null) {
            playSoundUrgent = MediaPlayer.create(getActivity(), URGENT_AUDIO_FILE);
        }
        if (playSoundEmergency == null) {
            playSoundEmergency = MediaPlayer.create(getActivity(), EMERGENCY_AUDIO_FILE);
        }
    }

    private void playUrgentAudio(boolean isSelected) {
        if (playSoundEmergency != null && playSoundEmergency.isPlaying()) {
            playSoundEmergency.pause();
        }
        if (isSelected) {
            playSoundUrgent.start();
        } else {
            playSoundUrgent.pause();
        }
    }

    private void playEmergencyAudio(boolean isSelected) {
        if (playSoundUrgent != null && playSoundUrgent.isPlaying()) {
            playSoundUrgent.pause();
        }
        if (isSelected) {
            playSoundEmergency.start();
        } else {
            playSoundEmergency.pause();
        }
    }

    private void cancelSounds() {
        if (playSoundUrgent != null && playSoundUrgent.isPlaying()) {
            playSoundUrgent.stop();
            playSoundUrgent.release();
            playSoundUrgent = null;
        }
        if (playSoundEmergency != null && playSoundEmergency.isPlaying()) {
            playSoundEmergency.stop();
            playSoundEmergency.release();
            playSoundEmergency = null;
        }
    }


    private void pickFromGallery() {
        mPresenter.requestPermissions();
    }

    @Override
    public void permissionResult(boolean granted) {
        if (granted) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                    .setType("image/*")
                    .addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }

            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)),
                    0);
        }
    }

    @Override
    public void groupCreateResult(boolean created) {
        if (created) {
            getBaseActivity().onFragmentDetached(TAG);
            generateGroupSelector.groupCreated();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {

                final Uri selectedUri = data.getData();

                Uri destinationUri = Uri.fromFile(new File(getCacheDir(),
                        "sca_cropped_thumbnail" + getMimeType(getActivity(), selectedUri)));

                if (selectedUri != null) {
                    cropImage(selectedUri, destinationUri);
                } else {

                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(UCrop.getOutput(data));
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
//      handleCropError(data);
        }
    }

    @OnClick(R.id.generate_group_create_button)
    void createClick(View v) {
        if (nameEdittext.getText().toString() != null
                & nameEdittext.getText().toString().length() > 2) {
            if (croppedUri != null) {
                mPresenter.upload(buildingId, croppedUri, nameEdittext.getText().toString(),
                        descEdittext.getText().toString());
            } else {
                CreateEditGroupRequest createEditGroupRequest = new CreateEditGroupRequest();
                createEditGroupRequest.setName(nameEdittext.getText().toString());
                createEditGroupRequest.setDescription(descEdittext.getText().toString());
                mPresenter.createNoThumbnail(buildingId, createEditGroupRequest);
            }


        }
    }

    private void handleCropResult(Uri uri) {
        croppedUri = uri;
        defaultThumbnailImage.setImageURI(uri);
        defaultThumbnailText.setText(R.string.create_thumbnail);
    }

    public String getMimeType(Context context, Uri uri) {
        String extension;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap
                    .getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }
}

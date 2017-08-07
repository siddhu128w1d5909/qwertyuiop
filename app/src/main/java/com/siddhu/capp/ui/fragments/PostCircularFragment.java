/*
package com.siddhu.capp.ui.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.siddhu.capp.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

*/
/**
 * Created by siddhu on 8/7/2017.
 *//*

public class PostCircularFragment extends BaseFragment {

    private View view;
    private Button mSelectOrCaptureImageBtn;
    private CircleImageView imageview;
    private static final String IMAGE_DIRECTORY = "/CollegeApp";
    private int GALLERY = 1, CAMERA = 2;
    private static final int CAMERA_PERMISSION = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
       view = inflater.inflate(R.layout.fragment_post_circular, container, false);
        initUi(view);
        return view;

    }

    private void initUi(View view) {
        mSelectOrCaptureImageBtn = (Button) view.findViewById(R.id.select_or_capture);
        imageview = (CircleImageView) view.findViewById(R.id.circle_iv);
        mSelectOrCaptureImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);

                } else {
                    showPictureDialog();
                }

            }
        });
    }
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode == CAMERA_PERMISSION && (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) ){

            }else if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    //Show permission explanation dialog...
                } else {
                    showDialogToenableCamera();
                    //Never ask again selected, or device policy prohibits the app from having that permission.
                    //So, disable that feature, or fall back to another situation...
                }
            }
        }

        public  void showDialogToenableCamera() {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity(),R.style.alertDialogTheme);
            alertDialog.setMessage("you need to give Permission").setCancelable(false);

            alertDialog.setPositiveButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startAppSettingsConfigActivity();
                }
            });
            alertDialog.show();
        }

        public  void startAppSettingsConfigActivity() {
            final Intent i = new Intent();
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.setData(Uri.parse("package:" + this.getActivity().getPackageName()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            activity.startActivity(i);
        }
        private void showPictureDialog() {
            final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
            pictureDialog.setTitle("Choose Action");
            String[] pictureDialogItems = {
                    "Select photo from gallery",
                    "Capture photo from camera",
                    "Cancel"
            };
            pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                        case 2:
                            dialog.dismiss();
                            break;
                    }
                }
            });
            pictureDialog.show();
        }

        private void takePhotoFromCamera() {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }

        private void choosePhotoFromGallary() {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == this.RESULT_CANCELED){
                return;
            }
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        String path = saveImage(bitmap);
                        Toast.makeText(CircleImageActivity.this, "path"+path, Toast.LENGTH_SHORT).show();
                        imageview.setImageBitmap(getResizedBitmap(bitmap,150,150));

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(CircleImageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }

            } else if (requestCode == CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imageview.setImageBitmap(thumbnail);
                saveImage(thumbnail);
                Toast.makeText(CircleImageActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }
        }

        private String saveImage(Bitmap bitmap) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            byte [] byteArray = bytes.toByteArray();
            // String mEncodedBase64 = Base64.encodeToString(byteArray,Base64.DEFAULT);
            String mEncodedBase64 = ImageUtil.convert(bitmap);
            Log.d("TAG", "mEncodedBase64::--->" + mEncodedBase64);
            File wallpaperDirectory = new File(
                    Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            try {
                File f = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

                return f.getAbsolutePath();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "";
        }

        public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
            int width = image.getWidth();
            int height = image.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);
            // recreate the new Bitmap
            Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                    matrix, false);
            return resizedBitmap;
        }


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Search Book");
    }
}*/

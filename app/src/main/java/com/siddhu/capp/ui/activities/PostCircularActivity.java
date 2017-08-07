package com.siddhu.capp.ui.activities;

/**
 * Created by siddhu on 8/7/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.siddhu.capp.R;
import com.siddhu.capp.interactor.CircularsPostInteractor;
import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.presenter.CircularsPostPresenter;
import com.siddhu.capp.presenter.CircularsPostPresenterImpl;
import com.siddhu.capp.presenter.CircularsPresenter;
import com.siddhu.capp.presenter.CircularsPresenterImpl;
import com.siddhu.capp.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostCircularActivity extends Activity implements CircularsPostPresenter.CircularsPostView{
    private Button mSelectOrCaptureImageBtn;
    private CircleImageView imageview;
    private static final String IMAGE_DIRECTORY = "/CollegeApp";
    private int GALLERY = 1, CAMERA = 2;
    private static final int CAMERA_PERMISSION = 100;
    private CircularsPostPresenterImpl mCircularsPostPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post_circular);
        mSelectOrCaptureImageBtn = (Button)findViewById(R.id.select_or_capture);
        imageview = (CircleImageView)findViewById(R.id.circle_iv);
        mCircularsPostPresenter = new CircularsPostPresenterImpl();
        mSelectOrCaptureImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(PostCircularActivity.this, android.Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PostCircularActivity.this, new String [] {Manifest.permission.CAMERA},CAMERA_PERMISSION);

                }else{
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
                showDialogToenableCamera(this);
                //Never ask again selected, or device policy prohibits the app from having that permission.
                //So, disable that feature, or fall back to another situation...
            }
        }
    }

    public static void showDialogToenableCamera(final Activity activity) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity, R.style.alertDialogTheme);
        alertDialog.setMessage("You need to give permission to access").setCancelable(false);

        alertDialog.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setNegativeButton(activity.getString(R.string.action_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettingsConfigActivity(activity);
            }
        });
        alertDialog.show();
    }

    public static void startAppSettingsConfigActivity(Activity activity) {
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
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
                    Toast.makeText(PostCircularActivity.this, "path"+path, Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(getResizedBitmap(bitmap,150,150));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PostCircularActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(PostCircularActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte [] byteArray = bytes.toByteArray();
         String mEncodedBase64 = Base64.encodeToString(byteArray,Base64.DEFAULT);
       // String mEncodedBase64 = ImageUtil.convert(bitmap);
        Log.d("TAG", "mEncodedBase64::--->" + mEncodedBase64);
        makeCircularPostServiceCall(mEncodedBase64);
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

    private void makeCircularPostServiceCall(String mEncodedBase64) {

        CircularsResponse circularsResponse = new CircularsResponse();
        circularsResponse.setTitle("Campus");
        circularsResponse.setSubject("fjskhfjksdvnfjkvbfdjkgvnfkgjnfdkkabnfjkdbdjkf");
        circularsResponse.setImage(mEncodedBase64);
        circularsResponse.setCreatedAt("f93285947548");
        mCircularsPostPresenter.postCiculars(circularsResponse);
        Utility.showProgressDialog(this);
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

    @Override
    public void onStart() {
        super.onStart();
        mCircularsPostPresenter.attachView(this);
    }



    @Override
    public void onStop() {
        super.onStop();
        mCircularsPostPresenter.detachView();

    }

    @Override
    public void onCircularsPostSuccessful(CircularsResponse circularsResponse) {
        Utility.hideProgressDialog();
        if(circularsResponse != null){
            Utility.showAlertMessage(this,"Do you want post one more Circular");
        }
    }

    @Override
    public void onCircularsPostFailed(APIError apiError) {
        Utility.hideProgressDialog();
        Log.e("Error", apiError.toString());
    }

    @Override
    public void onCircularsPostFailed(Throwable t) {
        Utility.hideProgressDialog();
        Log.e("Error", t.toString());
    }

    @Override
    public void showHideProgress(boolean show) {

    }
}

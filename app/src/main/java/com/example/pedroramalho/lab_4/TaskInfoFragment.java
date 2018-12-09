package com.example.pedroramalho.lab_4;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment implements View.OnClickListener {



    public static final int REQUEST_IMAGE_CAPTURE=1;
    public String mCurrentPhotoPath;
    public Task mDisplayedTask;


    public TaskInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();

        Task receivedTask = intent.getParcelableExtra(MainActivity.taskExtra);
        if(receivedTask != null)
            displayTask(receivedTask);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }

    public void displayTask(Task task){
        View displayedTaskView = getActivity().findViewById(R.id.displayFragment);
        displayedTaskView.setVisibility(View.VISIBLE);
        ((TextView)getActivity().findViewById(R.id.TitleTextView)).setText(task.title);
        ((TextView)getActivity().findViewById(R.id.descTextView)).setText(task.description);
        ((TextView)getActivity().findViewById(R.id.ageTextView)).setText(task.age);

        ImageView taskImage = (ImageView) getActivity().findViewById(R.id.taskImage);
        Random rand= new Random();
        int i = rand.nextInt(6);
        switch (i){

            case 0:
                taskImage.setImageResource(R.drawable.image1);
                break;
            case 1:
                taskImage.setImageResource(R.drawable.image2);
                break;
            case 2:
                taskImage.setImageResource(R.drawable.image4);
                break;
            case 3:
                taskImage.setImageResource(R.drawable.image3);
                break;
            case 4:
                taskImage.setImageResource(R.drawable.image5);
                break;
            case 5:
                taskImage.setImageResource(R.drawable.image6);
                break;
        }

        mDisplayedTask = task;

    }





    @Override
    public void onClick(View v) {

        Intent takePictureIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){

            File photoFile =null;
            try{
                photoFile = createImageFile();
                
            }catch (IOException ex){

            }

            if(photoFile != null){
                Uri photoURI=FileProvider.getUriForFile(getActivity(),
                        "com.example.pedroramalho.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_" + timeStamp + "-";
        File storageDir=getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath=image.getAbsolutePath();
        return image;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            mDisplayedTask.addPicPath(mCurrentPhotoPath);
            ImageView taskImage = (ImageView) getActivity().findViewById(R.id.taskPhoto);
        }
    }


}

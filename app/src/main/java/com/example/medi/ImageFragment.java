package com.example.medi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;


public class ImageFragment extends Fragment implements View.OnClickListener {
    private static final String CLOUD_VISION_API_KEY = "AIzaSyBnCoP_1U56eHh2-1tM_-x42Lz4XD3xHkE";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";

    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    String key = "QmrYeGvEgMdF16p%2BmzZxs%2FsLedKbnltlEJsbJ17SqI14N%2B6fM3pg2J94ktIZYXtFk3oxlTjxrAW7pKbE1aXZwQ%3D%3D";
    String data;

    String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Bitmap bitmap;

    @BindView(R.id.res_photo)
    ImageView resPhoto;

    @BindView(R.id.res_photo_txt)
    EditText resPhotoText;

    @BindView(R.id.result_view)
    TextView resultView;

    public void imageSetupFragment(Bitmap bitmap) {
        if (bitmap != null) {
            this.bitmap = bitmap;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        checkPermissions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);
        //check if bitmap exist, set to ImageView

        Button b = (Button) view.findViewById(R.id.search_button);
        b.setOnClickListener(this);

        if (bitmap != null) {
            if(flagPermissions){
                try{
                    callCloudVision(bitmap);
                    resPhoto.setImageBitmap(bitmap);
                } catch (IOException e){
                    Log.d(TAG, e.getMessage());
                }
            }

        }
        return view;
    }
    @SuppressLint("NewApi")
    void checkPermissions(){
        if (!hasPermissions(this, PERMISSIONS)){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
            flagPermissions = false;
        }
        flagPermissions = true;
    }

    public boolean hasPermissions(ImageFragment context, String... permissions){
        if (context != null && permissions != null){
            for(String permission : permissions){
                if(ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }
    @SuppressLint("StaticFieldLeak")
    private void callCloudVision(final Bitmap bitmap) throws IOException {

        new AsyncTask<Object, Void, String>() {

            @Override
            protected String doInBackground(Object... objects) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                        @Override
                        protected void initializeVisionRequest(VisionRequest visionRequest) throws IOException {
                            super.initializeVisionRequest(visionRequest);

                            String packageName = getActivity().getPackageName();
                            visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);
                            PackageInfo sig = null;
                            try {
                                sig = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                        }
                    };
                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();
                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        Image base64EncodedImage = new Image();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature textDetection = new Feature();
                            textDetection.setType("TEXT_DETECTION");
                            textDetection.setMaxResults(10);
                            add(textDetection);
                        }});
                        add(annotateImageRequest);
                    }});
                    Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "created Cloud Vision request object, sending request...");
                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);
                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API reqeust because of other IOExcepetion " + e.getMessage());
                }
                return "Cloud vision API request failed. Check logs for detail..";
            }

            protected void onPostExecute(String result) {

                resPhotoText.setText(result);
            }
        }.execute();
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "";
        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();
        if (labels != null) {
            message += labels.get(0).getDescription();
        } else {
            message = "아무 글자도 인식되지 않았습니다";
        }
        return message;
    }
    // editText 값가져와서 DB 에 검색할 것 : String number = resPhotoText.getText().toString();

    @Override
    public void onClick(View v){
        switch( v.getId() ){
            case R.id.search_button:
                Log.i("TAG","onclick success");

                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        data= getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                resultView.setText(data); //TextView에 문자열  data 출력
                            }
                        });
                    }
                }).start();

                break;
        }
    }//OnClick method..


    String getXmlData(){

        StringBuffer buffer=new StringBuffer();
        String code  = resPhotoText.getText().toString();//EditText에 작성된 Text얻어오기

        //String location = URLEncoder.encode(String);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String queryUrl = "http://apis.data.go.kr/B551182/msupCmpnMeftInfoService/getMajorCmpnNmCdList?ServiceKey="+key
                +"&numOfRows=10&pageNo=1&gnlNmCd="+code;

        //code : 100101AGN
        // 보험코드 찾을 수가 없어서 일반명 코드로 바꿈..
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("divNm")){
                            buffer.append("분류명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("fomnTpNm")){
                            buffer.append("제형구분명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("gnlNm")){
                            buffer.append("일반명 :");
                            xpp.next();
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("gnlNmCd")){
                            buffer.append("일반명 코드 :");
                            xpp.next();
                            buffer.append(xpp.getText());//telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("injcPthNm")){
                            buffer.append("투여 경로명 :");
                            xpp.next();
                            buffer.append(xpp.getText());//address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("iqtyTxt")){
                            buffer.append("함량 내용 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("meftDivNo")){
                            buffer.append("약효분류번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("unit")){
                            buffer.append("단위 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....


}

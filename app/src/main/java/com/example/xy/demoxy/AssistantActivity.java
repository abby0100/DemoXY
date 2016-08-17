package com.example.xy.demoxy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.FormatException;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class AssistantActivity extends Activity {

    private static final int REQUEST_CODE = 0x01;
    private static final int QR_WIDTH = 100;
    private static final int QR_HEIGHT = 100;

    private final String LOG_TAG = getClass().getSimpleName();

    private TextView tip;
    private Button buttonLoadPicture;
    private Button buttonExit;
    private View.OnClickListener mButtonClickListener;
    private String photo_path;
    private Bitmap scanBitmap;
    private ImageView sweepIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        init();
        String url = "Hello from xy!";
        generateQRCode(url);
    }

    private void init() {
        tip         = (TextView) findViewById(R.id.tip);
        buttonLoadPicture    = (Button) findViewById(R.id.load_pic);
        buttonExit  = (Button) findViewById(R.id.exit);
        sweepIV = (ImageView) findViewById(R.id.qr_code);

        mButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.load_pic:
//                        loadPicture();
                        break;
                    case R.id.exit:
                        break;
                    default:
                        break;
                }
            }
        };
        buttonLoadPicture.setOnClickListener(mButtonClickListener);
        buttonExit.setOnClickListener(mButtonClickListener);
    }

    private void generateQRCode(String url) {

        try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1)
            {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++)
            {
                for (int x = 0; x < QR_WIDTH; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            sweepIV.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }

//    private void loadPicture() {
//
//        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
//        if (Build.VERSION.SDK_INT < 19) {
//            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        }
//
//        innerIntent.setType("image/*");
//        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
//        startActivityForResult(wrapperIntent, REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            switch (requestCode) {
//
//                case REQUEST_CODE:
//
//                    String[] proj = { MediaStore.Images.Media.DATA };
//                    // 获取选中图片的路径
//                    Cursor cursor = getContentResolver().query(data.getData(),
//                            proj, null, null, null);
//
//                    if (cursor.moveToFirst()) {
//
//                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        photo_path = cursor.getString(column_index);
//                        if (photo_path == null) {
//                            photo_path = Utils.getPath(getApplicationContext(),
//                                    data.getData());
//                            Log.i("123path  Utils", photo_path);
//                        }
//                        Log.i("123path", photo_path);
//
//                    }
//
//                    cursor.close();
//
//                    new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            Result result = scanningImage(photo_path);
//                            // String result = decode(photo_path);
//                            if (result == null) {
//                                Looper.prepare();
//                                Toast.makeText(getApplicationContext(), "图片格式有误", 0)
//                                        .show();
//                                Looper.loop();
//                            } else {
//                                Log.i("123result", result.toString());
//                                // Log.i("123result", result.getText());
//                                // 数据返回
//                                String recode = recode(result.toString());
//                                Intent data = new Intent();
//                                data.putExtra("result", recode);
//                                setResult(300, data);
//                                finish();
//                            }
//                        }
//                    }).start();
//                    break;
//
//            }
//
//        }
//    }
//
//
//    protected Result scanningImage(String path) {
//        if (TextUtils.isEmpty(path)) {
//
//            return null;
//
//        }
//        // DecodeHintType 和EncodeHintType
//        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
//        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 先获取原大小
//        scanBitmap = BitmapFactory.decodeFile(path, options);
//        options.inJustDecodeBounds = false; // 获取新的大小
//
//        int sampleSize = (int) (options.outHeight / (float) 200);
//
//        if (sampleSize <= 0)
//            sampleSize = 1;
//        options.inSampleSize = sampleSize;
//        scanBitmap = BitmapFactory.decodeFile(path, options);
//
//
//
//        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
//        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//        QRCodeReader reader = new QRCodeReader();
//        try {
//
//            return reader.decode(bitmap1, hints);
//
//        } catch (Resources.NotFoundException e) {
//
//            e.printStackTrace();
//
//        } catch (ChecksumException e) {
//
//            e.printStackTrace();
//
//        } catch (FormatException e) {
//
//            e.printStackTrace();
//
//        }
//
//        return null;
//
//    }
}

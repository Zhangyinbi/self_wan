package com.domain.library.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.domain.library.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;

public class ImageDownloadUtils {

    private static final String FILE_PROVIDER = ".fileprovider";
    private static final String DEFAULT_SUFIX = ".jpeg";
    private static final int BUFFER_SIZE = 4096;

    public static void downLoadImage(final Context context, String url) {
        File savePath = StorageUtils.getNioImageDir(context);
        downLoadImageObservable(context, url, savePath == null ? "" : savePath.getAbsolutePath())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(context, context.getString(R.string.gallery_down_success, s), Toast.LENGTH_LONG).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context, R.string.gallery_down_fail, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static Observable<String> downLoadImageObservable(final Context context, final String url, final String path) {
        return Observable
                .fromCallable(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        File file = Glide.with(context)
                                         .load(url)
                                         .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                         .get();
                        String fileName = getFileNameFromUrl(url);
                        if (TextUtils.isEmpty(fileName)) fileName = file.getName();
                        return saveImage(context, file, path, fileName);
                    }
                });
    }

    private static String saveImage(Context context, File file, String path, String name) throws Exception {
        if (file == null || TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("image can not be null");
        }
        File dir = new File(path);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("can not get save dir");
        }

        File imageFile = new File(dir, name);
        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream(imageFile);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            int ins = in.read(buffer);
            if (ins == -1) {
                in.close();
                out.flush();
                out.close();
                break;
            } else {
                out.write(buffer, 0, ins);
            }
        }
        exportToGallery(context, imageFile);
        return imageFile.getCanonicalPath();
    }

    private static void exportToGallery(Context context, File file) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        } else {
            ContentValues values = new ContentValues(2);
            values.put(MediaStore.Video.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + FILE_PROVIDER, file);
            } else {
                uri = Uri.parse("file://" + file.getAbsolutePath());
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        }
    }

    private static String getFileNameFromUrl(String url) {
        if (TextUtils.isEmpty(url)) return null;
        String name = null;
        try {
            Uri uri = Uri.parse(url);
            String seg = uri.getLastPathSegment();
            if (!TextUtils.isEmpty(seg)) {
                if (!seg.contains(".")) seg = seg + DEFAULT_SUFIX;
                name = seg;
            }
        } catch (Exception e) {
        }
        return name;
    }
}

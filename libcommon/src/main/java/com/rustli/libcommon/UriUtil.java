package com.rustli.libcommon;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class UriUtil {

    private static final String TAG = "UriPathUtil";

    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d(TAG, "getFilePathByUri: uri = " + uri);
            Log.d(TAG, "getFilePathByUri: DocumentsContract.isDocumentUri(context, uri) = " + DocumentsContract.isDocumentUri(context, uri));
//            if (DocumentsContract.isDocumentUri(context, uri)) {
            Log.d(TAG, "getFilePathByUri: isDocumentUri");
            if (isCustomDocument(uri)) {
                Log.d(TAG, "getFilePathByUri: isCustomDocument");
                // ExternalStorageProvider
                final String docId = uri.toString();
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("content".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + split[1].substring(33);
                    Log.d(TAG, "getFilePathByUri: path = " + path);
                }
                Log.d(TAG, "getFilePathByUri: type = " + type);
                Log.d(TAG, "getFilePathByUri: split[1] = " + split[1]);
                if ("primary".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                    return path;
                }
            }
            if (isExternalStorageDocument(uri)) {
                Log.d(TAG, "getFilePathByUri: isExternalStorageDocument");
                // ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                    return path;
                }
            } else if (isDownloadsDocument(uri)) {
                Log.d(TAG, "getFilePathByUri: isDownloadsDocument");
                // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                path = getDataColumn(context, contentUri, null, null);
                return path;
            } else if (isMediaDocument(uri)) {
                Log.d(TAG, "getFilePathByUri: isMediaDocument");
                // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                path = getDataColumn(context, contentUri, selection, selectionArgs);
                return path;
            }
//            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isCustomDocument(Uri uri) {
        Log.d(TAG, "isCustomDocument: uri.getAuthority = " + uri.getAuthority());
        return "com.deepcam.clientapp.provider".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getPathByUri(final Context context, final Uri uri) {
        String path = uri.getPath();
        Log.d(TAG, "onActivityResult: path = " + path);
        final int splitIndex = path.indexOf('/', 1);
        path = Uri.decode(path.substring(splitIndex + 1));
        Log.d(TAG, "onActivityResult: Uri.decode path = " + path);
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path;
        return path;
    }

    /**
     * des: 反射的方式通过uri获取path
     * @param
     */
    public static String getFPUriToPath(Context context, Uri uri) {
        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            String fileProviderClassName = FileProvider.class.getName();
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (uri.getAuthority() != null && uri.getAuthority().equals(provider.authority)) {
                            if (provider.name.equalsIgnoreCase(fileProviderClassName)) {
                                Class<FileProvider> fileProviderClass = FileProvider.class;
                                try {
                                    Method getPathStrategy = fileProviderClass.getDeclaredMethod("getPathStrategy", Context.class, String.class);
                                    getPathStrategy.setAccessible(true);
                                    Object invoke = getPathStrategy.invoke(null, context, uri.getAuthority());
                                    if (invoke != null) {
                                        String PathStrategyStringClass = FileProvider.class.getName() + "$PathStrategy";
                                        Class<?> PathStrategy = Class.forName(PathStrategyStringClass);
                                        Method getFileForUri = PathStrategy.getDeclaredMethod("getFileForUri", Uri.class);
                                        getFileForUri.setAccessible(true);
                                        Object invoke1 = getFileForUri.invoke(invoke, uri);
                                        if (invoke1 instanceof File) {
                                            return ((File) invoke1).getAbsolutePath();
                                        }
                                    }
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

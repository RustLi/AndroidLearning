package com.rustli.libcommon;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {
    private static final String TAG = "FileUtil";
    /**
     * des: 获取应用私有目录
     * @param
     */
    public static String getAppPrivateDirectory(@NonNull Context context, String fileName) throws IOException {
        final File selectFace = context.getExternalFilesDir(fileName);
        if (selectFace == null) {
            throw new IOException("Unable to access application external storage.");
        }

        if (!selectFace.isDirectory() && !selectFace.mkdir()) {
            throw new IOException("Unable to create model root directory: " +
                    selectFace.getAbsolutePath());
        }
        return selectFace.getAbsoluteFile().toString();
    }

    /**
     * des: 获取字符串的Md5
     * @param
     */
    public String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : encryption) {
                if (Integer.toHexString(0xff & b).length() == 1) {
                    stringBuilder.append("0").append(Integer.toHexString(0xff & b));
                } else {
                    stringBuilder.append(Integer.toHexString(0xff & b));
                }
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e){
            return "";
        }
    }

    /**
     * des: 文件转String
     * @param
     */
    private static String loadFileAsString(String targetPath) throws IOException{
        StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(targetPath));

        char[] buf = new char[1024];
        int numRead;
        while((numRead = reader.read(buf)) != -1){
            String buffer = String.valueOf(buf, 0, numRead);
            result.append(buffer);
        }
        reader.close();

        return result.toString();
    }

    /**
     * des: 将assets中文件复制到sd卡中
     * @param assetDir assets下的目录
     * @param dir sd卡目录
     */
    public static void CopyAssetsToFile(Context context , String assetDir, String dir) {
        String[] files;
        try {
            files = context.getResources().getAssets().list(assetDir);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return;
        }
        if (files == null || files.length <= 0){
            return;
        }
        File mWorkingPath = new File(dir);
        if (!mWorkingPath.exists()) {
            mWorkingPath.mkdirs();
        }
        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssetsToFile(context,fileName, dir + fileName + "/");
                    } else {
                        CopyAssetsToFile(context,assetDir + "/" + fileName, dir + fileName + "/");
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists())
                    outFile.delete();
                InputStream in;
                if (0 != assetDir.length()){
                    in = context.getAssets().open(assetDir + "/" + fileName);
                } else{
                    in = context.getAssets().open(fileName);
                }
                OutputStream out = new FileOutputStream(outFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final String path =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/skynet/test";
    private void writeFile(String sb) throws Exception {
        Log.d(TAG, "writeFile: sb = " + sb);
        if (TextUtils.isEmpty(sb)){
            return;
        }
        File file = new File(path);
        if (!file.exists()){
            boolean createRet = file.mkdirs();
            Log.d(TAG, "writeFile: createRet = " + createRet);
        }
        String pFilePath = path + File.separator + "test.log";
        file = new File(pFilePath);
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write(sb.getBytes());
        fos.flush();
        fos.close();
    }

}

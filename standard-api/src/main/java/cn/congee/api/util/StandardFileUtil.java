package cn.congee.api.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:13
 **/
@Slf4j
public class StandardFileUtil extends FileUtils {

    public static boolean isXmlFile(File file) {
        return "xml".equalsIgnoreCase(getFileExtension(file.getName()));
    }

    /**
     * 文件后缀名
     *
     * @param fullName
     * @return
     */
    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * 不带后缀名的文件名
     *
     * @param file
     * @return
     */
    public static String getNameWithoutExtension(String file) {
        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 验证文件是否存在，如果不存在则抛出异常
     *
     * @param filePath
     * @throws IOException
     */
    public static void isFileExistThrowException(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
    }

    public static BufferedReader newBufferedReader(File file, Charset charset) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static BufferedWriter newBufferedWriter(File file, Charset charset) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

    public static boolean createParentDirs(File file) throws IOException {
        File parent = file.getCanonicalFile().getParentFile();
        if (parent == null) {
            return false;
        }
        return parent.mkdirs();
    }

    public static boolean createNotExistParentDirFile(File file) throws IOException {
        boolean createParentDirsRes = createParentDirs(file);
        if (!createParentDirsRes) {
            throw new IOException("cannot create parent Directory of " + file.getName());
        }
        return file.createNewFile();
    }

    /**
     * MultipartFile->File
     *
     * @param file
     * @return
     */
    public static File multipartFileToFile(MultipartFile file){
        File toFile = null;
        try {
            if(file.equals("") || file.getSize() <= 0){
                file = null;
            }else {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }
        }catch (Exception e){
            log.error("MultipartFile->File报错: " + e.getMessage());
            e.printStackTrace();
        }finally {
            return toFile;
        }
    }

    /**
     * File->MultipartFile
     *
     * @param file
     * @return
     */
    public static MultipartFile fileToMultipartFile(File file){
        MultipartFile multipartFile = null;
        try{
            InputStream inputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), inputStream);
        }catch (IOException e){
            log.error("File->MultipartFile报错: " + e.getMessage());
            e.printStackTrace();
        }finally {
            return multipartFile;
        }
    }

    /**
     * InputStream->File
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static File asFile(InputStream inputStream) throws IOException{
        File tmp = File.createTempFile("wgb", ".tmp", new File("/home/YHF/图片"));
        try{
            OutputStream os = new FileOutputStream(tmp);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        }catch (IOException e){
            log.error("InputStream->File报错: " + e.getMessage());
            e.printStackTrace();
        }finally {
            return tmp;
        }
    }

    /**
     * 获取流文件
     *
     * @param ins
     * @param file
     */
    public static void inputStreamToFile(InputStream ins, File file){
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1){
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }catch (Exception e){
            log.error("获取流文件报错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void deleteTempFile(File file){
        if(file != null){
            File del = new File(file.toURI());
            del.delete();
        }
    }

}

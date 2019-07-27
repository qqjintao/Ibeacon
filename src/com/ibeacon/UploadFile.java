package com.ibeacon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFile {
	public static String upload(MultipartFile file) throws IOException{
		// Tomcat服务区
		String dirName = "";
		if (file.getContentType().contains("image")) {
			dirName = "resources/upload/image";
		} else if (file.getContentType().contains("flash")) {
			dirName = "resources/upload/flash";
		} else if (file.getContentType().contains("media")) {
			dirName = "resources/upload/media";
		} else {
			dirName = "resources/upload/file";
		}
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		String path = request.getSession().getServletContext().getRealPath("/"); 
		String tomcatPath=path+dirName;
		ImageBussiness.createDir(tomcatPath);// 调用方法创建目录
		// 获取文件上传路径
		String imageName=ImageBussiness.generateFileName(file.getOriginalFilename());
		String filePath = tomcatPath+ "/" + imageName;
		System.out.println("上传的文件名：" + filePath);
		System.out.println("上传的路径：" + dirName);
		File target = new File(filePath);
		// 调用uploadFile方法进行文件复制
		uploadFile(target, multipartToFile(file));
		// 本服务区
		System.out.println("//本服务区-------------------");
		String dirName1 = "D:/MyEclipse2017/Ibeacon/WebContent/" + dirName + "/" + imageName;
		// 创建目录
		String localPath="D:/MyEclipse2017/Ibeacon/WebContent/" + dirName;
		System.out.println(localPath);
		ImageBussiness.createDir(localPath);// 调用方法创建目录
		File target1 = new File(dirName1);
		// 调用uploadFile方法进行文件复制
		uploadFile(target1, multipartToFile(file));
		return dirName+ "/"+imageName;
	}

	// MultipartFile 转换成File
	private static File multipartToFile(MultipartFile multfile) throws IOException {
		CommonsMultipartFile cf = (CommonsMultipartFile) multfile;
		// 这个myfile是MultipartFile的
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File file = fi.getStoreLocation();
		// 手动创建临时文件
		File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + file.getName());
		multfile.transferTo(tmpFile);
		return tmpFile;
	}  
	// 复制文件
	public static void uploadFile(File target, File source) {
		try {
			// 创建输入流对象
			FileInputStream fis = new FileInputStream(source);
			DataInputStream dis = new DataInputStream(fis);
			// 创建输出流对象
			FileOutputStream fos = new FileOutputStream(target);
			DataOutputStream dos = new DataOutputStream(fos);
			int temp;
			while ((temp = dis.read()) != -1) {
				dos.write(temp);
			}
			// 关闭
			dis.close();
			fis.close();
			dos.close();
			fos.close();
			System.out.println("复制成功！");
		} catch (FileNotFoundException ex) {
			System.out.println("文件找不到");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("文件读写异常");
			ex.printStackTrace();
		}
	}
	
}

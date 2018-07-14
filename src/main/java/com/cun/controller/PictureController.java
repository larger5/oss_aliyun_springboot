package com.cun.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.cun.entity.Picture;
import com.cun.service.PictureService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Controller
@RequestMapping("/picture")
@EnableSwagger2
public class PictureController {

	@Autowired
	private PictureService pictureService;

	/**
	 * 主页
	 * @return
	 */
	@GetMapping("/")
	public String toIndex() {
		return "add";
	}
	
	/**
	 * 全
	 * @return
	 */
	@ResponseBody
	@GetMapping("/all")
	public List<Picture> getAllPictures() {
		return pictureService.getAllPictures();
		
	}

	/**
	 * 增
	 * @param picture
	 * @throws IOException 
	 * @throws ClientException 
	 * @throws OSSException 
	 */
	@PostMapping("/insert")
	public String insertPicture(@RequestParam("fileupload") MultipartFile fileupload, String picName,HttpServletRequest request) throws OSSException, ClientException, IOException {
		Picture picture=new Picture();
		picture.setPicName(picName);
		picture.setUrl(getUrl(fileupload));//虽然传来的是文件，但是保存到数据库的是路径
		pictureService.insertPicture(picture);
		return "show";
	}

	/**
	 * 改
	 * @param picture
	 */
	@ResponseBody
	@PutMapping("/update")
	public void updatePicture(Picture picture) {
		pictureService.updatePicture(picture);
	}

	/**
	 * 删
	 * @param id
	 */
	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public void deletePicture(@PathVariable("id") Integer id) {
		pictureService.deletePicture(id);
	}
	
	/**
	 * 查
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping("/get/{id}")
	public Picture getPicture(@PathVariable("id") Integer id) {
		return pictureService.getPicture(id);
	}
	
	/**
	 * 把文件保存到阿里云OSS，返回路径保存到数据库
	 * @param fileupload
	 * @return
	 * @throws OSSException
	 * @throws ClientException
	 * @throws IOException
	 */
	public String getUrl(MultipartFile fileupload) throws OSSException, ClientException, IOException {
		String endpoint = "oss-cn-shenzhen.aliyuncs.com";
		String accessKeyId = "LTAIxxxu6vu4R";
		String accessKeySecret = "VuCGomJWxxxyqokEh1ywnifIR9QKR";

		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		// 文件桶
		String bucketName = "itaem";
		// 文件名格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		// 该桶中的文件key
		String dateString = sdf.format(new Date()) + ".jpg";// 20180322010634.jpg
		// 上传文件
		ossClient.putObject("itaem", dateString, new ByteArrayInputStream(fileupload.getBytes()));

		// 设置URL过期时间为100年，默认这里是int型，转换为long型即可
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 100);
		// 生成URL
		URL url = ossClient.generatePresignedUrl(bucketName, dateString, expiration);
		return url.toString();
	}

}

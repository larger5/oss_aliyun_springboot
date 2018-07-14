package com.cun.service;

import java.util.List;

import com.cun.entity.Picture;

public interface PictureService {

	/**
	 * 删
	 * @param id
	 */
	void deletePicture(Integer id);

	/**
	 * 改
	 * @param picture
	 */
	void updatePicture(Picture picture);

	/**
	 * 查
	 * @param id
	 * @return
	 */
	Picture getPicture(Integer id);

	/**
	 * 增
	 * @param picture
	 */
	void insertPicture(Picture picture);
	
	/**
	 * 全
	 * @return
	 */
	List<Picture> getAllPictures();

}

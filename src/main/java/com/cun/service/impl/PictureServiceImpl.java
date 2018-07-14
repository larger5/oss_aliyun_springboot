package com.cun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cun.dao.PictureDao;
import com.cun.entity.Picture;
import com.cun.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService{
	
	@Autowired
	private PictureDao pictureDao;

	@Override
	public void deletePicture(Integer id) {
		pictureDao.delete(id);
	}

	@Override
	public void updatePicture(Picture picture) {
		pictureDao.save(picture);
	}

	@Override
	public Picture getPicture(Integer id) {
		return pictureDao.getOne(id);
	}

	@Override
	public void insertPicture(Picture picture) {
		pictureDao.save(picture);
	}

	@Override
	public List<Picture> getAllPictures() {
		return pictureDao.findAll();
	}
	

}

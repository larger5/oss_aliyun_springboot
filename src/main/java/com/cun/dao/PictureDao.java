package com.cun.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cun.entity.Picture;

public interface PictureDao extends JpaRepository<Picture, Integer>{

}

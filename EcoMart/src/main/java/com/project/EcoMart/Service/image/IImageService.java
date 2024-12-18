package com.project.EcoMart.Service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.EcoMart.Model.Image;
import com.project.EcoMart.dtos.ImageDto;

public interface IImageService {

	Image getImageById(Long id);

	void deleteImageById(Long id);

	List<ImageDto> saveImage(List<MultipartFile> files, Long prodId);

	void updateImage(MultipartFile file, Long imageId);

}

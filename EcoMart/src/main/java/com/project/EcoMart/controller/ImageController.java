package com.project.EcoMart.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.EcoMart.Service.image.IImageService;
import com.project.EcoMart.response.ApiResponse;

import dto.ImageDto;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

	@Autowired
	private IImageService imageService;

	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long prodid) {
		try {
			List<ImageDto> imageDtos = imageService.saveImage(files, prodid);
			return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!", e.getMessage()));
		}
	}

//	public ResponseEntity<ApiResponse> 

}

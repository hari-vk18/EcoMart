package com.project.EcoMart.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.EcoMart.Model.Image;
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

	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
		Image image = imageService.getImageById(imageId);
		ByteArrayResource resource = new ByteArrayResource(
				image.getImage().getBytes(1, (int) image.getImage().length()));
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
				.body(resource);
	}

}

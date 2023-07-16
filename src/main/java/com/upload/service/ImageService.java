package com.upload.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upload.entity.Image;
import com.upload.repository.ImageRepository;

@Service
public class ImageService {
	@Autowired
	private ImageRepository imageRepository;
	
	public String saveImage(MultipartFile file, String name, String description) throws IOException {
		Image image = new Image();
		image.setName(name);
		image.setDescription(description);
		image.setImage(file.getBytes());
		imageRepository.save(image);
		return "Image saved in DB";
	}

	public List<Image> getAllData() {
		List<Image> findAll = imageRepository.findAll();
		return findAll.stream().collect(Collectors.toList());
	}
	
	public Page<Image> getUsersSorted(int pageNumber, int pageSize, String sortDirection, String sortField) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
		Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Image> images = imageRepository.findAll(pageable);
		List<Image> userDTOs = images.stream().collect(Collectors.toList());
		return new PageImpl<>(userDTOs, pageable, images.getTotalElements());
		}
	

	public Image getById(long id) {
		return imageRepository.findById(id).get();
	}

	public Image updateById(MultipartFile file, String name, String description, long id) {
	    Image image = imageRepository.findById(id).get();
	    
	    // Update the fields with the provided values
	    image.setName(name);
	    image.setDescription(description);

	    // Check if a new file is provided and update the image accordingly
	    if (file != null) {
	        try {
	            byte[] fileBytes = file.getBytes();
	            image.setImage(fileBytes);
	        } catch (IOException e) {
	            // Handle the exception according to your application's error handling strategy
	            e.printStackTrace();
	        }
	    }

	    Image updatedImage = imageRepository.save(image);
	    return updatedImage;
	}


	public void deleteDeleteByIb(long id) {
		imageRepository.findById(id).get();
		imageRepository.deleteById(id);
	}
}

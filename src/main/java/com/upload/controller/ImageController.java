package com.upload.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upload.entity.Image;
import com.upload.service.ImageService;

@RestController
@RequestMapping("/imageOrFile")
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping("/save")
	public String saveIMageOrFile(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("description") String description) throws IOException {
		return imageService.saveImage(file, name, description);
	}
	
	
	@GetMapping("/get")
	public ResponseEntity<List<Image>>GetAllData(){
		List<Image> allData = imageService.getAllData();
		return new ResponseEntity<>(allData, HttpStatus.OK);
	}
	

	@GetMapping("/getOne/{id}")
	public ResponseEntity<Image>getByid(@PathVariable long id){
		Image byId = imageService.getById(id);
		return new ResponseEntity<Image>(byId,HttpStatus.OK);
		
	}
	
	//http://localhost:8080/imageOrFile/getall?pageNumber=1&pageSize=10&sortDirection=asc&sortField=id
	@GetMapping("/getall")
	public ResponseEntity<Page<Image>> getUsersSorted(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "asc") String sortDirection,
			@RequestParam(defaultValue = "id") String sortField) {
			Page<Image> imagePage = imageService.getUsersSorted(pageNumber, pageSize, sortDirection, sortField);
			return ResponseEntity.ok(imagePage);
	}
			
	@PutMapping("/update/{id}")
	public ResponseEntity<Image>updateById(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@PathVariable("id")long id){
		Image updateById = imageService.updateById(file, name, description,id);
		return new ResponseEntity<Image>(updateById,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable long id){
		imageService.deleteDeleteByIb(id);
		return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);
	}
//	@GetMapping("/")
//	public String sayHello() {
//		return "Welcome.html";
//	}
//
//	@GetMapping("/fetch")
//	public String welcome() {
//		return "Welcome welcome!";
//	}


}

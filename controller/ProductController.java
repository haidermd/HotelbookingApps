package com.boot.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.boot.FileUploadUtils;
import com.boot.entity.Admin;
import com.boot.entity.Product;
import com.boot.entity.Users;
import com.boot.repositorys.ProductRepository;
import com.boot.services.AdminServices;
import com.boot.services.ProductServices;

@Controller
public class ProductController {
	     @Autowired
         ProductRepository productRepository;
	     
	     @Autowired
	 	 private ProductServices productDao;
	     
	    @GetMapping("/add-hotels-list")
	 	public String getCreateContactPage(Model model) {
	 		Product product=new Product();
	 		model.addAttribute("newProduct", product);
	 		//model.addAttribute("adding", true);
	 		return "product/addProduct";
	 	}
	    @PostMapping("/add-hotels-list")
		public String addNewProduct(@ModelAttribute("newProduct") @Valid Product product,
				                    @RequestParam("image") MultipartFile multipartFile,
				                    BindingResult result,Model model) throws IOException {
			if (result.hasErrors()) {
				//model.addAttribute("adding", true);
				return "product/addProduct";
			}	
			
			if (product != null) {				
				   String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				   
				   String uploadDir ="src/main/webapp/images/product/";
				   FileUploadUtils.saveFile(uploadDir, filename, multipartFile);
				   product.setImageUrl("images/"+filename);
				   productRepository.saveProduct(product);				  
				  
			}
			
			 return "redirect:/viewProduct";
		}
	    @GetMapping("/viewProduct")
		public String getHomePage(Model model, HttpServletRequest request) {
			
				model.addAttribute("products", productRepository.getProducts());			
			return "product/pView";
		}
	    
	    
	    
	    @GetMapping("/updateProduct")
		public String updateProductStatus(@RequestParam("p_id") Integer p_id) {
			if (p_id != null) {
				productDao.updateProduct(productDao.findProductByPid(p_id));
			}
			return "redirect:/viewProduct";
		}
		
	   @GetMapping("/deleteProduct")
		public String editHotel(@RequestParam("pId") Integer pId) {
			if (pId != null) {
				productDao.deleteProduct(productDao.findProductByPid(pId));
			}
			return "redirect:/viewProduct";
		}

		@GetMapping("/editProduct")
		public String getEditHotelPage(@RequestParam("pId") Integer pId, Model model) {
			model.addAttribute("newProduct", productDao.findProductByPid(pId));
			model.addAttribute("editing", true);
			return "product/addProduct";
		}

		@PostMapping("/editProduct")
		public String editContact(@ModelAttribute("newProduct") @Valid Product product, BindingResult result,
				HttpServletRequest request, Model model) {
			if (result.hasErrors()){
				model.addAttribute("editing", true);
				return "product/pView";
			}
				
			HttpSession session = request.getSession();
			//Users user = (Users) session.getAttribute("currentUser");
			if (product != null) {
				//product.setpId(null)
				productDao.editProduct(product);
			}
			return "redirect:/viewProduct";
		}
	    
	    
}

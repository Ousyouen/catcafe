package com.example.demo.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.example.demo.model.CatInfoMst;
import com.example.demo.service.CatInfoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/catManage")
public class CatInfoManageController {

	@Autowired
	private CatInfoService catInfoService;

	@GetMapping
	public String catMagementIndex() {
		return "catinfomanagement"; // 猫の管理ページを返す
	}

//分页查询
	@ResponseBody
	@GetMapping("/api/cats")
	public Map<String, Object> getAllCats(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		int offset = (page - 1) * pageSize;
		List<CatInfoMst> cats = catInfoService.getCatInfoList(offset, pageSize);
		int totalCats = catInfoService.getTotalCatCount();
		int totalPages = (int) Math.ceil((double) totalCats / pageSize);

		Map<String, Object> response = new HashMap<>();
		response.put("cats", cats);
		response.put("totalCats", totalCats);
		response.put("totalPages", totalPages);
		response.put("currentPage", page);

		return response;
	}

//根据id查询
	@ResponseBody
	@GetMapping("/api/cat/{id}")
	public CatInfoMst getCatInfoById(@PathVariable Long id) {
		return catInfoService.getCatInfoById(id);
	}

//新增猫咪
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addCatInfo(@RequestParam("name") String name,
			@RequestParam("personality") String personality, @RequestParam("age") Integer age,
			@RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request) {
		if (name == null || name.isEmpty() || personality == null || personality.isEmpty() || age == null) {
			Map<String, String> response = new HashMap<>();
			response.put("message", "他の情報を入力してください");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		CatInfoMst catInfo = new CatInfoMst();
		catInfo.setCatName(name);
		catInfo.setCatIntro(personality);
		catInfo.setCatAge(age);

		if (image != null && !image.isEmpty()) {
			try {
				// 获取 ServletContext

				// 修改图片保存的路径为相对路径
				String uploadDir = "src/main/resources/static/images/";

				// 确保目标文件夹存在
				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdirs();
				}

				// 获取现有文件数目
				File[] files = directory.listFiles();
				int nextImageNumber = files != null ? files.length + 1 : 1; // 确保下一个图片的编号

				// 构建新的图片文件名，确保命名规律
				String uniqueImageName = "catImage" + nextImageNumber + ".jpg";

				// 构建保存路径
				Path path = Paths.get(uploadDir + File.separator + uniqueImageName);
				Files.write(path, image.getBytes());

				// 设置相对路径
				catInfo.setCatImage("images/" + uniqueImageName);

			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Collections.singletonMap("message", "画像のアップロードに失敗しました"));
			}
		}

		int result = catInfoService.addCatInfo(catInfo);
		Map<String, String> response = new HashMap<>();
		if (result > 0) {
			response.put("message", "猫の情報が追加されました");
			if (catInfo.getCatImage() != null) {
				response.put("imagePath", catInfo.getCatImage());
			}
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "猫の情報の追加に失敗しました");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

//更新猫咪信息
	@PutMapping("/update")
	@ResponseBody
	public Map<String, String> updateCatInfo(@RequestParam("id") Long id, @RequestParam("name") String name,
			@RequestParam("personality") String personality, @RequestParam("age") Integer age,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		Map<String, String> response = new HashMap<>();
		CatInfoMst catInfo = new CatInfoMst();
		catInfo.setId(id);
		catInfo.setCatName(name);
		catInfo.setCatIntro(personality);
		catInfo.setCatAge(age);

		if (image != null && !image.isEmpty()) {
			String imageName = "catImage" + System.currentTimeMillis() + ".jpg"; // 确保图片文件名规律
			try {
				// 获取相对路径
				Path path = Paths.get("src/main/resources/static/images/" + imageName);
				Files.write(path, image.getBytes());
				catInfo.setCatImage("images/" + imageName);
			} catch (IOException e) {
				e.printStackTrace();
				response.put("message", "画像のアップロードに失敗しました");
				return response;
			}
		} else {
			// 如果图片为空，保留原来的图片路径
			CatInfoMst existingCat = catInfoService.getCatInfoById(id);
			catInfo.setCatImage(existingCat.getCatImage());
		}

		int result = catInfoService.updateCatInfo(catInfo);
		if (result > 0) {
			response.put("message", "猫の情報が更新されました");
		} else {
			response.put("message", "猫の情報の更新に失敗しました");
		}
		return response;
	}

//删除猫咪，把deleteCatInfo设置为1
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public Map<String, String> deleteCatInfo(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		int result = catInfoService.deleteCatInfo(id);
		if (result > 0) {
			response.put("message", "猫の情報が削除されました");
		} else {
			response.put("message", "猫の情報の削除に失敗しました");
		}
		return response;
	}

	// 根据名字和年龄进行查询猫咪
	@ResponseBody
	@GetMapping("/api/searchCats")
	public Map<String, Object> searchCats(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		int offset = (page - 1) * pageSize;
		List<CatInfoMst> cats = catInfoService.searchCats(name, age, offset, pageSize);
		int totalCats = catInfoService.getTotalCatCountBySearch(name, age);
		int totalPages = (int) Math.ceil((double) totalCats / pageSize);

		Map<String, Object> response = new HashMap<>();
		response.put("cats", cats);
		response.put("totalCats", totalCats);
		response.put("totalPages", totalPages);
		response.put("currentPage", page);

		return response;
	}

}

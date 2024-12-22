package com.example.demo.controller.admin;

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

@Controller
@RequestMapping("/catManage")
public class CatInfoManageController {
	@Autowired
	private CatInfoService catInfoService;

	@GetMapping
	public String catMagementIndex() {
		return "catinfomanagement";// 返回猫咪管理页面
	}

	@GetMapping("/api/cats")
	@ResponseBody
	public List<CatInfoMst> getAllCats() {
		return catInfoService.getCatInfoList(); // 这里返回一个猫咪列表
	}

	@GetMapping("/api/cat/{id}")
	@ResponseBody
	public CatInfoMst getCatInfoById(@PathVariable Long id) {
		return catInfoService.getCatInfoById(id); // 通过ID获取猫咪信息
	}

	// 新增猫咪
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addCatInfo(@RequestParam("name") String name,
			@RequestParam("personality") String personality, @RequestParam("age") Integer age,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		// 检查表单是否填写完整
		if (name == null || name.isEmpty() || personality == null || personality.isEmpty() || age == null) {
			Map<String, String> response = new HashMap<>();
			response.put("message", "请输入其他信息"); // 提示用户填写完整信息
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		CatInfoMst catInfo = new CatInfoMst();
		catInfo.setCatName(name);
		catInfo.setCatIntro(personality);
		catInfo.setCatAge(age);

		// 图片上传处理
		if (image != null && !image.isEmpty()) {
			String imageName = image.getOriginalFilename();

			// 生成唯一的文件名，避免文件冲突
			String uniqueImageName = System.currentTimeMillis() + "_" + imageName;

			try {
				// 设置图片保存路径（项目的 static/images 目录下）
				Path path = Paths.get(
						"C:/Users/OuSyoen/Desktop/catcafe/catcafe/src/main/resources/static/images/" + uniqueImageName);
				Files.write(path, image.getBytes());

				// 保存图片的相对路径（用于前端显示）
				catInfo.setCatImage("images/" + uniqueImageName); // 注意，这里保存的是图片相对路径
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Collections.singletonMap("message", "图片上传失败"));
			}
		}

		// 调用 Service 层保存数据
		int result = catInfoService.addCatInfo(catInfo);
		Map<String, String> response = new HashMap<>();

		// 根据保存结果返回不同的响应
		if (result > 0) {
			response.put("message", "猫咪信息新增成功");
			if (catInfo.getCatImage() != null) {
				response.put("imagePath", catInfo.getCatImage()); // 返回图片的路径给前端
			}
			return ResponseEntity.ok(response); // 返回成功消息
		} else {
			response.put("message", "猫咪信息新增失败");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 返回失败消息
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

		// 如果图片被更改，保存新图片
		if (image != null && !image.isEmpty()) {
			String imageName = image.getOriginalFilename();
			try {
				// 保存新图片
				Path path = Paths
						.get("C:/Users/OuSyoen/Desktop/catcafe/catcafe/src/main/resources/static/images/" + imageName);
				Files.write(path, image.getBytes());
				catInfo.setCatImage("images/" + imageName); // 更新猫咪图片路径
			} catch (IOException e) {
				e.printStackTrace();
				response.put("message", "图片上传失败");
				return response; // 返回上传失败的消息
			}
		} else {
			// 如果没有上传新图片，保持原来的图片路径不变
			CatInfoMst existingCat = catInfoService.getCatInfoById(id);
			catInfo.setCatImage(existingCat.getCatImage()); // 保持原图片路径
		}

		// 调用 Service 层更新数据
		int result = catInfoService.updateCatInfo(catInfo);
		if (result > 0) {
			response.put("message", "猫咪信息更新成功");
		} else {
			response.put("message", "猫咪信息更新失败");
		}
		return response;
	}

//删除猫咪
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public Map<String, String> deleteCatInfo(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		int result = catInfoService.deleteCatInfo(id);
		if (result > 0) {
			response.put("message", "猫咪信息删除成功");
		} else {
			response.put("message", "猫咪信息删除失败");
		}
		return response;
	}
}
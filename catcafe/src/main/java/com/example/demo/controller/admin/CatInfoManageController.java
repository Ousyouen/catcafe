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

	// 猫の管理ページを返す
	@GetMapping
	public String catMagementIndex() {
		return "catinfomanagement"; // 猫の管理ページを返す
	}

	// ページネーション付きで猫情報を取得
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

	// IDで猫情報を取得
	@ResponseBody
	@GetMapping("/api/cat/{id}")
	public CatInfoMst getCatInfoById(@PathVariable Long id) {
		return catInfoService.getCatInfoById(id);
	}

	// 新しい猫情報を追加
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

		// 画像がある場合、アップロード処理を実行
		if (image != null && !image.isEmpty()) {
			try {
				// 画像の保存パスを相対パスに変更
				String uploadDir = "src/main/resources/static/images/";

				// 画像を保存するフォルダが存在しない場合、作成
				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdirs();
				}

				// 現在のファイル数を取得
				File[] files = directory.listFiles();
				int nextImageNumber = files != null ? files.length + 1 : 1; // 次の画像番号を確保

				// 新しい画像のファイル名を作成
				String uniqueImageName = "catImage" + nextImageNumber + ".jpg";

				// 保存パスを作成
				Path path = Paths.get(uploadDir + File.separator + uniqueImageName);
				Files.write(path, image.getBytes());

				// 相対パスを設定
				catInfo.setCatImage("images/" + uniqueImageName);

			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Collections.singletonMap("message", "画像のアップロードに失敗しました"));
			}
		}

		// 猫情報の追加
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

	// 猫情報の編集
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

		// 画像がある場合、アップロード処理を実行
		if (image != null && !image.isEmpty()) {
			String imageName = "catImage" + System.currentTimeMillis() + ".jpg"; // 生成唯一图片文件名
			try {
				Path path = Paths.get("src/main/resources/static/images/" + imageName);
				Files.write(path, image.getBytes());
				catInfo.setCatImage("images/" + imageName); // 设置相对路径
			} catch (IOException e) {
				e.printStackTrace();
				response.put("message", "画像のアップロードに失敗しました");
				return response;
			}
		} else {
			// もし新しい画像がなければ、既存の画像パスを保持
			CatInfoMst existingCat = catInfoService.getCatInfoById(id);
			catInfo.setCatImage(existingCat.getCatImage());
		}

		// 猫情報を更新
		int result = catInfoService.updateCatInfo(catInfo);

		if (result > 0) {
			response.put("message", "猫の情報が更新されました");
		} else {
			response.put("message", "猫の情報の更新に失敗しました");
		}

		// 結果をログに記録
		System.out.println("Update Result: " + result); // コンソールにログを出力

		return response;
	}

	// 猫情報を削除（deleteCatInfoを1に設定）
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

	// 猫情報検索のAPI
	@GetMapping("/search/api/cats")
	public ResponseEntity<Map<String, Object>> searchCats(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer age) {
		List<CatInfoMst> cats = catInfoService.searchCats(name, age);

		// JSON形式でレスポンスを返す
		Map<String, Object> response = new HashMap<>();
		response.put("cats", cats); // 猫の情報をそのまま返す
		return ResponseEntity.ok(response);
	}
}

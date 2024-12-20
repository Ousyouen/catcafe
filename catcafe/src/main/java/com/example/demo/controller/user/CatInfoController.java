package com.example.demo.controller.user;

import com.example.demo.model.CatInfoMst;
import com.example.demo.service.CatInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CatInfoController {

    @Autowired
    private CatInfoService catInfoService;

    @GetMapping("/cats")
    public String getAllCats(Model model) {
        List<CatInfoMst> cats = catInfoService.getCatInfoList();
        model.addAttribute("cats", cats);
        return "cats";  // ビュー名を返す（ビュー解析器がこの名前に対応するページを探します）
    }
    
    // すべての猫の情報を取得
    @GetMapping("/list")
    public String getCatInfoList(Model model) {
        List<CatInfoMst> catInfoList = catInfoService.getCatInfoList();
        model.addAttribute("catInfoList", catInfoList);  // 猫の情報をモデルに追加
        return "cats";  // ビュー名を返す（ビュー解析器がこの名前に対応するページを探します）
    }

    // 猫のIDで情報を取得（返すビュー名「cat/detail」）
    @GetMapping("/{id}")
    public String getCatInfoById(@PathVariable Long id, Model model) {
        CatInfoMst catInfo = catInfoService.getCatInfoById(id);
        model.addAttribute("catInfo", catInfo);  // 特定の猫の情報をモデルに追加
        return "cats/detail";  // ビュー名を返す、詳細な猫情報を表示するページ
    }

    // 猫の情報を追加（成功メッセージを表示）
    @PostMapping("/add")
    public String addCatInfo(@ModelAttribute CatInfoMst catInfo, Model model) {
        int result = catInfoService.addCatInfo(catInfo);
        String message = result > 0 ? "猫の情報が追加されました" : "追加失敗";
        model.addAttribute("message", message);  // 結果メッセージをモデルに追加
        return "cats/add";  // 追加後の確認ページを返す
    }

    // 猫の情報を更新（成功メッセージを表示）
    @PutMapping("/update")
    public String updateCatInfo(@ModelAttribute CatInfoMst catInfo, Model model) {
        int result = catInfoService.updateCatInfo(catInfo);
        String message = result > 0 ? "猫の情報が更新されました" : "更新失敗";
        model.addAttribute("message", message);  // 結果メッセージをモデルに追加
        return "cats/update";  // 更新後の確認ページを返す
    }

    // 猫の情報をソフト削除（成功メッセージを表示）
    @DeleteMapping("/delete/{id}")
    public String deleteCatInfo(@PathVariable Long id, Model model) {
        int result = catInfoService.deleteCatInfo(id);
        String message = result > 0 ? "猫の情報が削除されました" : "削除失敗";
        model.addAttribute("message", message);  // 結果メッセージをモデルに追加
        return "cats/delete";  // 削除結果のページを返す
    }
}

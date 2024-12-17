package com.example.demo.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    // 处理点赞请求
    @PostMapping("/like")
    public LikeResponse like(@RequestParam int likeCount) {
        // 返回新的点赞数
        return new LikeResponse(likeCount);
    }

    // 点赞响应对象
    public static class LikeResponse {
        private int newLikeCount;

        public LikeResponse(int newLikeCount) {
            this.newLikeCount = newLikeCount;
        }

        public int getNewLikeCount() {
            return newLikeCount;
        }

        public void setNewLikeCount(int newLikeCount) {
            this.newLikeCount = newLikeCount;
        }
    }
}

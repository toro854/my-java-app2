package com.example.demo;

import org.springframework.stereotype.Component;

@Component // これを付けるとSpring Bootが自動で管理してくれるようになります
public class ResourceManager {

    // 画像が格納されているフォルダのパス
    private final String IMAGE_DIR = "/images/";

    /**
     * 数字に応じたPNG画像のパスを返す
     * 例: 5 を渡すと "/images/5.png" を返す
     */
    public String getImagePath(int n) {
    	return "/images/" + n + ".png";
    }
}
package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnzanController {
    // 状態を保持するため static でインスタンス化
    private static final FlashAnzanLogic logic = new FlashAnzanLogic();

    @GetMapping("/")
    public String index(Model model) {
        // 常に最新のフラグ状態を渡す
        boolean started = logic.isGameStarted();
        model.addAttribute("gameStarted", started);
        model.addAttribute("level", logic.getLevel());
        model.addAttribute("mode", logic.getMode());
        model.addAttribute("score", logic.getScore());
        model.addAttribute("highScore", logic.getHighScore());

        // ゲーム中なら、必ず数字と選択肢を生成してモデルに入れる
        if (started) {
            List<Integer> numbers = logic.generateQuestions();
            model.addAttribute("numbers", numbers); // これがJSの numbers になります
            model.addAttribute("answer", logic.getTotalSum());
            model.addAttribute("choices", logic.getChoices());
            model.addAttribute("speed", logic.getDisplaySpeed());
            
            System.out.println("Numbers generated: " + numbers); // Javaのコンソールで確認用
        }
        return "index";
    }

    @GetMapping("/start")
    public String start(
        @RequestParam(defaultValue = "normal") String mode,
        @RequestParam(defaultValue = "1") int startLevel) { // レベルを受け取る
        
        logic.startGame(mode, startLevel);
        return "redirect:/";
    }

    @GetMapping("/reset")
    public String reset() {
        logic.reset();
        return "redirect:/";
    }

    @GetMapping("/next")
    public String next(@RequestParam(defaultValue = "0") double bonus) {
        logic.addScore(bonus); // スコア加算
        logic.incrementLevel();
        return "redirect:/";
    }
}
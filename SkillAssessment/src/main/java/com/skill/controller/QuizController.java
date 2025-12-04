package com.skill.controller;

import com.skill.model.QuizQuestion;
import com.skill.model.QuizResult;
import com.skill.repository.QuizQuestionRepository;
import com.skill.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class QuizController {
     @Autowired
    private QuizQuestionRepository quizQuestionRepository;


  //  @Autowired
  //  private QuizQuestionRepository questionRepo;

    @Autowired
private QuizResultRepository quizResultRepository;

   // @Autowired
  //  private QuizResultRepository resultRepo;

    private List<QuizQuestion> quizQuestions;


    public class ErrorHandlerController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error"; // create error.html to show fallback
    }
}

    @GetMapping("/select-level")
    public String showselectLevel() {
        return "select-level";  // HTML form to choose Easy/Medium/Hard
    }



    @PostMapping("/study")
    public String studyMaterial(@RequestParam("level") String level, Model model) {
        System.out.println("Selected Level: " + level); 
        List<QuizQuestion> questions = quizQuestionRepository.findByLevel(level);
if (questions == null || questions.size() < 20) {
        model.addAttribute("error", "Not enough questions for this level.");
        return "select-level";
    }
        quizQuestions = getRandomQuestions(questions, 10); // pick 10 from 20
        model.addAttribute("studyQuestions", questions);
        return "study-material";
    }
    @GetMapping("/start-quiz")
    public String startQuiz(Model model) {
        model.addAttribute("quizQuestions", quizQuestions);
        return "quiz-page";
    }

    



    @PostMapping("/submitQuiz")
public String submitQuiz(@RequestParam Map<String, String> formData, Model model) {
    int score = 0;
    List<QuizResult> results = new ArrayList<>();

    for (String key : formData.keySet()) {
        if (!key.startsWith("answer_")) continue;

        int questionId = Integer.parseInt(key.replace("answer_", ""));
        QuizQuestion question = quizQuestionRepository.findById(questionId).orElse(null);
        if (question == null) continue;

        String userAnswer = formData.get(key).toLowerCase();
        String[] keywords = question.getKeywords().toLowerCase().split(",");
        boolean matched = false;

        for (String keyword : keywords) {
            if (userAnswer.contains(keyword.trim())) {
                matched = true;
                break;
            }
        }

        if (matched) score++;

        QuizResult result = new QuizResult(
            question.getQuestion(),
            question.getKeywords(),
            userAnswer,
            matched
        );

        results.add(result);
        quizResultRepository.save(result);
    }

    model.addAttribute("score", score);
    model.addAttribute("results", results);
    return "result-page";
}

  private List<QuizQuestion> getRandomQuestions(List<QuizQuestion> list, int count) {
        Collections.shuffle(list);
        return list.subList(0, Math.min(count, list.size()));
    }
}


      // result.html


    /*@PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam Map<String, String> responses, Model model) {
        int score = 0;
        List<QuizResult> results = new ArrayList<>();

        for (QuizQuestion q : quizQuestions) {
            String userAnswer = responses.get("answer_" + q.getId()).toLowerCase();
            String[] keywords = q.getKeywords().toLowerCase().split(",");

            boolean matched = Arrays.stream(keywords).anyMatch(userAnswer::contains);
            if (matched) score++;

            results.add(new QuizResult(q.getQuestion(), q.getKeywords(), userAnswer, matched));
        }

        resultRepo.saveAll(results);
        model.addAttribute("results", results);
        model.addAttribute("score", score);
        return "result-page";
    }*/
  
/*package com.skill.controller;

import com.skill.model.QuizQuestion;
import com.skill.model.QuizResult;
import com.skill.repository.QuizQuestionRepository;
import com.skill.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class QuizController {

    @Autowired
    private QuizQuestionRepository questionRepo;

    @Autowired
    private QuizResultRepository resultRepo;

    private List<QuizQuestion> quizQuestions;

    // Show homepage
    @GetMapping("/")
    public String home() {
        return "index";  // index.html should have a "Start Quiz" button that links to /select-level
    }

    // Show level selection form
    @GetMapping("/select-level")
    public String showSelectLevel(Model model) {
        return "select-level";
    }

    // After selecting level, show 20 study questions
    @PostMapping("/study")
    public String studyMaterial(@RequestParam("level") String level, Model model) {
        List<QuizQuestion> questions = questionRepo.findByLevel(level);

        if (questions == null || questions.size() < 10) {
            model.addAttribute("error", "Not enough questions available for this level.");
            return "select-level";
        }

        quizQuestions = getRandomQuestions(questions, 10); // Store 10 random for quiz
        model.addAttribute("studyQuestions", questions);   // Show all 20 in study page
        model.addAttribute("level", level);
        return "study-material";
    }

    // Start quiz with 10 random questions
    @GetMapping("/start-quiz")
    public String startQuiz(Model model) {
        if (quizQuestions == null || quizQuestions.isEmpty()) {
            model.addAttribute("error", "Please study the material first.");
            return "select-level";
        }

        model.addAttribute("quizQuestions", quizQuestions);
        return "quiz-page";
    }

    // Submit quiz and calculate score
    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam Map<String, String> responses, Model model) {
        int score = 0;
        List<QuizResult> results = new ArrayList<>();
     for (QuizQuestion q : quizQuestions) {
    String userAnswer = responses.get("answer_" + q.getId());
    if (userAnswer == null) userAnswer = "";

    String[] keywords = q.getKeywords().toLowerCase().split(",");
    boolean matched = Arrays.stream(keywords)
                            .anyMatch(keyword -> userAnswer.toLowerCase().contains(keyword.trim()));

    if (matched) score++;

    results.add(new QuizResult(q.getQuestion(), q.getKeywords(), userAnswer, matched));
}

       



        resultRepo.saveAll(results);
        model.addAttribute("results", results);
        model.addAttribute("score", score);
        return "result-page";
    }

    // Helper method to get random questions
    private List<QuizQuestion> getRandomQuestions(List<QuizQuestion> list, int count) {
        Collections.shuffle(list);
        return list.subList(0, Math.min(count, list.size()));
    }
}*/

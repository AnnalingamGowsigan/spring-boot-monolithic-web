package com.spring.springbootmonolithicweb.service;

import com.spring.springbootmonolithicweb.Question;
import com.spring.springbootmonolithicweb.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public List<Question> getQuestions(){
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category){
        return questionDao.findByCategory(category);
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "Question added successfully";
    }

    public void initQuestions(String questionTitle, String option1, String option2, String option3, String option4,
                            String rightAnswer, String difficultyLevel, String category) {
        Question question = new Question();
        question.setQuestionTitle(questionTitle);
        question.setOption1(option1);
        question.setOption2(option2);
        question.setOption3(option3);
        question.setOption4(option4);
        question.setRightAnswer(rightAnswer);
        question.setDifficultyLevel(difficultyLevel);
        question.setCategory(category);

        questionDao.save(question);
    }
}

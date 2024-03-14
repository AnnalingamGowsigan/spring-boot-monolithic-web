package com.spring.springbootmonolithicweb.service;

import com.spring.springbootmonolithicweb.dao.QuestionDao;
import com.spring.springbootmonolithicweb.dao.QuizDao;
import com.spring.springbootmonolithicweb.model.Question;
import com.spring.springbootmonolithicweb.model.QuestionWrapper;
import com.spring.springbootmonolithicweb.model.Quiz;
import com.spring.springbootmonolithicweb.model.QuizAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<String> createQuiz(String category, String title, int numberOfQuestions) {
        try
        {
            List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numberOfQuestions);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("Quiz created successfully", org.springframework.http.HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Failed to create quiz", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {
        try
        {
            Optional<Quiz> quiz = quizDao.findById(id);
            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<QuestionWrapper>();
            for (Question question : questionsFromDB) {

                questionsForUser.add(new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()));
            }
            return new ResponseEntity<>(questionsForUser, org.springframework.http.HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> submitQuiz(int id, List<QuizAnswer> quizAnswers) {
        try
        {
            Optional<Quiz> quiz = quizDao.findById(id);
            List<Question> questionsFromDB = quiz.get().getQuestions();
            int score = 0;
            // Assuming QuizAnswer has a method to get the response for a question ID
            for (Question question : questionsFromDB) {
                // Find the corresponding quiz answer for the current question
                Optional<QuizAnswer> quizAnswer = quizAnswers.stream()
                        .filter(qa -> qa.getId().equals(question.getId()))
                        .findFirst();
                // Check if the quiz answer exists and if it matches the correct answer
                if (quizAnswer.isPresent() && question.getRightAnswer().equals(quizAnswer.get().getResponse())) {
                    score++;
                }
            }

            return new ResponseEntity<>(score, org.springframework.http.HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

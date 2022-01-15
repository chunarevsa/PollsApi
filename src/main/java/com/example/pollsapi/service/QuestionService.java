package com.example.pollsapi.service;

import java.util.Optional;
import java.util.Set;

import com.example.pollsapi.entity.Question;
import com.example.pollsapi.exception.ResourceNotFoundException;
import com.example.pollsapi.payload.QuestionRequest;
import com.example.pollsapi.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements DeleteInterface, QuestionServiceInterface {

	private final QuestionRepository questionRepository;

	@Autowired
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Override
	public Question addQuestion(QuestionRequest questionRequest) {
		
		Question newQuestion = new Question();
		newQuestion.setText(questionRequest.getText());
		newQuestion.setActive(questionRequest.getActive());
		newQuestion.setQuestionType(questionRequest.getQuestionType()); 
		//TODO: проверка
		return saveQuestion(newQuestion);
	}

	@Override
	public Optional<Question> editQuestion(QuestionRequest questionRequest, Long questionId) {
		
		Question question = findById(questionId);
		question.setText(questionRequest.getText());
		question.setActive(questionRequest.getActive());
		question.setQuestionType(questionRequest.getQuestionType());//TODO: проверка

		return Optional.of(saveQuestion(question));
	}

	private Question findById(Long questionId) {
		return questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
	}

	@Override
	public void delete(Long id) {
		Question question = findById(id);
		question.setActive(false);
		saveQuestion(question);
	}

	public void deleteQuestions(Set<Question> questions) {
		questions.forEach(question -> delete(question.getId()));
	}

	public Question getQuestionFromRequest(QuestionRequest questionRequest) {
		
		// валидация реквеста

		Question question = addQuestion(questionRequest);

		return question;
	}

	public void saveQuestions(Set<Question> questions) {
		questions.forEach(question -> saveQuestion(question));
	}

	private Question saveQuestion(Question question) {
		return questionRepository.save(question);
	}
	
}

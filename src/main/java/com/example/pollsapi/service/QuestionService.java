package com.example.pollsapi.service;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import com.example.pollsapi.entity.Answer;
import com.example.pollsapi.entity.Question;
import com.example.pollsapi.entity.QuestionType;
import com.example.pollsapi.exception.InvalidQuestionException;
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
		
		validateQuestionRequest(questionRequest);
		
		Question newQuestion = new Question();
		newQuestion.setBody(questionRequest.getText());
		newQuestion.setActive(questionRequest.getActive());
		newQuestion.setQuestionType(questionRequest.getQuestionType());
		newQuestion.setAnswers(questionRequest.getAnswers());
		//TODO: проверка
		return saveQuestion(newQuestion);
	}

	private void validateQuestionRequest(QuestionRequest questionRequest) {

		QuestionType questionType = questionRequest.getQuestionType();
		Set<Answer> answers = questionRequest.getAnswers();
		switch (questionType) {
			case TEXT_ANSWER:
				if (!answers.isEmpty()) {
					throw new InvalidQuestionException("Вопрос", "", questionType);
				}
				break;
			case ONE_ANSWER:
				if (answers.size() < 2) {
					throw new InvalidQuestionException("Вопрос", "", questionType);
				}
				break;
			case MANY_ANSWER:
				if (answers.size() < 2) {
				throw new InvalidQuestionException("Вопрос", "", questionType);
				}
				break;	
		}
	}

	@Override
	public Optional<Question> editQuestion(QuestionRequest questionRequest, 
				Set<Question> questions, int questionQueueId) {
		
		validateQuestionRequest(questionRequest);
		
		Question question = getQuestionFromQueue(questions, questionQueueId);

		question.setBody(questionRequest.getText());
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

	public Question getQuestionFromQueue(Set<Question> questions,int questionQueueId) {
		if (questions.size() < questionQueueId) {
			throw new ResourceNotFoundException("Вопрос", "номером", questionQueueId);
		}

		Iterator<Question> iterator = questions.iterator();
		
		for (int i = 1; i<questionQueueId; i++) {
			System.err.println(i);
			iterator.next();
		}
		return iterator.next();
	}

	public void saveQuestions(Set<Question> questions) {
		questions.forEach(question -> saveQuestion(question));
	}

	private Question saveQuestion(Question question) {
		return questionRepository.save(question);
	}
	
}

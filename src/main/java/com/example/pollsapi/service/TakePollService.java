package com.example.pollsapi.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.example.pollsapi.entity.Answer;
import com.example.pollsapi.entity.Question;
import com.example.pollsapi.entity.QuestionType;
import com.example.pollsapi.entity.UserAnswer;
import com.example.pollsapi.entity.UserPollAnswers;
import com.example.pollsapi.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class TakePollService {

    public UserPollAnswers getUserPollAnswers(Set<Question> pollQuestions) {
        
        UserPollAnswers userPollAnswers = new UserPollAnswers();
		Scanner in1 = new Scanner(System.in);
		Scanner in2 = new Scanner(System.in);
		Scanner in3 = new Scanner(System.in); 

		// Цикл вопросов 
		for (Question pollQuestion: pollQuestions) {

			// Список ответов в вопросе
			Set<Answer> pollAnswers = pollQuestion.getAnswers();
			// Тип вороса
			QuestionType questionType = pollQuestion.getQuestionType();

			// Тело вопроса
			System.out.println(pollQuestion.getBody());

			// Обработка ответов в зависимости от типа
			if (questionType == QuestionType.TEXT_ANSWER) {
				System.out.println("Введи ваш ответ: ");
				String userAnswerBody = in1.nextLine();
				UserAnswer userAnswer = new UserAnswer();
				userAnswer.setUserAnswerBody(userAnswerBody);
				userAnswer.setQuestionBody(pollQuestion.getBody());
				userPollAnswers.getUserAnswers().add(userAnswer);

			} else if (questionType == QuestionType.ONE_ANSWER) {
				// Вывод вопрос с номером
				Map<Integer, String> map = outPutAnswers(pollAnswers);
				System.out.println("Введите номер ответа :");
				int userNumber = in2.nextInt();
					
				if (!map.containsKey(userNumber)) {
					throw new ResourceNotFoundException("Ответ", "номером", userNumber);
				}

				String userAnswerFromMap = map.get(userNumber);
				Answer answerFromPoll = pollAnswers.stream().filter(answer -> answer.getText().equalsIgnoreCase(userAnswerFromMap))
						.findAny().orElseThrow(); 

				UserAnswer userAnswer = new UserAnswer();
				userAnswer.setUserAnswerBody(answerFromPoll.getText());
				userAnswer.setQuestionBody(pollQuestion.getBody());
				userPollAnswers.getUserAnswers().add(userAnswer);
	
			} else if (questionType == QuestionType.MANY_ANSWER) {
				Map<Integer, String> mapAnswers = outPutAnswers(pollAnswers);
				System.out.println("Введите номера ответов через запятую без пробела:");
				System.out.println("Пример :1,2");
				String userAnswers = in3.nextLine();

				String [] input = userAnswers.split(",");

				for (int i = 0; i < input.length; i++) {
					int parseInt = Integer.parseInt(input[i]);
					if (!mapAnswers.containsKey(parseInt)) {
						throw new ResourceNotFoundException("Ответ", "номером", parseInt);
					}

					String answerFromMap = mapAnswers.get(parseInt);
					Answer answerPoll = pollAnswers.stream().filter(answer -> answer.getText().equalsIgnoreCase(answerFromMap))
						.findAny().orElseThrow(); //TODO: искл
					
					UserAnswer userAnswer = new UserAnswer();
					userAnswer.setUserAnswerBody(answerPoll.getText());
					userAnswer.setQuestionBody(pollQuestion.getBody());
					userAnswer.setQuestion(pollQuestion);
					userPollAnswers.getUserAnswers().add(userAnswer);


				} // for

			} // if

		} // for question

		in1.close();
		in2.close();
		in3.close();
        return userPollAnswers;
    }
    
    private Map<Integer, String> outPutAnswers(Set<Answer> answers) {
		
		Iterator<Answer> iterator = answers.iterator();
		Map<Integer, String> map = new HashMap<>();

		int i = 1;
		while (iterator.hasNext()) {
			String textAnswer = iterator.next().getText();
			map.put(i, textAnswer);
			System.out.println(i + ": " + textAnswer);
			i++;
		}
		return map;
	} 


}

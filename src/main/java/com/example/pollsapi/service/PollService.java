package com.example.pollsapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import com.example.pollsapi.entity.Answer;
import com.example.pollsapi.entity.CompletedPoll;
import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.entity.Question;
import com.example.pollsapi.entity.QuestionType;
import com.example.pollsapi.entity.UserAnswers;
import com.example.pollsapi.exception.FinalDateException;
import com.example.pollsapi.exception.ResourceNotFoundException;
import com.example.pollsapi.payload.PollRequest;
import com.example.pollsapi.payload.QuestionRequest;
import com.example.pollsapi.repository.AnswerRepository;
import com.example.pollsapi.repository.CompletedPollRepository;
import com.example.pollsapi.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: активный опрос по времени и isActive
// проверка на дату, что бы не была меньше нау
@Service
public class PollService implements DeleteInterface, PollServiceInterface {

	private final PollRepository pollRepository;
	private final QuestionService questionService;
	private final AnswerRepository answerRepository;
	private final CompletedPollRepository completedPollRepository;

	@Autowired
	public PollService(PollRepository pollRepository, 
							QuestionService questionService,
							AnswerRepository answerRepository,
							CompletedPollRepository completedPollRepository) {
		this.pollRepository = pollRepository;
		this.questionService = questionService;
		this.answerRepository = answerRepository;
		this.completedPollRepository = completedPollRepository;
	}

	// только активных по дате и из актив
	@Override
	public List<Poll> getPolls() {
		
		return pollRepository.findAll();
	}

	/**
 *
- добавление/изменение/удаление вопросов в опросе. 
Атрибуты вопросов: текст вопроса, тип вопроса 
(ответ текстом, ответ с выбором одного варианта, 
ответ с выбором нескольких вариантов)

- получение списка активных опросов
- прохождение опроса: опросы можно проходить анонимно, 
в качестве идентификатора пользователя в API передаётся числовой ID, 
по которому сохраняются ответы пользователя на вопросы; 
один пользователь может участвовать в любом количестве опросов
	 * @param long1
 */

	@Override
	public Object start(Long pollId, Long userId) {
		// Провкрка проходил ли данный юзер опросы
		// проверка не проходил ли уже этот юзер этот опрос
		Poll poll = findById(pollId);
		CompletedPoll completedPoll = new CompletedPoll(poll);

		System.out.println("Старт опроса :" + pollId);
		Set<Question> questions = completedPoll.getQuestions();
		Set<Question> newSetQuestion = questions;

		Scanner in = new Scanner(System.in);
		Scanner in2 = new Scanner(System.in);

		for (Question question: questions) {
			System.out.println(question.getBody());
			QuestionType questionType = question.getQuestionType();

			switch (questionType) {
				case TEXT_ANSWER:
					System.out.println("Введи ваш ответ: ");
					String userAnswer = in2.nextLine();

					Answer newAnswer = new Answer();
					newAnswer.setText(userAnswer);
					answerRepository.save(newAnswer);
					question.getAnswers().add(newAnswer);

					break;
				
				case ONE_ANSWER:
					
					Set<Answer> answers = question.getAnswers();

					Map<Integer, String> map = outPutAnswers(answers);
					

					System.out.println("Введите номер ответа :");
					int userNumber = in.nextInt();

					if (!map.containsKey(userNumber)) {
						throw new ResourceNotFoundException("Ответ", "номером", userNumber);
					}

					String userAnswerFromMap = map.get(userNumber);
					Answer answerFromPoll = answers.stream().filter(answer -> answer.getText().equalsIgnoreCase(userAnswerFromMap))
							.findAny().orElseThrow(); //TODO: искл
					Set<Answer> newSetAnswer = new HashSet<>();
					newSetAnswer.add(answerFromPoll);
					question.setAnswers(newSetAnswer);
					break;

				case MANY_ANSWER:

					Set<Answer> setAnswers = question.getAnswers();
					Map<Integer, String> mapAnswers = outPutAnswers(setAnswers);
					
					System.out.println("Введите номера ответов через запятую без пробела:");
					System.out.println("Пример :1,3,4");
					String userAnswers = in2.nextLine();
					String [] input = userAnswers.split(",");
					Set<Answer> newUserSetAnswer = new HashSet<>();

					for (int i = 0; i < input.length; i++) {
						int parseInt = Integer.parseInt(input[i]);
						if (!mapAnswers.containsKey(parseInt)) {
							throw new ResourceNotFoundException("Ответ", "номером", parseInt);
						}
						String answerFromMap = mapAnswers.get(parseInt);
						Answer answerPoll = setAnswers.stream().filter(answer -> answer.getText().equalsIgnoreCase(answerFromMap))
							.findAny().orElseThrow(); //TODO: искл
						newUserSetAnswer.add(answerPoll);
					}
					question.setAnswers(newUserSetAnswer);
					break;

			}
			System.err.println("1");
			Question newQuestion = new Question(question);
			newQuestion.setPoll(completedPoll);
			System.err.println("2");
			newSetQuestion.add(newQuestion);
			System.err.println("3");

		}
		in.close();
		in2.close();

		System.err.println("4");
		questionService.saveQuestions(newSetQuestion);
		System.err.println("5");
		CompletedPoll savedCompletedPoll = saveCopletedPoll(completedPoll);
		System.err.println("6");

		UserAnswers newUserCompletedPoll = new UserAnswers();
		System.err.println("7");
		newUserCompletedPoll.setUserId(userId);
		System.err.println("8");
		newUserCompletedPoll.getCompletedPolls().add(savedCompletedPoll);
		System.err.println("9");

		return null;
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

	@Override
	public Optional<Poll> addPoll(PollRequest pollRequest) throws ParseException  {

		
		Instant expirationDate = getInstantDate(pollRequest.getExpirationDate());
		
		if (validateExpirationDate(expirationDate)) {
			throw new FinalDateException("Дата", pollRequest.getExpirationDate(), Instant.now());
		}

		Poll newPoll = new Poll();
		newPoll.setName(pollRequest.getName());
		newPoll.setActive(pollRequest.getActive());
		newPoll.setExpirationDate(expirationDate);
		newPoll.setDescription(pollRequest.getDescription());
		Poll savedPoll = savePoll(newPoll);

		return Optional.of(savedPoll);
	}

	private Instant getInstantDate(String expirationDate) throws ParseException {
		return new SimpleDateFormat("y-M-d").parse(expirationDate).toInstant();
	}

	@Override
	public Optional<Poll> editPoll(Long id, PollRequest pollRequest) throws ParseException {
		
		Instant expirationDate = getInstantDate(pollRequest.getExpirationDate());

		if (validateExpirationDate(expirationDate)) {
			throw new FinalDateException("Дата", pollRequest.getExpirationDate().toString(), Instant.now());
		}

		Poll poll = findById(id);
		poll.setName(pollRequest.getName());
		poll.setActive(pollRequest.getActive());
		poll.setExpirationDate(expirationDate);
		poll.setDescription(pollRequest.getDescription());
		Poll savedPoll = savePoll(poll);

		return Optional.of(savedPoll);
	}

	@Override
	public void delete(Long id) {

		Poll poll = findById(id);
		questionService.deleteQuestions(poll.getQuestions());
		poll.setActive(false);
		savePoll(poll);

	}

	public Optional<Question> addQuestion(QuestionRequest questionRequest, Long pollId) {
		
		Poll poll = findById(pollId);
		Question newQuestion = questionService.addQuestion(questionRequest);
		poll.getQuestions().add(newQuestion);
		questionService.saveQuestions(poll.getQuestions());
		savePoll(poll);
		return Optional.of(newQuestion);
	}

	public Optional<Question> editQuestion(QuestionRequest questionRequest, Long pollId, int questionQueueId) {
		Poll poll = findById(pollId);
		Set<Question> questions = poll.getQuestions();

		return questionService.editQuestion(questionRequest, questions, questionQueueId);
	}

	public void deleteQuestion(Long pollId, int questionQueueId) {
		Poll poll = findById(pollId);
		Set<Question> questions = poll.getQuestions();
		Question question = questionService.getQuestionFromQueue(questions ,questionQueueId);

		questionService.delete(question.getId());
	}

	private Poll findById(Long id) {
		return pollRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Poll", "id", id));
	}

	private boolean validateExpirationDate(Instant expirationDate) {
		return expirationDate.isBefore(Instant.now());
	}

	private Poll savePoll(Poll poll) {
		return pollRepository.save(poll);
	}

	private CompletedPoll saveCopletedPoll(CompletedPoll completedPoll) {
		return completedPollRepository.save(completedPoll);
	}	

}

package com.example.pollsapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Null;

import com.example.pollsapi.entity.Answer;
import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.entity.Question;
import com.example.pollsapi.entity.QuestionType;
import com.example.pollsapi.entity.UserAnswer;
import com.example.pollsapi.entity.UserPollAnswers;
import com.example.pollsapi.entity.UserPolls;
import com.example.pollsapi.exception.AlreadyUseException;
import com.example.pollsapi.exception.FinalDateException;
import com.example.pollsapi.exception.ResourceNotFoundException;
import com.example.pollsapi.payload.PollRequest;
import com.example.pollsapi.payload.QuestionRequest;
import com.example.pollsapi.repository.AnswerRepository;
import com.example.pollsapi.repository.PollRepository;
import com.example.pollsapi.repository.UserAnswerRepository;
import com.example.pollsapi.repository.UserPollAnswersRepository;
import com.example.pollsapi.repository.UserPollsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: активный опрос по времени и isActive
// проверка на дату, что бы не была меньше нау
@Service
public class PollService implements DeleteInterface, PollServiceInterface {

	private final PollRepository pollRepository;
	private final QuestionService questionService;
	private final AnswerRepository answerRepository;
	private final UserPollsRepository userPollsRepository;
	private final UserAnswerRepository userAnswerRepository;
	private final UserPollAnswersRepository userPollAnswersRepository;

	@Autowired
	public PollService(PollRepository pollRepository, 
							QuestionService questionService,
							AnswerRepository answerRepository,
							UserPollsRepository userPollsRepository,
							UserAnswerRepository userAnswerRepository,
							UserPollAnswersRepository userPollAnswersRepository) {
		this.pollRepository = pollRepository;
		this.questionService = questionService;
		this.answerRepository = answerRepository;
		this.userPollsRepository = userPollsRepository;
		this.userAnswerRepository = userAnswerRepository;
		this.userPollAnswersRepository = userPollAnswersRepository;
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
	 *
 */

	@Override
	public Object start(Long pollId, Long userUniqueId) {
	
		Poll poll = findById(pollId);

		System.out.println("Старт опроса :" + pollId);
		Set<Question> pollQuestions = poll.getQuestions();
		System.err.println("0");
		
		UserPolls userPolls = findUserPollsByUserUniqueId(userUniqueId);
		
		if (userPolls == null) {
			userPolls = new UserPolls();
			userPolls.setUserUniqueId(userUniqueId);
		} 

		// проврка на проходил ли уже этот опрос 
		UserPollAnswers find = userPolls.getUserPollAnswers().stream()
				.filter(userPollAnswers1 -> pollId.equals(userPollAnswers1.getPoll().getId()))
				.findAny().orElse(null);
		
		if (find != null) {
			throw new AlreadyUseException("Poll", "id", pollId);
		}

		UserPollAnswers userPollAnswers = new UserPollAnswers();

		Scanner in1 = new Scanner(System.in);
		Scanner in2 = new Scanner(System.in);
		Scanner in3 = new Scanner(System.in); 

		// Цикл вопросов 
		for (Question pollQuestion: pollQuestions) {
			
			System.err.println("1");
			// Список ответов в вопросе
			Set<Answer> pollAnswers = pollQuestion.getAnswers();
			// Тип вороса
			QuestionType questionType = pollQuestion.getQuestionType();

			System.err.println("2");
			
			// Создание ответов пользователя
			UserAnswer userAnswer = new UserAnswer();
			userAnswer.setQuestion(pollQuestion);
			userAnswer.setQuestionBody(pollQuestion.getBody());

			// Тело вопроса
			System.out.println(pollQuestion.getBody());

			// Обработка ответов в зависимости от типа
			switch (questionType) {
				case TEXT_ANSWER:
					System.out.println("Введи ваш ответ: ");
					String userAnswerBody = in1.nextLine();
					System.err.println("3");

					userAnswer.setUserAnswerBody(userAnswerBody);

					System.err.println("4");
					System.err.println("5");
					
						break;
				case ONE_ANSWER:

					System.err.println("6");
					/* Map<Integer, String> map = outPutAnswers(pollAnswers);
					System.err.println("7");
					System.out.println("Введите номер ответа :");
					int userNumber = in2.nextInt();
					System.err.println("8");
					
					if (!map.containsKey(userNumber)) {
						throw new ResourceNotFoundException("Ответ", "номером", userNumber);
					}

					String userAnswerFromMap = map.get(userNumber);
					System.err.println("9");

					Answer answerFromPoll = pollAnswers.stream().filter(answer -> answer.getText().equalsIgnoreCase(userAnswerFromMap))
							.findAny().orElseThrow(); 
					
					System.err.println("10");

					completedAnswers.add(answerFromPoll);
					System.err.println("11");
					completedQuestion.setAnswers(completedAnswers);
					
					System.err.println("12"); */
					break;
				case MANY_ANSWER:

					System.err.println("13");
					/* Map<Integer, String> mapAnswers = outPutAnswers(pollAnswers);
					System.out.println("Введите номера ответов через запятую без пробела:");
					System.out.println("Пример :1,3,4");
					String userAnswers = in3.nextLine();
					System.err.println("14");
					String [] input = userAnswers.split(",");
					System.err.println("15");
					System.err.println("16");

					for (int i = 0; i < input.length; i++) {
						int parseInt = Integer.parseInt(input[i]);
						if (!mapAnswers.containsKey(parseInt)) {
							throw new ResourceNotFoundException("Ответ", "номером", parseInt);
						}
						System.err.println("17");
						String answerFromMap = mapAnswers.get(parseInt);
						System.err.println("18");
						Answer answerPoll = pollAnswers.stream().filter(answer -> answer.getText().equalsIgnoreCase(answerFromMap))
							.findAny().orElseThrow(); //TODO: искл
							System.err.println("19");

						completedAnswers.add(answerPoll);

						System.err.println("20");
					}
					completedQuestion.setAnswers(completedAnswers); */

					System.err.println("21");
					break;

			} // swith

			// Добавляем ответ пользователя по этому вопросу
			userPollAnswers.getUserAnswers().add(userAnswer);
			System.err.println("22");
			
			System.err.println("23");

			System.err.println("24");
			

		} // for question

		userPollAnswers.getUserAnswers().stream()
				.map(saveAnswer -> saveAnswer(saveAnswer))
				.collect(Collectors.toSet());
		
		userPollAnswers.setPoll(poll);
		
		userPolls.getUserPollAnswers().add(userPollAnswers);

		userPolls.getUserPollAnswers().stream()
				.map(saveUserPollAnswers -> saveUserPollAnswers(saveUserPollAnswers))
				.collect(Collectors.toSet());

		UserPolls savedUserPolls = saveUserPolls(userPolls);

		System.err.println("36"); 

		return savedUserPolls;
	}

	

	private UserPolls saveUserPolls(UserPolls userPolls) {
		return userPollsRepository.save(userPolls);
	}

	private UserPolls findUserPollsByUserUniqueId(Long userUniqueId) {
		return userPollsRepository.findUserPollsByUserUniqueId(userUniqueId);
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

	private UserAnswer saveAnswer (UserAnswer userAnswer) {
		return userAnswerRepository.save(userAnswer);
	}

	private UserPollAnswers saveUserPollAnswers(UserPollAnswers userPollAnswers) {
		return userPollAnswersRepository.save(userPollAnswers);
	}

}

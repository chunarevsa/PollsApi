package com.example.pollsapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.pollsapi.dto.UserPollsDto;
import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.entity.Question;
import com.example.pollsapi.entity.UserPollAnswers;
import com.example.pollsapi.entity.UserPolls;
import com.example.pollsapi.exception.AlreadyUseException;
import com.example.pollsapi.exception.FinalDateException;
import com.example.pollsapi.exception.ResourceNotFoundException;
import com.example.pollsapi.payload.PollRequest;
import com.example.pollsapi.payload.QuestionRequest;
import com.example.pollsapi.repository.PollRepository;
import com.example.pollsapi.repository.UserPollAnswersRepository;
import com.example.pollsapi.repository.UserPollsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: активный опрос по времени и isActive

@Service
public class PollService implements DeleteInterface, PollServiceInterface {

	private final PollRepository pollRepository;
	private final QuestionService questionService;
	private final UserPollsRepository userPollsRepository;
	private final UserPollAnswersRepository userPollAnswersRepository;
	private final TakePollService takePollService;

	@Autowired
	public PollService(PollRepository pollRepository, 
							QuestionService questionService,
							UserPollsRepository userPollsRepository,
							UserPollAnswersRepository userPollAnswersRepository,
							TakePollService takePollService) {
		this.pollRepository = pollRepository;
		this.questionService = questionService;
		this.userPollsRepository = userPollsRepository;
		this.userPollAnswersRepository = userPollAnswersRepository;
		this.takePollService = takePollService;
	}

	// только активных по дате и из актив
	@Override
	public List<Poll> getPolls() {
		
		return pollRepository.findAll();
	}

	@Override
	public UserPollsDto start(Long pollId, Long userUniqueId) {
	
		Poll poll = findById(pollId);

		System.out.println("Старт опроса :" + pollId);
		Set<Question> pollQuestions = poll.getQuestions();
		
		// Проверка на то, не проходил ли пользовательэто опрос 
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
	
		UserPollAnswers userPollAnswers = takePollService.getUserPollAnswers(pollQuestions);

		userPollAnswers.setPoll(poll);

		// Сохранение пройденого опроса
		userPolls.getUserPollAnswers().add(userPollAnswers);
		userPolls.getUserPollAnswers().stream()
				.map(saveUserPollAnswers -> saveUserPollAnswers(saveUserPollAnswers))
				.collect(Collectors.toSet());

		UserPolls savedUserPolls = saveUserPolls(userPolls);
		return UserPollsDto.fromUser(savedUserPolls);

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

	private UserPolls findUserPollsByUserUniqueId(Long userUniqueId) {
		return userPollsRepository.findUserPollsByUserUniqueId(userUniqueId);
	}

	private boolean validateExpirationDate(Instant expirationDate) {
		return expirationDate.isBefore(Instant.now());
	}

	private Poll savePoll(Poll poll) {
		return pollRepository.save(poll);
	}

	private UserPolls saveUserPolls(UserPolls userPolls) {
		return userPollsRepository.save(userPolls);
	}

	private UserPollAnswers saveUserPollAnswers(UserPollAnswers userPollAnswers) {
		return userPollAnswersRepository.save(userPollAnswers);
	}

}

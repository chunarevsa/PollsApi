package com.example.pollsapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.entity.Question;
import com.example.pollsapi.exception.FinalDateException;
import com.example.pollsapi.exception.ResourceNotFoundException;
import com.example.pollsapi.payload.PollRequest;
import com.example.pollsapi.payload.QuestionRequest;
import com.example.pollsapi.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: активный опрос по времени и isActive
// проверка на дату, что бы не была меньше нау
@Service
public class PollService implements DeleteInterface, PollServiceInterface {

	private final PollRepository pollRepository;
	private final QuestionService questionService;

	@Autowired
	public PollService(PollRepository pollRepository, QuestionService questionService) {
		this.pollRepository = pollRepository;
		this.questionService = questionService;
	}

	// только активных по дате и из актив
	@Override
	public List<Poll> getPolls() {
		
		return pollRepository.findAll();
	}

	@Override
	public Object start() {
		return null;
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
		Question newQuestion = questionService.getQuestionFromRequest(questionRequest);
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

}

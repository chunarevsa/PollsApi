package com.example.pollsapi.controllers;

import java.text.ParseException;

import javax.validation.Valid;

import com.example.pollsapi.payload.ApiResponse;
import com.example.pollsapi.payload.PollRequest;
import com.example.pollsapi.payload.QuestionRequest;
import com.example.pollsapi.payload.StartPollRequest;
import com.example.pollsapi.service.PollService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO:

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
 */

@RestController
@RequestMapping("/poll") 
//@Api(value = "Admin Rest API", description = "Функции администратора")
public class PollController {

	private final PollService pollService;

	@Autowired
	public PollController(PollService pollService) {
		this.pollService = pollService;
	}

	/**
	 * Получение Polls
	 * @return
	 */
	@GetMapping("/all")
	// @ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity getPolls() {

		return ResponseEntity.ok().body(pollService.getPolls());
	}

	@GetMapping("/all/admin")
	@PreAuthorize("hasRole('ADMIN')")
	// @ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity getPollsFromAdmin() {

		return ResponseEntity.ok().body(pollService.getPollsFromAdmin());
	}

	/**
	 * Прохождение опроса
	 * @param poleId
	 * @return
	 */
	@PostMapping("/{pollid}/start")
	//@ApiOperation(value = "Добавление Item")
	public ResponseEntity start(@PathVariable(value = "pollid") Long poleId, 
			@Valid @RequestBody StartPollRequest startPollRequest) {

		return ResponseEntity.ok().body(pollService.start(poleId ,startPollRequest.getUserId()));
	}

	/**
	 * Добавлние опроса
	 * @param pollRequest
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Добавление Item")
	public ResponseEntity addPoll(@Valid @RequestBody PollRequest pollRequest) throws ParseException {

		return ResponseEntity.ok().body(pollService.addPoll(pollRequest));
	}

	/**
	 * Изменение опроса
	 * @param poleId
	 * @param editPollRequest
	 * @return
	 * @throws ParseException
	 */
	@PutMapping("/{pollid}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Изменение Item (без цен)")
	public ResponseEntity editItem(@PathVariable(value = "pollid") Long poleId,
			@Valid @RequestBody PollRequest pollRequest) throws ParseException {

		return ResponseEntity.ok().body(pollService.editPoll(poleId, pollRequest));

	}

	/**
	 *  Удаление опроса
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{pollid}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity deleteItem(@PathVariable(value = "pollid") Long id) {
		pollService.delete(id);
		return ResponseEntity.ok(new ApiResponse(true, "Опрос " + id + " был удален"));
	}

	/**
	 * Добавление вопроса
	 * @param pollId
	 * @param qestionRequest
	 * @return
	 */
	@PostMapping("/{pollid}/question/add")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Добавление цены Item")
	public ResponseEntity addQuestion (@PathVariable(value = "pollid") Long pollId,
			@Valid @RequestBody QuestionRequest qestionRequest) {

		return ResponseEntity.ok().body(pollService.addQuestion(qestionRequest, pollId));
	}

	/**
	 * Изменение вопроса
	 * @param questionId
	 * @param qestionRequest
	 * @return
	 */
	@PutMapping("/{pollId}/question/{questionQueueId}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity editQuestion(
			@PathVariable(value = "pollId") Long pollId,
			@PathVariable(value = "questionQueueId") int questionQueueId,
			@Valid @RequestBody QuestionRequest qestionRequest) {

		return ResponseEntity.ok().body(pollService.editQuestion(qestionRequest, pollId, questionQueueId));
	}

	/**
	 *  Удаление вопроса
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{pollId}/question/{questionQueueId}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity deleteQuestion(
		@PathVariable(value = "pollId") Long pollId,
		@PathVariable(value = "questionQueueId") int questionQueueId) {
		pollService.deleteQuestion(pollId, questionQueueId);
		return ResponseEntity.ok(new ApiResponse(true, "Question " + questionQueueId + " был удален"));
	}


	
}

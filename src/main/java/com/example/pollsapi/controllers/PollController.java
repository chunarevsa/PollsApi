package com.example.pollsapi.controllers;

import java.util.Set;

import javax.validation.Valid;

import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.payload.ApiResponse;
import com.example.pollsapi.payload.EditPollRequest;
import com.example.pollsapi.payload.PollRequest;
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
- добавление/изменение/удаление опросов.
Атрибуты опроса: название, дата старта, дата окончания, описание. 
После создания поле "дата старта" у опроса менять нельзя

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
	 * @param setPoll
	 * @param jwtUser
	 * @return
	 */
	@GetMapping("/all")
	// @ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity getItems() {

		return ResponseEntity.ok().body(pollService.getPolls());
	}

	/**
	 * Добавлние опроса
	 * @param pollRequest
	 * @return
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Добавление Item")
	public ResponseEntity addItem(@Valid @RequestBody PollRequest pollRequest) {

		return ResponseEntity.ok().body(pollService.addItem(pollRequest));

	}

	/**
	 * Изменение опроса
	 * @param id
	 * @param editPollRequest
	 * @return
	 */
	@PutMapping("/{id}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Изменение Item (без цен)")
	public ResponseEntity editItem(@PathVariable(value = "id") Long id,
			@Valid @RequestBody EditPollRequest editPollRequest) {

		return ResponseEntity.ok().body(pollService.editPoll(id, editPollRequest));

	}

	/**
	 *  Удаление (Выключение) Item
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity deleteItem(@PathVariable(value = "id") Long id) {
		pollService.deletePoll(id);
		return ResponseEntity.ok(new ApiResponse(true, "Poll " + id + " был удален"));
	}
	
}

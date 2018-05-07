package com.excilys.formation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.binding.mappers.ComputerDTOMapper;
import com.excilys.formation.service.validator.ValidatorException;
import com.excilys.formation.core.dtos.ComputerDTO;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.excilys.formation.service.service.ComputerService;

@RestController
@RequestMapping("/ComputerDatabaseService")
public class ComputerWebServiceController {

	private ComputerService computerService;
	private ComputerDTOMapper computerDTOMapper;

	@Autowired
	public ComputerWebServiceController(ComputerService computerService, ComputerDTOMapper computerDTOMapper) {
		this.computerService = computerService;
		this.computerDTOMapper = computerDTOMapper;
	}

	@GetMapping("computer/{computerId}")
	public ComputerDTO getComputer(@PathVariable Long computerId) {
		Optional<Computer> optComputer = computerService.getComputerById(computerId);
		if (optComputer.isPresent()) {
			return computerDTOMapper.toDTO(optComputer.get());
		} else {
			return null;
		}
	}

	@GetMapping("computer/{pageNumber}/{pageSize}")
	public List<ComputerDTO> getComputers(@PathVariable Long pageNumber, @PathVariable Long pageSize) {
		return computerDTOMapper.toDTOList(computerService.getComputerList(pageSize * pageNumber, pageSize));
	}

	@GetMapping("computer/{pageNumber}/{pageSize}/{name}")
	public List<ComputerDTO> getComputersByName(@PathVariable Long pageNumber, @PathVariable Long pageSize,
			@PathVariable String name) {
		return computerDTOMapper.toDTOList(computerService.getByName(name, pageSize * pageNumber, pageSize));
	}

	@GetMapping("computer/{pageNumber}/{pageSize}/{orderBy}/{order}")
	public List<ComputerDTO> getComputerByOrder(@PathVariable Long pageNumber, @PathVariable Long pageSize,
			@PathVariable String orderBy, @PathVariable String order, HttpServletResponse response) {
		List <Computer> computers = new ArrayList<>();
		try {
			computers = computerService.getByOrder(orderBy, order, pageSize * pageNumber, pageSize);
		}catch(DAOException e) {
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
		return computerDTOMapper.toDTOList(computers);
	}
	
	@GetMapping("computer/{pageNumber}/{pageSize}/{orderBy}/{order}/{name}")
	public List<ComputerDTO> getComputerByOrderBy(@PathVariable Long pageNumber, @PathVariable Long pageSize,
		@PathVariable String orderBy, @PathVariable String order, @PathVariable String name, HttpServletResponse response){
		List <Computer> computers = new ArrayList<>();
		try {
			computers = computerService.getByOrder(orderBy, order, name, pageSize * pageNumber, pageSize);
		}catch(DAOException e) {
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
		return computerDTOMapper.toDTOList(computers);
		
	}

	@PostMapping("computer")
	public Long addComputer(@RequestBody ComputerDTO computerDTO, HttpServletResponse response) {
		Computer computer = computerDTOMapper.toComputer(computerDTO);
		Long id = null;
		try {
			id = computerService.createComputer(computer);
		} catch (ValidatorException e) {
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
		return id;
	}

	@PutMapping("computer")
	public void updateComputer(@RequestBody ComputerDTO computerDTO, HttpServletResponse response) {
		Computer computer = computerDTOMapper.toComputer(computerDTO);
		try {
			computerService.updateComputer(computer);
		} catch (ValidatorException e) {
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}

	@PatchMapping(value = "computer/{computerId}/{name}")
	public void updateName(@PathVariable Long computerId, @PathVariable String name, HttpServletResponse response) {
		Optional<Computer> optComputer = computerService.getComputerById(computerId);
		if (optComputer.isPresent()) {
			Computer computer = optComputer.get();
			computer.setName(name);
			try {
				computerService.updateComputer(computer);
			} catch (ValidatorException e) {
				response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
			}
		} else {
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}

	@DeleteMapping(value = "computer/{computerId}")
	public void delete(@PathVariable Long computerId) {
		computerService.deleteComputer(computerId);
	}

	
	@GetMapping(value = "computer/count")
	public Long count (){
		return computerService.count();
	}
	
	@GetMapping(value = "computer/count/{name}")
	public Long count (@PathVariable String name) {
		return computerService.count(name);
	}
	
	@DeleteMapping(value = "computer")
	public void delete(@RequestBody List<Long> computerIds) {
		computerService.deleteComputer(computerIds);
	}

}

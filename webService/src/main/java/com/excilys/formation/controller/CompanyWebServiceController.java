package com.excilys.formation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.binding.mappers.CompanyDTOMapper;
import com.excilys.formation.core.dtos.CompanyDTO;
import com.excilys.formation.service.service.CompanyService;
import com.excilys.formation.service.service.ServiceException;

@RestController
@RequestMapping("/CompanyDatabaseService")
public class CompanyWebServiceController {

	private CompanyService companyService;
	private CompanyDTOMapper companyDTOMapper;
	
	@Autowired
	public CompanyWebServiceController(CompanyService companyService, CompanyDTOMapper companyDTOMapper) {
		this.companyService = companyService;
		this.companyDTOMapper = companyDTOMapper;
	}
	 
	@GetMapping("company/{pageNumber}/{pageSize}")
    public List<CompanyDTO> getCompanyList(@PathVariable Long pageNumber, @PathVariable Long pageSize) {
        return companyDTOMapper.toDTOList(companyService.getCompanyList(pageNumber * pageSize, pageSize));
    }

	@GetMapping("company/count")
    public long count() {
		return companyService.count();
    }

	@GetMapping("company/{name}")
    public List<CompanyDTO> getCompaniesByName(@PathVariable String name) {
    	return companyDTOMapper.toDTOList(companyService.getCompaniesByName(name));
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("company/{computerId}")
    public void delete(@PathVariable Long computerId) throws ServiceException {
    	companyService.delete(computerId);
    }
}

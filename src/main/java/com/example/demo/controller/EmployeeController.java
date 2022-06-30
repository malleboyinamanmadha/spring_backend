package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// get all employees rest api
	@GetMapping("/employees")
	@CrossOrigin(origins = "http://localhost:4200")
	//@CrossOrigin(origins = "/*")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee with id "+id+"not found"));
		return ResponseEntity.ok(employee);
	}
	
	
	@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employee){
		Employee employee1=employeeRepository.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("Employee is not found with id" +id ));
		employee1.setFirstName(employee.getFirstName());
		employee1.setLastName(employee.getLastName());
		employee1.setEmailId(employee.getEmailId());
		Employee updatedEmployee=employeeRepository.save(employee1);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	
	
	@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String ,Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee with id "+id+" not found"));
		employeeRepository.delete(employee);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}

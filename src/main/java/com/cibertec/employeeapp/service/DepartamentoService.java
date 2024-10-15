package com.cibertec.employeeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.employeeapp.model.Departamento;
import com.cibertec.employeeapp.repository.DepartamentoRepository;

@Service
public class DepartamentoService {
	
	@Autowired
	private DepartamentoRepository departamentoRepository;

	
	public List<Departamento> obtenerDepartamentos(){
		return departamentoRepository.findAll();
	}
	
	public void guardarDepartamento(Departamento departamento) {
		departamentoRepository.save(departamento);
	}
	
	public DepartamentoRepository getDepartamentoRepository() {
		return departamentoRepository;
	}

	public void setDepartamentoRepository(DepartamentoRepository departamentoRepository) {
		this.departamentoRepository = departamentoRepository;
	}

	public Departamento obtenerDepartamentoPorId(Long id) {
	
		return departamentoRepository.findById(id).orElse(null);
	}

	public void eliminarDepartamentoPorId(Long id) {
		departamentoRepository.deleteById(id);
		
	}

	
	
}

package com.cibertec.employeeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.employeeapp.model.Empleado;
import com.cibertec.employeeapp.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;

	public EmpleadoRepository getEmpleadoRepository() {
		return empleadoRepository;
	}

	public void setEmpleadoRepository(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	public List<Empleado> obtenerEmpleados() {
		return empleadoRepository.findAll();
	}

	public void guardarEmpleado(Empleado empleado) {
		empleadoRepository.save(empleado);
		
	}

	public Empleado obtenerEmpleadoPorId(Long id) {
		return empleadoRepository.findById(id).orElse(null);
	}

	
//	 Usado para probar Spring Data
//	public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
//		return empleadoRepository.findByNombreContainingIgnoreCase(nombre);
//	}
	
	public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
		return empleadoRepository.obtenerEmpleadoPorNombre(nombre);
	}
	
	
}

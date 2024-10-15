package com.cibertec.employeeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.employeeapp.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

	// 1. Convenciones con Spring Data
	List<Empleado> findByNombreContainingIgnoreCase(String nombre);

	// 2. JPA Query
//	@Query("Select e from Empleado e where e.nombre like %:nombre%")
//	List<Empleado> obtenerEmpleadoPorNombre(@Param("nombre") String nombre);

	// 3. SQL Query
	@Query(value = "Select * from empleado where nombre = :nombre", nativeQuery = true)
	List<Empleado> obtenerEmpleadoPorNombre(@Param("nombre") String nombre);

}

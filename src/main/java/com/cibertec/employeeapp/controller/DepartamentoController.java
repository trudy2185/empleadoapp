package com.cibertec.employeeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.cibertec.employeeapp.model.Departamento;
import com.cibertec.employeeapp.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping
	public String listaDepartamentos(Model model) {
		List<Departamento> departamentos = departamentoService.obtenerDepartamentos();
		model.addAttribute("departamentos", departamentos);
		return "lista_departamentos";
	}
	
	@GetMapping("/agregar")
	public String mostraFormularioDepartamento(Model model) {
		model.addAttribute("departamento",new Departamento());
		return "departamento_form";
	}
	
	@GetMapping("/editar/{id}")
	public String editarDepartamentoForm(@PathVariable("id") Long id, Model model) {
		
		// obtener el departamento por Id y luego pasarselo al model->página
		Departamento departamento = departamentoService.obtenerDepartamentoPorId(id);
		model.addAttribute("departamento",departamento);
		return "departamento_edit";
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminarDepartamentoForm(@PathVariable("id") Long id, Model model) {
		
		// obtener el departamento por Id y luego pasarselo al model->página
		departamentoService.eliminarDepartamentoPorId(id);
		return "redirect:/departamentos";
	}
	
	
	@PostMapping("/guardar")
	public String actualizarDepartamento(@ModelAttribute("departamento") 
											Departamento departamento) {
		
		departamentoService.guardarDepartamento(departamento);
		//return "lista_departamentos";
		return "redirect:/departamentos";
	}
	
//	@PostMapping("/guardar")
//	public ResponseEntity<String> agregarDepartamento(@ModelAttribute("departamento") Departamento departamento) {
//	    departamentoService.guardarDepartamento(departamento);
//	    return ResponseEntity.ok("Departamento guardado con éxito.");
//	}
	
	@GetMapping("/")
	public RedirectView redireccionarDepartamentosConBarraFinal() { 
	    return new RedirectView("/departamentos"); 
	}
	
	
	public DepartamentoService getDepartamentoService() {
		return departamentoService;
	}

	public void setDepartamentoService(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}
	
	
}

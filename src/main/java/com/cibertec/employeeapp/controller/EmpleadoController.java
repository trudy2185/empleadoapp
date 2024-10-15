package com.cibertec.employeeapp.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.employeeapp.model.Departamento;
import com.cibertec.employeeapp.model.Empleado;
import com.cibertec.employeeapp.service.DepartamentoService;
import com.cibertec.employeeapp.service.EmpleadoService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private DepartamentoService departamentoService;

	public EmpleadoService getEmpleadoService() {
		return empleadoService;
	}

	
	@GetMapping
	public String listarEmpleados(Model model) {
		List<Empleado> empleados = empleadoService.obtenerEmpleados();
		model.addAttribute("empleados", empleados);
		
		return "lista_empleados";
	}
	
	
	@GetMapping("/editar/{id}")
	public String mostrarFormularioAgregar(@PathVariable("id") Long id, Model model) {
		
		
		List<Departamento> departamentos = departamentoService.obtenerDepartamentos();
		
		// Se envia la lista de departamentos para que el usuario pueda cambiar en el combo
		model.addAttribute("departamentos", departamentos);
		
		Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
		
		model.addAttribute("empleado", empleado);
		return "empleado_edit";
	}
	
	@GetMapping("/agregar")
	public String editarEmpleadoForm(Model model) {
		
		List<Departamento> departamentos = departamentoService.obtenerDepartamentos();
		// se envia una lista de departamentos para cargar el combo
		model.addAttribute("departamentos", departamentos);
		model.addAttribute("empleado", new Empleado());
		return "empleado_form";
	}
	
	
	@GetMapping("/buscar")
	public String buscarEmpleadoform(Model model) {
		List<Empleado> empleados = empleadoService.obtenerEmpleados();
		model.addAttribute("empleados", empleados);
		return "busq_empleado";
	}
	
	@GetMapping("/buscarxnombre")
	public String buscarEmpleados(@RequestParam("nombre") String nombre, Model model) {
	    List<Empleado> empleados = empleadoService.buscarEmpleadosPorNombre(nombre);
	    model.addAttribute("empleados", empleados);
	    return "busq_empleado";
	}
	
	
	
	@PostMapping("/guardar")
	public String actualizarEmpleado(@ModelAttribute("empleado") 
											Empleado empleado) {
		
		empleadoService.guardarEmpleado(empleado);
		return "redirect:/empleados";
	}
	
	
	
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	public DepartamentoService getDepartamentoService() {
		return departamentoService;
	}

	public void setDepartamentoService(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}
	
	@GetMapping("/report")
    public void generateReport(HttpServletResponse response) throws Exception {
        // Obtener la lista de empleados
        List<Empleado> empleados= empleadoService.obtenerEmpleados();

        // Cargar el archivo Jasper 
        InputStream reportStream = getClass().getResourceAsStream("/reports/Cherry_Landscape_1.jrxml");

        // Llenar el reporte con los datos de los empleados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleados);
        Map<String, Object> parameters = new HashMap<String, Object>();

        // Agregar la imagen como parámetro
        String imagePath = getClass().getResource("/images/cherry.jpg").getPath();
        parameters.put("imagePath", imagePath); // 'imagePath' es el nombre del parámetro en el informe

        // Compilar el archivo .jrxml
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Llenar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Establecer la respuesta HTTP para generar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_empleado.pdf");

        // Exportar el reporte a PDF
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

}

package com.vck;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.vck.model.Categoria;
import com.vck.model.Perfil;
import com.vck.model.Usuario;
import com.vck.model.Vacante;
import com.vck.repository.CategoriasRepository;
import com.vck.repository.PerfilesRepository;
import com.vck.repository.UsuariosRepository;
import com.vck.repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {
	
    @Autowired
	private CategoriasRepository repoCategorias; 
    @Autowired
   	private VacantesRepository repoVacantes; 
    @Autowired
  	private PerfilesRepository repoPerfiles; 
    @Autowired
    private UsuariosRepository repoUsuarios; 

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//CATEGORIAS
		
		//guardar();
		//buscarPorId();
		//modificar();
		//buscarPorId();
		//eliminar();
		//conteo();
		//eliminarTodos();
		//buscarTodosPorId();
		//buscarTodos();
		//existeId();
		//guardarTodo();
		
		//buscarTodosJpa();
		//borrarTodoElBloque();
		//buscarTodosOrdenadosJpa();
		//buscarTodosPaginadosJpa();
		//buscarTodosPaginadosOrdenadosJpa();
		
		
		//VACANTES
		//buscarVacantesJpa();
		//guardarVacantes();
		
		
		//PERFILES
		//crearPerfilesAplicaicon();
		
		//USUARIOS
		//crearUsuarioCon2Perfiles();
		//buscarUsuario();
		//buscarVacantesporEstatus();
		//buscarVacantesporDestacadoEstatusOrdenadoDesc();
		buscarVacanteRangoSalario();
	}
	
	/**
	 * Metodo para bescar vacantes por RANGO DE SALARIO //query
	 */

	private void buscarVacanteRangoSalario() {
			
		List<Vacante> rangoSalarioVacantes = repoVacantes.findBySalarioBetweenOrderBySalarioDesc(7000, 14000);
		
		System.out.println("Registros encontrados: " + rangoSalarioVacantes.size());
		for(Vacante v: rangoSalarioVacantes ) {
			System.out.println(v.getId() +"------- " + v.getNombre()+ "-------Salario:  " + v.getSalario());
		}
	}
	/**
	 * Metodo para bescar vacantes por DESTACADO Y ESTATUS ORDENADO POR ID DESC //query
	 */

	private void buscarVacantesporDestacadoEstatusOrdenadoDesc() {
			
		List<Vacante> estatusVacantes = repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
		
		System.out.println("Registros encontrados: " + estatusVacantes.size());
		for(Vacante v: estatusVacantes ) {
			System.out.println(v.getId() +"------- " + v.getNombre()+ "------- " + v.getDestacado() + "------- " + v.getEstatus());
		}
	}
	
	
	/**
	 * Metodo para bescar vacantes por ESTATUS //query
	 */

	private void buscarVacantesporEstatus() {
			
		List<Vacante> estatusVacantes = repoVacantes.findByEstatus("eliminada");
		
		System.out.println("Registros encontrados: " + estatusVacantes.size());
		for(Vacante v: estatusVacantes ) {
			System.out.println(v.getId() +" " + v.getNombre()+ " " + v.getEstatus());
		}
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Metodo para guardar Perfiles
	 */

	private void crearPerfilesAplicaicon() {
			
		repoPerfiles.saveAll(getPerfilesAplicacion());
	}
		
	/**
	 * Metodo Buscar Todos Vacantes con JPA
	 */
	private void buscarVacantesJpa() {
		
		List<Vacante> todasVacantes = repoVacantes.findAll();

		for(Vacante c: todasVacantes ) {
			System.out.println(c.getId()+"------ "+c.getNombre()+"------- "+ c.getCategoria().getNombre());
		}
	}
	/**
	 * Metodo Salvar Vacantes
	 */
	private void guardarVacantes() {
		Vacante vac = new Vacante();
		vac.setNombre("Profesor de Sping-Boot");
		vac.setDescripcion("Udemy solicita maestro de Java SB");
		vac.setFecha(new Date());
		vac.setSalario(9500.0);
		vac.setEstatus("Aprobada");
		vac.setDestacado(0);
		vac.setImagen("escuela.png");
		vac.setDetalles("sin detalles");
		
		Categoria cat = new Categoria();
		cat.setId(15);
		vac.setCategoria(cat);
						
		repoVacantes.save(vac);
		System.out.println(vac.toString());
		
	}	
	
	/**
	 * crear un usuario con 2 perfiles ADMINISTRADOR/USUARIO
	 */
	private void crearUsuarioCon2Perfiles() {
		Usuario user = new Usuario();
		
		user.setNombre("Victor Hernandez");
		user.setEmail("delacadena@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("vck");
		user.setPassword("1245");
		user.setEstatus(1);
		
		Perfil perf1 = new Perfil();
		perf1.setId(2);
		
		Perfil perf2 = new Perfil();
		perf2.setId(3);
		
		
		user.agregar(perf1);
		user.agregar(perf2);
		
		repoUsuarios.save(user);
		
	}	
	/**
	 * Metodo Buscar usuario y desplegar sus perfiles
	 */
	private void buscarUsuario() {
		Optional<Usuario> optional = repoUsuarios.findById(1);
		
		if(optional.isPresent()) {
			Usuario u =optional.get();
			System.out.println("Usuario: "+ u.getNombre());
			System.out.println("Perfiles asignados: ");
			for(Perfil p: u.getPerfiles()) {
				System.out.println(p.getPerfil());
			}
		}else {
			System.out.println("Usuario no encontrado");
		}
		
		
	}
	/**
	 * Metodo deleteAllinBatch  USARLO CON MUCHA PREAUCION
	 */
	private void borrarTodoElBloque() {
		repoCategorias.deleteAllInBatch();
		
	}	
	/**
	 * Metodo Exist by Id
	 */
	private void existeId() {
		boolean existe = repoCategorias.existsById(10);
		
		System.out.println("La categoria existe? "+ existe);
		
	}	
	
	/**
	 * Metodo Modificar
	 */
	private void modificar() { //primero valida que exista el id y luego hace las modificaciones
		int id = 5;
		Optional<Categoria> optional = repoCategorias.findById(id);
		if(optional.isPresent()) {
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ingenieria de software");
			catTmp.setDescripcion("Ingeniero  para validaciones se SonarCube");
			repoCategorias.save(catTmp);
			
		}else{
			System.out.println("Categoria no encontrada con el ID= " + id);
		}
				
	}
	/**
	 * Metodo Buscar por ID
	 */
	private void buscarPorId() {// primero checa si existe el id y luego lo imprime desde un optional
		int id = 5;
		Optional<Categoria> optional = repoCategorias.findById(id);
		if(optional.isPresent()) {
			System.out.println("El valor encontrado es: " + optional.get());
		}else{
			System.out.println("Categoria no encontrada con el ID= " + id);
		}
		
	}
	/**
	 * Metodo Buscar Todos
	 */
	private void buscarTodos() {
		
		Iterable<Categoria> todasCategorias = repoCategorias.findAll();

		for(Categoria c: todasCategorias ) {
			System.out.println(c);
		}
	}
	
	/**
	 * Metodo Buscar Todos con JPA
	 */
	private void buscarTodosJpa() {
		
		List<Categoria> todasCategorias = repoCategorias.findAll();

		for(Categoria c: todasCategorias ) {
			System.out.println(c.getId()+""+c.getNombre());
		}
	}
	
	/**
	 * Metodo Buscar Todos con JPA con orden
	 */
	private void buscarTodosOrdenadosJpa() {
		
		List<Categoria> todasCategorias = repoCategorias.findAll(Sort.by("nombre").descending()); //<- nombre es el atributo declarada en la clase modelo de Categorias puede ser descendente o ascendente

		for(Categoria c: todasCategorias ) {
			System.out.println(c.getId()+" "+c.getNombre());
		}
		
	}
	/**
	 * Metodo Buscar Todos con JPA con Paginacion
	 */
	private void buscarTodosPaginadosJpa() {
		Page<Categoria> categoriasPage = repoCategorias.findAll(PageRequest.of(0, 5)); //<-- numero de pagina y numeros por pagina, se divide todo entre el numero de elementos por pagina
		System.out.println("Total de registros: " + categoriasPage.getTotalElements());
		System.out.println("Total de paginas: " + categoriasPage.getTotalPages());
		for(Categoria c: categoriasPage.getContent() ) {
			System.out.println(c.getId()+" "+c.getNombre());
		}				
	}
	/**
	 * Metodo Buscar Todos con JPA con Paginacion y ordenados
	 */
	private void buscarTodosPaginadosOrdenadosJpa() {
		Page<Categoria> categoriasPage = repoCategorias.findAll(PageRequest.of(0, 5, Sort.by("nombre").descending())); // se agrega el sort by al pageRequest para paginarlo y ordenarlo
		System.out.println("Total de registros: " + categoriasPage.getTotalElements());
		System.out.println("Total de paginas: " + categoriasPage.getTotalPages());
		for(Categoria c: categoriasPage.getContent() ) {
			System.out.println(c.getId()+" "+c.getNombre());
		}					
	}

	/**
	 * Metodo Buscar Todos por ID
	 */
	private void buscarTodosPorId() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(9);
		ids.add(11);
		ids.add(12);
		
		Iterable<Categoria> categorias = repoCategorias.findAllById(ids);
		
		for(Categoria c: categorias ) {
			System.out.println(c);
		}
	}
	/**
	 * Metodo Salvar
	 */
	private void guardar() {
		Categoria cat = new Categoria();
		cat.setNombre("Finanzas");
		cat.setDescripcion("Trabajos relacionados con Finanzas y Contabilidad");
		repoCategorias.save(cat);
		System.out.println(cat.toString());
		
	}
	/**
	 * Metodo salvar todo
	 */
	private void guardarTodo() {
		
		List<Categoria> categorias = getListaCategorias();
		repoCategorias.saveAll(categorias);
		
	}
	/**
	 * metodo eliminar
	 */
	private void eliminar() {
		int id = 2;
		Optional<Categoria> optional = repoCategorias.findById(id);
		if(optional.isPresent()) {
			Categoria catTmp = optional.get();
			repoCategorias.deleteById(catTmp.getId());
			System.out.println("Categoria ELIMINADA con el ID= " + id);
			
		}else{
			System.out.println("Categoria no encontrada con el ID= " + id);
		}
		
	}
	/**
	 * Metodo Count (contar)
	 * 
	 */
	private void conteo() {
		long count = repoCategorias.count();
		System.out.println("Total de categorias: " + count);
	}
	/**
	 * Metodo Delete All (contar)
	 * 
	 */
	private void eliminarTodos() {
		repoCategorias.deleteAll();
	}
	
	private List<Categoria> getListaCategorias(){
		List<Categoria> lista = new LinkedList<Categoria>();
		
		//Categoria 1
		Categoria cat1 = new Categoria();
		cat1.setNombre("Programador de blockchain");
		cat1.setDescripcion("Trabajo relacionados con Bitcoin y Criptomonedas");
		
		//Categoria 1
		Categoria cat2 = new Categoria();
		cat2.setNombre("Soldador/Pintura");
		cat2.setDescripcion("Trabajo relacionados soldadura, pintura y enderezado");
		
		//Categoria 3
		Categoria cat3 = new Categoria();
		cat3.setNombre("Ingeniero industrial");
		cat3.setDescripcion("Trabajo relacionados con Ingenieria industrial");
		
		lista.add(cat1);
		lista.add(cat2);
		lista.add(cat3);
		
		return lista;
				
	}
	
	//regresa objetos de tipo perfil para pruebas.
	private List<Perfil> getPerfilesAplicacion(){
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil perf1= new Perfil();
		perf1.setPerfil("SUPERVISOR");
		
		Perfil perf2= new Perfil();
		perf2.setPerfil("ADMINISTRADOR");
		
		Perfil perf3= new Perfil();
		perf3.setPerfil("USUARIO");
		
		lista.add(perf1);
		lista.add(perf2);
		lista.add(perf3);
		
		return lista;
		
		
	}
	
}

package ucb.edu.bo.storebackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ucb.edu.bo.storebackend.mapping.ComentarioEntity;
import ucb.edu.bo.storebackend.mapping.UsuarioEntity;
import ucb.edu.bo.storebackend.repo.ComentarioEntityRepository;
import ucb.edu.bo.storebackend.repo.UsuarioEntityRepository;
import ucb.edu.bo.storebackend.repo.UsuarioEntityRepositorySinJPA;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioEntityRepository usuarioEntityRepository;

    @Autowired
    private UsuarioEntityRepositorySinJPA usuarioEntityRepositorySinJPA;

    @Autowired
    private ComentarioEntityRepository comentarioEntityRepository;

    @GetMapping(path = "/todos")
    public @ResponseBody
    Page<UsuarioEntity> getAllUsuarios(@RequestParam Integer page, @RequestParam Integer size) {
        // This returns a JSON or XML with the users
        Pageable pageable = PageRequest.of(page,size);
        return usuarioEntityRepository.findAll(pageable);
    }

    @PostMapping(path = "/add")
    public @ResponseBody UsuarioEntity addUsuario (@RequestBody UsuarioEntity usuario){
        return usuarioEntityRepository.save(usuario);
    }
    @GetMapping("comentarios/usuario/{id}")
    public @ResponseBody List<Object> getComentariosUsuario(@PathVariable("id")Long id){
        return usuarioEntityRepositorySinJPA.getComentarioPorUsuario(id);
    }
    @PutMapping("comentario")
    public @ResponseBody String updateComment(@RequestBody ComentarioEntity comentario){
        System.out.println(comentario);
        comentarioEntityRepository.save(comentario);
        return "Comentario Actualizado Correctamente";
    }
    @PutMapping("comentario/habilitar/{id}")
    public @ResponseBody String habilitarComentario(@PathVariable("id")Integer id){
        System.out.println(id);
        ComentarioEntity ce=comentarioEntityRepository.findById(id).get();
        ce.setEstado_comentario(1);
        comentarioEntityRepository.save(ce);
        return "Comentario Habilitado";
    }
    @PutMapping("comentario/deshabilitar/{id}")
    public @ResponseBody String dehabilitarComentario(@PathVariable("id")Integer id){
        System.out.println(id);
        ComentarioEntity ce=comentarioEntityRepository.findById(id).get();
        ce.setEstado_comentario(0);
        comentarioEntityRepository.save(ce);
        return "Comentario Deshabilitado";
    }
    @GetMapping("usuario/{id}")
    public @ResponseBody Optional<UsuarioEntity> getUser(@PathVariable("id")Long id){
        return usuarioEntityRepository.findById(id);
    }
    @PutMapping("usuario/{id}")
    public @ResponseBody String updateUser(@RequestBody UsuarioEntity usuario,@PathVariable("id")Long id){
        UsuarioEntity ue=usuarioEntityRepository.findById(id).get();
        ue.setNombre(usuario.getNombre());
        ue.setApellido(usuario.getApellido());
        ue.setContraseña(usuario.getContraseña());
        ue.setEmail(usuario.getEmail());
        usuarioEntityRepository.save(ue);
        return "Usuario Actualizado Correctamente";
    }
    @DeleteMapping("delete/{id}")
    public @ResponseBody String deleteUser(@PathVariable("id")Long id){
        usuarioEntityRepository.deleteById(id);
        return "Usuario Eliminado Correctamente";
    }

}

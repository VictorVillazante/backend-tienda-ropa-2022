package ucb.edu.bo.storebackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.storebackend.entityInterfaces.ProductInfoInterface;
import ucb.edu.bo.storebackend.mapping.ProductoEntity;
import ucb.edu.bo.storebackend.objAux.disponibilidadAux;
import ucb.edu.bo.storebackend.repo.ProductoEntityRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping(path = "/productos")
public class ProductoController {
    @Autowired
    private ProductoEntityRepository productoEntityRepository;

    @PostMapping(path = "/add")
    public @ResponseBody ProductoEntity addNewProducto (@RequestBody ProductoEntity producto){
        return productoEntityRepository.save(producto);
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Page<ProductoEntity> getAllProductos(@RequestParam Integer page, @RequestParam Integer size) {
        // This returns a JSON or XML with the users
        Pageable pageable = PageRequest.of(page,size);
        return productoEntityRepository.findAll(pageable);
    }

    @GetMapping(path = "/buscar")
    public @ResponseBody Iterable<ProductoEntity> getName(@RequestParam("nombre") String nombre){

        return productoEntityRepository.findByNombre(nombre);
    }

    @GetMapping(path = "/buscarc")
    public @ResponseBody
    List<ProductInfoInterface> getProc(@RequestParam("nompro")String nompro){
        return productoEntityRepository.getNomPro(nompro);

    }

    @GetMapping("/detail/{id}")
    public  @ResponseBody Optional<ProductoEntity> getById(@PathVariable("id") int id){

        return productoEntityRepository.findById(id);
    }


    @PutMapping("/update/{id}")
    public  @ResponseBody ProductoEntity update(@PathVariable("id")int id, @RequestBody ProductoEntity productoDto){

        ProductoEntity producto=new ProductoEntity();
        producto.setIdProducto(id);
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setIdCategoria(productoDto.getIdCategoria());
        producto.setPrecio(productoDto.getPrecio());
        return productoEntityRepository.save(producto);
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id")int id){
       productoEntityRepository.Delete_id(id);
        return "successful removal";
    }
    
    @GetMapping(path = "/categoria")
    public @ResponseBody Iterable<ProductoEntity> getProductosCategoria(@RequestParam("id_categoria") int categoria){
        return productoEntityRepository.findByIdCategoria(categoria);
    }
    
    @GetMapping(path = "/coincidencias")
    public @ResponseBody Iterable<ProductoEntity> getCoincidencias(@RequestParam("coincidencias") String nombre){
        return productoEntityRepository.findByCoincidencias(nombre);
    }

    @GetMapping(path = "/productosSinStock")
    public @ResponseBody Iterable<disponibilidadAux> getProductosSinStock(){
        List<Object>lista= productoEntityRepository.findByProductosSinStock();
        ArrayList<disponibilidadAux>listaDA=new ArrayList<>();
        for(Object e:lista){
            Object[]l=(Object[])e;
            disponibilidadAux da=new disponibilidadAux(l[0],""+l[1],l[2], ""+l[3], l[4],"" + l[5], l[6], l[7]);
            listaDA.add(da);
        }
        return listaDA;
    }
    @GetMapping(path = "/productosPocoStock")
    public @ResponseBody Iterable<disponibilidadAux> productosPocoStock(){
        List<Object>lista= productoEntityRepository.findByProductosPocoStock();
        ArrayList<disponibilidadAux>listaDA=new ArrayList<>();
        for(Object e:lista){
            Object[]l=(Object[])e;
            disponibilidadAux da=new disponibilidadAux(l[0],""+l[1],l[2], ""+l[3], l[4],"" + l[5], l[6], l[7]);
            listaDA.add(da);
        }
        return listaDA;
    }
}

package com.librarymanagement.library.controllers;

import com.librarymanagement.library.entities.Book;
import com.librarymanagement.library.entities.Reader;
import com.librarymanagement.library.entities.Response;
import com.librarymanagement.library.entities.ResponseReader;
import com.librarymanagement.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("/customer")
public class ReaderController {
 
    @Autowired
    ReaderRepository readerRepository;

    @GetMapping(value = "/readers")
    public List<Reader> getReaders(){return readerRepository.findAll();}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReader(@PathVariable Long id){

        if(!readerRepository.existsById(id)){
            return ResponseEntity.ok("No such Reader!");
        }
        readerRepository.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");

    }


    @PostMapping("/newreader")
    public  String  createReader(@RequestParam(required = false) String first_name,
                                @RequestParam(required = false) String last_name,
                                @RequestParam(required = false) String city,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String email){

        Long id=readerRepository.getDuplicateReader(first_name,last_name,phone);

        Reader newReader= new Reader();
        newReader.setFirst_name(first_name);
        newReader.setLast_name(last_name);
        newReader.setCity(city);
        newReader.setPhone(phone);
        newReader.setEmail(email);

        if(id==null){
            newReader =readerRepository.save(newReader);
            return "Няма такъв читател";
        }else {
            return "Читателят съществува";
        }

    }
    @PostMapping("/updateReader/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable(value = "id") Long reader_id,
                                               @RequestBody Reader readerData) throws Exception {
        Reader reader = readerRepository.findById(reader_id)
                .orElseThrow(() -> new Exception("Reader not found for this id :: " + reader_id));

        reader.setFirst_name(readerData.getFirst_name());
        reader.setLast_name(readerData.getLast_name());
        reader.setCity(readerData.getCity());
        reader.setPhone(readerData.getPhone());
        reader.setEmail(readerData.getEmail());

        final Reader updatedReader = readerRepository.save(reader);
        return ResponseEntity.ok(updatedReader);

    }
    @GetMapping("/readers/pageable")
    public ResponseReader retrieveEmployee(
            @Param(value = "page") int page,
            @Param(value = "size") int size,
            @Param(value = "city") String city)
            {

        Page<Reader> readers = null;
                Pageable pageable =PageRequest.of(page-1,size);
        if(city.equals("")) {
            readers=readerRepository.findAll(pageable);
        }
        else {
            readers= readerRepository.getReaderByCity(city,pageable);
        }

        return new ResponseReader(readers.getContent(), readers.getTotalPages(), readers.getNumber(), readers.getSize());
    }

    @GetMapping(value = "/cities")
    public List<String> getCities(){
        try {
            return readerRepository.findDistinctCity();
        }
        catch(Exception e) {
         System.out.println(e);
         return Arrays.asList();
        }
    }
}


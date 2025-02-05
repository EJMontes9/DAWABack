package ug.edu.ec.dawa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ug.edu.ec.dawa.entity.ManagerData;
import ug.edu.ec.dawa.entity.Person;
import ug.edu.ec.dawa.service.SystemService;

import java.util.List;

@RestController
@RequestMapping("api/v1/system")
public class SystemController {
    private final SystemService systemService;

    @Autowired
    public SystemController (SystemService service){
        this.systemService = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveData(@RequestBody ManagerData managerData){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(systemService.saveManagerData(managerData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-students")
    public ResponseEntity<List<Person>> getStudents() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(systemService.getStudents());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los estudiantes: " + e.getMessage());
        }
    }

    @GetMapping("/get-teacher")
    public ResponseEntity<List<Person>> getTeacher() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(systemService.getTeacher());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los estudiantes: " + e.getMessage());
        }
    }

}

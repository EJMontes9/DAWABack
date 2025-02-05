package ug.edu.ec.dawa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ug.edu.ec.dawa.entity.ManagerData;
import ug.edu.ec.dawa.entity.Person;
import ug.edu.ec.dawa.entity.dto.DocumentIn;
import ug.edu.ec.dawa.entity.dto.SaveManaDataDTO;
import ug.edu.ec.dawa.repository.ManagerDataRepository;
import ug.edu.ec.dawa.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemService {
    private final ManagerDataRepository managerDataRepository;
    private final PersonRepository personRepository;

    @Autowired
    public SystemService(ManagerDataRepository managerDataRepository,
                         PersonRepository personRepository) {
        this.managerDataRepository = managerDataRepository;
        this.personRepository = personRepository;
    }

    public String saveManagerData(SaveManaDataDTO dtoData) {
        DocumentIn documento = dtoData.getDocument();
        ManagerData data= new ManagerData();
        data.setStudent(dtoData.getStudent());
        data.setTeacher(dtoData.getTeacher());
        data.setDocument(saveDocument(documento));
        data.setNote(dtoData.getNote());
        data.setObservation(dtoData.getObservation());

        managerDataRepository.save(data);
        return "Datos guardados correctamente";
    }

    public byte[] saveDocument(DocumentIn document) {
        // Convertir base64 a byte[]
        byte[] documentBytes = java.util.Base64.getDecoder().decode(document.getBase64());

        // Crear instancia de ManagerData
        ManagerData managerData = new ManagerData();
        managerData.setDocument(documentBytes);
        managerData.setDocumentName(document.getName());

        // Aquí puedes asignar datos adicionales como estudiante/profesor, nota, observación, etc.
        managerData.setNote(0.0); // Ejemplo: valores predeterminados
        managerData.setObservation("Documento subido correctamente");

        // Guardar en la base de datos
        managerDataRepository.save(managerData);

        // Retornar el array de bytes completo
        return documentBytes;
    }


    public List<Person> getStudents() {
        return personRepository.findAll().stream()
                .filter(person -> person.getRole() != null && person.getRole().toLowerCase().contains("estudiante"))
                .collect(Collectors.toList());
    }

    public List<Person> getTeacher() {
        System.out.println();
        return personRepository.findAll().stream()
                .filter(person -> person.getRole() != null && person.getRole().toLowerCase().contains("profesor"))
                .collect(Collectors.toList());
    }



}

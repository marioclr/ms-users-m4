package gob.issste.usersM4.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gob.issste.usersM4.entities.ProfileM4;
import gob.issste.usersM4.repositories.ProfileM4Repository;

@RestController
@RequestMapping("profile")
public class ProfileM4Controller {

	@Autowired
    ProfileM4Repository profileRepository;

    @GetMapping()
    public List<ProfileM4> findAll() {
        return profileRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
         Optional<ProfileM4> customer = profileRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ProfileM4 profile) {
    	profile.addPermission(profile.getPermisos());
    	ProfileM4 prof = profileRepository.save(profile);
        return ResponseEntity.ok(prof);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody ProfileM4 input) {
        Optional<ProfileM4> optionalProf = profileRepository.findById(id);
        if (optionalProf.isPresent()) {
        	ProfileM4 newProf = optionalProf.get();
        	newProf.setProfileCode(input.getProfileCode());
        	newProf.setDescription(input.getDescription());
			ProfileM4 save = profileRepository.save(newProf);
			return new ResponseEntity<>(save, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
    	profileRepository.deleteById(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

}
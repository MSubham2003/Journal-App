package com.subham.journalApp.controller;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        List<JournalEntry> journalEntry = journalEntryService.find();
        if(journalEntry!=null && !journalEntry.isEmpty()){
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>("NO Journals in the DB",HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>("Journal entry is not found with id "+id,HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry){
        try{
            journalEntryService.save(journalEntry);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id){
        try{
            journalEntryService.deleteJournal(id);
            return new ResponseEntity<>("Journal With ID "+id+" deleted successfully.", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<String> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry){
        JournalEntry journal1 = null;
        try{
            journal1 = journalEntryService.updateJournal(id,myEntry);
        }
        catch (Exception e){
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
        if(journal1!=null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }
}

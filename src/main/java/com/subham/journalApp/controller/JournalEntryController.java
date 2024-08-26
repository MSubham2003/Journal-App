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
    public List<JournalEntry> getAll(){
        return journalEntryService.find();
    }

    @GetMapping("id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId id){
        return journalEntryService.findById(id).orElse(null);
    }

    @PostMapping()
    public boolean createEntry(@RequestBody JournalEntry journalEntry){
        journalEntryService.save(journalEntry);
        return true;
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

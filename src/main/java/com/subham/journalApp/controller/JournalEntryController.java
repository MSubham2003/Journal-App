package com.subham.journalApp.controller;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<JournalEntry> getJournalEntryById(@PathVariable String id){
        return journalEntryService.findById(id);
    }

    @PostMapping()
    public boolean createEntry(@RequestBody JournalEntry journalEntry){
        journalEntryService.save(journalEntry);
        return true;
    }
}

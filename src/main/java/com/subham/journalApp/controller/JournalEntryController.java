package com.subham.journalApp.controller;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.entity.User;
import com.subham.journalApp.service.JournalEntryService;
import com.subham.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        try {
            User user = userService.findByUserName(username);

            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }

            List<JournalEntry> journalEntries = user.getJournalEntries();

            if (journalEntries != null && !journalEntries.isEmpty()) {
                return new ResponseEntity<>(journalEntries, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No journals found for the user", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>("Journal entry is not found with id "+id,HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry,@PathVariable String username){
        try{
            journalEntryService.saveEntry(journalEntry,username);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }
    }

    @DeleteMapping("id/{username}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id,@PathVariable String username){
        try{
            journalEntryService.deleteJournal(id,username);
            return new ResponseEntity<>("Journal With ID "+id+" deleted successfully.", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{username}/{id}")
    public ResponseEntity<String> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry, @PathVariable String username) {
        try {
            JournalEntry oldJournalEntry = journalEntryService.findById(id).orElse(null);
            if (oldJournalEntry != null) {
                oldJournalEntry.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().isEmpty() ? myEntry.getTitle() : oldJournalEntry.getTitle());
                oldJournalEntry.setContent(myEntry.getContent() != null && !myEntry.getContent().isEmpty() ? myEntry.getContent() : oldJournalEntry.getContent());
                journalEntryService.saveEntry(oldJournalEntry);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update: Entry not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

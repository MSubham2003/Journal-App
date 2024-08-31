package com.subham.journalApp.service;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.entity.User;
import com.subham.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.save(user);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An Error Occur While Saving the entry\n",e);
        }

    }

    public List<JournalEntry> find() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournal(ObjectId id, String username) throws Exception {
        User user = userService.findByUserName(username);
        if(!journalEntryRepository.existsById(id)){
            throw new Exception("User With ID "+id+" not found");
        }
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.save(user);
        journalEntryRepository.deleteById(id);
    }

    public void saveEntry(JournalEntry oldJournalEntry) {
        journalEntryRepository.save(oldJournalEntry);
    }
}

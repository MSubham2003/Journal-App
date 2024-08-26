package com.subham.journalApp.service;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void save(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> find() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournal(ObjectId id) throws Exception {
        if(!journalEntryRepository.existsById(id)){
            throw new Exception("Employee With ID "+id+" not found");
        }
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateJournal(ObjectId id, JournalEntry myEntry) {
        Optional<JournalEntry> optionalJournalEntry = journalEntryRepository.findById(id);
        if(optionalJournalEntry.isPresent()){
            JournalEntry existingJournalEntry = optionalJournalEntry.get();
            existingJournalEntry.setDate(LocalDateTime.now());
            existingJournalEntry.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().isEmpty() ? myEntry.getTitle() : existingJournalEntry.getTitle());
            existingJournalEntry.setContent(myEntry.getContent() != null && !myEntry.getContent().isEmpty() ? myEntry.getContent() : existingJournalEntry.getContent());

            return journalEntryRepository.save(existingJournalEntry);
        }
        return null;
    }
}

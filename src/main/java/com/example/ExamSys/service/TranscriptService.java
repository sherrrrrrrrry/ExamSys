package com.example.ExamSys.service;

import com.example.ExamSys.dao.TranscriptRepository;
import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.Transcript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TranscriptService {
    @Resource
    private TranscriptRepository transcriptRepository;

    @Transactional
    public Transcript save(Transcript transcript){
        return transcriptRepository.save(transcript);
    }
    @Transactional
    public Optional<Transcript> findOne(String bankname, String stuname, String type){
        return transcriptRepository.findOne(bankname,stuname,type);
    }
}

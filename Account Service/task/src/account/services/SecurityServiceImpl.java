package account.services;

import account.dtos.LogEntryDTO;
import account.mappers.LogEntryMapper;
import account.repositories.LogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    private LogEntryRepository logEntryRepository;
    private LogEntryMapper logEntryMapper;

    public SecurityServiceImpl(LogEntryRepository logEntryRepository, LogEntryMapper logEntryMapper) {
        this.logEntryRepository = logEntryRepository;
        this.logEntryMapper = logEntryMapper;
    }

    @Override
    public List<LogEntryDTO> getAllEvents() {
        return logEntryMapper.toDtos(logEntryRepository.findAll());
    }
}

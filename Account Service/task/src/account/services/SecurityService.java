package account.services;

import account.dtos.LogEntryDTO;

import java.util.List;

public interface SecurityService {
    List<LogEntryDTO> getAllEvents();
}

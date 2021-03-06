package ua.com.owu.service.managerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.owu.dao.ManagerDAO;
import ua.com.owu.models.Manager;
import ua.com.owu.service.managerService.ManagerService;

@Service
@Transactional
public class ManagerServiceImp implements ManagerService {
    @Autowired
    ManagerDAO managerDAO;

    @Override
    public Manager findByFirstName(String firstname) {
        return managerDAO.findByFirstName(firstname);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return managerDAO.findByUsername(username);
    }
}

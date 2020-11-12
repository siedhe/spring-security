package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.dao.RoleDaoImpl;
import web.model.Role;

import java.util.List;

@Repository
public class RoleServiceImpl implements RoleService{

    private RoleDaoImpl roleDao;

    @Autowired
    public void setRoleDao(RoleDaoImpl roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}

package seyfa.afreeplace.managers;

import seyfa.afreeplace.exceptions.ManagerException;

public interface IManager<Obj, Id> {

    Obj find(Id id)         throws ManagerException;

    void create(Obj obj)    throws ManagerException;

    void update(Obj object) throws ManagerException;

    void delete(Id id) throws ManagerException;

}
